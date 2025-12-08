# MoneyFlow Data Layer Improvements

## Overview

This document captures the findings from a comprehensive audit of the MoneyFlow data layer and business logic, along with planned improvements.

**Audit Date**: December 2024
**Status**: Fresh start (no existing users)

---

## Table of Contents

1. [Current Architecture Summary](#current-architecture-summary)
2. [Identified Issues](#identified-issues)
3. [Design Decisions](#design-decisions)
4. [Improvement Plan](#improvement-plan)
5. [New Database Schema](#new-database-schema)
6. [Migration Strategy](#migration-strategy)

---

## Current Architecture Summary

### Tech Stack
- **Database**: SQLDelight (Kotlin Multiplatform)
- **DI**: Koin
- **Async**: Kotlin Coroutines + Flow
- **Architecture**: MVVM with Repository pattern

### Current Tables
| Table | Purpose |
|-------|---------|
| `TransactionTable` | Financial transactions |
| `CategoryTable` | Income/expense categories |
| `AccountTable` | Single account storage |
| `MonthlyRecapTable` | Denormalized monthly aggregates |

### Current Files
- Schema: `shared/src/commonMain/sqldelight/com/prof18/moneyflow/db/*.sq`
- Database: `shared/src/commonMain/kotlin/com/prof18/moneyflow/database/`
- Repository: `shared/src/commonMain/kotlin/com/prof18/moneyflow/data/MoneyRepository.kt`
- Domain: `shared/src/commonMain/kotlin/com/prof18/moneyflow/domain/entities/`

---

## Identified Issues

### Critical Issues

#### 1. MonthlyRecapTable ID Generation Bug
**Location**: `Utils.kt:10-14`, `DatabaseHelper.kt:246-250`

```kotlin
// Current - inconsistent digit count
val id = "${dateTime.year}${dateTime.month.ordinal + 1}"
// January 2024 = "20241" (5 digits)
// December 2024 = "202412" (6 digits)
```

**Impact**: Potential sorting issues and ID collisions.

#### 2. Floating-Point Money Storage
**Location**: All `.sq` schema files

```sql
amount REAL NOT NULL  -- Floating point
```

**Impact**: Precision errors in financial calculations (e.g., `0.1 + 0.2 ≠ 0.3`).

#### 3. No Database Indexes
**Impact**: Query performance degrades as data grows.

#### 4. No Foreign Key Constraints
**Location**: `TransactionTable.sq:8`

```sql
categoryId INTEGER NOT NULL,  -- No FK reference
```

**Impact**: No referential integrity, orphaned records possible.

#### 5. Silent Error Swallowing
**Location**: `MoneyRepository.kt:39-40`

```kotlin
allTransactions = dbSource.selectLatestTransactions().catch {
    // Empty catch block!
}
```

### Medium Issues

#### 6. Hardcoded Single Account
**Location**: `DatabaseHelper.kt:126, 160`

```kotlin
id = 1, // no multi-account support for now
```

**Impact**: Difficult to add multi-account support later.

#### 7. Categories Not Customizable
- 34 hardcoded categories in `DefaultValues.kt`
- No CRUD operations for categories
- Not localized (English only)

#### 8. Denormalized Monthly Recap
- Manual accumulation prone to drift
- Complex delete logic to maintain consistency
- Cannot easily recalculate if data corrupts

#### 9. Inconsistent Amount Sign Convention
- Transactions: OUTCOME stored as negative
- MonthlyRecap: OUTCOME stored as positive
- Requires `abs()` calls throughout codebase

#### 10. Manual Date Formatting
**Location**: `Utils.kt:17-21`

```kotlin
"${dateTime.day}/${dateTime.month.ordinal + 1}/${dateTime.year}"
```

**Impact**: Not localized, repeated logic, error-prone.

---

## Design Decisions

Based on project requirements:

| Decision | Choice | Rationale |
|----------|--------|-----------|
| Currency | Formatting only | No conversion needed, simpler implementation |
| Categories | User-customizable | Allow CRUD, distinguish system vs user categories |
| Multi-account | Schema-ready | Add accountId now, implement UI later |
| Monthly recap | Calculated on-demand | Simpler, always accurate, fresh start allows this |
| Money storage | Integer (cents) | Avoid floating-point precision issues |
| Amount convention | Always positive | Type determines sign, cleaner logic |

---

## Improvement Plan

### Phase 1: Database Schema Overhaul

#### 1.1 Fix Money Storage (REAL → INTEGER)
Store all amounts in smallest currency unit (cents).

```sql
-- Before
amount REAL NOT NULL

-- After
amountCents INTEGER NOT NULL  -- 10.50 stored as 1050
```

#### 1.2 Add Foreign Key Constraints

```sql
categoryId INTEGER NOT NULL REFERENCES CategoryTable(id),
accountId INTEGER NOT NULL REFERENCES AccountTable(id),
```

#### 1.3 Add Database Indexes

```sql
CREATE INDEX idx_transaction_date ON TransactionTable(dateMillis DESC);
CREATE INDEX idx_transaction_account ON TransactionTable(accountId);
CREATE INDEX idx_transaction_category ON TransactionTable(categoryId);
CREATE INDEX idx_category_type ON CategoryTable(type);
```

#### 1.4 Remove MonthlyRecapTable
Replace with calculated queries - no separate table needed.

### Phase 2: Category System

#### 2.1 Enhanced Category Schema

```sql
CREATE TABLE CategoryTable (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    type TEXT AS TransactionType NOT NULL,
    iconName TEXT NOT NULL,
    isSystem INTEGER NOT NULL DEFAULT 0,
    sortOrder INTEGER NOT NULL DEFAULT 0,
    isArchived INTEGER NOT NULL DEFAULT 0,
    createdAtMillis INTEGER NOT NULL
);
```

#### 2.2 Category CRUD Operations

- Add: `insertCategory` (user categories only)
- Update: `updateCategory` (name, icon, sortOrder)
- Archive: `archiveCategory` (soft delete, preserve history)
- Reorder: `updateCategorySortOrder`

#### 2.3 System vs User Categories
- System categories: `isSystem = 1`, cannot be deleted/archived
- User categories: `isSystem = 0`, full CRUD

### Phase 3: Multi-Account Readiness

#### 3.1 Account Schema Update

```sql
CREATE TABLE AccountTable (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    currencyCode TEXT NOT NULL DEFAULT 'EUR',
    currencySymbol TEXT NOT NULL DEFAULT '€',
    isDefault INTEGER NOT NULL DEFAULT 0,
    isArchived INTEGER NOT NULL DEFAULT 0,
    createdAtMillis INTEGER NOT NULL
);
```

#### 3.2 Transaction-Account Relationship

```sql
-- TransactionTable gets accountId
accountId INTEGER NOT NULL DEFAULT 1 REFERENCES AccountTable(id),
```

#### 3.3 Code Updates
- Remove hardcoded `id = 1` references
- Add `accountId` parameter to queries
- For now: auto-select default account in UI

### Phase 4: Currency Formatting

#### 4.1 Currency Configuration

```kotlin
data class CurrencyConfig(
    val code: String,      // "EUR", "USD", "JPY"
    val symbol: String,    // "€", "$", "¥"
    val decimalPlaces: Int // 2, 2, 0
)
```

#### 4.2 Amount Formatting Utility

```kotlin
fun Long.formatAsCurrency(config: CurrencyConfig): String {
    val wholePart = this / 100
    val decimalPart = this % 100
    return "${config.symbol}$wholePart.${decimalPart.toString().padStart(2, '0')}"
}
```

### Phase 5: Calculated Monthly Recap

#### 5.1 Remove MonthlyRecapTable
Delete the table entirely.

#### 5.2 Add Aggregation Query

```sql
-- In TransactionTable.sq
selectMonthlyRecap:
SELECT
    SUM(CASE WHEN type = 'INCOME' THEN amountCents ELSE 0 END) AS incomeCents,
    SUM(CASE WHEN type = 'OUTCOME' THEN amountCents ELSE 0 END) AS outcomeCents
FROM TransactionTable
WHERE accountId = :accountId
  AND dateMillis >= :monthStartMillis
  AND dateMillis < :monthEndMillis;
```

#### 5.3 Balance Calculation

```sql
selectAccountBalance:
SELECT
    SUM(CASE WHEN type = 'INCOME' THEN amountCents ELSE -amountCents END) AS balanceCents
FROM TransactionTable
WHERE accountId = :accountId;
```

### Phase 6: Code Cleanup

#### 6.1 Fix Error Handling
Replace empty catch blocks with proper logging.

#### 6.2 Extract Date Utilities
Create proper `DateFormatter` class with localization support.

#### 6.3 Consistent Amount Handling
Always store positive, apply sign based on type.

---

## New Database Schema

### TransactionTable.sq

```sql
import com.prof18.moneyflow.database.model.TransactionType;

CREATE TABLE TransactionTable (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    accountId INTEGER NOT NULL DEFAULT 1 REFERENCES AccountTable(id),
    categoryId INTEGER NOT NULL REFERENCES CategoryTable(id),
    dateMillis INTEGER NOT NULL,
    amountCents INTEGER NOT NULL,
    description TEXT,
    type TEXT AS TransactionType NOT NULL,
    createdAtMillis INTEGER NOT NULL
);

CREATE INDEX idx_transaction_date ON TransactionTable(dateMillis DESC);
CREATE INDEX idx_transaction_account ON TransactionTable(accountId);
CREATE INDEX idx_transaction_category ON TransactionTable(categoryId);

selectLatestTransactions:
SELECT
    T.id,
    T.dateMillis,
    T.amountCents,
    T.description,
    T.type,
    C.name AS categoryName,
    C.iconName
FROM TransactionTable T
INNER JOIN CategoryTable AS C ON T.categoryId = C.id
WHERE T.accountId = :accountId
ORDER BY T.dateMillis DESC
LIMIT :limit;

selectTransactionsPaginated:
SELECT
    T.id,
    T.dateMillis,
    T.amountCents,
    T.description,
    T.type,
    C.name AS categoryName,
    C.iconName
FROM TransactionTable T
INNER JOIN CategoryTable AS C ON T.categoryId = C.id
WHERE T.accountId = :accountId
ORDER BY T.dateMillis DESC
LIMIT :pageSize
OFFSET :offset;

insertTransaction:
INSERT INTO TransactionTable (accountId, categoryId, dateMillis, amountCents, description, type, createdAtMillis)
VALUES (?, ?, ?, ?, ?, ?, ?);

selectTransaction:
SELECT * FROM TransactionTable WHERE id = :transactionId;

deleteTransaction:
DELETE FROM TransactionTable WHERE id = :transactionId;

selectMonthlyRecap:
SELECT
    SUM(CASE WHEN type = 'INCOME' THEN amountCents ELSE 0 END) AS incomeCents,
    SUM(CASE WHEN type = 'OUTCOME' THEN amountCents ELSE 0 END) AS outcomeCents
FROM TransactionTable
WHERE accountId = :accountId
  AND dateMillis >= :monthStartMillis
  AND dateMillis < :monthEndMillis;

selectAccountBalance:
SELECT
    COALESCE(SUM(CASE WHEN type = 'INCOME' THEN amountCents ELSE -amountCents END), 0) AS balanceCents
FROM TransactionTable
WHERE accountId = :accountId;
```

### CategoryTable.sq

```sql
import com.prof18.moneyflow.database.model.TransactionType;

CREATE TABLE CategoryTable (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    type TEXT AS TransactionType NOT NULL,
    iconName TEXT NOT NULL,
    isSystem INTEGER NOT NULL DEFAULT 0,
    sortOrder INTEGER NOT NULL DEFAULT 0,
    isArchived INTEGER NOT NULL DEFAULT 0,
    createdAtMillis INTEGER NOT NULL
);

CREATE INDEX idx_category_type ON CategoryTable(type);

selectAll:
SELECT * FROM CategoryTable
WHERE isArchived = 0
ORDER BY isSystem DESC, sortOrder ASC, name ASC;

selectByType:
SELECT * FROM CategoryTable
WHERE type = :type AND isArchived = 0
ORDER BY isSystem DESC, sortOrder ASC, name ASC;

selectById:
SELECT * FROM CategoryTable WHERE id = :id;

insertCategory:
INSERT INTO CategoryTable (name, type, iconName, isSystem, sortOrder, isArchived, createdAtMillis)
VALUES (?, ?, ?, ?, ?, 0, ?);

updateCategory:
UPDATE CategoryTable
SET name = :name, iconName = :iconName, sortOrder = :sortOrder
WHERE id = :id AND isSystem = 0;

archiveCategory:
UPDATE CategoryTable
SET isArchived = 1
WHERE id = :id AND isSystem = 0;

countTransactionsForCategory:
SELECT COUNT(*) FROM TransactionTable WHERE categoryId = :categoryId;
```

### AccountTable.sq

```sql
CREATE TABLE AccountTable (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    currencyCode TEXT NOT NULL DEFAULT 'EUR',
    currencySymbol TEXT NOT NULL DEFAULT '€',
    currencyDecimalPlaces INTEGER NOT NULL DEFAULT 2,
    isDefault INTEGER NOT NULL DEFAULT 0,
    isArchived INTEGER NOT NULL DEFAULT 0,
    createdAtMillis INTEGER NOT NULL
);

selectDefaultAccount:
SELECT * FROM AccountTable WHERE isDefault = 1 AND isArchived = 0 LIMIT 1;

selectAllAccounts:
SELECT * FROM AccountTable WHERE isArchived = 0 ORDER BY isDefault DESC, name ASC;

selectAccountById:
SELECT * FROM AccountTable WHERE id = :id;

insertAccount:
INSERT INTO AccountTable (name, currencyCode, currencySymbol, currencyDecimalPlaces, isDefault, isArchived, createdAtMillis)
VALUES (?, ?, ?, ?, ?, 0, ?);

updateAccount:
UPDATE AccountTable
SET name = :name, currencyCode = :currencyCode, currencySymbol = :currencySymbol, currencyDecimalPlaces = :decimalPlaces
WHERE id = :id;

setDefaultAccount:
UPDATE AccountTable SET isDefault = CASE WHEN id = :id THEN 1 ELSE 0 END;

archiveAccount:
UPDATE AccountTable SET isArchived = 1 WHERE id = :id AND isDefault = 0;
```

---

## Migration Strategy

Since this is a fresh start with no existing users, the migration is straightforward:

### Step 1: Delete Old Schema Files
Remove all existing `.sq` files.

### Step 2: Create New Schema Files
Add the new schema files as defined above.

### Step 3: Update Database Models

```kotlin
// Currency.kt - Remove or repurpose
// TransactionType.kt - Keep as-is

// Add new model
data class CurrencyConfig(
    val code: String,
    val symbol: String,
    val decimalPlaces: Int
)
```

### Step 4: Update DefaultValues.kt
Add `isSystem = 1`, `sortOrder`, and `createdAtMillis` to default categories.

### Step 5: Update DatabaseHelper.kt
- Remove `MonthlyRecapTable` references
- Update seeding logic for new schema
- Remove hardcoded account ID
- Add calculated balance/recap queries

### Step 6: Update MoneyRepository.kt
- Change `Double` amounts to `Long` (cents)
- Remove monthly recap accumulation logic
- Use new aggregation queries
- Add category CRUD methods

### Step 7: Update Domain Entities

```kotlin
data class MoneyTransaction(
    val id: Long,
    val title: String,
    val icon: CategoryIcon,
    val amountCents: Long,  // Changed from Double
    val type: TransactionTypeUI,
    val milliseconds: Long,
    val formattedDate: String,
)

data class BalanceRecap(
    val totalBalanceCents: Long,  // Changed from Double
    val monthlyIncomeCents: Long,
    val monthlyExpensesCents: Long,
)
```

### Step 8: Update ViewModels and UI
- Format amounts using `CurrencyConfig`
- Parse user input to cents
- Update all amount displays

---

## Implementation Checklist

### Database Layer
- [ ] Create new `TransactionTable.sq` with accountId, FK, indexes
- [ ] Create new `CategoryTable.sq` with isSystem, sortOrder, isArchived
- [ ] Create new `AccountTable.sq` with currency config
- [ ] Delete `MonthlyRecapTable.sq`
- [ ] Update `DatabaseHelper.kt` for new schema
- [ ] Update default categories seeding

### Domain Layer
- [ ] Update `MoneyTransaction` to use `amountCents: Long`
- [ ] Update `BalanceRecap` to use cents
- [ ] Add `CurrencyConfig` data class
- [ ] Update `Category` entity with new fields

### Data Layer
- [ ] Rewrite `MoneyRepository` for calculated aggregations
- [ ] Add category CRUD methods to repository
- [ ] Remove monthly recap accumulation logic
- [ ] Fix error handling (remove empty catch blocks)

### Presentation Layer
- [ ] Add currency formatting utility
- [ ] Update ViewModels to handle cents
- [ ] Add category management ViewModel
- [ ] Update UI to display formatted amounts

### Testing
- [ ] Update unit tests for new schema
- [ ] Add tests for currency formatting
- [ ] Add tests for balance calculation
- [ ] Add tests for category CRUD

---

## Notes

- MonthlyRecapTable is being removed entirely in favor of calculated queries
- All amounts stored as INTEGER (cents) to avoid floating-point issues
- accountId added to TransactionTable now for future multi-account support
- Categories can be archived (soft delete) to preserve transaction history
- System categories (isSystem=1) cannot be modified or deleted by users

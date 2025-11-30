# MoneyFlow
MoneyFlow is a Kotlin Multiplatform personal finance app targeting Android and iOS with Compose Multiplatform.

<div align="center">
  <img src="image/money-flow-light.png">
</div>

## Current direction

- The app is in the middle of a rewrite with an updated architecture to better support Compose Multiplatform and keep the Android/iOS shells thin.
- Expect fast-moving changes while core features are rebuilt.
- The shared module remains the source of truth, and platform-specific code is being pared back to minimal glue.

## Project history

- MoneyFlow originally explored several shared-architecture approaches for KMP + Jetpack Compose + SwiftUI; the ongoing rewrite changes direction to simplify the stack and improve maintainability.
- Legacy articles documenting the earlier architecture remain for context:
  - [Improving shared architecture for a Kotlin Multiplatform, Jetpack Compose and SwiftUI app](https://www.marcogomiero.com/posts/2022/improved-kmm-shared-app-arch/)
  - [Choosing the right architecture for a [new] Kotlin Multiplatform, Jetpack Compose and SwiftUI app](https://www.marcogomiero.com/posts/2020/kmm-shared-app-architecture/)

## Feature roadmap

- ✅ Transaction Entry
- 🏗 Transaction List
- 💭 Edit Transaction
- 💭 Add custom category
- 💭 Recap screen with plots
- 💭 Budgeting feature
- 💭 Import from CSV
- 💭 CSV data export
- 💭 Change currency
- 🏗 Lock view with biometrics

Legend:
- ✅ Implemented
- 💭 Not yet implemented
- 🏗 In progress

## License 📄

```
   Copyright 2020-2022 Marco Gomiero

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

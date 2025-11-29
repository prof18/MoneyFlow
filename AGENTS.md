# Repository Guidelines

## Project Structure & Module Organization
- `shared/`: Kotlin Multiplatform code, including all Compose Multiplatform UI. Platform specifics live in `commonMain`, `androidMain`, and `iosMain`; tests in the matching `*Test` source sets.
- `androidApp/`: Android launcher shell (activities, manifests, resources). Build outputs and keystore settings stay local.
- `iosApp/`: Thin wrapper and Xcode project files; iOS contains only the bare minimum glue.
- `build-logic/`: Gradle convention plugins (Detekt setup, Android/KMP defaults); shared quality configs live in `config/detekt/`.
- Supporting files: `version.properties`, `versioning.gradle.kts`, and `setup.sh` manage versioning and environment setup; images for docs live in `image/`.

## Build, Test, and Development Commands
- `./gradlew :androidApp:assembleDebug` — build the Android APK for local installs/emulators.
- `./gradlew :shared:linkDebugFrameworkIosArm64` (device) or `...IosSimulatorArm64` (simulator) — produce the iOS framework consumed by the Xcode project.
- `./gradlew check` — run unit tests and static analysis for all modules; use before opening a PR.
- `./gradlew detekt` — run Detekt with the repo ruleset (`config/detekt/detekt.yml`).
- Cloud setup: when running in Codex/CI-like agents, run `./setup.sh` once to install Android SDK prerequisites.
- IDEs: Android Studio for Android/Shared modules; Xcode for `iosApp/`. Keep Gradle synced before switching platforms.

## Coding Style & Naming Conventions
- Kotlin style: 4-space indentation; public APIs in PascalCase for types, camelCase for functions/properties; prefer expression-bodied functions when clear.
- Compose Multiplatform: keep composables small and reusable; name UI builders with intent (e.g., `TransactionRow`, `BudgetCard`); keep preview-like helpers in shared code.
- Static analysis: Detekt with formatting and Compose rules is enforced; fix violations rather than disabling rules unless justified in code comments.

## Testing Guidelines
- Shared unit tests live in `shared/src/commonTest`; platform-specific unit tests in `androidUnitTest` and `iosTest`. No UI test suite is present.
- When modifying or adding UI, add screenshot tests in `androidApp/src/androidTest` following existing patterns to keep visual coverage current.
- Run `./gradlew :shared:check` for KMP unit coverage;
- Name tests with intent (e.g., `shouldReturnZeroBalanceWhenEmptyWallet`); group by feature or use-case.

## Commit & Pull Request Guidelines
- Commit messages: concise, imperative. Conventional prefixes (`feat:`, `fix:`, `chore:`, `docs:`) are welcome and match existing history.
- One logical change per commit; include follow-up refactors separately.
- PRs: describe scope, rationale, and testing performed; link issues when applicable. Add screenshots or screen recordings for UI changes on Android and iOS.
- Ensure CI-ready state: `./gradlew check` and `detekt` should pass; note any platform-specific caveats (e.g., missing local config files) in the PR description.

## Platform & Configuration Notes
- do not commit personal provisioning profiles. UI lives in shared Compose code—add iOS code only when absolutely required.
- Versioning is centralized in `version.properties`; adjust via `versioning.gradle.kts` helpers rather than editing app manifests directly.

# AGENTS.md

## Project Overview

MoneyFlow is a Kotlin Multiplatform (KMP) personal finance app targeting Android and iOS with Compose Multiplatform. 
The app is actively being rewritten to keep Android/iOS shells thin while concentrating UI and business logic in the shared module.

## Project Structure & Module Organization

### Module Structure

- `shared/`: All KMP code including Compose UI
- `androidApp/`: Android launcher shell (minimal)
- `iosApp/`: Xcode project wrapper (minimal)
- `build-logic/`: Gradle convention plugins
- `config/`: Detekt and quality configurations


## Build, Test, and Development Commands

### Build Commands

All Gradle commands should use `-q --console=plain` for readable output.

- `./gradlew :androidApp:assembleDebug` -> Build Android app
- `./gradlew test` -> Run all tests for Android & iOS 
- `./gradlew detekt` -> Run static analysis with Detekt for Shared code, Android and Desktop
- `./gradlew recordRoborazziDebug` -> Record new snapshots

### Building for iOS Simulator
To build MoneyFlow for iPhone 17 Pro simulator:
```bash
mcp__XcodeBuildMCP__build_sim_name_proj projectPath: "/Users/mg/Workspace/MoneyFlow/iosApp/MoneyFlow.xcodeproj" scheme: "MoneyFlow" simulatorName: "iPhone 17 Pro"
```
There could be different project path on your machine. Always use the first one. The alternative paths will be:
```bash
mcp__XcodeBuildMCP__build_sim_name_proj projectPath: "/Users/mg/Workspace/tmp/MoneyFlow/iosApp/MoneyFlow.xcodeproj" scheme: "MoneyFlow" simulatorName: "iPhone 17 Pro"
```

To launch MoneyFlow for iPhone 17 Pro simulator:
```bash
mcp__XcodeBuildMCP__launch_app_sim projectPath: "/Users/mg/Workspace/MoneyFlow/iosApp/MoneyFlow.xcodeproj" scheme: "MoneyFlow" simulatorName: "iPhone 17 Pro"
```

### Build Verification Process

IMPORTANT: When editing code, you MUST:
1. Build the project after making changes
2. Fix any compilation errors before proceeding
Be sure to build ONLY for the platform you are working on to save time.

## Handing off

Before handing off you must run `./gradlew detekt` to ensure all checks pass - don't run it if you modified only swift files

## General rules:

- DO NOT write comments for every function or class. Only write comments when the code is not self-explanatory.
- DO NOT write tests unless specifically told to do so.
- DO NOT excessively use try/catch blocks for every function. Use them only for the top caller or the bottom callers, depending on the cases.
- ALWAYS run gradle tasks with the following flag: `--quiet --console=plain`

### Git Commit Messages
When creating commits:
- Use simple, one-liner commit messages
- DO NOT include phase numbers (e.g., "Phase 1", "Phase 2")
- DO NOT add "Generated with Claude Code" attribution
- DO NOT add "Co-Authored-By: Claude" attribution
- Example: `git commit -m "Add foundation for unified article parsing system"`

## Testing

- Unit tests: `shared/src/commonTest/` (cross-platform), `androidUnitTest/`, `iosTest/`
- Screenshot tests: Use Roborazzi, run `recordRoborazziDebug` to update snapshots
- When modifying UI composables, search for related tests and update snapshots

## CI/CD Notes

- `./setup.sh` installs Android SDK if you are an agent running on a sandbox environment (Codex Cloud, Claude Code Web).
DO NOT run it on local-machine.

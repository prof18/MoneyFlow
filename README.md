# MoneyFlow
A money management Android and iOS app wrote with Kotlin Multiplatform, Jetpack Compose and Swift UI.

Coming soon: Desktop App (with Compose for Desktop).

<div align="center">
  <img src="image/money-flow-light.png">
</div>

MoneyFlow comes also in dark 🌃:

<div align="center">
  <img src="image/money-flow-dark.png">
</div>

## Features roadmap 

🚧 This project is a work in progress, some features are missing and they will arrive in the future.

- ✅ Transaction Entry
- 🏗 Transaction List 
- 💭 Edit Transaction
- 💭 Add custom category
- 💭 Recap screen with plots 
- 💭 Budgeting feature  
- ✅ Database import and export
- ⚙️ Local database backup/restore
- 💭 Import from CSV
- 💭 Change currency
- 🏗 Lock view with biometrics

Legend:
- ✅ Implemented
- 💭 Not yet implemented, still in my mind!
- 🏗 Working on it


## How to build:

In order to build the iOS project, you will need to add a `Config.xcconfig` file inside the [iosApp/Assets](https://github.com/prof18/MoneyFlow/tree/main/iosApp/Assets) folder, with the content of the [Config.xcconfig.template](https://github.com/prof18/MoneyFlow/blob/main/iosApp/Assets/Config.xcconfig.template) file. 

### Backups

Database backup and restore are available from the Settings screen on both platforms without any external sync provider.

## Further Readings

I wrote some articles about all the decisions and the thoughts that I’ve made to come up with an architecture for MoneyFlow.

- [Improving shared architecture for a Kotlin Multiplatform, Jetpack Compose and SwiftUI app](https://www.marcogomiero.com/posts/2022/improved-kmm-shared-app-arch/)
- [Choosing the right architecture for a [new] Kotlin Multiplatform, Jetpack Compose and SwiftUI app](https://www.marcogomiero.com/posts/2020/kmm-shared-app-architecture/)

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

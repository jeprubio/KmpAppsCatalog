This is a **Kotlin Multiplatform** project targeting Android, iOS.

The app uses a different theme for android and iOS and some elements have a look and feel according to the OS (toolbar, buttons & circular progress)

Android:

<img src="screenshots/android-01-home.png" width="200" alt="Android Home" />  <img src="screenshots/android-02-home-list-filtered.png" width="200" alt="Android Home List Filtered" />  <img src="screenshots/android-03-details.png" width="200" alt="Android Details" />

iOS:

<img src="screenshots/ios-01-home.png" width="200" alt="iOS Home" />  <img src="screenshots/ios-02-home-list-filtered.png" width="200" alt="iOS Home List Filtered" />  <img src="screenshots/ios-03-details.png" width="200" alt="iOS Details" />

https://github.com/jeprubio/KmpAppsCatalog/assets/13270085/31977e2a-b2fa-4a1f-b9a0-7993834fcc9b

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

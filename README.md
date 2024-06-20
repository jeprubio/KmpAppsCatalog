This is a **Kotlin Multiplatform** project targeting Android, iOS.

The app uses a different theme for android and iOS and some elements have a look and feel according to the OS (toolbar, buttons & circular progress)

Android:

![androidCatalog](https://github.com/jeprubio/KmpAppsCatalog/assets/13270085/744f8143-227a-44ce-a830-685bb3cabe03)
![androidDetails](https://github.com/jeprubio/KmpAppsCatalog/assets/13270085/8321ce0f-bdb2-4e4d-a8c9-2d5e0bb3b17d)

iOS:

![iosCatalog](https://github.com/jeprubio/KmpAppsCatalog/assets/13270085/8b3e618c-c8ec-452c-91d6-029f71395340)
![iosDetails](https://github.com/jeprubio/KmpAppsCatalog/assets/13270085/a4e02079-53eb-4d89-bff7-2cabd23e7cdb)

https://github.com/jeprubio/KmpAppsCatalog/assets/13270085/31977e2a-b2fa-4a1f-b9a0-7993834fcc9b

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

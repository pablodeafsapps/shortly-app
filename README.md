# Shortly App

**Shortly App** is a sample Android project based in a *Clean Architecture* implementation using some of the latest state-of-the-art features.


## Tech Stack

In an attempt of using some of the most remarkable and robust APIs and frameworks, this app makes use of:
- [Jetpack Compose UI](https://developer.android.com/jetpack/compose)
- [MVVM](https://developer.android.com/topic/architecture?gclsrc=ds) for the presentation layer of any available feature
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/flow.html) for multithreading
- [Retrofit](https://square.github.io/retrofit) for network operations
- [Room](https://developer.android.com/training/data-storage/room) for data persistence
- Vanilla [Dagger 2](https://developer.android.com/training/dependency-injection/dagger-android) for Dependency Injection
- Includes [Unit Tests](https://developer.android.com/training/testing/local-tests) and [Instrumented Tests](https://developer.android.com/training/testing/instrumented-tests)
- The whole app has been developed with an eye on [SOLID Principles](https://medium.com/android-news/android-development-the-solid-principles-3b5779b105d2)


## Versions and Compatibility

The app has been developed for:
```kotlin
Kotlin 1.6.10

Android
    compileSdk 33
    minSdk 21
    targetSdk 33
```


## License
This project is subject to the MIT License. Checkout [LICENSE.md](./LICENSE.md)


## Maintainers
This project is mantained by:
* [Pablo L. Sordo Martínez](http://github.com/pablodeafsapps)


## Contributing
1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
4. Push your branch (git push origin my-new-feature)
5. Create a new Pull Request

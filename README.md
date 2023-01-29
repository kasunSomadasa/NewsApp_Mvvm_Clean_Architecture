
# The News App - Mvvm Clean Architecture

The NewsApp - App consuming a [News API](https://newsapi.org) to display news details. it has been built with clean architecture principles and MVVM pattern.

This app shows the usage of the new Navigation Architecture Component in collaboration with the Bottom Navigation view and this codebase also includes unit testing.


## App features:

 - List of Latest News
 - Save favorite news
 - Search for news
- Details of the News

## Screenshots:
<p align="center"><kbd><img src="https://user-images.githubusercontent.com/32154905/215327933-5c1b57a1-de85-4c8b-835a-bfaabd177b8e.jpg" width="320"></kbd>&nbsp;&nbsp;&nbsp;&nbsp;<kbd><img src="https://user-images.githubusercontent.com/32154905/215327935-f7c23604-e961-46e6-8d81-2cc7ed43c76f.jpg" width="320"></kbd><p>

## Architecture:
Uses concepts of the notorious Uncle Bob's architecture called [Clean Architecture.](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

 - Better separation of concerns. Each module has a clear API. Feature related classes life in different modules and can't be referenced without explicit module dependency.
 - Features can be developed in parallel eg. by different teams
 - Each feature can be developed in isolation, independently from other features
- Faster compile time
<p align="center"><br><img src="https://user-images.githubusercontent.com/32154905/215327633-ddad324e-29ac-4aaf-9207-cac798b925b0.png" width="1020"><p>

## Tech stack

- [Kotlin](https://kotlinlang.org/) - Programming language
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
- [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) - Flow is used to pass (send) a stream of data that can be computed asynchronously.
- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Used to handle gradle dependencies and config versions.
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - For reactive style programming (from VM to UI).
- [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Used get lifecyle event of an activity or fragment and performs some action in response to change.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
- [Room](https://developer.android.com/training/data-storage/room) - Used to create room db and store the data.
- [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Used to navigate between fragments.
- [Data Binding](https://developer.android.com/topic/libraries/data-binding) - Used to bind UI components in your XML layouts.
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - Used to load and display pages of data from a larger dataset.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- [Retrofit](https://github.com/square/retrofit) - Used for REST api communication.
- [OkHttp](https://square.github.io/okhttp/) - HTTP client that's efficient by default: HTTP/2 support allows all requests to the same host to share a socket
- [Glide](https://bumptech.github.io/glide/) - Glide is a fast and efficient image loading library for Android
- [Junit](https://developer.android.com/training/testing/local-tests) - Used as a unit testing framework
- [Truth](https://truth.dev/) - Fluent assertions for Java and Android

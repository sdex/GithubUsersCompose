## Android application that displays a list of GitHub users and their details across two screens.

The app is written on Kotlin. 
The app architecture has three layers: a data layer, a domain layer and a UI layer.
The architecture follows a reactive programming model with unidirectional data flow.
The data flow is achieved using streams, implemented using Kotlin Flows.

## Data layer

The data layer is implemented as an offline-first source of app data and business logic. 
It is the source of truth for the list of the users.
The details of users is cached using OkHttp cache for demonstration purposes. 

## Domain layer

The domain layer contains use cases.

## UI Layer

The UI layer comprises:
- UI elements built using Jetpack Compose
- Android ViewModels

## The app uses the next libraries: 

- Jetpack Compose for UI
- Jetpack Navigation
- Coroutines/Flow for background execution
- Retrofit and OkHttp for networking
- Coil for image loading
- Koin for dependency injection
- Room to store data for offline access
- Jetpack Paging for pagination

## Run application

You need to set your own Github API token in the `AuthorizationInterceptor` class. 

## Download application

Note: It expires on Mon, Apr 8 2024. \
[Download apk file](https://github.com/sdex/GithubUsersCompose/raw/main/apk/Github-app-release.apk)

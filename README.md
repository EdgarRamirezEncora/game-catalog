<p align="center">
  <a href="" rel="noopener">
 <img width=200px height=200px src="./assets/game_icon.jpeg" alt="Project logo"></a>
</p>

<h3 align="center">Game Catalogue</h3>

## 📝 Table of Contents

- [About](#about)
- [Getting Started](#getting_started)
- [Usage](#usage)
- [Built Using](#built_using)
- [Authors](#authors)

## 🧐 About <a name = "about"></a>

Game catalogue is an Android app, which allows you to search information about your favourite videogames. The information about the videogames is retrieved from [RAWG API](https://rawg.io/apidocs) and displayed in the app.

## 🏁 Getting Started <a name = "getting_started"></a>

* Install Android Studio, which will help you to setup the environment to run the application.
* Clone this repository to you computer.
* Open the project in Android Studio.
* Run the app

![Android studio](./assets/android_studio.png)

### Prerequisites

- Gradle
- Android Studio
- Kotlin SDK

## 🎈 Usage <a name="usage"></a>

### Home screen

In the home screen you will find a list of the videgames that are available in the catalogue. Each videogame is displayed in its own card, which contains an image and the name of the videogame.

If you select the card, you will navigate to the detail screen, which will show you the information about the videogame you selected.

![home screen](./assets/home_screen.png)

The home screen contains a search bar to find an specific videogame and videogames with similar names.

![search bar](./assets/searchbar.png)

The game catalog must have internet connection to retrieve the videogames and their information, that's why if there is no internet connection, you won't be able to use the app.

![No internet connection](./assets/no_connection.png)

### Detail screen

In the detail screen you will find information (name, image, metacritics score, description, website) about an specific videogame.

![Detail view](./assets/detail_screen.png)


## ⛏️ Built Using <a name = "built_using"></a>

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - UI
- [Kotlin](https://kotlinlang.org/) - Server Environment
- [Dagger Hilt](https://dagger.dev/hilt/) - Dependency Injection
- [Retrofit](https://github.com/square/retrofit) - HTTP Client

## ✍️ Authors <a name = "authors"></a>

- [@EdgarRamirezFuentes](https://github.com/EdgarRamirezFuentes)
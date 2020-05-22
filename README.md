# Tic Tac Toe
![Tic tac toe illustration](https://raw.githubusercontent.com/stephane-genicot/katas/master/images/Kata_TicTacToe.png)

## About this Kata

This short and simple Kata was performed using the **Test Driven Development** principle (TDD).

## Rules

The rules are described below :

- X always goes first.
- Players cannot play on a played position.
- Players alternate placing X’s and O’s on the board until either:
	- One player has three in a row, horizontally, vertically or diagonally
	- All nine squares are filled.
- If a player is able to draw three X’s or three O’s in a row, that player wins.
- If all nine squares are filled and neither player has three in a row, the game is a draw.

## Build it, run it

1. Clone this repository
```sh
git clone git@github.com:2020-DEV-065/TicTacToe.git
```
2. You can execute the build tasks available to this Android project using the Gradle wrapper command line tool.  
It's available as a batch file, accessible from the root of the project.
```sh
cd ./TicTacToe
```
3. Make sure Gradle can find your Java and Android SDK's installations
```sh
export JAVA_HOME=<path to Java>
export ANDROID_HOME=<path to Android SDK location - tested with Platform versions 21 & 29>
```
4. Run the build
```sh
./gradlew build
```
5. The Android application package (APK) has now been built and deposited into the build directory 
```sh
cd ./app/build/outputs/apk/debug
```
6. Install the package into your device
```sh
adb -s <serial of device> install app-debug.apk
```
7. Run
```sh
adb -s <serial of device> shell am start -n com.vanzoconsulting.tictactoe/com.vanzoconsulting.tictactoe.ui.BoardActivity
```

## Architecture

This small application's choice of conception showcases, with a very simple (but hopefully clear) a pragmatic and simplistic approach, Uncle Bob's Clean Architecture which is a craftsman's guide to software structure and design.
Such an architecture allows decoupling different units of the code in an organized manner. That way the code gets easier to understand, modify, extend and test.

The ultimate goal of any software architecture is to minimize the human resources required to build and maintain the required system.
Cost per line increases dramatically if code is a mess. So the only way to go fast, is to go well.

### The layers
There are lots of different choices of conception from different perspectives based on the application's general complexity, business rules, the lifespan and the size of the software and also on the team. 
But for clarity and simplicity, I'm sticking to 5 layers.

1. **Presentation**

Following the [MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) (Model View Presenter) pattern, it’s the layer that interacts with the UI consisting mostly of Android UI (activities, fragments, views) and presenters.
Due to the game's simplicity, the main structure is simple :
* `BoardActivity` taking care of the pure UI part dictated by the Android framework
* `BoardPresenter` respecting `BoardContract` being responsible to act as the middleman between view and model.  
It retrieves data from the model and returns it to the view
* `BoardLayout` extending Jetpack's [`GridLayout`](https://developer.android.com/jetpack/androidx/releases/gridlayout) in order to draw lines and columns divider for a better representation of Tic Tac Toe's Cartesian plan

2. **Use cases**

This layer encompasses mainly the actions that the user can trigger. Those can be active actions (the user clicks on a the board) or implicit actions (reloading the app thus recovering the existing session).

The module's composition is straightforward:
* 'DeleteBoard'
* 'GetBoard'
* 'SaveBoard'

These classes naming speak for themselves. We have the possibility

3. **Domain**

You'll find basically the business logic in this package, in other words, [the rules](#rules) of the game.  
Ideally, it should be the biggest layer, though Android Apps mostly tend to just draw an API in the screen of a phone, so most of the core logic will just consist of requesting and persisting data. 

In our case of sampling, I kept the business models quiet simple:
* `Player` symbolized by **X** or **O**.
* `Board` upon which players mark their spots. The little magic behind this [`data class`](https://kotlinlang.org/docs/reference/data-classes.html) relies on a simple array of 9 indexes representing the board cells. Each cell's occupation is regulated by this class.

4. **Data**

In this layer gives an abstract definition of the different data sources, and how they should be used.  
You will normally use a repository pattern that is able to decide where to find the information for a given request.

You would save your data locally and recover it from the network in a typical application. So this layer's component would check whether the data is in a local database and serve it or synchronize with a distant backend server through an API.  
But your data does not only come from a request. It might arise from the device sensors, or from a BroadcastReceiver even though the data layer should never know about this concept!

Let's get back to our elementary case and discover the following structure:
* `BoardRepository` loading, saving and deleting game sessions.
* `BoardPersistenceSource` providing the repository with a source of storage by abstracting the access to this source following the dependency inversion principle of [SOLI**D**](https://es.wikipedia.org/wiki/SOLID). So instead of depending on the specific implementation, we’re going to depend on an abstraction (an interface).

5. **Framework**

Framework basically encapsulates the interaction with the framework, so that the rest of the code can be agnostic and reusable in another platform which is a real option with [Kotlin multi-platform](https://kotlinlang.org/docs/reference/multiplatform.html) projects!  
Framework only refers to the Android framework in this case, but with further features involving external libraries, we can expand it in order to interact with those SDKs.

Remember that the data layer needs to persist the current game, so the framework layer will provide a `BoardPersistenceSource` concretization called `SharedPrefsPersistenceSource`, which as the name indicates it, supports data storage with [`SharedPreferences`](https://developer.android.com/training/data-storage/shared-preferences) API.  
Our architecture allows us to amplify business entities persistence by providing a [Room](https://developer.android.com/topic/libraries/architecture/room) implementation for example. Or if it needs to do a request, we would use [Retrofit](https://square.github.io/retrofit/).

### Interaction between layers
The presentation relies on the use cases layer, which will then use the data layer to access the domain entities, which will finally use the framework to get access to the requested data. Then this data flies back to the layer structure until it reaches the presentation layer updating the UI. 

This would be a simple graph of the flow:  

![layers interactions](readme/clean-architecture-interaction.png)

The two edges of the flow depend on the [framework](#framework), using the Android dependency, while the rest of the layers only require Kotlin. Which helped me to divide each layer into a separate module thanks to Gradle's [multi-project builds](https://guides.gradle.org/creating-multi-project-builds/). If I was to reuse the same code for a Web App, I’d just need to reimplement the presentation and framework layers.

It's important to not mix the flow of the application with the direction of the dependencies between layers :

<p class="rich-diff-level-zero" align="center">
  <img width="400" height="400" src="https://raw.githubusercontent.com/2020-DEV-065/TicTacToe/master/readme/clean-architecture-layers.png" alt="layers interactions" style="max-width:100%;">
</p>

Our architecture stipulates that that the inner layers shouldn’t know about the outer ones. This indicates that an outer class can have an explicit dependency from an inner class, but not the other way round.
The presentation layer has a Use Case dependency, and it’s able to call to start the flow. Then the Use Case has a dependency to the domain.

For simplification purposes, the presentation and framework layers form the `app` module while every other layer was developed in its own module.

## Screens

Given the fact that this challenge is not about demonstrating the developer's sense of design nor the ability to integrate complex designs, I remained faithful to the [KISS principle](https://en.wikipedia.org/wiki/KISS_principle) (keep it simple, stupid).

You'll find the different visual states of the game in the screenshots below:
| | | |
|:-------------------------:|:-------------------------:|:-------------------------:|
|<img width="241" height="429" alt="screen shot draw game" src="https://raw.githubusercontent.com/2020-DEV-065/TicTacToe/master/readme/screenshot_start.png"></br>Fig.3 - Game starts|<img width="241" height="429" alt="screen shot game in progress" src="https://raw.githubusercontent.com/2020-DEV-065/TicTacToe/master/readme/screenshot_progress.png"></br>Fig.4 - Game in progress|<img width="241" height="429" alt="screen shot game finishes with winner" src="https://github.com/2020-DEV-065/TicTacToe/blob/master/readme/screenshot_winner_x.png"></br>Fig.5 - X wins|
| <img width="241" height="429" alt="screen shot game finishes with winner" src="https://raw.githubusercontent.com/2020-DEV-065/TicTacToe/master/readme/screenshot_winner_o.png"></br>Fig.6 - O wins|<img width="241" height="429" alt="screen shot of a draw" src="https://raw.githubusercontent.com/2020-DEV-065/TicTacToe/master/readme/screenshot_draw.png"></br>Fig.7 - Draw|


## One step further

Constrained by time limits, my current injury, the context of this development process resulting from a code kata and its scope which is not meant to evolve, I opted to leave out some of the aspects of my programming habits that I ususally apply in the enterprise World. 

Here is a non-exhaustive list of areas to improve:
* **One model representation per layer**
I'd use data transformations to convert it through different layers. That makes layers less coupled, but also everything more complex.
* [**Configuring dagger for each module**](https://developer.android.com/training/dependency-injection/dagger-multi-module)
* **Adopting the behavior-driven development principle (BDD)**
It's an [Agile software development](https://en.wikipedia.org/wiki/Agile_software_development) process that encourages collaboration among developers, QA and non-technical or business participants in a software project.
In parallel with TDD I usually make sure to maximize the test coverage following [The Testing Pyramid](https://developer.android.com/training/testing/fundamentals#write-tests) scheme.
* **Keeping `git` history in accordance with user stories**  
To demonstrate the respect of this code kata's main instruction (TDD), I committed every step of my development cycle relying on [code iterativity](https://developer.android.com/training/testing/fundamentals#create-test-iteratively) despite my desire for atomic commits, each entree of which must provide a working change.  
Any roll back operation of the application to a state before the feature was added, shouldn't involve multiple commit entries, to avoid any confusion.  
It's also preferable to squash a feature branch's commits as a whole before merging it to `master`.
* **[Extend `Truth`](https://truth.dev/extension.html) assertions**  
By writing reusable custom `Subject` for making tests more verbose and more readable.
* [**Set up continuous integration**](https://developer.android.com/studio/projects/continuous-integration)
This code kata requires focusing on writing the best code a developer can produce.  
From the moment the additional features suggested in the following section, come into play, CI incorporation into the code repository becomes a must.
This development practice consists of integrating code into a shared repository frequently, preferably several times a day. Each integration can then be verified by an automated build and automated tests. While automated testing is not strictly part of CI it is typically implied.
Most of the time I configure it in 4 stages:
  * test & [coverage](https://www.eclemma.org/jacoco/)
  * [quality](https://www.sonarqube.org/)
  * build
  * distribute
* **Proper exception handling and error reporting**
  
## Nice to haves

The established architecture makes it easier to extends this application's functionalities to give the user a better experience.
We can imagine the following features aiming to engage the audience:
* Establishing the current player's definition as an actual user and giving it a profile
* Keeping record of each stage of the game for a replay section
* Saving game results and ranking players
* Sharing the game's outcome
* Possibility to play against the CPU in different difficulty levels
* Competing online against other players

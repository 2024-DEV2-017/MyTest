# TicTacToe Code Kata

## Compile and install

To compile and install the app, you can use Android Studio or the command line.
This guide assumes you have a working JDK environment as well as the Android SDK installed and properly configured.

### Using the command line (recommended)

1. Open a terminal.
2. Navigate to the project directory.
3. Connect your device or start an emulator.
4. Run `./gradlew installDebug`

### Using Android Studio

1. Open the project in Android Studio.
2. Click on the green play button in the top right corner.
3. Select the device/emulator you want to install the app on.

The app will then be installed on your device/emulator.

## Run tests

### JUnit tests
Simply run `./gradlew test`

### Instrumented tests
Simply run `./gradlew connectedAndroidTest`

## Important Notes

Although I have some experience writing JUnit tests, my past positions were not in a TDD environment, please take this in consideration.
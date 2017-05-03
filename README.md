# Are You Kitty Me

Are You Kitty Me is a multi-purpose android app combining productivity, fitness, as well as vocab studying, that integrates a cat feeding system to motivate the user to complete his/her goals on a daily basis.
## Files

```
AreYouKittyMe/
+-- src/
    +-- androidTest/
    +-- main/
    +-- test/
design/
dist/
docs/
scrum/
```

### Description
* `AreYouKittyMe/` contains the buildable Android project
* `src/` contains the android project files with tests and resources
* `design/` contains CRC cards, Use-case diagram, and UML for the project
* `dist/` contains the generated and runnable `.apk` file
* `docs/` contains the user manual, the design manual, and the presentation presented in class on December 5, 2016
* `scrum/` contains user stories, backlogs, and task board

## Getting Started

### Prerequisites

* Android Studio, preferably 2.2.+
* Android SDK Build Tools v25.0.0
* Emulator or physical device of Nexus 6P (Our current release is optimized for Nexus 6P. For best user experience, please try the app on Nexus 6P)

### Installation

Following are the two ways you can use our application.

1. Install using `.apk` file in `dist/` folder. 
    * You would need to enable _Install from Unknown Sources_ in the Security Settings on your Android.
    * Download the `.apk` file onto your phone. (You can use USB or upload to cloud storage)
    * Click on the `.apk` file to install it on your phone
2. Import the entire project in [Android Studio](https://developer.android.com/studio/index.html) and download all SDK requirements.
    * Install project dependencies from __Tools > Android > Sync Gradle with Project Files__
    * Run `app` module on an emulator or an Android

## Testing

To test the functionality of our app, two approaches of testing are implementated:
First, we used intrumentation test (`/src/AndroidTest`) to verify the UI functionality of our application. This is realized with [`Espresso`](https://developer.android.com/training/testing/ui-testing/espresso-testing.html) framework by Google.
Secondly, we implemented Junit test ( (`/src/test`)) to make sure our User model functions correctly.

To run the instrumentation test:
* Using the left pane, change the Project view to __Project__ (default)
* Open `app/src/androidTest/`
* Right-click on `androidTest/java/` and click __Run 'All Tests'__
* Select your preferred device to run UI tests on


## Authors
Thanks for the delication and time investment the following amazing people:
* [**Cade Chen**]Computer Engineering '19 <bc029@bucknell.edu>
* [**Haoyu Xiong**]Computer Science and Engineering '19 <hx003@bucknell.edu>
* [**Sarah Xu**]Computer Engineering '18 <ysx001@bucknell.edu>
* [**Jingya Wu**]Computer Science and Engineering '19 <jw057@bucknell.edu>
# Shape Matching Game
This is a classic shape matching game where teh goal is the swap shapes to make 3 in a row.  Once 3 in a row is made, the shapes are removed.  This version of the game never ends as tiles can be shuffled by dragging a finger outside of the grid.  Pausing the game is done by tapping outside the grid.

## Included
All necessary files to run the application using Android Studio using either an actual plugges in device or an emulator.  It was created with Android Studio version 2.3.2. The contents of the build.gradle file are below. It was successfully run in Android Studio with Nexus 5x emulator (API 24).

## build.gradle
```
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.3'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        jcenter()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

```

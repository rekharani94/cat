// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()


    }
    dependencies{
        //classpath ("com.android.tools.build:gradle:8.1.4")
        // classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7")
        //classpath ("com.android.tools.build:gradle:3.3.3")
       // classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:1.8.0")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
        classpath  ("com.squareup.okhttp3:mockwebserver:4.12.0")
    }
}
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.android.library") version "8.1.2" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
}
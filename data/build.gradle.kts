plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "me.amitshekhar.newsapp.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":utils"))

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // OkHttp interceptor
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation ("androidx.room:room-runtime:2.5.0")
    implementation ("androidx.room:room-ktx:2.5.0")
    kapt ("androidx.room:room-compiler:2.5.0")
    implementation ("androidx.room:room-paging:2.6.1")
    implementation ("androidx.paging:paging-common-ktx:3.2.1")
    implementation ("androidx.paging:paging-runtime-ktx:3.3.0")
    implementation ("androidx.paging:paging-compose:3.3.0")
    testImplementation("junit:junit:4.13.2")

    //WorkManager
   // implementation ("androidx.work:work-runtime-ktx:2.9.0")

    // Tools
   // implementation( "com.jakewharton.timber:timber:5.0.1")
/*
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")

    testImplementation("junit:junit:4.13.2")*/
}
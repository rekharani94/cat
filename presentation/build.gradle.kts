plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "me.intuit.cat.presentation"
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
        freeCompilerArgs = listOf("-Xjvm-default = compatibility")
    }
    buildFeatures {
        dataBinding=true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/*"
        }
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":utils"))
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.databinding:databinding-compiler-common:8.4.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.databinding:databinding-common:8.4.1")
    implementation("androidx.databinding:databinding-runtime:8.4.1")
    implementation ("com.google.android.material:material:1.12.0")
    implementation("androidx.compose.material3:material3-android:1.2.1")
    implementation ("com.google.android.material:material:1.12.0")
    implementation("androidx.hilt:hilt-common:1.2.0")
    implementation("androidx.test:core-ktx:1.5.0")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.activity:activity:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.browser:browser:1.7.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.1")
    implementation ("androidx.paging:paging-runtime-ktx:3.3.0")
    implementation ("androidx.paging:paging-compose:3.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation ("androidx.navigation:navigation-ui-ktx:2.3.5")
    //Glide
    implementation( "com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")


    // Use: def instead of val if you are not using Kotlin Gradle(.kts)

    implementation("androidx.core:core-splashscreen:1.0.1")
    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation ("com.squareup.okhttp3:okhttp")
    //circleimage
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //KotlinX Serializer
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
   //gif player
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.28")
    //lottie
    implementation ("com.airbnb.android:lottie:3.6.0")
    //swipetorefreshlayout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    //Mockito
    testImplementation ("org.mockito:mockito-core:5.0.0")
    testImplementation ("org.mockito:mockito-inline:5.0.0")

    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("app.cash.turbine:turbine:0.12.1")
    testImplementation ("com.google.truth:truth:1.1.3")

    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")

    // Dependencies for Android instrumented unit tests
   // androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    // Coroutine test
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")

    testImplementation  ("com.squareup.okhttp3:mockwebserver:4.12.0")
   //expresso
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.3.0")
    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.3.0")
    implementation ("androidx.test.espresso:espresso-idling-resource:3.3.0")
    androidTestImplementation ("androidx.test.espresso:espresso-idling-resource:3.3.0")
    implementation ("androidx.fragment:fragment:1.1.0-alpha03")
    debugImplementation ("androidx.fragment:fragment-testing:1.1.0-alpha03")
    //testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation("android.arch.core:core-testing:1.1.1")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation ("androidx.arch.core:core-testing:2.1.0")
/*
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.browser:browser:1.7.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    */

}
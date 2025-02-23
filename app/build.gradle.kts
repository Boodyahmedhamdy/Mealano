plugins {
    alias(libs.plugins.android.application)
    id("androidx.navigation.safeargs")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "io.github.boodyahmedhamdy.mealano"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.boodyahmedhamdy.mealano"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("com.google.code.gson:gson:2.12.1")

    // room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // room with rxjava
//    implementation("androidx.room:room-rxjava3:$room_version")


     // signin with google

//    implementation ("androidx.credentials:credentials:<latest version>")
//    implementation ("androidx.credentials:credentials-play-services-auth:<latest version>")
//    implementation ("com.google.android.libraries.identity.googleid:googleid:<latest version>")

    implementation("androidx.credentials:credentials:1.5.0-rc01")
    implementation("com.google.android.gms:play-services-auth:21.3.0")

    // optional - needed for credentials support from play services, for devices running
    // Android 13 and below.
    implementation("androidx.credentials:credentials-play-services-auth:1.5.0-rc01")
}
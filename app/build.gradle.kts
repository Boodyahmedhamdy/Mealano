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
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    

    //---------------- Retrofit Started -----------------------
    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("com.google.code.gson:gson:2.12.1")
    // Retrofit with Rxjava
    implementation ("com.squareup.retrofit2:adapter-rxjava3:2.9.0")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //---------------- Retrofit Ended -----------------------



    //---------------- Room Started -----------------------
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // room with rxjava
    implementation("androidx.room:room-rxjava3:$room_version")
    //---------------- Room Ended -----------------------



    //---------------- Rxjava Started -----------------------
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.5")
    //---------------- Rxjava Ended -----------------------


    //---------------- Sign in with google Started -----------------------
     // signin with google
//    implementation ("androidx.credentials:credentials:<latest version>")
//    implementation ("androidx.credentials:credentials-play-services-auth:<latest version>")
//    implementation ("com.google.android.libraries.identity.googleid:googleid:<latest version>")
    implementation("androidx.credentials:credentials:1.5.0-rc01")
    implementation("com.google.android.gms:play-services-auth:21.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.5.0-rc01")
    //---------------- Sign in with google Ended -----------------------
}
plugins {
    id("com.android.application")
    id("com.google.gms.google-services") // Firebase plugin
    kotlin("android") version "1.8.10"   // Kotlin plugin
}

android {
    namespace = "com.example.ques4_mad"
    compileSdk = 35 // API level for compileSdk

    defaultConfig {
        applicationId = "com.example.ques4_mad"
        minSdk = 24  // Minimum SDK version
        targetSdk = 35  // Target SDK version
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.activity:activity:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:20.7.0")
}

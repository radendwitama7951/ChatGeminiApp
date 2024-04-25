plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // Secret Gradle plugin for Env Variable
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

    // Dagger Hilt
    kotlin("kapt")
    id("com.google.dagger.hilt.android")


}

android {
    namespace = "com.example.chatgeminiapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.chatgeminiapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.example.chatgeminiapp.CustomTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.core.ktx)
    implementation(libs.core)
    implementation(libs.androidx.navigation.common.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Compose
    // Constraint Layout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    // System bar
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.35.0-alpha")

    // Markdown compose
//    implementation("com.github.jeziellago:compose-markdown:0.4.1")
    implementation("com.yazantarifi:markdown-compose:1.0.4")

    // Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")

    // add the dependency for the Google AI client SDK for Android
    implementation("com.google.ai.client.generativeai:generativeai:0.3.0")

    // From Ahmed Guedmioui
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.5")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    // Coil
    implementation("io.coil-kt:coil:2.6.0")

    // Image cropper
    implementation("com.vanniktech:android-image-cropper:4.3.3")

    // Testing
    // DaggerHilt InstrumentedTest
    // For instrumented tests.
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    // ...with Kotlin.
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44")
    // For Robolectric tests.
    testImplementation("com.google.dagger:hilt-android-testing:2.44")
    // ...with Kotlin.
    kaptTest("com.google.dagger:hilt-android-compiler:2.44")

    // Runner
//    val androidXTestVersion = "1.12.0"
//    androidTestImplementation ("androidx.test:runner:$androidXTestVersion")
//    androidTestImplementation ("androidx.test:rules:$androidXTestVersion")

    // Traditional
//    val androidXTestVersion = "1.12.0"
//    val testRunnerVersion = "1.5.2"
//    val testRulesVersion = "1.5.0"
//    val testJunitVersion = "1.1.5"
    val truthVersion = "1.5.0"
//    val espressoVersion = "3.5.1"
//
//    // Core library
//    androidTestImplementation("androidx.test:core:$androidXTestVersion")
//
//    // AndroidJUnitRunner and JUnit Rules
//    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
//    androidTestImplementation("androidx.test:rules:$testRulesVersion")
//
//    // Assertions
//    androidTestImplementation("androidx.test.ext:junit:$testJunitVersion")
    androidTestImplementation("androidx.test.ext:truth:$truthVersion")
//
//    // Espresso dependencies
//    androidTestImplementation( "androidx.test.espresso:espresso-core:$espressoVersion")
//    androidTestImplementation( "androidx.test.espresso:espresso-contrib:$espressoVersion")
//    androidTestImplementation( "androidx.test.espresso:espresso-intents:$espressoVersion")
//    androidTestImplementation( "androidx.test.espresso:espresso-accessibility:$espressoVersion")
//    androidTestImplementation( "androidx.test.espresso:espresso-web:$espressoVersion")
//    androidTestImplementation( "androidx.test.espresso.idling:idling-concurrent:$espressoVersion")
//
//    // The following Espresso dependency can be either "implementation",
//    // or "androidTestImplementation", depending on whether you want the
//    // dependency to appear on your APK"s compile classpath or the test APK
//    // classpath.
//    androidTestImplementation( "androidx.test.espresso:espresso-idling-resource:$espressoVersion")


}

// 2. Optionally configure Secret Gradle plugin
secrets {
    // Optionally specify a different file name containing your secrets.
    // The plugin defaults to "local.properties"
    propertiesFileName = "local.properties"
    defaultPropertiesFileName = "local.properties"

    // Add keys that the plugin should ignore from the properties file
    ignoreList.add("keyToIgnore")
    ignoreList.add("ignore*")


}

// Kapt
// Allow references to generated code
kapt {
    correctErrorTypes = true
}
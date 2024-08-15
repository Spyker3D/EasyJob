plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.spyker3d.easyjob.plugins.developproperties")

    // Serialization plugin
    kotlin("plugin.serialization") version "1.9.22"
    id("kotlin-kapt")
}
android {
    namespace = "com.spyker3d.easyjob"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "com.spyker3d.easyjob"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(type = "String", name = "HH_ACCESS_TOKEN", value = "\"${developProperties.hhAccessToken}\"")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

        // Binding
        viewBinding = true
    }
}

val retrofitVersion = "2.9.0"
val koinVersion = "3.2.2"
val coroutineVersion = "1.7.1"
val glideVersion = "4.14.2"
val navigationVersion = "2.7.7"
val roomVersion = "2.6.1"

dependencies {
    implementation(libs.androidX.core)
    implementation(libs.androidX.appCompat)
    // UI layer libraries
    implementation(libs.ui.material)
    implementation(libs.ui.constraintLayout)
    // region Unit tests
    testImplementation(libs.unitTests.junit)
    // endregion
    // region UI tests
    androidTestImplementation(libs.uiTests.junitExt)
    androidTestImplementation(libs.uiTests.espressoCore)
    // endregion

    // Constraint layout
    implementation(libs.ui.constraintLayout)

    // Fragments
    implementation("androidx.fragment:fragment-ktx:1.8.1")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    // Glide
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    annotationProcessor("com.github.bumptech.glide:compiler:$glideVersion")

    // Gson and Json (Serialization)
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Koin (DI)
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-core:$koinVersion")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

}

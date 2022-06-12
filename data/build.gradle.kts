plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        val baseUrl:String by project
        val clientId:String by project
        val clientSecret:String by project
        val grantType:String by project
        val baseUrlCard:String by project

        buildConfigField("String","BASE_URL", baseUrl)
        buildConfigField("String","CLIENT_ID", clientId)
        buildConfigField("String","CLIENT_SECRET", clientSecret)
        buildConfigField("String","GRANT_TYPE", grantType)
        buildConfigField("String","BASE_URL_CARD", baseUrlCard)


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(project(":domain"))

    kapt(libs.androidx.room.compiler)
    implementation(libs.bundles.androidx.room)

    implementation(libs.bundles.androidx.retrofit)

    implementation(libs.koin.android)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso)
}
plugins {
    alias(libs.plugins.android.application)
    // Applique le plugin Google Services : lit app/google-services.json à la compilation
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.mobilite"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.mobilite"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    // Importe la BoM : toutes les bibliothèques Firebase ci-dessous auront des versions compatibles
    implementation(platform(libs.firebase.bom))
    // Ajoute Cloud Firestore (base de données des trajets)
    implementation(libs.firebase.firestore)
    // Ajoute Firebase Authentication (comptes utilisateur et admin)
    implementation(libs.firebase.auth)
}
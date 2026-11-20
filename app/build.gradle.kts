// Fichier de build du module "app" : comment compiler notre application et de quelles bibliothèques elle dépend.

plugins {
    // Applique le plugin Android : ce module produit une application installable (.apk)
    alias(libs.plugins.android.application)
    // Applique le plugin Google Services : lit app/google-services.json à la compilation
    alias(libs.plugins.google.services)
}

android {
    // Nom du package Java utilisé pour générer la classe R (ressources)
    namespace = "com.example.mobilite"
    // Version de l'API Android utilisée pour compiler (la plus récente installée)
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        // Identifiant unique de l'application sur le téléphone (doit correspondre à google-services.json)
        applicationId = "com.example.mobilite"
        // Version minimale d'Android supportée : Android 7.0 (API 24)
        minSdk = 24
        // Version d'Android pour laquelle l'application est conçue et testée
        targetSdk = 37
        // Numéro de version interne (à incrémenter à chaque publication)
        versionCode = 1
        // Numéro de version affiché à l'utilisateur
        versionName = "1.0"

        // Outil qui lance les tests instrumentés (tests exécutés sur un téléphone)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // Optimisation du code en version "release" (réduction de taille)
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        // Version du langage Java utilisée pour compiler notre code
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Bibliothèques d'interface générées avec le projet
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    // Bibliothèques de tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    // Importe la BoM : toutes les bibliothèques Firebase ci-dessous auront des versions compatibles
    implementation(platform(libs.firebase.bom))
    // Ajoute Cloud Firestore (base de données des trajets)
    implementation(libs.firebase.firestore)
    // Ajoute Firebase Authentication (comptes utilisateur et admin)
    implementation(libs.firebase.auth)
    // ÉTAPE 2 : FusedLocationProviderClient pour récupérer les positions GPS
    // (et, à l'étape 3, l'API Activity Recognition)
    implementation(libs.play.services.location)
}

plugins {
    // Plugin para aplicaciones Android
    id("com.android.application")
    // Plugin para integrar Google Play Services y Firebase
    id("com.google.gms.google-services")
    // Plugin para soportar el uso de Kotlin en el proyecto Android
    kotlin("android")
}

android {
    namespace = "com.example.partyapp8" // El espacio de nombres (namespace) del proyecto
    compileSdk = 34 // La versión del SDK de compilación

    defaultConfig {
        applicationId = "com.example.partyapp8" // Identificador único de la aplicación
        minSdk = 27 // La versión mínima del SDK soportada
        targetSdk = 33 // La versión del SDK de destino
        versionCode = 1 // Código de versión de la aplicación
        versionName = "1.0" // Nombre de la versión de la aplicación

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Runner para pruebas instrumentadas
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Desactiva la minificación de código en la versión de lanzamiento
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro" // Archivos de configuración para ProGuard
            )
        }
    }

    buildFeatures {
        viewBinding = true // Habilita View Binding en el proyecto
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11 // Configura la compatibilidad de origen con Java 11
        targetCompatibility = JavaVersion.VERSION_11 // Configura la compatibilidad de destino con Java 11
    }

    kotlinOptions {
        jvmTarget = "11" // Configura la versión de la JVM objetivo para Kotlin
    }
}

dependencies {
    // Dependencias para las bibliotecas de AndroidX y Firebase
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")

    // Dependencias para pruebas unitarias y pruebas instrumentadas
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Dependencias de Firebase utilizando BOM para la gestión de versiones
    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.firebase:firebase-config:22.0.0")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx:21.0.0")
    implementation ("com.google.firebase:firebase-storage:21.0.0")
    implementation ("com.google.firebase:firebase-appcheck-safetynet:16.1.2")

    // Otras dependencias de AndroidX y bibliotecas adicionales
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("com.airbnb.android:lottie:6.4.0")

    // Dependencias para manejo de imágenes con Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")



}



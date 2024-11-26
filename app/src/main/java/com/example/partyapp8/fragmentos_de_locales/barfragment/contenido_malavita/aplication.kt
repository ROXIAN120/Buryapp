package com.example.partyapp8.fragmentos_de_locales.barfragment.contenido_malavita

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.appcheck.ktx.appCheck

/**
 * [MyApp] es la clase de aplicación que extiende [Application].
 * Se utiliza para inicializar Firebase App Check con SafetyNet como proveedor de verificación.
 */
class MyApp : Application() {

    /**
     * Método que se llama cuando la aplicación se crea.
     * Inicializa Firebase App Check utilizando SafetyNet como proveedor de App Check.
     */
    override fun onCreate() {
        super.onCreate()
        // Configurar Firebase App Check para utilizar SafetyNet como proveedor
        Firebase.appCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance()
        )
    }
}
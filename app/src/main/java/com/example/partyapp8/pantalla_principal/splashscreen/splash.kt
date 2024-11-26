package com.example.partyapp8.pantallas_de_registros.pantalla_principal.pantalla_bar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.partyapp8.R
import com.example.partyapp8.pantallas_de_registros.Pantalladeinicio
import com.google.firebase.auth.FirebaseAuth

/**
 * SplashActivity es la actividad de inicio que muestra un video de splash.
 * Después de que el video termina de reproducirse, la aplicación navega a la pantalla de inicio de sesión
 * o a la pantalla principal, dependiendo del estado de autenticación del usuario.
 */
class SplashActivity : AppCompatActivity() {

    // Declaración del VideoView que muestra el video de splash
    private lateinit var videoView: VideoView

    /**
     * Método llamado cuando se crea la actividad.
     * Configura el VideoView y maneja la navegación al finalizar la reproducción del video.
     * @param savedInstanceState Estado de la instancia guardado.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Obtén la vista del VideoView del layout
        videoView = findViewById(R.id.videoView)

        // Ruta del recurso de video para reproducir el splash
        val videoPath = "android.resource://" + packageName + "/" + R.raw.splash_video
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        // Configura el listener para que navegue a la pantalla de inicio de sesión o a la principal dependiendo de la sesión
        videoView.setOnCompletionListener {
            navigateToNextScreen()  // Navegar a la siguiente pantalla cuando el video termine
        }

        videoView.start() // Inicia la reproducción del video
    }

    /**
     * Método llamado cuando la actividad se pausa.
     * Pausa la reproducción del video.
     */
    override fun onPause() {
        super.onPause()
        videoView.pause() // Pausar el video cuando la actividad se pausa
    }

    /**
     * Método llamado cuando la actividad se reanuda.
     * Reinicia la reproducción del video.
     */
    override fun onResume() {
        super.onResume()
        videoView.start() // Reiniciar el video cuando la actividad se reanuda
    }

    /**
     * Método llamado cuando la actividad se destruye.
     * Detiene la reproducción del video y libera los recursos.
     */
    override fun onDestroy() {
        super.onDestroy()
        videoView.stopPlayback() // Detener la reproducción y liberar recursos
    }

    /**
     * Método para navegar a la siguiente pantalla después de que el video termine.
     * Navega a MainActivity4 si el usuario está autenticado, de lo contrario, a Pantalladeinicio.
     */
    private fun navigateToNextScreen() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val nextActivity = if (currentUser != null) {
            MainActivity4::class.java  // Si hay una sesión activa, ve a MainActivity4
        } else {
            Pantalladeinicio::class.java  // Si no hay sesión, ve a Pantalladeinicio
        }

        startActivity(Intent(this, nextActivity))
        finish() // Finaliza la actividad de splash
    }
}
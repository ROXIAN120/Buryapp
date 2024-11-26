package com.example.partyapp8.pantallas_de_registros

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.partyapp8.R
import com.example.partyapp8.pantallas_de_registros.pantalla_principal.pantalla_bar.MainActivity4
import com.example.partyapp8.pantallas_de_registros.pantalla_principal.pantalla_bar.ProviderType
import com.google.firebase.auth.FirebaseAuth

/**
 * Registrarcuenta es una actividad para el registro de usuarios utilizando email y contraseña.
 */
class Registrarcuenta : AppCompatActivity() {

    // Declaración de variables para las vistas y la autenticación
    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button

    /**
     * Método llamado cuando se crea la actividad.
     * Inicializa Firebase Auth y las vistas necesarias para el registro de usuarios.
     * @param savedInstanceState Estado de la instancia guardado.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_email)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Inicializar vistas del layout
        emailEditText = findViewById(R.id.Registro_Gmail)  // Campo de entrada de correo electrónico
        passwordEditText = findViewById(R.id.Registro_Contraseña)  // Campo de entrada de contraseña
        registerButton = findViewById(R.id.Butto_Registro)  // Botón de registro

        // Configurar el listener del botón de registro
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()  // Obtener el correo electrónico ingresado y eliminar espacios
            val password = passwordEditText.text.toString().trim()  // Obtener la contraseña ingresada y eliminar espacios

            // Verificar que los campos no estén vacíos antes de intentar el registro
            if (email.isNotEmpty() && password.isNotEmpty()) {
                registerUser(email, password)  // Llamar al método para registrar al usuario
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()  // Mostrar mensaje de error si los campos están vacíos
            }
        }
    }

    /**
     * Registra un nuevo usuario con email y contraseña en Firebase Authentication.
     * @param email Email del usuario.
     * @param password Contraseña del usuario.
     */
    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Si el registro es exitoso, obtener el usuario actual y navegar a la pantalla principal
                    val user = auth.currentUser
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    showHome(user?.email ?: "", ProviderType.BASIC)
                } else {
                    // Si el registro falla, mostrar un mensaje de error en el log y una alerta
                    Log.w("Registrarcuenta", "createUserWithEmail:failure", task.exception)
                    showAlert("Error en el registro: ${task.exception?.message}")
                }
            }
    }

    /**
     * Muestra un diálogo de alerta en caso de error.
     * @param message Mensaje a mostrar en la alerta.
     */
    private fun showAlert(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Error")  // Título del diálogo
            setMessage(message)  // Mensaje de error
            setPositiveButton("Aceptar", null)  // Botón de aceptar
            create().show()  // Crear y mostrar el diálogo
        }
    }

    /**
     * Navega a la pantalla principal de la aplicación y cierra la pantalla actual.
     * @param email Email del usuario autenticado.
     * @param provider Proveedor de autenticación (ej. BASIC).
     */
    private fun showHome(email: String, provider: ProviderType) {
        startActivity(Intent(this, MainActivity4::class.java).apply {
            putExtra("email", email)  // Pasar el email del usuario a la siguiente actividad
            putExtra("provider", provider.name)  // Pasar el tipo de proveedor de autenticación
        })
        finish()  // Cerrar la actividad actual
    }
}
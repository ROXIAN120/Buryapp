    package com.example.partyapp8.pantallas_de_registros

    import android.content.Intent
    import android.os.Bundle
    import android.util.Log
    import android.view.View
    import android.widget.Button
    import android.widget.EditText
    import android.widget.TextView
    import androidx.appcompat.app.AlertDialog
    import androidx.appcompat.app.AppCompatActivity
    import androidx.constraintlayout.widget.ConstraintLayout
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import com.example.partyapp8.pantallas_de_registros.pantalla_principal.pantalla_bar.MainActivity4
    import com.example.partyapp8.pantallas_de_registros.pantalla_principal.pantalla_bar.ProviderType
    import com.example.partyapp8.R
    import com.google.android.gms.auth.api.signin.GoogleSignIn
    import com.google.android.gms.auth.api.signin.GoogleSignInAccount
    import com.google.android.gms.auth.api.signin.GoogleSignInClient
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions
    import com.google.android.gms.common.api.ApiException
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.GoogleAuthProvider
    import com.airbnb.lottie.LottieAnimationView

    /**
     * Pantalladeinicio es la actividad de inicio de sesión de la aplicación.
     * Permite a los usuarios iniciar sesión mediante correo electrónico/contraseña o Google.
     */
    class Pantalladeinicio : AppCompatActivity() {

        // Declaración de variables de vista y autenticación
        private lateinit var ingresar: Button
        private lateinit var email: EditText
        private lateinit var password1: EditText
        private lateinit var ptaemail: ConstraintLayout
        private lateinit var animation: LottieAnimationView
        private lateinit var googleSignInClient: GoogleSignInClient
        private val GOOGLE_SIGN_IN = 100
        private lateinit var btnGoogleSignIn: Button

        /**
         * Método que se llama cuando se crea la actividad.
         * @param savedInstanceState Estado de la instancia guardado.
         */
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_email)

            // Ajustar los margenes de las vistas según las barras del sistema
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.authlayout1)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            // Inicialización de vistas
            email = findViewById(R.id.Registro_nombre)
            password1 = findViewById(R.id.editTextTextPassword)
            ingresar = findViewById(R.id.Siguiente)
            ptaemail = findViewById(R.id.authlayout1)
            animation = findViewById(R.id.animation)
            btnGoogleSignIn = findViewById(R.id.Icon_google)

            // Configuración del botón de registro
            val btn2: TextView = findViewById(R.id.registrar1)
            btn2.setOnClickListener {
                val intent2 = Intent(this, Registrarcuenta::class.java)
                startActivity(intent2)
            }

            setupGoogleSignIn()  // Configurar inicio de sesión con Google
            setup()  // Configurar el comportamiento de los botones de inicio de sesión
        }

        /**
         * Método llamado cuando la actividad pasa a primer plano.
         */
        override fun onStart() {
            super.onStart()
            ptaemail.visibility = View.VISIBLE  // Mostrar el layout de autenticación
        }

        /**
         * Configura los listeners de los botones para el inicio de sesión.
         */
        private fun setup() {
            title = "Autenticación"  // Título de la actividad

            // Listener para iniciar sesión con correo electrónico y contraseña
            ingresar.setOnClickListener {
                if (email.text.isNotBlank() && password1.text.isNotBlank()) {
                    showTermsAndPrivacyDialog { signInUser() }  // Mostrar diálogo de términos antes de iniciar sesión
                } else {
                    showAlert("Por favor, complete todos los campos antes de continuar.")  // Mostrar alerta si hay campos vacíos
                }
            }

            // Listener para iniciar sesión con Google
            btnGoogleSignIn.setOnClickListener {
                showTermsAndPrivacyDialog { signInWithGoogle() }  // Mostrar diálogo de términos antes de iniciar sesión con Google
            }
        }

        /**
         * Configura el cliente de inicio de sesión de Google.
         */
        private fun setupGoogleSignIn() {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("116819790990-eikil63jt2u8kerb60nqs2ulh4om66d2.apps.googleusercontent.com") // ID de cliente web
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(this, gso)  // Inicializar GoogleSignInClient
        }

        /**
         * Inicia el proceso de inicio de sesión con Google.
         */
        private fun signInWithGoogle() {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN)  // Iniciar actividad para el resultado de inicio de sesión
        }

        /**
         * Maneja el resultado de actividades iniciadas para resultado.
         */
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == GOOGLE_SIGN_IN) {  // Verificar si el resultado es de Google Sign-In
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)  // Obtener cuenta de Google
                    firebaseAuthWithGoogle(account)  // Autenticar con Firebase
                } catch (e: ApiException) {
                    Log.w("Pantalladeinicio", "Google sign in failed", e)
                    showAlert("Error al iniciar sesión con Google")  // Mostrar alerta si falla el inicio de sesión
                }
            }
        }

        /**
         * Autentica al usuario con Firebase utilizando la cuenta de Google.
         * @param account La cuenta de Google del usuario.
         */
        private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        showhome(FirebaseAuth.getInstance().currentUser?.email ?: "", ProviderType.GOOGLE)  // Navegar a la pantalla principal
                    } else {
                        Log.w("Pantalladeinicio", "signInWithCredential:failure", task.exception)
                        showAlert("Error al iniciar sesión con Google")  // Mostrar alerta en caso de fallo
                    }
                }
        }

        /**
         * Muestra un diálogo de términos y condiciones.
         * @param onAccepted Acción a realizar cuando se aceptan los términos.
         */
        private fun showTermsAndPrivacyDialog(onAccepted: () -> Unit) {
            AlertDialog.Builder(this).apply {
                setTitle("Términos y Política de Privacidad")
                setMessage("Debes aceptar los términos y la política de privacidad para continuar.")
                setPositiveButton("Aceptar") { _, _ ->
                    onAccepted()  // Ejecutar acción si se acepta
                }
                setNegativeButton("Cancelar", null)
                setNeutralButton("Leer Términos") { _, _ ->
                    val intent = Intent(this@Pantalladeinicio, TerminosYPrivacidadActivity::class.java)
                    startActivity(intent)  // Navegar a la actividad de términos y privacidad
                }
                setCancelable(true)
                create().show()
            }
        }

        /**
         * Inicia sesión con correo electrónico y contraseña.
         */
        private fun signInUser() {
            animation.visibility = View.VISIBLE  // Mostrar animación de carga
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(), password1.text.toString()).addOnCompleteListener {
                animation.visibility = View.GONE  // Ocultar animación de carga
                if (it.isSuccessful) {
                    showhome(it.result?.user?.email ?: "", ProviderType.BASIC)  // Navegar a la pantalla principal
                } else {
                    showAlert("Se ha producido un error de autenticación de usuario")  // Mostrar alerta en caso de error
                }
            }
        }

        /**
         * Muestra un mensaje de alerta.
         * @param message El mensaje a mostrar.
         */
        private fun showAlert(message: String) {
            AlertDialog.Builder(this).apply {
                setTitle("Error")
                setMessage(message)
                setPositiveButton("Aceptar", null)
                create().show()
            }
        }

        /**
         * Navega a la pantalla principal de la aplicación.
         * @param email Email del usuario autenticado.
         * @param provider Proveedor de autenticación.
         */
        private fun showhome(email: String, provider: ProviderType) {
            startActivity(Intent(this, MainActivity4::class.java).apply {
                putExtra("email", email)
                putExtra("provider", provider.name)
            })
            finish()  // Finalizar la actividad actual
        }
    }
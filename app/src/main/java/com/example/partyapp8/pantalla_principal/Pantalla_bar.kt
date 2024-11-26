package com.example.partyapp8.pantallas_de_registros.pantalla_principal.pantalla_bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.NavigationUI
import com.example.partyapp8.R
import com.example.partyapp8.databinding.ActivityMain4Binding
import com.example.partyapp8.pantallas_de_registros.Pantalladeinicio
import com.google.firebase.auth.FirebaseAuth

// Enumeración para definir el tipo de proveedor de autenticación
enum class ProviderType {
    BASIC,
    GOOGLE
}

/**
 * MainActivity4 es la actividad principal que maneja la barra de navegación y el contenido principal de la aplicación.
 */
class MainActivity4 : AppCompatActivity() {

    // Declaración de variables para la configuración de la barra de navegación y el enlace de vistas
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain4Binding

    /**
     * Método llamado cuando se crea la actividad.
     * Inicializa la barra de navegación, establece el contenido de la vista y maneja la sesión de usuario.
     * @param savedInstanceState Estado de la instancia guardado.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el layout y establecer el contenido de la vista
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain4.toolbar)  // Configurar la barra de herramientas como la barra de acción
        setTheme(R.style.SplashTheme)  // Configuración del tema de splash screen

        // Obtener el correo electrónico del usuario actualmente autenticado
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userEmail = currentUser?.email

        // Establecer el correo electrónico del usuario en el TextView del encabezado de la barra de navegación
        val headerView = binding.navView.getHeaderView(0)
        val userEmailTextView: TextView = headerView.findViewById(R.id.nav_gmail)
        userEmailTextView.text = userEmail

        // Guardar el correo electrónico en SharedPreferences para persistencia de datos
        val sharedPreferences = getSharedPreferences(getString(R.string.Guardar_datos), Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", userEmail ?: "") // Si userEmail es nulo, se guarda una cadena vacía
        editor.apply()

        // Configurar el DrawerLayout y NavigationView
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main4)

        // Configuración de la barra de navegación con los destinos principales
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_resto_bar,
                R.id.nav_discoteca,
                R.id.nav_ayuda_online,
                R.id.nav_datos_personales,
                R.id.nav_cerrarsesion,
                R.id.nav_notificaciones,
                R.id.nav_informacion_legal,
                R.id.nav_invitar_amigos
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Configuración del listener para manejar la selección de ítems en la barra de navegación
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_cerrarsesion -> {
                    // Limpiar datos de inicio de sesión en SharedPreferences
                    val grd = getSharedPreferences(getString(R.string.Guardar_datos), Context.MODE_PRIVATE).edit()
                    grd.clear()
                    grd.apply()

                    // Cerrar sesión de Firebase y regresar a la actividad de inicio
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, Pantalladeinicio::class.java))
                    finish() // Cerrar esta actividad en lugar de llamar a onBackPressed()
                    true
                }
                else -> {
                    // Permite que NavigationUI maneje automáticamente los otros ítems del menú.
                    NavigationUI.onNavDestinationSelected(menuItem, navController) || super.onOptionsItemSelected(menuItem)
                }
            }
        }
    }

    /**
     * Método llamado cuando se presiona el botón de "navegar hacia arriba".
     * @return true si se maneja la navegación hacia arriba correctamente, de lo contrario, delega al método superior.
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main4)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
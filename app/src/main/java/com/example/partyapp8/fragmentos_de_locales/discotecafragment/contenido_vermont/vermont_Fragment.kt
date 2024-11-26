package com.example.partyapp8.fragmentos_de_locales.discotecafragment.contenido_vermont

import android.content.Intent
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import com.example.partyapp8.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.example.partyapp8.databinding.FragmentVermontBinding
import com.example.tarea.MediaAdapterVermont // Asegúrate de tener el MediaAdapterVermont creado

/**
 * [VermontFragment] es un fragmento que muestra contenido relacionado con el local "Vermont".
 * Este fragmento incluye funciones para mostrar videos desde Firebase Storage,
 * interactuar con la base de datos de Firebase, y abrir mapas con la ubicación del local.
 */
class VermontFragment : Fragment() {

    // Variable para almacenar el binding, inicialmente nulo
    private var _binding: FragmentVermontBinding? = null
    private val binding get() = _binding!! // Acceso seguro al binding

    // Handler para programar tareas en el hilo principal
    private val handler = Handler(Looper.getMainLooper())

    // Lista para almacenar las URIs de los videos cargados desde Firebase
    private val mediaList = mutableListOf<Uri>()

    /**
     * Método que se llama para crear e inflar la vista del fragmento.
     * Se utiliza View Binding para acceder a las vistas del layout asociado.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVermontBinding.inflate(inflater, container, false)
        loadFirebaseMedia() // Cargar videos al crear la vista
        return binding.root
    }

    /**
     * Método que se llama después de que la vista ha sido creada.
     * Aquí se configuran las interacciones del usuario y otras funcionalidades.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el botón para abrir la ubicación en Google Maps
        binding.buttonIrAlLocal.setOnClickListener {
            openMaps()
        }

        // Configurar el listener para mostrar el menú de Vermont
        binding.textViewmenu.setOnClickListener {
            findNavController().navigate(R.id.action_nav_vermont_to_nav_vermont_menu)
        }

        // Configurar el listener para mostrar la pantalla de reservas
        binding.imagenDeReserva.setOnClickListener {
            findNavController().navigate(R.id.action_nav_vermont_to_nav_vermont_mesas)
        }

        // Programar la actualización del TextView a "abierto" o "cerrado" basado en la hora actual
        scheduleTextViewUpdates()

        // Configurar el listener para mostrar el fragmento HorarioVermontFragment
        binding.textViewabiertoVermont.setOnClickListener {
            val fragment = HorarioVermont()
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // Reemplazar el FrameLayout con el nuevo fragmento
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            fragmentTransaction.addToBackStack(null) // Agregar a la pila de retroceso
            fragmentTransaction.commit()

            // Hacer visible el contenedor del fragmento
            val container = binding.root.findViewById<FrameLayout>(R.id.fragment_container)
            container.visibility = View.VISIBLE
        }
    }

    /**
     * Método para cargar videos desde Firebase Storage y actualizar el adaptador del ViewPager2.
     */
    private fun loadFirebaseMedia() {
        val storage = Firebase.storage
        val storageRef = storage.reference

        // Cargar videos
        val videoNames = listOf("Publicidad de vermont/videos/publicidad.mp4", "Publicidad de vermont/videos/publicidad2.mp4")
        videoNames.forEach { videoName ->
            val videoRef = storageRef.child(videoName)
            videoRef.downloadUrl.addOnSuccessListener { uri ->
                mediaList.add(uri) // Añadir URI de video
                setupViewPager() // Configurar ViewPager cada vez que se añada un medio
            }.addOnFailureListener {
                Log.e("Firebase", "Error al obtener la URL de descarga para $videoName", it)
            }
        }
    }

    /**
     * Configurar el ViewPager2 con el adaptador adecuado.
     */
    private fun setupViewPager() {
        val adapter = MediaAdapterVermont(mediaList)
        binding.viewPagerVermont.adapter = adapter
    }

    /**
     * Método para abrir Google Maps con la ubicación del local "Vermont".
     */
    private fun openMaps() {
        val gmmIntentUri = Uri.parse("geo:0,0?q=vermont+discoteca")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
            setPackage("com.google.android.apps.maps")
        }
        if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(mapIntent)
        } else {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/17°46'53.2\"S+63°10'53.3\"W/@-17.781456,-63.1840362,17z/data=!3m1!4b1!4m4!3m3!8m2!3d-17.781456!4d-63.1814613?hl=es-419&entry=ttu")))
        }
    }

    /**
     * Método para programar la actualización del estado "abierto" o "cerrado" basado en la hora actual.
     */
    private fun scheduleTextViewUpdates() {
        val timeZone = TimeZone.getTimeZone("America/La_Paz")

        val updateTextView = {
            val currentTime = Calendar.getInstance(timeZone)
            val currentDay = currentTime.get(Calendar.DAY_OF_WEEK)
            val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)

            val isOpenToday: Boolean = when (currentDay) {
                Calendar.SATURDAY -> (currentHour >= 21) || (currentHour < 5)
                Calendar.SUNDAY -> (currentHour < 5) // Considerando que el horario de cierre es hasta las 5:00 AM del domingo
                else -> false
            }

            binding.textViewabiertoVermont.text = if (isOpenToday) "ABIERTO" else "CERRADO"
        }

        val oneMinuteInMillis = 60000L
        handler.postDelayed(object : Runnable {
            override fun run() {
                updateTextView()
                handler.postDelayed(this, oneMinuteInMillis)
            }
        }, oneMinuteInMillis)

        updateTextView()
    }

    /**
     * Método que se llama cuando la vista del fragmento se destruye.
     * Aquí se limpian las referencias al binding y se eliminan los callbacks del Handler.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacksAndMessages(null) // Limpiar los callbacks pendientes cuando se destruya la vista
    }
}
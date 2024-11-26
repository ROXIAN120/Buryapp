package com.example.partyapp8.fragmentos_de_locales.barfragment.contenido_malavita

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
import com.example.partyapp8.databinding.FragmentMalavitaBinding
import com.example.partyapp8.fragmentos_de_locales.discotecafragment.contenido_vermont.HorarioVermont
import com.example.tarea.MediaAdapterMalavita
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors

/**
 * Fragmento que muestra el contenido del local "Malavita", incluyendo eventos, menú, reservas y la reproducción de videos promocionales.
 */
class MalavitaFragment : Fragment() {

    // Variable para almacenar el binding, inicialmente nulo
    private var _binding: FragmentMalavitaBinding? = null
    private val binding get() = _binding!!

    // Adaptador para el RecyclerView de videos
    private var adapter: MediaAdapterMalavita? = null

    // Handler para gestionar las tareas de actualización en el hilo principal
    private val handler = Handler(Looper.getMainLooper())
    private val mediaList = mutableListOf<Uri>()  // Lista de videos de medios a mostrar
    private val executor = Executors.newSingleThreadExecutor()  // Executor para gestionar descargas de videos en segundo plano

    /**
     * Método que se llama para crear e inflar la vista del fragmento.
     * @param inflater Inflador de layout.
     * @param container Contenedor del fragmento.
     * @param savedInstanceState Estado de la instancia guardado.
     * @return Vista inflada.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMalavitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Método que se llama después de que la vista ha sido creada.
     * Configura los botones de navegación, carga los videos de Firebase y gestiona la visibilidad de la animación de carga.
     * @param view Vista creada.
     * @param savedInstanceState Estado de la instancia guardado.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mostrar la animación de carga
        binding.animation.visibility = View.VISIBLE

        // Cargar videos de Firebase y limpiar videos antiguos
        loadFirebaseMedia()
        cleanUpOldVideos()  // Limpiar videos antiguos

        // Configurar listeners de botones para navegación
        binding.buttonIrAlLocal.setOnClickListener { openMaps() }
        binding.textViewmenu.setOnClickListener {
            findNavController().navigate(R.id.action_nav_malavita_to_nav_menu_malavita)
        }
        binding.imagenDeReserva.setOnClickListener {
            findNavController().navigate(R.id.action_nav_malavita_to_nav_mesas_malavita)
        }
        binding.buttonEventosMalavita.setOnClickListener {
            findNavController().navigate(R.id.action_nav_malavita_to_nav_enventos_malavita)
        }

        scheduleTextViewUpdates()

        // Configurar el listener para mostrar el fragmento HorarioMalavitaFragment
        binding.textViewabierto.setOnClickListener {
            val fragment = HorarioMalavitaFragment()
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
     * Carga los videos promocionales desde Firebase Storage.
     * Si los videos ya están en caché, los carga desde allí; de lo contrario, los descarga y guarda localmente.
     */
    private fun loadFirebaseMedia() {
        val storage = Firebase.storage
        val storageRef = storage.reference

        val videoNames = listOf(
            "Publicidad de vermont/videos/publicidad.mp4",
            "Publicidad de vermont/videos/publicidad2.mp4"
        )

        videoNames.forEach { videoName ->
            val videoRef = storageRef.child(videoName)
            val localFile = getLocalVideoFile(Uri.parse(videoName))

            if (localFile.exists() && localFile.length() > 0) {
                mediaList.add(Uri.fromFile(localFile))
                setupViewPager()
            } else {
                videoRef.downloadUrl.addOnSuccessListener { uri ->
                    if (!executor.isShutdown) {
                        executor.execute {
                            if (isAdded) {
                                if (downloadAndSaveVideo(uri, localFile)) {
                                    mediaList.add(Uri.fromFile(localFile))
                                    handler.post { setupViewPager() }
                                } else {
                                    Log.e("MalavitaFragment", "Error al descargar el video $videoName")
                                }
                            } else {
                                Log.d("MalavitaFragment", "Fragmento no agregado, no se realiza la operación")
                            }
                        }
                    } else {
                        Log.w("MalavitaFragment", "Executor has been shut down, task rejected.")
                    }
                }.addOnFailureListener {
                    Log.e("Firebase", "Error al obtener la URL de descarga para $videoName", it)
                }
            }
        }
    }

    /**
     * Configura el ViewPager con los videos cargados.
     * Si el adaptador es nulo, lo inicializa; de lo contrario, notifica los cambios.
     */
    private fun setupViewPager() {
        _binding?.let { binding ->
            if (adapter == null) {
                adapter = MediaAdapterMalavita(requireContext(), mediaList)
                binding.viewPagerMalavita.adapter = adapter
            } else {
                adapter?.notifyDataSetChanged()
            }

            if (mediaList.isNotEmpty()) {
                binding.animation.visibility = View.GONE
            }
            Log.d("MalavitaFragment", "ViewPager setup complete with ${mediaList.size} items")
        } ?: Log.d("MalavitaFragment", "Binding es null, no se puede actualizar el ViewPager")
    }

    /**
     * Abre Google Maps con la ubicación del local "Malavita".
     * Si la aplicación de Google Maps no está disponible, abre el navegador con un enlace web.
     */
    private fun openMaps() {
        val gmmIntentUri = Uri.parse("geo:0,0?q=Malavita+Bar")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
            setPackage("com.google.android.apps.maps")
        }
        if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(mapIntent)
        } else {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.app.goo.gl/9Y1zMmW1oVT37e9N7?g_st=iw")))
        }
    }

    /**
     * Programa actualizaciones periódicas del TextView para mostrar si el local está "ABIERTO" o "CERRADO".
     */
    private fun scheduleTextViewUpdates() {
        val timeZone = TimeZone.getTimeZone("America/La_Paz")

        val updateTextView = {
            val currentTime = Calendar.getInstance(timeZone)
            val currentDay = currentTime.get(Calendar.DAY_OF_WEEK)
            val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val currentMinute = currentTime.get(Calendar.MINUTE)

            val isOpenToday: Boolean = when (currentDay) {
                Calendar.SUNDAY -> (currentHour >= 16 && currentHour < 24)
                Calendar.MONDAY,
                Calendar.TUESDAY,
                Calendar.WEDNESDAY,
                Calendar.THURSDAY -> false
                Calendar.FRIDAY -> (currentHour >= 20 || (currentHour < 3 && currentMinute <= 30))
                Calendar.SATURDAY -> (currentHour >= 20 || (currentHour < 3))
                else -> false
            }

            binding.textViewabierto.text = if (isOpenToday) "ABIERTO" else "CERRADO"
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
     * Limpia los videos antiguos de la caché local que ya no son necesarios.
     */
    private fun cleanUpOldVideos() {
        val videoDir = File(requireContext().filesDir, "cached_videos")
        if (videoDir.exists()) {
            val currentVideos = mediaList.map { getLocalVideoFile(it).name }
            videoDir.listFiles()?.forEach { file ->
                if (!currentVideos.contains(file.name)) {
                    file.delete()  // Elimina los archivos antiguos que no están en la lista actual
                }
            }
        }
    }

    /**
     * Obtiene el archivo de video local correspondiente a una URI de video.
     * @param videoUri URI del video.
     * @return Archivo local donde se almacenará el video.
     */
    private fun getLocalVideoFile(videoUri: Uri): File {
        val cacheDir = File(requireContext().filesDir, "cached_videos")
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        val sanitizedFileName = videoUri.lastPathSegment?.replace("/", "_") ?: "cached_video"
        return File(cacheDir, sanitizedFileName)
    }

    /**
     * Descarga y guarda un video desde una URI a un archivo local.
     * @param videoUri URI del video.
     * @param localFile Archivo local donde se guardará el video.
     * @return True si la descarga fue exitosa, de lo contrario False.
     */
    private fun downloadAndSaveVideo(videoUri: Uri, localFile: File): Boolean {
        if (localFile.exists()) {
            // Si ya existe en caché, no es necesario descargarlo de nuevo
            return true
        }

        return try {
            val url = URL(videoUri.toString())
            val inputStream: InputStream = url.openStream()
            val outputStream = FileOutputStream(localFile)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            Log.d("MediaAdapterMalavita", "Archivo guardado en: ${localFile.absolutePath}")
            true
        } catch (e: Exception) {
            Log.e("MediaAdapterMalavita", "Error al descargar el archivo: $videoUri", e)
            false
        }
    }

    /**
     * Método que se llama cuando la vista del fragmento se destruye.
     * Limpia el binding, detiene el handler y cierra el executor.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacksAndMessages(null)

        if (!executor.isShutdown) {
            executor.shutdownNow()
        }

        adapter = null
        Log.d("MalavitaFragment", "View destroyed, all players released")
    }
}
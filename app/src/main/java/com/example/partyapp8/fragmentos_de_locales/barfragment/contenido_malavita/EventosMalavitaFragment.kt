package com.example.partyapp8.fragmentos_de_locales.barfragment.contenido_malavita

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.partyapp8.databinding.FragmentMalavitaEventosBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.example.tarea.ImageAdapter

/**
 * [EventosMalavitaFragment] es un fragmento que muestra una serie de imágenes promocionales de eventos en el local "Malavita".
 * Utiliza Firebase Storage para cargar las imágenes dinámicamente y las muestra en un ViewPager2.
 */
class EventosMalavitaFragment : Fragment() {

    // Variable para almacenar el binding, inicialmente nulo
    private var _binding: FragmentMalavitaEventosBinding? = null
    private val binding get() = _binding!!

    /**
     * Método que se llama para crear e inflar la vista del fragmento.
     * Se utiliza View Binding para acceder a las vistas del layout asociado.
     * @param inflater Inflador de layout.
     * @param container Contenedor del fragmento.
     * @param savedInstanceState Estado de la instancia guardado.
     * @return Vista inflada.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el layout usando View Binding y devolver la raíz de la vista
        _binding = FragmentMalavitaEventosBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Método que se llama después de que la vista ha sido creada.
     * Configura el ViewPager2 con un adaptador de imágenes y carga las imágenes desde Firebase Storage.
     * @param view Vista creada.
     * @param savedInstanceState Estado de la instancia guardado.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mediaList = mutableListOf<String>()  // Lista para almacenar las URLs de las imágenes

        // Configurar el ViewPager2 con el adaptador de imágenes
        val shouldInvalidateCache = false // Cambia a true si quieres invalidar el caché
        val mediaAdapter = ImageAdapter(mediaList, shouldInvalidateCache)
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.viewPager2.adapter = mediaAdapter

        // Cargar medios desde Firebase Storage y actualizar el adaptador
        loadFirebaseMedia(mediaAdapter, mediaList)
    }

    /**
     * Carga imágenes desde Firebase Storage y las añade a la lista de imágenes.
     * Luego notifica al adaptador que se han añadido nuevos elementos.
     * @param adapter Adaptador de imágenes para el ViewPager2.
     * @param mediaList Lista de URLs de las imágenes.
     */
    private fun loadFirebaseMedia(adapter: ImageAdapter, mediaList: MutableList<String>) {
        val storage = Firebase.storage
        val storageRef = storage.reference

        val mediaNames = listOf(
            "Publicidad de malavita/Publicidad1.jpg",
            "Publicidad de malavita/Publicidad2.jpg",
            "Publicidad de malavita/Publicidad3.jpg",
            "Publicidad de malavita/Publicidad4.jpg"
        )

        // Descargar cada imagen desde Firebase Storage
        mediaNames.forEach { mediaName ->
            val mediaRef = storageRef.child(mediaName)

            // Verificar que el archivo existe en Firebase Storage
            mediaRef.metadata.addOnSuccessListener {
                // Obtener la URL de descarga de la imagen
                mediaRef.downloadUrl.addOnSuccessListener { uri ->
                    mediaList.add(uri.toString())  // Añadir la URL a la lista
                    adapter.notifyItemInserted(mediaList.size - 1)  // Notificar al adaptador
                }.addOnFailureListener {
                    Log.e("Firebase", "Error al obtener la URL de descarga para $mediaName", it)
                }
            }.addOnFailureListener {
                Log.e("Firebase", "El archivo no existe en Firebase Storage: $mediaName", it)
            }
        }
    }

    /**
     * Método que se llama cuando la vista del fragmento se destruye.
     * Aquí se limpia la referencia al binding para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
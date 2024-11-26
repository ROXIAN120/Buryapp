package com.example.partyapp8.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.partyapp8.R
import com.example.partyapp8.databinding.FragmentBarBinding

/**
 * Fragmento que muestra las opciones de navegación para la sección de Bar.
 * Permite a los usuarios navegar a diferentes secciones de la aplicación mediante botones interactivos.
 */
class BarFragment : Fragment() {

    // Variable para almacenar el binding, inicialmente nulo
    private var _binding: FragmentBarBinding? = null
    private val binding get() = _binding!! // Acceso seguro al binding

    /**
     * Método que se llama para crear e inflar la vista del fragmento.
     * @param inflater Inflador de layout.
     * @param container Contenedor del fragmento.
     * @param savedInstanceState Estado de la instancia guardado.
     * @return Vista inflada.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el layout usando View Binding y devolver la raíz de la vista
        _binding = FragmentBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Método que se llama después de que la vista ha sido creada.
     * Configura los listeners de clic para la navegación y establece opciones de navegación.
     * @param view Vista creada.
     * @param savedInstanceState Estado de la instancia guardado.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar opciones de navegación para evitar agregar al backstack
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.mobile_navigation, true)  // Elimina el fragmento actual del backstack
            .build()

        // Configurar los listeners de clic para navegar a diferentes fragmentos

        // Navegar al fragmento de Resto Bar al hacer clic en el elemento de "restoBar"
        binding.restoBar.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_resto_bar, null, navOptions)
        }

        // Navegar al fragmento de Discoteca al hacer clic en el elemento de "discoteca"
        binding.discoteca.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_discoteca, null, navOptions)
        }

        // Navegar al fragmento de Malavita al hacer clic en el texto correspondiente
        binding.textMalavita.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_malavita)
        }

        // Navegar al fragmento de Malavita al hacer clic en la imagen correspondiente
        binding.imagenDeMalavita.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_malavita)
        }

        // Navegar al fragmento de RecyclerView al hacer clic en la barra de navegación
        binding.barraDeNavegacion.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_recyclerview)
        }
    }

    /**
     * Método que se llama cuando la vista del fragmento se destruye.
     * Limpia el binding para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Limpieza del binding para evitar fugas de memoria
        _binding = null
    }
}
package com.example.partyapp8.fragments.discoteca

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.partyapp8.R
import com.example.partyapp8.databinding.FragmentDiscotecaBinding

/**
 * Fragmento que muestra las opciones de navegación para la sección de Discoteca.
 * Permite navegar a diferentes fragmentos de la aplicación a través de botones interactivos.
 */
class DiscotecaFragment : Fragment() {

    // Variable para almacenar el binding, inicialmente nulo
    private var _binding: FragmentDiscotecaBinding? = null
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
        _binding = FragmentDiscotecaBinding.inflate(inflater, container, false)
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
            .setPopUpTo(R.id.mobile_navigation, false)  // No agregar al backstack
            .build()

        // Configurar los listeners de clic para navegar a diferentes fragmentos

        // Navegar al fragmento de Home al hacer clic en el elemento de "bar"
        binding.bar.setOnClickListener {
            findNavController().navigate(R.id.action_nav_discoteca_to_nav_home, null, navOptions)
        }

        // Navegar al fragmento de Resto Bar al hacer clic en el elemento de "restoBar"
        binding.restoBar.setOnClickListener {
            findNavController().navigate(R.id.action_nav_discoteca_to_nav_resto_bar, null, navOptions)
        }

        // Navegar al fragmento de RecyclerView al hacer clic en el elemento de "barraDeNavegacion"
        binding.barraDeNavegacion.setOnClickListener {
            findNavController().navigate(R.id.action_nav_discoteca_to_nav_recyclerview)
        }

        // Navegar al fragmento de Vermont al hacer clic en el texto correspondiente
        binding.textVermont.setOnClickListener {
            findNavController().navigate(R.id.action_nav_discoteca_to_nav_vermont)
        }

        // Navegar al fragmento de Vermont al hacer clic en la imagen correspondiente
        binding.imagenVermont.setOnClickListener {
            findNavController().navigate(R.id.action_nav_discoteca_to_nav_vermont)
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
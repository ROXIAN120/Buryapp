package com.example.partyapp8.fragmentos_de_locales.barfragment.contenido_malavita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.partyapp8.R
import com.example.partyapp8.databinding.FragmentMesasMalavitaBinding
/**
 * Fragmento que muestra las opciones de reserva de mesas en el local "Malavita".
 * Permite a los usuarios seleccionar una mesa y navegar a la pantalla de reserva.
 */
class MesasMalavitaFragment : Fragment() {

    // Variable para almacenar el binding, inicialmente nulo
    private var _binding: FragmentMesasMalavitaBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentMesasMalavitaBinding.inflate(inflater, container, false)
        val rootView = binding.root

        // Descripciones de mesas
        val mesa1 = listOf(
            "- Mesa para 4 personas",
            "- Precio de reserva anticipada: 200 bs"
        )
        val mesa2 = listOf(
            "- Mesa para 3 personas",
            "- Precio de reserva anticipada: 100 bs"
        )
        val mesa3 = listOf(
            "- Mesa para 2 personas",
            "- Precio de reserva anticipada: 50 bs"
        )

        // Convertir las listas a cadenas con saltos de línea
        val satomesa1 = mesa1.joinToString(separator = "\n")
        val satomesa2 = mesa2.joinToString(separator = "\n")
        val satomesa3 = mesa3.joinToString(separator = "\n")

        // Establecer el texto de los TextView para mostrar las descripciones de las mesas
        binding.textviewMesa1.text = satomesa1
        binding.textviewMesa2.text = satomesa2
        binding.textviewMesa3.text = satomesa3

        // Configurar los botones para navegar a la pantalla de reserva, pasando la información de la mesa seleccionada

        // Navegar con la información de la mesa 1
        binding.button1.setOnClickListener {
            val ms1 = "mesa: 1"
            val bundle = Bundle().apply {
                putString("mesa", ms1)
            }
            findNavController().navigate(R.id.action_nav_mesas_malavita_to_nav_reserva_malavita, bundle)
        }

        // Navegar con la información de la mesa 2
        binding.button2.setOnClickListener {
            val ms2 = "mesa: 2"
            val bundle2 = Bundle().apply {
                putString("mesa2", ms2)
            }
            findNavController().navigate(R.id.action_nav_mesas_malavita_to_nav_reserva_malavita, bundle2)
        }

        // Navegar con la información de la mesa 3
        binding.Buttoon3.setOnClickListener {
            val ms3 = "mesa: 3"
            val bundle3 = Bundle().apply {
                putString("mesa3", ms3)
            }
            findNavController().navigate(R.id.action_nav_mesas_malavita_to_nav_reserva_malavita, bundle3)
        }

        return rootView
    }

    /**
     * Método que se llama cuando la vista del fragmento se destruye.
     * Limpia el binding para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Limpieza del binding para evitar fugas de memoria
    }
}
package com.example.partyapp8.fragmentos_de_locales.discotecafragment.contenido_vermont

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.partyapp8.databinding.FragmentHorarioVermontBinding

/**
 * [HorarioVermont] es un fragmento que muestra los horarios de apertura y cierre del local "Vermont".
 * Este fragmento también incluye un botón para cerrar el fragmento y regresar a la pantalla anterior.
 */
class HorarioVermont : Fragment() {

    // Variable para almacenar el binding, inicialmente nulo
    private var _binding: FragmentHorarioVermontBinding? = null
    private val binding get() = _binding!! // Acceso seguro al binding

    /**
     * Método que se llama para crear e inflar la vista del fragmento.
     * Se utiliza View Binding para acceder a las vistas del layout asociado.
     *
     * @param inflater El LayoutInflater utilizado para inflar la vista del fragmento.
     * @param container El contenedor opcional en el que se insertará la vista del fragmento.
     * @param savedInstanceState Si no es nulo, este fragmento se está recreando a partir de un estado guardado.
     * @return La vista raíz del layout asociado al fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflar el layout usando View Binding y devolver la raíz de la vista
        _binding = FragmentHorarioVermontBinding.inflate(inflater, container, false)

        // Descripciones de horarios del local
        val horarios = listOf(
            "                                      ",
            "                    HORARIO                  ",
            "                                      ",
            "Lunes: CERRADO",
            "Martes: CERRADO",
            "Miércoles: CERRADO",
            "Jueves: CERRADO",
            "Viernes: CERRADO",
            "Sábado: 21:00p.m. - 5:00a.m.",
            "Domingo: CERRADO"
        )
        // Unir la lista de horarios en una sola cadena con saltos de línea entre cada elemento
        val separador = horarios.joinToString(separator = "\n")
        // Establecer el texto de los horarios en el TextView correspondiente
        binding.horarioMalavita.text = separador

        // Configurar el OnClickListener para cerrar el fragmento al presionar el botón cerrar
        binding.buttonCerrar.setOnClickListener {
            // Notificar al fragmento padre que debe ocultar este fragmento
            parentFragmentManager.popBackStack() // Simula que se presionó "Atrás"
        }

        return binding.root
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

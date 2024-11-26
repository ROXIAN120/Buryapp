package com.example.partyapp8.fragmentos_de_locales.barfragment.contenido_malavita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.partyapp8.databinding.FragmentHorarioMalavitaBinding

/**
 * [HorarioMalavitaFragment] es un fragmento que muestra los horarios de apertura y cierre del local "Malavita".
 * Este fragmento también incluye un botón para cerrar el fragmento y regresar a la pantalla anterior.
 */
class HorarioMalavitaFragment : Fragment() {

    // Variable para almacenar el binding, inicialmente nulo
    private var _binding: FragmentHorarioMalavitaBinding? = null
    private val binding get() = _binding!! // Acceso seguro al binding

    /**
     * Método que se llama para crear e inflar la vista del fragmento.
     * Se utiliza View Binding para acceder a las vistas del layout asociado.
     * @param inflater Inflador de layout.
     * @param container Contenedor del fragmento.
     * @param savedInstanceState Estado de la instancia guardado.
     * @return Vista inflada.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflar el layout usando View Binding y devolver la raíz de la vista
        _binding = FragmentHorarioMalavitaBinding.inflate(inflater, container, false)

        // Descripciones de horarios del local
        val horarios = listOf(
            "                                      ",
            "                    HORARIO                  ",
            "                                      ",
            "Lunes: CERRADO",
            "Martes: CERRADO",
            "Miércoles: CERRADO",
            "Jueves: CERRADO",
            "Viernes: 8:00p.m. - 2:30a.m.",
            "Sábado: 8:00p.m. - 3:00a.m.",
            "Domingo: 4:00p.m. - 12:00a.m.",
        )

        // Unir los horarios en un solo String con separadores de nueva línea
        val separador = horarios.joinToString(separator = "\n")
        // Mostrar los horarios en el TextView correspondiente
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
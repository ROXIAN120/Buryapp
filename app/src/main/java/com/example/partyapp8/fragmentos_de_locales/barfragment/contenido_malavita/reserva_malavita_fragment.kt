package com.example.partyapp8.fragments.restobar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.partyapp8.databinding.FragmentMalavitaReservaBinding

/**
 * Fragmento que permite realizar reservas para el local "Malavita".
 * Recoge información del usuario y la envía a través de WhatsApp.
 */
class ReservaMalavita : Fragment() {

    // Variable para almacenar el binding, inicialmente nulo
    private var _binding: FragmentMalavitaReservaBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentMalavitaReservaBinding.inflate(inflater, container, false)

        // Configurar el listener para el botón de reserva
        binding.buttonReserva.setOnClickListener {
            // Obtener los valores de los campos de entrada
            val nombreApellido = binding.nombreApellido.text.toString()
            val cantidadPersonas = binding.cantidadDePersonas.text.toString()
            val fecha = binding.fecha.text.toString()
            val hora = binding.editTextHora.text.toString()

            // Obtener los argumentos pasados desde el fragmento anterior (información de la mesa)
            val mesa1 = arguments?.getString("mesa")
            val mesa2 = arguments?.getString("mesa2")
            val mesa3 = arguments?.getString("mesa3")

            // Crear el mensaje que se enviará a WhatsApp
            val mensaje = "        MALAVITA          \n" +  // Añadir espacios adicionales antes y después de "MALAVITA"
                    "Nombre y Apellido: $nombreApellido\n" +
                    "Cantidad de Personas: $cantidadPersonas\n" +
                    "Fecha: $fecha\n" +
                    "Hora: $hora\n" +
                    (mesa1 ?: "") +
                    (mesa2 ?: "") +
                    (mesa3 ?: "")

            // Número de teléfono al que se enviará el mensaje
            val phoneNumber = "+591 63551738"

            // Llamar a la función para enviar el mensaje a WhatsApp
            enviarMensajeAWhatsApp(phoneNumber, mensaje)
        }

        return binding.root
    }

    /**
     * Función para enviar un mensaje a través de WhatsApp.
     * @param numeroTelefono Número de teléfono al que se enviará el mensaje.
     * @param mensaje Mensaje a enviar.
     */
    private fun enviarMensajeAWhatsApp(numeroTelefono: String, mensaje: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://api.whatsapp.com/send?phone=$numeroTelefono&text=${Uri.encode(mensaje)}")
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Error al enviar mensaje.", Toast.LENGTH_SHORT).show()  // Mostrar mensaje de error si falla
        }
    }

    /**
     * Método que se llama cuando la vista del fragmento se destruye.
     * Limpia el binding para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
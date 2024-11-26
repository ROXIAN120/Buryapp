package com.example.partyapp8.fragments.restobar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.partyapp8.databinding.FragmentVermontReservaBinding

/**
 * [ReservaVermont] es un fragmento que permite a los usuarios realizar reservas para el local "Vermont".
 * Los usuarios pueden ingresar su información, seleccionar una mesa y enviar la reserva a través de WhatsApp.
 */
class ReservaVermont : Fragment() {

    // Variable para almacenar el binding, inicialmente nulo
    private var _binding: FragmentVermontReservaBinding? = null
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el layout usando View Binding y devolver la raíz de la vista
        _binding = FragmentVermontReservaBinding.inflate(inflater, container, false)

        // Configurar el listener para el botón de reserva
        binding.buttonReserva.setOnClickListener {
            // Obtener los valores de los campos de entrada
            val nombreApellido = binding.nombreApellido.text.toString()
            val cantidadPersonas = binding.cantidadDePersonas.text.toString()
            val fecha = binding.fecha.text.toString()
            val hora = binding.editTextHora.text.toString()

            // Obtener los argumentos pasados desde el fragmento anterior (información de la mesa)
            val mesa1 = arguments?.getString("mesa_externa1") // La clave debe coincidir con la usada al enviar el argumento
            val mesa2 = arguments?.getString("mesa_externa2")
            val mesa3 = arguments?.getString("mesa_externa3")
            val mesa4 = arguments?.getString("mesa_interna")
            val mesa5 = arguments?.getString("mesa_cumpleañera")

            // Crear el mensaje que se enviará a WhatsApp
            val mensaje = "        VERMONT          \n" +  // Añadir espacios adicionales antes y después de "VERMONT"
                    "Nombre y Apellido: $nombreApellido\n" +
                    "Cantidad de Personas: $cantidadPersonas\n" +
                    "Fecha: $fecha\n" +
                    "Hora: $hora\n" +
                    (mesa1 ?: "") +
                    (mesa2 ?: "") +
                    (mesa3 ?: "") +
                    (mesa4 ?: "") +
                    (mesa5 ?: "") +
                    "Hora: Cover: 20 Bs"

            // Número de teléfono al que se enviará el mensaje
            val phoneNumber = "+591 63551738"

            // Llamar a la función para enviar el mensaje a WhatsApp
            enviarMensajeAWhatsApp(phoneNumber, mensaje)
        }

        return binding.root
    }

    /**
     * Función para enviar un mensaje a través de WhatsApp.
     *
     * @param numeroTelefono El número de teléfono al que se enviará el mensaje.
     * @param mensaje El contenido del mensaje que se enviará.
     */
    private fun enviarMensajeAWhatsApp(numeroTelefono: String, mensaje: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://api.whatsapp.com/send?phone=$numeroTelefono&text=${Uri.encode(mensaje)}")
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Error al enviar mensaje.", Toast.LENGTH_SHORT).show()
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

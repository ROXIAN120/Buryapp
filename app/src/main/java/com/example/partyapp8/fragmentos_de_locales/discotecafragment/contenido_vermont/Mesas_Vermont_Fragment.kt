package com.example.partyapp8.fragmentos_de_locales.discotecafragment.contenido_vermont

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.partyapp8.R
import com.example.partyapp8.databinding.FragmentMesasMalavitaBinding
import com.example.partyapp8.databinding.FragmentVermontMesaBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
/**
 * [MesasVermontFragment] es un fragmento que muestra las opciones de reserva de mesas en el local "Vermont".
 * Este fragmento permite a los usuarios seleccionar una mesa y navegar a la pantalla de reserva con la información de la mesa seleccionada.
 */
class MesasVermontFragment : Fragment() {

    // Variable para almacenar el binding, inicialmente nulo
    private var _binding: FragmentVermontMesaBinding? = null
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
        _binding = FragmentVermontMesaBinding.inflate(inflater, container, false)
        val rootView = binding.root

        // Cargar imágenes desde Firebase Storage
        loadImagesFromFirebase()

        // Descripciones de mesas
        val mesa_externa1 = listOf(
            "- Mesa externa (5-10 personas)",
            "- Precio de reserva de mesa: 50 bs",
            "- cover: 20bs"
        )
        val mesa_externa2 = listOf(
            "- Mesa externa (5-10 personas)",
            "- Precio de reserva de mesa: 50 bs",
            "- cover: 20bs"
        )
        val mesa_externa3 = listOf(
            "- Mesa externa (1-4 personas)",
            "- Precio de reserva de mesa: 50 bs",
            "- cover: 20bs"
        )
        val mesa_interna = listOf(
            "- Mesa cumpleañera (1-8 personas)",
            "- Precio de reserva de mesa: 50 bs",
            "- cover: 20bs"
        )
        val mesa_cumpleañera = listOf(
            "- Mesa interna (1-8 personas)",
            "- Precio de reserva de mesa: 50 bs",
            "- cover: 20bs"
        )

        // Convertir las listas a cadenas con saltos de línea
        val satomesa1 = mesa_externa1.joinToString(separator = "\n")
        val satomesa2 = mesa_externa2.joinToString(separator = "\n")
        val satomesa3 = mesa_externa3.joinToString(separator = "\n")
        val satomesa4 = mesa_interna.joinToString(separator = "\n")
        val satomesa5 = mesa_cumpleañera.joinToString(separator = "\n")

        // Establecer el texto de los TextView
        binding.textviewMesa1.text = satomesa1
        binding.textviewMesa2.text = satomesa2
        binding.textviewMesa3.text = satomesa3
        binding.textviewMesa4.text = satomesa4
        binding.textviewMesa5.text = satomesa5

        // Configurar los botones para navegar a la pantalla de reserva, pasando la información de la mesa seleccionada

        // Navegar con la información de la mesa 1
        binding.button1.setOnClickListener {
            val ms1 = "Mesa: externa (5-10 personas)"
            val bundle = Bundle().apply {
                putString("mesa_externa1", ms1) // Asegúrate de que esta clave coincide con la que usas en el fragmento de destino
            }
            findNavController().navigate(R.id.action_nav_vermont_mesas_to_nav_vermont_reserva, bundle)
        }

        // Navegar con la información de la mesa 2
        binding.button2.setOnClickListener {
            val ms2 = "Mesa: externa (5-10 personas)"
            val bundle2 = Bundle().apply {
                putString("mesa_externa2", ms2)
            }
            findNavController().navigate(R.id.action_nav_vermont_mesas_to_nav_vermont_reserva, bundle2)
        }

        // Navegar con la información de la mesa 3
        binding.Buttoon3.setOnClickListener {
            val ms3 = "Mesa: externa (1-4 personas)"
            val bundle3 = Bundle().apply {
                putString("mesa_externa3", ms3)
            }
            findNavController().navigate(R.id.action_nav_vermont_mesas_to_nav_vermont_reserva, bundle3)
        }

        // Navegar con la información de la mesa 4
        binding.Buttoon4.setOnClickListener {
            val ms4 = "Mesa: cumpleañera (1-8 personas)"
            val bundle4 = Bundle().apply {
                putString("mesa_interna", ms4)
            }
            findNavController().navigate(R.id.action_nav_vermont_mesas_to_nav_vermont_reserva, bundle4)
        }

        // Navegar con la información de la mesa 5
        binding.Buttoon5.setOnClickListener {
            val ms5 = "Mesa: interna (1-8 personas)"
            val bundle5 = Bundle().apply {
                putString("mesa_cumpleañera", ms5)
            }
            findNavController().navigate(R.id.action_nav_vermont_mesas_to_nav_vermont_reserva, bundle5)
        }

        return rootView
    }

    /**
     * Método para cargar imágenes desde Firebase Storage y mostrarlas en los ImageView correspondientes.
     */
    private fun loadImagesFromFirebase() {
        val storage = Firebase.storage
        val storageRef = storage.reference

        // Referencia a las imágenes en Firebase Storage
        val mesa1Ref = storageRef.child("Publicidad de vermont/mesas_vermont/mesa_externa_5-10p2.jpg")
        val mesa2Ref = storageRef.child("Publicidad de vermont/mesas_vermont/mesa_externa_5-10p.jpg")
        val mesa3Ref = storageRef.child("Publicidad de vermont/mesas_vermont/mesa_externa_1-4p.jpg")
        val mesa4Ref = storageRef.child("Publicidad de vermont/mesas_vermont/mesa_cumpleañera_1-8p.jpg")
        val mesa5Ref = storageRef.child("Publicidad de vermont/mesas_vermont/mesa_interna_1-8p.jpg")

        // Cargar la imagen de mesa 1
        mesa1Ref.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this).load(uri).into(binding.mesa1)
        }.addOnFailureListener {
            // Manejar cualquier error
        }

        // Cargar la imagen de mesa 2
        mesa2Ref.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this).load(uri).into(binding.mesa2)
        }.addOnFailureListener {
            // Manejar cualquier error
        }

        // Cargar la imagen de mesa 3
        mesa3Ref.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this).load(uri).into(binding.mesa3)
        }.addOnFailureListener {
            // Manejar cualquier error
        }

        // Cargar la imagen de mesa 4
        mesa4Ref.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this).load(uri).into(binding.mesa4)
        }.addOnFailureListener {
            // Manejar cualquier error
        }

        // Cargar la imagen de mesa 5
        mesa5Ref.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this).load(uri).into(binding.mesa5)
        }.addOnFailureListener {
            // Manejar cualquier error
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

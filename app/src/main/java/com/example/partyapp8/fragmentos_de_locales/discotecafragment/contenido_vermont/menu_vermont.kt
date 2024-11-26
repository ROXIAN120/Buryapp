package com.example.partyapp8.fragmentos_de_locales.discotecafragment.contenido_vermont

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.partyapp8.databinding.FragmentVermontMenuBinding
import com.google.firebase.storage.FirebaseStorage

// Fragmento que muestra el menú de bebidas del local "Vermont"
class MenuVermont : Fragment() {

    // Variable para almacenar el binding, inicialmente nulo
    private var _binding: FragmentVermontMenuBinding? = null
    private val binding get() = _binding!! // Acceso seguro al binding

    // Método que se llama para crear e inflar la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el layout usando View Binding y devolver la raíz de la vista
        _binding = FragmentVermontMenuBinding.inflate(inflater, container, false)

        // Cargar imagen desde Firebase Storage
        loadImageFromFirebase()

        return binding.root
    }

    // Método para cargar una imagen desde Firebase Storage
    private fun loadImageFromFirebase() {
        // Obtén la referencia a Firebase Storage
        val storageReference = FirebaseStorage.getInstance().reference

        // Especifica la ruta de la imagen en Firebase Storage
        val imageRef = storageReference.child("Publicidad de vermont/menu/Menu_vermont.jpg")

        // Cargar la imagen en el ImageView utilizando Glide
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this).load(uri).into(binding.imageView) // Asegúrate de que 'imageView' coincide con el ID de tu ImageView en el layout
        }.addOnFailureListener {
        }
    }

    // Método que se llama cuando la vista del fragmento se destruye
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Limpieza del binding para evitar fugas de memoria
    }
}

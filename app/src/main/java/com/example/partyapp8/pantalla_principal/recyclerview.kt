package com.example.filtrador_buscador_kotlin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.partyapp8.R
import com.example.partyapp8.databinding.BarraDeNavegacionBinding

// Fragmento que contiene un RecyclerView con la funcionalidad de filtrado mediante un campo de búsqueda
class RecyclerviewFragment : Fragment() {

    // Binding para la vista del fragmento, inicializado como nulo
    private var _binding: BarraDeNavegacionBinding? = null
    private val binding get() = _binding!! // Referencia no nula al binding

    // Adaptador para el RecyclerView
    private lateinit var adaptador: AdaptadorUsuario

    // Lista de usuarios que se mostrará en el RecyclerView
    private var listaUsuarios = arrayListOf<Usuario>()

    /**
     * Método que se llama al crear la vista del fragmento.
     * Inflamos el layout utilizando el binding y devolvemos la raíz de la vista.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el layout usando el binding y devolver la raíz de la vista
        _binding = BarraDeNavegacionBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Método que se llama después de que la vista ha sido creada.
     * Configura el RecyclerView y el listener de búsqueda.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Llenar la lista de usuarios con datos predefinidos
        llenarLista()

        // Configurar el RecyclerView con un layout manager y un adaptador
        setupRecyclerView()

        // Agregar un listener al campo de búsqueda para filtrar la lista en tiempo real
        binding.etbuscador.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                // Filtrar la lista de usuarios basado en el texto ingresado
                filtrar(p0.toString())
            }
        })
    }

    /**
     * Método que se llama cuando la vista del fragmento se destruye.
     * Libera el binding para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Liberar el binding para evitar fugas de memoria
    }

    /**
     * Método para llenar la lista de usuarios con datos predefinidos.
     * Se utiliza para inicializar la lista que se mostrará en el RecyclerView.
     */
    private fun llenarLista() {
        listaUsuarios.add(Usuario("Doumo", "bar", R.drawable.doumo))
        listaUsuarios.add(Usuario("Kaos", "Resto bar", R.drawable.kaos))
        listaUsuarios.add(Usuario("Anemona", "bar", R.drawable.anemona))
        listaUsuarios.add(Usuario("Rustido", "bar", R.drawable.rustido))
        listaUsuarios.add(Usuario("Vila Bela", "Resto bar", R.drawable.vila_bela))

        // Agregar "Malavita" solo si no está ya en la lista
        if (listaUsuarios.none { it.nombre == "Malavita" }) {
            listaUsuarios.add(Usuario("Malavita", "bar", R.drawable.malavita))
        }

        listaUsuarios.add(Usuario("Luna Rosa", "Resto bar", R.drawable.luna_rosa))
        listaUsuarios.add(Usuario("Singapur", "bar", R.drawable.singapur))
        listaUsuarios.add(Usuario("Tulum", "Resto bar", R.drawable.tulum))
        listaUsuarios.add(Usuario("Duda", "bar", R.drawable.duda))
        listaUsuarios.add(Usuario("La Rivera", "Resto bar", R.drawable.la_riviera))
        listaUsuarios.add(Usuario("Tarantino", "bar", R.drawable.tarantino))
        listaUsuarios.add(Usuario("Mojitos", "Resto bar", R.drawable.mojitos))
        listaUsuarios.add(Usuario("Kuka", "bar", R.drawable.kuka))
        listaUsuarios.add(Usuario("Premier", "Resto bar", R.drawable.premier))
        listaUsuarios.add(Usuario("Cancun", "Discoteca", R.drawable.cancun))
        listaUsuarios.add(Usuario("Chopao", "Resto bar", R.drawable.chopao))
        listaUsuarios.add(Usuario("Woods", "bar", R.drawable.woods))
        listaUsuarios.add(Usuario("Casa Grande", "Resto bar", R.drawable.casa_grande))
        listaUsuarios.add(Usuario("Havana", "bar", R.drawable.havana))
        listaUsuarios.add(Usuario("Solares", "Resto bar", R.drawable.solares))
        listaUsuarios.add(Usuario("Bahia", "bar", R.drawable.bahia))
        listaUsuarios.add(Usuario("021 Hall", "Resto bar", R.drawable.hall))
    }

    /**
     * Método para configurar el RecyclerView, incluyendo el layout manager y el adaptador.
     * Configura la lista de usuarios que se mostrará.
     */
    private fun setupRecyclerView() {
        binding.rvLista.layoutManager = LinearLayoutManager(requireContext())  // Establecer el LinearLayoutManager
        adaptador = AdaptadorUsuario(listaUsuarios)  // Crear instancia del adaptador con la lista de usuarios
        binding.rvLista.adapter = adaptador  // Asignar el adaptador al RecyclerView
    }

    /**
     * Método para filtrar la lista de usuarios según el texto ingresado en el campo de búsqueda.
     * Actualiza el RecyclerView con los resultados filtrados.
     * @param texto Texto ingresado por el usuario para filtrar.
     */
    private fun filtrar(texto: String) {
        val listaFiltrada = arrayListOf<Usuario>()
        listaUsuarios.forEach {
            if (it.nombre.toLowerCase().contains(texto.toLowerCase())) {  // Comparación no sensible a mayúsculas/minúsculas
                listaFiltrada.add(it)
            }
        }
        adaptador.filtrar(listaFiltrada)  // Actualizar el adaptador con la lista filtrada
    }
}
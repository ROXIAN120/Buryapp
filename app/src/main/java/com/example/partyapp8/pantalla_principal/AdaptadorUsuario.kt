package com.example.filtrador_buscador_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.partyapp8.R

/**
 * Adaptador para el RecyclerView que maneja la lista de usuarios.
 * Este adaptador permite enlazar los datos de los usuarios a las vistas del RecyclerView y manejar la navegación.
 */
class AdaptadorUsuario(
    private var listaUsuarios: ArrayList<Usuario>  // Lista de usuarios a mostrar en el RecyclerView
) : RecyclerView.Adapter<AdaptadorUsuario.ViewHolder>() {

    /**
     * ViewHolder que contiene las vistas para un solo elemento de la lista.
     * Proporciona referencias a las vistas de cada elemento para mejorar el rendimiento.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.nombre_de_local)  // TextView para el nombre del usuario
        val tvTelefono: TextView = itemView.findViewById(R.id.tipo_de_local)  // TextView para el tipo de local o descripción
        val imagenlocal: ImageView = itemView.findViewById(R.id.imagen_del_local)  // ImageView para la imagen del usuario/local
    }

    /**
     * Método para inflar el layout de cada elemento del RecyclerView.
     * @param parent ViewGroup al que el nuevo View será agregado después de ser inflado.
     * @param viewType Tipo de vista del nuevo View.
     * @return Un nuevo ViewHolder que contiene la vista inflada.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_usuario, parent, false)
        return ViewHolder(vista)
    }

    /**
     * Método para enlazar los datos de un usuario a las vistas del ViewHolder.
     * @param holder ViewHolder que debe ser actualizado para representar los contenidos del elemento en la posición dada.
     * @param position Posición del elemento en el conjunto de datos.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = listaUsuarios[position]  // Obtener el usuario en la posición actual

        holder.tvNombre.text = usuario.nombre  // Establecer el nombre del usuario
        holder.tvTelefono.text = usuario.telefono  // Establecer el tipo o descripción del usuario/local
        holder.imagenlocal.setImageResource(usuario.Imagen)  // Establecer la imagen del usuario/local

        // Configurar un click listener en el itemView para navegar a otro fragmento si el nombre es "Malavita"
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            if (usuario.nombre == "Malavita") {  // Navegar solo si el usuario es "Malavita"
                val action = R.id.action_nav_recyclerview_to_nav_malavita  // Acción de navegación definida en el NavController
                holder.itemView.findNavController().navigate(action)  // Navegar al fragmento "Malavita"
            }
        }
    }

    /**
     * Método para obtener la cantidad de elementos en la lista de usuarios.
     * @return Cantidad de elementos en la lista.
     */
    override fun getItemCount(): Int {
        return listaUsuarios.size
    }

    /**
     * Método para filtrar la lista de usuarios y actualizar el RecyclerView.
     * @param listaFiltrada Nueva lista de usuarios filtrados a mostrar.
     */
    fun filtrar(listaFiltrada: ArrayList<Usuario>) {
        this.listaUsuarios = listaFiltrada  // Asignar la lista filtrada a la lista principal
        notifyDataSetChanged()  // Notificar al adaptador que los datos han cambiado para que actualice el RecyclerView
    }
}
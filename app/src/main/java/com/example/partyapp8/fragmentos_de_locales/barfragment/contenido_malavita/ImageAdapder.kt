package com.example.tarea

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.partyapp8.R

/**
 * Adaptador para un RecyclerView que muestra una lista de imágenes desde URLs.
 * Utiliza Glide para manejar la carga y la caché de las imágenes.
 * @param mediaList Lista de URLs de imágenes a cargar.
 * @param shouldInvalidateCache Booleano que indica si se debe invalidar la caché de imágenes.
 */
class ImageAdapter(
    private val mediaList: List<String>,
    private val shouldInvalidateCache: Boolean
) : RecyclerView.Adapter<ImageAdapter.MediaViewHolder>() {

    /**
     * Crea un nuevo ViewHolder para contener la vista de cada elemento de la lista.
     * @param parent ViewGroup al que pertenece el ViewHolder.
     * @param viewType Tipo de vista de la nueva vista.
     * @return Un nuevo MediaViewHolder que contiene la vista inflada.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.publicidad_malavita, parent, false)
        return MediaViewHolder(view)
    }

    /**
     * Vincula los datos de una URL de imagen al ImageView del ViewHolder.
     * @param holder MediaViewHolder que contiene la vista.
     * @param position Posición del elemento en la lista.
     */
    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        var mediaItem = mediaList[position]

        // Si se necesita invalidar el caché, agregar un timestamp a la URL
        if (shouldInvalidateCache) {
            val timestamp = System.currentTimeMillis().toString()
            mediaItem = "$mediaItem?t=$timestamp"
        }

        // Utiliza Glide para cargar la imagen desde la URL en el ImageView con compresión
        Glide.with(holder.itemView.context)
            .load(mediaItem)
            .diskCacheStrategy(DiskCacheStrategy.ALL)  // Cache en disco
            .skipMemoryCache(false)  // Usa caché en memoria
            .encodeQuality(75)  // Comprime la imagen al 75% de calidad
            .placeholder(R.drawable.error_de_conexion)  // Imagen de placeholder mientras se carga
            .error(R.drawable.error_de_conexion)  // Imagen de error si la carga falla
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    // Cuando la imagen está lista, se asigna al ImageView
                    holder.imageView.setImageDrawable(resource)
                    // Cachear manualmente la imagen después de cargarla
                    Glide.with(holder.itemView.context)
                        .load(mediaItem)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .preload()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Manejar la limpieza del recurso
                    holder.imageView.setImageDrawable(placeholder)
                }
            })
    }

    /**
     * Devuelve el tamaño de la lista de imágenes.
     * @return Número de elementos en la lista.
     */
    override fun getItemCount(): Int = mediaList.size

    /**
     * ViewHolder que contiene la vista para un solo elemento de la lista de imágenes.
     * @param itemView Vista de un solo elemento.
     */
    class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)  // ImageView para mostrar la imagen cargada
    }
}
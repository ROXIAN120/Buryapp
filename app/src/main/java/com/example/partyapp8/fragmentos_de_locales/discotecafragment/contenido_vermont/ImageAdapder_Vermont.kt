package com.example.tarea

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.partyapp8.R

/**
 * [MediaAdapterVermont] es un adaptador para un RecyclerView que muestra una lista de videos.
 * Este adaptador maneja solo URIs de videos.
 *
 * @param mediaList La lista de URIs de videos que se mostrarán en el RecyclerView.
 */
class MediaAdapterVermont(private val mediaList: List<Uri>) : RecyclerView.Adapter<MediaAdapterVermont.VideoViewHolder>() {

    /**
     * Crea un nuevo ViewHolder cada vez que el RecyclerView necesita uno.
     *
     * @param parent El ViewGroup en el que se añadirá la nueva vista creada.
     * @param viewType El tipo de vista que se creará (en este caso, solo videos).
     * @return Un nuevo VideoViewHolder que contiene la vista inflada.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.publicidad_malavita_video, parent, false)
        return VideoViewHolder(view)
    }

    /**
     * Vincula los datos de una posición específica del RecyclerView con el ViewHolder.
     *
     * @param holder El ViewHolder que se actualizará con los nuevos datos.
     * @param position La posición en la lista de datos que se vinculará con el ViewHolder.
     */
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(mediaList[position])
    }

    /**
     * Devuelve la cantidad de elementos en la lista de medios.
     *
     * @return El número de elementos en la lista de medios.
     */
    override fun getItemCount(): Int = mediaList.size

    /**
     * [VideoViewHolder] es un ViewHolder que contiene las vistas para un solo elemento de la lista de videos.
     *
     * @param itemView La vista del elemento que se está mostrando en el RecyclerView.
     */
    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val videoView: VideoView = itemView.findViewById(R.id.videoView)

        fun bind(videoUri: Uri) {
            videoView.setVideoURI(videoUri)
            videoView.setOnPreparedListener { mp ->
                mp.isLooping = true
                videoView.start()
            }
        }
    }
}
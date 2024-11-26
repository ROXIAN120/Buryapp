package com.example.tarea

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.partyapp8.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Adaptador para un RecyclerView que muestra una lista de videos utilizando VideoView.
 * Este adaptador permite la reproducción de videos almacenados en caché localmente.
 * Si un video no está en caché, lo descarga y lo almacena para su futura visualización.
 */
class MediaAdapterMalavita(
    private val context: Context,  // Contexto de la aplicación
    private val mediaList: List<Uri>  // Lista de URIs de videos
) : RecyclerView.Adapter<MediaAdapterMalavita.VideoViewHolder>() {

    private val executor: ExecutorService = Executors.newFixedThreadPool(8)  // Executor para manejar descargas en segundo plano

    /**
     * Método que se llama para crear un nuevo ViewHolder de video.
     * @param parent ViewGroup al que se agregará la nueva vista.
     * @param viewType Tipo de la vista.
     * @return Un nuevo VideoViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.publicidad_malavita_video, parent, false)
        return VideoViewHolder(view)
    }

    /**
     * Método que se llama para enlazar los datos de un video a un VideoViewHolder.
     * @param holder ViewHolder que debe ser actualizado.
     * @param position Posición del elemento en la lista de datos.
     */
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoUri = mediaList[position]  // URI del video en la posición actual
        holder.bind(videoUri, context, executor)  // Enlazar los datos del video al ViewHolder
    }

    /**
     * Devuelve la cantidad de elementos en la lista de videos.
     * @return Tamaño de la lista de videos.
     */
    override fun getItemCount(): Int = mediaList.size

    /**
     * ViewHolder personalizado para gestionar la vista de un video.
     */
    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val videoView: VideoView = itemView.findViewById(R.id.videoView)  // VideoView para mostrar el video

        /**
         * Enlaza el URI del video con el VideoView y gestiona su caché local.
         * @param videoUri URI del video.
         * @param context Contexto de la aplicación.
         * @param executor ExecutorService para manejar la descarga en segundo plano.
         */
        fun bind(videoUri: Uri, context: Context, executor: ExecutorService) {
            val localFile = getLocalVideoFile(videoUri, context)  // Obtener la ruta del archivo local del video

            if (localFile.exists() && localFile.length() > 0) {
                // Si el archivo ya existe en caché, lo cargamos en el VideoView
                videoView.setVideoURI(Uri.fromFile(localFile))
                videoView.setOnPreparedListener { videoView.start() }
            } else {
                // Si no existe, lo descargamos y guardamos en caché
                executor.submit {
                    if (downloadAndSaveVideo(videoUri, localFile)) {
                        videoView.post {
                            videoView.setVideoURI(Uri.fromFile(localFile))
                            videoView.setOnPreparedListener { videoView.start() }
                        }
                    } else {
                        Log.e("MediaAdapterMalavita", "Error al descargar el video $videoUri")
                    }
                }
            }
        }

        /**
         * Obtiene el archivo de video local basado en el URI del video.
         * @param videoUri URI del video.
         * @param context Contexto de la aplicación.
         * @return Archivo de video local donde se almacenará el video en caché.
         */
        private fun getLocalVideoFile(videoUri: Uri, context: Context): File {
            val cacheDir = File(context.filesDir, "cached_videos")  // Directorio de caché de videos
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            return File(cacheDir, "cached_video_${videoUri.lastPathSegment?.replace("/", "_")}")  // Nombre del archivo de caché basado en el URI
        }

        /**
         * Descarga y guarda el video en la ubicación especificada.
         * @param videoUri URI del video a descargar.
         * @param localFile Archivo local donde se guardará el video.
         * @return true si la descarga fue exitosa, false de lo contrario.
         */
        private fun downloadAndSaveVideo(videoUri: Uri, localFile: File): Boolean {
            return try {
                val url = URL(videoUri.toString())  // Crear objeto URL a partir del URI
                val inputStream: InputStream = url.openStream()  // Abrir flujo de entrada desde la URL
                val outputStream = FileOutputStream(localFile)  // Crear flujo de salida al archivo local

                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)  // Copiar los datos del flujo de entrada al flujo de salida
                    }
                }
                Log.d("MediaAdapterMalavita", "Archivo guardado en: ${localFile.absolutePath}")
                true
            } catch (e: Exception) {
                Log.e("MediaAdapterMalavita", "Error al descargar el archivo: $videoUri", e)
                false
            }
        }
    }
}
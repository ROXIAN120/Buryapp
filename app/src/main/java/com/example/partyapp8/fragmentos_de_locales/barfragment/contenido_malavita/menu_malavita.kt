package com.example.partyapp8.fragmentos_de_locales.barfragment.contenido_malavita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.partyapp8.databinding.FragmentMenuMalavitaBinding
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

/**
 * Fragmento que muestra el menú de bebidas del local "Malavita".
 * Utiliza Firebase Remote Config para obtener y mostrar dinámicamente el menú y sus precios.
 */
class MenuMalavita : Fragment() {

    // Variable para almacenar el binding, inicialmente nulo
    private var _binding: FragmentMenuMalavitaBinding? = null
    private val binding get() = _binding!! // Acceso seguro al binding

    // Instancia de FirebaseRemoteConfig para obtener la configuración remota
    private lateinit var remoteConfig: FirebaseRemoteConfig

    /**
     * Método que se llama para crear e inflar la vista del fragmento.
     * @param inflater Inflador de layout.
     * @param container Contenedor del fragmento.
     * @param savedInstanceState Estado de la instancia guardado.
     * @return Vista inflada.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el layout usando View Binding y devolver la raíz de la vista
        _binding = FragmentMenuMalavitaBinding.inflate(inflater, container, false)

        // Inicializar Firebase Remote Config
        remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(100) // Intervalo mínimo de tiempo para actualizar la configuración
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)

        // Mostrar la animación de carga mientras se obtienen los datos
        binding.animation.visibility = View.VISIBLE

        // Obtener valores de configuración remota desde Firebase
        fetchRemoteConfigValues()

        return binding.root
    }

    /**
     * Obtiene los valores de configuración remota desde Firebase Remote Config.
     * Configura el contenido de los TextViews con las bebidas y sus precios.
     */
    private fun fetchRemoteConfigValues() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful && _binding != null) {  // Verifica si el binding aún está disponible
                    // Obtener valores de bebidas y precios desde Firebase Remote Config

                    // Bebidas de shots y tragos
                    val bbd1 = remoteConfig.getString("malavita")
                    val bbd2 = remoteConfig.getString("tabla_de_shots_malavita")
                    val bbd3 = remoteConfig.getString("tabla_de_tequila_malavita")
                    val bbd4 = remoteConfig.getString("shot_jagger_malavita")
                    // Precios de shots y tragos
                    val p1 = remoteConfig.getString("precio_de_malavita")
                    val p2 = remoteConfig.getString("precio_de_tabla_de_shots_malavita")
                    val p3 = remoteConfig.getString("precio_de_tabla_de_tequila_malavita")
                    val p4 = remoteConfig.getString("precio_de_shot_jagger_malavita")

                    // Bebidas de vasos y cervezas
                    val bbd5 = remoteConfig.getString("fernet_malavita")
                    val bbd6 = remoteConfig.getString("vodka_malavita")
                    val bbd7 = remoteConfig.getString("tequila_malavita")
                    val bbd8 = remoteConfig.getString("gin_malavita")
                    val bbd9 = remoteConfig.getString("corona_malavita")
                    val bbd10 = remoteConfig.getString("ron_malavita")
                    val bbd11 = remoteConfig.getString("desatornillador_malavita")
                    val bbd12 = remoteConfig.getString("sunrise_malavita")
                    val bbd13 = remoteConfig.getString("pico_de_plata_malavita")
                    val bbd14 = remoteConfig.getString("whisky_malavita")
                    // Precios de vasos y cervezas
                    val p5 = remoteConfig.getString("vaso_precio_de_fernet_malavita")
                    val p6 = remoteConfig.getString("vaso_precio_de_vodka_malavita")
                    val p7 = remoteConfig.getString("vaso_precio_de_tequila_malavita")
                    val p8 = remoteConfig.getString("vaso_precio_gin_malavita")
                    val p9 = remoteConfig.getString("vaso_precio_de_corona_malavita")
                    val p10 = remoteConfig.getString("vaso_precio_de_ron_malavita")
                    val p11 = remoteConfig.getString("vaso_precio_destornillador_malavita")
                    val p12 = remoteConfig.getString("vaso_precio_sunrise_malavita_malavita")
                    val p13 = remoteConfig.getString("vaso_precio_pico_de_plata_malavita")
                    val p14 = remoteConfig.getString("vaso_precio_whisky_malavita")

                    // Bebidas de botellas
                    val bbd15 = remoteConfig.getString("four_loko_malavita")
                    val bbd16 = remoteConfig.getString("botella_de_ron_malavita")
                    val bbd17 = remoteConfig.getString("botella_champagne_malavita")
                    val bbd18 = remoteConfig.getString("botella_jaggermeister_malavita")
                    val bbd19 = remoteConfig.getString("bottella_Tequila_jose_cuervo_malavita")
                    val bbd20 = remoteConfig.getString("botella_fernet_malavita")
                    val bbd21 = remoteConfig.getString("botella_vodka_malavita")
                    val bbd22 = remoteConfig.getString("bottella_Johnnie_Walker_malavita")
                    val bbd23 = remoteConfig.getString("botella_gin_malavita")
                    // Precios de botellas
                    val p15 = remoteConfig.getString("precio_de_four_loko_malavita")
                    val p16 = remoteConfig.getString("precio_de_ron_malavita")
                    val p17 = remoteConfig.getString("precio_de_champagne_malavita")
                    val p18 = remoteConfig.getString("precio_de_jaggermeister_malavita")
                    val p19 = remoteConfig.getString("precio_de_tequila_jose_cuervo_malavita")
                    val p20 = remoteConfig.getString("precio_de_fernet_malavita")
                    val p21 = remoteConfig.getString("precio_de_vodka_malavita")
                    val p22 = remoteConfig.getString("precio_de_johnnie_walker_malavita")
                    val p23 = remoteConfig.getString("precio_de_gin_malavita")

                    // Bebidas de baldes
                    val bbd24 = remoteConfig.getString("corona_x5_malavita")
                    val bbd25 = remoteConfig.getString("pico_de_plata_x5_malavita")
                    val bbd26 = remoteConfig.getString("Martens_x10_malavita")
                    // Precios de baldes
                    val p24 = remoteConfig.getString("precio_de_coronax5_malavita")
                    val p25 = remoteConfig.getString("precio_de_pico_de_platax5_malavita")
                    val p26 = remoteConfig.getString("precio_de_martensx10_malavita")

                    // Bebidas sin alcohol
                    val bbd27 = remoteConfig.getString("agua_malavita")
                    val bbd28 = remoteConfig.getString("soda_2_litros_malavita")
                    val bbd29 = remoteConfig.getString("Agua_tonica_malavita")
                    val bbd30 = remoteConfig.getString("red_bull_malavita")
                    // Precios de bebidas sin alcohol
                    val p27 = remoteConfig.getString("precio_agua_malavita")
                    val p28 = remoteConfig.getString("precio_soda_malavita")
                    val p29 = remoteConfig.getString("precio_agua_tonica_malavita")
                    val p30 = remoteConfig.getString("precio_red_bull_malavita")

                    // Unir las listas de bebidas y precios en cadenas de texto
                    val bebidas = listOf(bbd1, bbd2, bbd3, bbd4).joinToString(separator = "\n")
                    val precios = listOf(p1, p2, p3, p4).joinToString(separator = "\n")
                    val bebidas2 = listOf(bbd5, bbd6, bbd7, bbd8, bbd9, bbd10, bbd11, bbd12, bbd13, bbd14).joinToString(separator = "\n")
                    val precios2 = listOf(p5, p6, p7, p8, p9, p10, p11, p12, p13, p14).joinToString(separator = "\n")
                    val bebidas3 = listOf(bbd15, bbd16, bbd17, bbd18, bbd19, bbd20, bbd21, bbd22, bbd23).joinToString(separator = "\n")
                    val precios3 = listOf(p15, p16, p17, p18, p19, p20, p21, p22, p23).joinToString(separator = "\n")
                    val bebidas4 = listOf(bbd24, bbd25, bbd26).joinToString(separator = "\n")
                    val precios4 = listOf(p24, p25, p26).joinToString(separator = "\n")
                    val bebidas5 = listOf(bbd27, bbd28, bbd29, bbd30).joinToString(separator = "\n")
                    val precios5 = listOf(p27, p28, p29, p30).joinToString(separator = "\n")

                    // Mostrar los valores obtenidos en los TextViews correspondientes
                    binding.textviewShotsTragos.text = bebidas
                    binding.textviewPreciosDeShotsTragos.text = precios
                    binding.textviewDeVasosCerbezas.text = bebidas2
                    binding.textviewPreciosDeVasosCerbezas.text = precios2
                    binding.textviewBottellas.text = bebidas3
                    binding.textviewPrecioDeBotellas.text = precios3
                    binding.textviewBaldes.text = bebidas4
                    binding.textviewPrecioDeBaldes.text = precios4
                    binding.textviewSinAlcohol.text = bebidas5
                    binding.textviewPrecioDeSinAlcohol.text = precios5

                    // Ocultar la animación de carga una vez obtenidos los datos
                    binding.animation.visibility = View.GONE

                } else {
                    if (_binding != null) {
                        // Mostrar un mensaje de error en caso de fallo en la obtención de datos
                        binding.textviewShotsTragos.text = "Error al obtener precios"
                        // Ocultar la animación de carga
                        binding.animation.visibility = View.GONE
                    }
                }
            }
    }

    /**
     * Método que se llama cuando la vista del fragmento se destruye.
     * Limpia el binding para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Limpieza del binding para evitar fugas de memoria
    }
}
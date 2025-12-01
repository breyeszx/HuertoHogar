package com.brzdev.huertohogar.data

import com.brzdev.huertohogar.model.Category
import com.brzdev.huertohogar.model.Product
import com.brzdev.huertohogar.R
object DataSource {

    val categories = listOf(
        // ... (tus categorías siguen igual) ...
        Category("Frutas Frescas", "Nuestra selección de frutas frescas ofrece una experiencia directa del campo a tu hogar."),
        Category("Verduras Orgánicas", "Descubre nuestra gama de verduras orgánicas, cultivadas sin el uso de pesticidas ni químicos."),
        Category("Productos Orgánicos", "Nuestros productos orgánicos están elaborados con ingredientes naturales y procesados de manera responsable."),
        Category("Productos Lácteos", "Los productos lácteos de Huerto Hogar provienen de granjas locales que se dedican a la producción responsable y de calidad.")
    )

    private const val PACKAGE_NAME = "com.brzdev.huertohogar"

    val products = listOf(

        Product(
            "FR001",
            "Manzanas Fuji",
            1200.0,
            150,
            "Manzanas Fuji crujientes...",
            "kilo",
            "Frutas Frescas",

            "android.resource://$PACKAGE_NAME/${R.drawable.manzanas_fuji}"
        ),
        Product(
            "FR002",
            "Naranjas Valencia",
            1000.0,
            200,
            "Jugosas y ricas en vitamina C...",
            "kilo",
            "Frutas Frescas",
            "android.resource://$PACKAGE_NAME/${R.drawable.naranjas_valencia}"
        ),
        Product(
            "FR003",
            "Plátanos Cavendish",
            800.0,
            250,
            "Plátanos maduros...",
            "kilo",
            "Frutas Frescas",
            "android.resource://$PACKAGE_NAME/${R.drawable.platanos_cavendish}"
        ),


        Product(
            "VR001",
            "Zanahorias Orgánicas",
            900.0,
            100,
            "Zanahorias crujientes...",
            "kilo",
            "Verduras Orgánicas",
            "android.resource://$PACKAGE_NAME/${R.drawable.zanahorias_organicas}"
        ),
        Product(
            "VR002",
            "Espinacas Frescas",
            700.0,
            80,
            "Espinacas frescas...",
            "bolsa de 500g",
            "Verduras Orgánicas",
            "android.resource://$PACKAGE_NAME/${R.drawable.espinacas_frescas}"
        ),
        Product(
            "VR003",
            "Pimientos Tricolores",
            1500.0,
            120,
            "Pimientos rojos, amarillos y verdes...",
            "kilo",
            "Verduras Orgánicas",
            "android.resource://$PACKAGE_NAME/${R.drawable.pimientos_tricolor}"
        ),


        Product(
            "PO001",
            "Miel Orgánica",
            5000.0,
            50,
            "Miel pura y orgánica...",
            "frasco de 500g",
            "Productos Orgánicos",
            "android.resource://$PACKAGE_NAME/${R.drawable.miel_organica}"
        )
    )
}
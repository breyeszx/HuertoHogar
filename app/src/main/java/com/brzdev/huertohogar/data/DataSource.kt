package com.brzdev.huertohogar.data

import com.brzdev.huertohogar.model.Category
import com.brzdev.huertohogar.model.Product

object DataSource {

    val categories = listOf(
        Category(
            "Frutas Frescas",
            "Nuestra selección de frutas frescas ofrece una experiencia directa del campo a tu hogar."
        ), // [cite: 45, 46]
        Category("Verduras Orgánicas", "Descubre nuestra gama de verduras orgánicas, cultivadas sin el uso de pesticidas ni químicos."), // [cite: 50]
        Category("Productos Orgánicos", "Nuestros productos orgánicos están elaborados con ingredientes naturales y procesados de manera responsable."), // [cite: 53]
        Category("Productos Lácteos", "Los productos lácteos de Huerto Hogar provienen de granjas locales que se dedican a la producción responsable y de calidad.")  // [cite: 56]
    )

    val products = listOf(
        // Frutas Frescas
        Product("FR001", "Manzanas Fuji", 1200.0, 150, "Manzanas Fuji crujientes y dulces, cultivadas en el Valle del Maule.", "kilo", "Frutas Frescas", "URL_IMAGEN_MANZANA"),
        Product("FR002", "Naranjas Valencia", 1000.0, 200, "Jugosas y ricas en vitamina C, estas naranjas Valencia son ideales para zumos frescos.", "kilo", "Frutas Frescas", "URL_IMAGEN_NARANJA"),
        Product("FR003", "Plátanos Cavendish", 800.0, 250, "Plátanos maduros y dulces, perfectos para el desayuno o como snack energético.", "kilo", "Frutas Frescas", "URL_IMAGEN_PLATANO"),

        // Verduras Orgánicas
        Product("VR001", "Zanahorias Orgánicas", 900.0, 100, "Zanahorias crujientes cultivadas sin pesticidas en la Región de O'Higgins.", "kilo", "Verduras Orgánicas", "URL_IMAGEN_ZANAHORIA"),
        Product("VR002", "Espinacas Frescas", 700.0, 80, "Espinacas frescas y nutritivas, perfectas para ensaladas y batidos verdes.", "bolsa de 500g", "Verduras Orgánicas", "URL_IMAGEN_ESPINACA"),
        Product("VR003", "Pimientos Tricolores", 1500.0, 120, "Pimientos rojos, amarillos y verdes, ideales para salteados y platos coloridos.", "kilo", "Verduras Orgánicas", "URL_IMAGEN_PIMIENTOS"),

        // Productos Orgánicos
        Product(
            "PO001",
            "Miel Orgánica",
            5000.0,
            50,
            "Miel pura y orgánica producida por apicultores locales.",
            "frasco de 500g",
            "Productos Orgánicos",
            "URL_IMAGEN_MIEL"
        )
    )
}

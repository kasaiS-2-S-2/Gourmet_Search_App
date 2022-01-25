package com.kasai.gourmet_search_app.service.model

data class Gourmet(
    val results: Results
) {
    data class Results(
        val shop: List<Shop>
    ) {
        data class Shop(
            val access: String,
            val address: String,
            val id: String,
            val logo_image: String,
            val name: String,
            val `open`: String,
            val photo: Photo
        ) {
            data class Photo(
                val pc: Pc
            ) {
                data class Pc(
                    val l: String,
                    val m: String,
                    val s: String
                )
            }
        }
    }
}
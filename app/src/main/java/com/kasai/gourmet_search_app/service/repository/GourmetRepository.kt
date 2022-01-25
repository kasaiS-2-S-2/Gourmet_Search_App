package com.kasai.gourmet_search_app.service.repository

import com.kasai.gourmet_search_app.service.model.Gourmet

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val HTTPS_API_GOURMET_API_URL = "https://webservice.recruit.co.jp/hotpepper/gourmet/v1/"

class GourmetRepository {

    companion object Factory {
        val instance: GourmetRepository
            @Synchronized get() {
                return GourmetRepository()
            }
    }

    private val retrofit = Retrofit.Builder()
            .baseUrl(HTTPS_API_GOURMET_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val gourmetService: GourmetService = retrofit.create(GourmetService::class.java)

    suspend fun getGourmetList(key: String, lat: String, lng: String, range: String, format: String): Response<Gourmet> =
        gourmetService.getGourmetList(key, lat, lng, range, format)

    suspend fun getGourmetDetails(key: String, id: String, format: String): Response<Gourmet> =
        gourmetService.getGourmetDetails(key, id, format)
}

package com.kasai.gourmet_search_app.service.repository

import com.kasai.gourmet_search_app.service.model.Gourmet

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GourmetService {

    // お店一覧を取得
    @GET("?")
    suspend fun getGourmetList(
        @Query("key") key: String,
        @Query("lat") lat: String,
        @Query("lng") lng: String,
        @Query("range") range: String,
        @Query("format") format: String
    ): Response<Gourmet>

    // お店の詳細を取得
    @GET("?")
    suspend fun getGourmetDetails(
        @Query("key") key: String,
        @Query("id") id: String,
        @Query("format") format: String
    ): Response<Gourmet>
}

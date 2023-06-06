package com.example.casoscovidus.data.remote

import com.example.casoscovidus.data.remote.services.ReportsService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.covidtracking.com/"

    private val gson = GsonBuilder().create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val reportsService: ReportsService by lazy {
        retrofit.create(ReportsService::class.java)
    }
}
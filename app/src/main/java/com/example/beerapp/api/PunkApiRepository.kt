package com.example.beerapp.api

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

class PunkApiRepository {
    private val PunkClient by lazy {
        Retrofit.Builder()
            .baseUrl(Config.API_ROUTE)
            .client(
                OkHttpClient()
                    .newBuilder()
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(Api::class.java)
    }

    // GET ALL BEERS
    suspend fun getAllBeers(page: Int) = PunkClient.getAllBeers(page)
    //GET ONE BEER
    suspend fun getDetailBeer(id: String) = PunkClient.getDetailBeer(id)
    //SEARCH BEER
    suspend fun searchBeer(name: String) = PunkClient.searchBeer(name)
}
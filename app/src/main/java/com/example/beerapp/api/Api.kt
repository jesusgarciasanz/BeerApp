package com.example.beerapp.api

import com.example.beerapp.pojo.Beer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    //  GET ALL BEERS
    @GET("beers")
    suspend fun getAllBeers(@Query("page")page: Int): Response<MutableList<Beer>>
    // GET DETAIL BEER
    @GET("beers/{id}")
    suspend fun getDetailBeer(@Path("id") id: String): Response<MutableList<Beer>>
    //SEARCH
    @GET("beers")
    suspend fun searchBeer(@Query("beer_name")name: String): Response<MutableList<Beer>>
}
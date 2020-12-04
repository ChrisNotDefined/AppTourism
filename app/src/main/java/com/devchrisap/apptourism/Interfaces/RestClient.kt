package com.devchrisap.apptourism.Interfaces

import com.devchrisap.apptourism.Entities.City
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface RestClient {

    @GET("cities") //localhost:3000/cities
    fun cargarCiudades(): Call<List<City>>

    //@GET("/by-city/{cityId}")

}
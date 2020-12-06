package com.devchrisap.apptourism.Interfaces

import com.devchrisap.apptourism.Entities.City
import com.devchrisap.apptourism.Entities.Destiny
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RestClient {

    /**
     *  Obtener todas las ciudades
     */
    @GET("cities") //localhost:3000/cities
    fun cargarCiudades(): Call<List<City>>

    /**
     * Para obtener el listado de destinos de una ciudad
     * @param cityId El ID de la ciudad
     * */
    @GET("by-city/{cityId}")
    fun cargarDestinosDe(@Path("cityId") cityId: String): Call<List<Destiny>>

    /**
     * Para obtener el destino con ese ID
     * @param destinyId ID del Destino
     */
    @GET("by-id/{destinyId}")
    fun cargarDestino(@Path("destinyId") destinyId: String): Call<Destiny>
}
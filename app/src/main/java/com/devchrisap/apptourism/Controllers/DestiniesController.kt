package com.devchrisap.apptourism.Controllers

import android.util.Log
import com.devchrisap.apptourism.Entities.Destiny
import com.devchrisap.apptourism.Interfaces.RestClient
import com.devchrisap.apptourism.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DestiniesController {
    var destiniesList: List<Destiny> = emptyList()
    var baseUrl: String = "http://localhost:3000"

    fun getDestinies() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val restClient: RestClient = retrofit.create(RestClient::class.java)

        val call: Call<List<Destiny>> = restClient.cargarDestinosDe("1")

        call.enqueue(object : Callback<List<Destiny>> {
            override fun onResponse(call: Call<List<Destiny>>, response: Response<List<Destiny>>) {
                when (response.code()) {
                    200 -> {
                        destiniesList = emptyList()
                        destiniesList = response.body()!!
                        Log.i("Entro http 200", destiniesList.toString())
                    }
                    401 -> {

                    }
                    else -> {

                    }
                }
            }

            override fun onFailure(call: Call<List<Destiny>>, t: Throwable) {
                Log.e("Failed getdestinies", t.toString())
            }
        })

    }
}
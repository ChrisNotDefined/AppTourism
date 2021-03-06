package com.devchrisap.apptourism.Controllers

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.devchrisap.apptourism.Adapters.CityAdapter
import com.devchrisap.apptourism.Entities.City
import com.devchrisap.apptourism.Interfaces.RestClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class CitiesController {
    lateinit var mAdapter: RecyclerView.Adapter<*>
    lateinit var recyclerViewCities: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var context: Context
    var cityList: List<City> = emptyList()
    val baseUrl = "https://lit-lowlands-87518.herokuapp.com"

    fun getCities(
        recyclerView: RecyclerView, context: Context, progressControl: ProgressBar
    ) {

        this.context = context

        progressBar = progressControl
        progressBar.visibility = View.VISIBLE

        recyclerViewCities = recyclerView
        //listCitySearch = ArrayList()


        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val restClient: RestClient = retrofit.create(RestClient::class.java)

        val call: Call<List<City>> = restClient.cargarCiudades()

        call.enqueue(object : Callback<List<City>> {
            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                when (response.code()) {
                    200 -> {
                        Log.i("Entro city 200", cityList.toString())
                        cityList = emptyList()
                        cityList = response.body()!!

                        mAdapter = CityAdapter(cityList)
                        recyclerViewCities.adapter = mAdapter
                        mAdapter!!.notifyDataSetChanged()
                        progressBar.visibility = View.GONE
                    }
                    401 -> {
                    }
                    else -> {
                    }
                }
            }

            override fun onFailure(call: Call<List<City>>, t: Throwable) {
                System.out.println(t)
                progressBar.visibility = View.GONE
            }
        })
    }

    // Retornará el llamado para manejar las respuestas
    fun getCity(cityID: String): Call<City> {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val restClient: RestClient = retrofit.create(RestClient::class.java)

        val call: Call<City> = restClient.obtenerCiudadDe(cityID)

        return call
    }
}
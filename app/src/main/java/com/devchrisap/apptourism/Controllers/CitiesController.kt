package com.devchrisap.apptourism.Controllers

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.devchrisap.apptourism.Entities.City
import com.devchrisap.apptourism.Interfaces.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CitiesController {
    lateinit var mAdapter: RecyclerView.Adapter<*>
    lateinit var recyclerViewCities: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var context: Context
    private val searchQuery = ""
    lateinit var listCitySearch: List<City>
    var cityList: List<City> = emptyList()

    fun getCities(
        recyclerView: RecyclerView, context: Context, query: String, progressControl: ProgressBar) {

        this.context = context

        progressBar = progressControl
        progressBar.visibility = View.VISIBLE

        recyclerViewCities = recyclerView
        listCitySearch = ArrayList()


        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:3000/cities/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val restClient: RestClient = retrofit.create(RestClient::class.java)

        val call: Call<List<City>> = restClient.cargarCiudades()

        call.enqueue(object : Callback<List<City>> {
            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                when (response.code()) {
                    200 -> {
                        Log.i("Entro http 200", cityList.toString())
                        cityList = emptyList()
                        cityList = response.body()!!

                        mAdapter = CitiesAda

                    }
                    401 -> {
                    }
                    else -> {
                    }
                }
            }

            override fun onFailure(call: Call<List<City>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}
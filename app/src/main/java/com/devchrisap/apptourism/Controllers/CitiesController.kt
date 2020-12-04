package com.devchrisap.apptourism.Controllers

import com.devchrisap.apptourism.Entities.City
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CitiesController {
    lateinit var mAdapter: RecyclerView.Adapter<*>
    lateinit var recyclerViewArticulos2: RecyclerView
    lateinit var context: Context
    var cityList: List<City> = emptyList()
    fun getCities() {
        val retofit = Retrofit.Builder()
                .baseUrl("http://localhost:3000/cities/")
                .addConvertFactory(GsonConverterFactory.create())
                .build()

        val restClient: RestClient = retrofit.create(RestClient::class.java)

        val call: Call<List<City>> = restClient.getCities()

        call.enqueue(object: Callback<List<City>> {
            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                when (response.code()) {
                    200 -> {
                        Log.i("Entro http 200", cityList.toString())
                        cityList = emptyList()
                        cityList = response.body()!!

                    }
                }
            }
        })
    }
}
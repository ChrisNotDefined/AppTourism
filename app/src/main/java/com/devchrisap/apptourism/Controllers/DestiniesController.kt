package com.devchrisap.apptourism.Controllers

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.devchrisap.apptourism.Adapters.DestinyAdapter
import com.devchrisap.apptourism.Entities.Destiny
import com.devchrisap.apptourism.Interfaces.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DestiniesController {
    var destiniesList: List<Destiny> = emptyList()
    var baseUrl: String = "https://lit-lowlands-87518.herokuapp.com/"

    lateinit var mAdapter: RecyclerView.Adapter<*>
    lateinit var recyclerViewDestinies: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var context: Context

    fun getDestiniesFrom(
        cityId: String,
        recyclerView: RecyclerView,
        context: Context,
        progressControl: ProgressBar
    ) {

        this.context = context

        this.progressBar = progressControl
        progressBar.visibility = View.VISIBLE

        recyclerViewDestinies = recyclerView

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val restClient: RestClient = retrofit.create(RestClient::class.java)

        Log.i("ASKING FOR: ", cityId)
        val call: Call<List<Destiny>> = restClient.cargarDestinosDe(cityId)

        call.enqueue(object : Callback<List<Destiny>> {
            override fun onResponse(call: Call<List<Destiny>>, response: Response<List<Destiny>>) {
                when (response.code()) {
                    200 -> {
                        Log.i("Entro http 200", destiniesList.toString())
                        destiniesList = emptyList()
                        destiniesList = response.body()!!

                        mAdapter = DestinyAdapter(destiniesList)
                        recyclerViewDestinies.adapter = mAdapter
                        mAdapter!!.notifyDataSetChanged()
                        progressControl.visibility = View.GONE
                    }
                    401 -> {

                    }
                    else -> {
                        Log.e("Failed fetch", response.code().toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<Destiny>>, t: Throwable) {
                Log.e("Failed getdestinies", t.toString())
                progressControl.visibility = View.GONE
            }
        })

    }

    fun getDestinyById(
        destinyId: String
    ): Call<Destiny> {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val restClient: RestClient = retrofit.create(RestClient::class.java)

        Log.i("ASKING FOR: ", destinyId)
        val call: Call<Destiny> = restClient.cargarDestino(destinyId)

        return call

    }
}
package com.devchrisap.apptourism

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.devchrisap.apptourism.Controllers.CitiesController
import com.devchrisap.apptourism.Controllers.DestiniesController
import com.devchrisap.apptourism.Entities.City
import com.squareup.picasso.Picasso

import com.devchrisap.apptourism.Entities.User
import com.devchrisap.apptourism.Entities.userFavorites
import com.devchrisap.apptourism.Models.DbUserFavorites
import com.devchrisap.apptourism.Models.DbUsers
import io.sulek.ssml.SSMLLinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.lista_destinos.*
import kotlinx.android.synthetic.main.lista_destinos.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var cityId: String
class DestinyListActivity : Fragment() {
    lateinit var destiniesController: DestiniesController
    lateinit var citiesController: CitiesController
    private lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var cityId: String
    lateinit var citySelected: City


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        cityId = arguments?.getString("cityID")!!
        var destinyView: View = inflater.inflate(R.layout.lista_destinos, container, false)

        loadData(destinyView)

        return destinyView
    }

    private fun loadData(destinyView: View) {
        destinyView.lstDestinies.setHasFixedSize(true)

        citiesController = CitiesController()
        val call: Call<City> = citiesController.getCity(cityId)

        call.enqueue(object : Callback<City> {
            override fun onResponse(call: Call<City>, response: Response<City>) {
                when(response.code()){
                    200 -> {
                        citySelected = response.body()!!
                        Log.i("Catched data: ", citySelected.toString())
                        txtCityName.text = citySelected!!.name
                        if(citySelected!!.imageUrl != ""){
                            Picasso.get().load(citySelected!!.imageUrl).into(imgDestinyBig)
                        }
                    }
                    404 -> {

                    }
                    else -> {
                        Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<City>, t: Throwable) {
                System.out.println(t)
            }

        })

        layoutManager = SSMLLinearLayoutManager(context!!.applicationContext)
        destinyView.lstDestinies.layoutManager = layoutManager

        destiniesController = DestiniesController()

        destiniesController.getDestiniesFrom(
            cityId,
            destinyView.lstDestinies,
            context!!.applicationContext,
            destinyView.pgBarLoading2
        )
    }
}
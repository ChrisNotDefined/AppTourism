package com.devchrisap.apptourism

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.devchrisap.apptourism.Controllers.CitiesController
import io.sulek.ssml.SSMLLinearLayoutManager
import kotlinx.android.synthetic.main.lista_ciudades.*

class CityListActivity : AppCompatActivity() {

    lateinit var citiesController: CitiesController

    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_ciudades)

        loadData()

    }

    private fun loadData() {
        lstCities.setHasFixedSize(true)

        layoutManager = SSMLLinearLayoutManager(this)
        lstCities.layoutManager = layoutManager

        citiesController = CitiesController()

        citiesController.getCities(lstCities, applicationContext, "", pgBarLoading)
    }


}
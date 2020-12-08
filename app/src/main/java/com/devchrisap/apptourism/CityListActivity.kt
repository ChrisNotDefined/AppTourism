package com.devchrisap.apptourism

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.devchrisap.apptourism.Controllers.CitiesController
import io.sulek.ssml.SSMLLinearLayoutManager
import kotlinx.android.synthetic.main.lista_ciudades.view.*

class CityListActivity : Fragment() {

    lateinit var citiesController: CitiesController

    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var cityView: View = inflater.inflate(R.layout.lista_ciudades, container, false)
        loadData(cityView)
        return cityView
    }


    private fun loadData(cityView: View) {
        cityView.lstCities.setHasFixedSize(true)

        layoutManager = SSMLLinearLayoutManager(context!!.applicationContext)
        cityView.lstCities.layoutManager = layoutManager

        citiesController = CitiesController()

        citiesController.getCities(cityView.lstCities, context!!.applicationContext, cityView.pgBarLoading)
    }


}
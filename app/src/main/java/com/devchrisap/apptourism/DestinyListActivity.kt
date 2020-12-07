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
import com.devchrisap.apptourism.Controllers.DestiniesController
import com.devchrisap.apptourism.Entities.User
import com.devchrisap.apptourism.Entities.userFavorites
import com.devchrisap.apptourism.Models.DbUserFavorites
import com.devchrisap.apptourism.Models.DbUsers
import io.sulek.ssml.SSMLLinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.lista_destinos.*
import kotlinx.android.synthetic.main.lista_destinos.view.*

lateinit var cityId: String
class DestinyListActivity : Fragment() {
    lateinit var destiniesController: DestiniesController
    private lateinit var layoutManager: RecyclerView.LayoutManager


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
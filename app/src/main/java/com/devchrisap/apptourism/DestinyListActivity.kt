package com.devchrisap.apptourism

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.devchrisap.apptourism.Controllers.DestiniesController
import io.sulek.ssml.SSMLLinearLayoutManager
import kotlinx.android.synthetic.main.lista_destinos.*

class DestinyListActivity : AppCompatActivity() {
    lateinit var destiniesController: DestiniesController
    private lateinit var layoutManager: RecyclerView.LayoutManager
    var cityId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.lista_destinos)
        cityId = intent.getIntExtra("CityID", 0)
        Log.i("EXTRA", cityId.toString())
        loadData()
    }

    private fun loadData() {
        lstDestinies.setHasFixedSize(true)

        layoutManager = SSMLLinearLayoutManager(this)
        lstDestinies.layoutManager = layoutManager

        destiniesController = DestiniesController()

        destiniesController.getDestiniesFrom(cityId!!.toString(), lstDestinies, applicationContext, pgBarLoading2)
    }
}
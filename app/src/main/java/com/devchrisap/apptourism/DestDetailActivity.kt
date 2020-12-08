package com.devchrisap.apptourism

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devchrisap.apptourism.Controllers.CitiesController
import com.devchrisap.apptourism.Controllers.DestiniesController
import com.devchrisap.apptourism.Entities.City
import com.devchrisap.apptourism.Entities.Destiny
import retrofit2.Call

class DestDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalle_destinos)
        var destinyId = intent.getStringExtra("DestinyID")
        var destiniesController = DestiniesController()
        // destiniesController.getDestinieById(destinyId, this)
    }
}
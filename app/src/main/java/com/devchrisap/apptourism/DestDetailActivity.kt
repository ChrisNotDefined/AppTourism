package com.devchrisap.apptourism

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devchrisap.apptourism.Controllers.DestiniesController
import com.devchrisap.apptourism.Entities.Destiny
import com.devchrisap.apptourism.Entities.userFavorites
import com.devchrisap.apptourism.Models.DbUserFavorites
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detalle_destinos.*
import kotlinx.android.synthetic.main.detalle_destinos.view.*
import kotlinx.android.synthetic.main.item_destiny_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestDetailActivity : Fragment() {

    lateinit var destinyId: String
    var cityId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        destinyId = arguments?.getString("destId")!!
        var detailView: View = inflater.inflate(R.layout.detalle_destinos, container, false)
        loadData(detailView)
        return detailView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prgDetailDestiny.visibility = View.VISIBLE
    }

    private fun loadData(view: View) {
        var destiniesController = DestiniesController()
        var call = destiniesController.getDestinyById(destinyId)

        call.enqueue(object : Callback<Destiny> {
            override fun onResponse(call: Call<Destiny>, response: Response<Destiny>) {
                when (response.code()) {
                    200 -> {
                        var destiny = response.body()!!
                        toolBarDestDetail.title = destiny.name
                        rtDestiny.rating = destiny.score
                        txtDescriptionDestiny.text = destiny.description

                        if(destiny.image != ""){
                            Picasso.get().load(destiny.image).into(imgDestinyDetail)
                        }

                        val datasource = DbUserFavorites(com.devchrisap.apptourism.Adapters.context!!.applicationContext)
                        cityId = destiny.cityId
                        var favorite = userFavorites(userProfile.id, destinyId, destiny.cityId)
                        val cursor = datasource.added(favorite)
                        if (cursor.count > 0) {
                            view.btnAddFavorite.text = "Eliminar de favoritos"
                        } else {
                            view.btnAddFavorite.text = "Agregar a favoritos"
                        }

                        prgDetailDestiny.visibility = View.GONE
                    }
                    404 -> {
                        txtDescriptionDestiny.text = "Resource not Found 404"
                        prgDetailDestiny.visibility = View.GONE
                    }
                    else -> {

                    }
                }
            }

            override fun onFailure(call: Call<Destiny>, t: Throwable) {
                txtDescriptionDestiny.text = t.message
                prgDetailDestiny.visibility = View.GONE
            }

        })

        btnAddFavorite.setOnClickListener {
            if(btnAddFavorite.text.toString().equals("Agregar a favoritos")) {
                val datasource = DbUserFavorites(com.devchrisap.apptourism.Adapters.context!!.applicationContext)
                var favorite = userFavorites(userProfile.id, destinyId, cityId)
                datasource.newUserFavoritesPlaces(favorite)
                btnAddFavorite.text = "Eliminar de favoritos"
            }
            else {
                val datasource = DbUserFavorites(com.devchrisap.apptourism.Adapters.context!!.applicationContext)
                var favorite = userFavorites(userProfile.id, destinyId, cityId)
                datasource.deleteUserFavorite(favorite)
                btnAddFavorite.text = "Agregar a favoritos"
            }
        }


    }
}
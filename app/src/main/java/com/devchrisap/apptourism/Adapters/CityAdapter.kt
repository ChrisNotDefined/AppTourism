package com.devchrisap.apptourism.Adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.devchrisap.apptourism.DestinyListActivity
import com.devchrisap.apptourism.Entities.City
import com.devchrisap.apptourism.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.sulek.ssml.OnSwipeListener
import io.sulek.ssml.SimpleSwipeMenuLayout

class CityAdapter(private val cityModel: List<City>) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_city_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = cityModel[position].name
        val climate = cityModel[position].climate
        val budget = cityModel[position].budget
        val id = cityModel[position]._id
        val imageUrl = cityModel[position].imageUrl

        holder.title.text = name
        holder.climate.text = climate
        holder.budget.text = "$$budget pesos"

        if (imageUrl != "") {
            Picasso.get().load(imageUrl).into(holder.imgCity)
        }


        holder.container.setOnClickListener {
            var fragment = DestinyListActivity()
            fragment.arguments = Bundle()
            fragment.arguments!!.putString("cityID", id.toString())
            (it.context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return cityModel.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val container = v.findViewById<View>(R.id.itemLista) as LinearLayout

        val title: TextView = v.findViewById<View>(R.id.lblCityTitle) as TextView
        val climate: TextView = v.findViewById<View>(R.id.lblClimate) as TextView
        val budget: TextView = v.findViewById<View>(R.id.lblBudget) as TextView
        val imgCity: CircleImageView = v.findViewById<View>(R.id.cities_image) as CircleImageView
    }
}
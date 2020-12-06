package com.devchrisap.apptourism.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devchrisap.apptourism.Entities.City
import com.devchrisap.apptourism.R
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

        holder.title.text = name
        holder.climate.text = climate
        holder.budget.text = "$$budget pesos"

        holder.container.setOnSwipeListener(object : OnSwipeListener {
            override fun onSwipe(isExpanded: Boolean) {
                cityModel[position].isExpanded = isExpanded
            }
        })

        holder.container.apply(cityModel[position].isExpanded)
    }

    override fun getItemCount(): Int {
        return cityModel.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val container = v.findViewById<View>(R.id.swipeContainer) as SimpleSwipeMenuLayout

        val title: TextView = v.findViewById<View>(R.id.lbltitulo) as TextView
        val climate: TextView = v.findViewById<View>(R.id.lblClimate) as TextView
        val budget: TextView = v.findViewById<View>(R.id.lblBuget) as TextView

    }
}
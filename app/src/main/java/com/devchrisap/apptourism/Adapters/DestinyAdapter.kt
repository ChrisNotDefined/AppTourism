package com.devchrisap.apptourism.Adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.devchrisap.apptourism.DestDetailActivity
import com.devchrisap.apptourism.Entities.Destiny
import com.devchrisap.apptourism.Entities.userFavorites
import com.devchrisap.apptourism.Models.DbUserFavorites
import com.devchrisap.apptourism.R
import com.devchrisap.apptourism.userProfile
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.sulek.ssml.OnSwipeListener
import io.sulek.ssml.SimpleSwipeMenuLayout
import kotlinx.android.synthetic.main.item_destiny_list.view.*


var context: Context? = null

class DestinyAdapter(private val destinyModel: List<Destiny>) :
    RecyclerView.Adapter<DestinyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_destiny_list, parent, false)
        context = parent.context
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val id = destinyModel[position]._id
        val score = destinyModel[position].score
        val imageUrl = destinyModel[position].image
        val name = destinyModel[position].name
        val cityId = destinyModel[position].cityId
        val datasource = DbUserFavorites(context!!.applicationContext)
        holder.title.text = name
        holder.rating.text = "$score estrellas"
        var favorite = userFavorites(userProfile.id, id, cityId)
        val cursor = datasource.added(favorite)
        if (cursor.count > 0) {
            holder.container.isFavorite.setImageResource(R.drawable.ic_estrella_full)
        } else {
            holder.container.isFavorite.setImageResource(R.drawable.ic_estrella_empty)
        }

        if (imageUrl != "") {
            Picasso.get().load(imageUrl).into(holder.imgDest)
        }

        holder.container.setOnSwipeListener(object : OnSwipeListener {
            override fun onSwipe(isExpanded: Boolean) {
                destinyModel[position].isExpanded = isExpanded
            }
        })

        holder.container.setOnClickListener {
            var fragment = DestDetailActivity()
            fragment.arguments = Bundle()
            fragment.arguments!!.putString("destId", id)
            (it.context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit()
        }

        holder.container.apply(destinyModel[position].isExpanded)

        holder.container.isFavorite.setOnClickListener {
            val cursor = datasource.added(favorite)
            if (cursor.count > 0) {
                datasource.deleteUserFavorite(favorite)
                Toast.makeText(
                    context!!.applicationContext, "Eliminado de favoritos",
                    Toast.LENGTH_SHORT
                ).show()
                holder.container.isFavorite.setImageResource(R.drawable.ic_estrella_empty)
            } else {
                datasource.newUserFavoritesPlaces(favorite)
                Toast.makeText(
                    context!!.applicationContext, "Agregado a favoritos",
                    Toast.LENGTH_SHORT
                ).show()
                holder.container.isFavorite.setImageResource(R.drawable.ic_estrella_full)
            }
        }
    }

    override fun getItemCount(): Int {
        return destinyModel.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val container = v.findViewById<View>(R.id.destSwipeContainer) as SimpleSwipeMenuLayout

        val title: TextView = v.findViewById<View>(R.id.lblDestTitle) as TextView
        val rating: TextView = v.findViewById<View>(R.id.lblRating) as TextView
        val imgDest: CircleImageView = v.findViewById<View>(R.id.destinies_image) as CircleImageView
    }

    private fun addFavorite() {

    }
}
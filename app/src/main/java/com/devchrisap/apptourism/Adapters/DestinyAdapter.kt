package com.devchrisap.apptourism.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.devchrisap.apptourism.*
import com.devchrisap.apptourism.Entities.Destiny
import com.devchrisap.apptourism.Entities.User
import com.devchrisap.apptourism.Entities.userFavorites
import com.devchrisap.apptourism.Models.DbUserFavorites
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.sulek.ssml.OnSwipeListener
import io.sulek.ssml.SimpleSwipeMenuLayout
import kotlinx.android.synthetic.main.item_destiny_list.view.*

var context: Context? = null
var idPlace : String = ""
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
        idPlace = id
        val score = destinyModel[position].score
        val imageUrl = destinyModel[position].image
        val name = destinyModel[position].name

        holder.title.text = name
        holder.rating.text = "$score estrellas"

        if (imageUrl != "") {
            Picasso.get().load(imageUrl).into(holder.imgDest)
        }

        holder.container.setOnSwipeListener(object : OnSwipeListener {
            override fun onSwipe(isExpanded: Boolean) {
                destinyModel[position].isExpanded = isExpanded
            }
        })

        holder.container.setOnClickListener {
            Toast.makeText(it.context, "Going to $name", Toast.LENGTH_SHORT).show()
            var intent = Intent(it.context, DestDetailActivity::class.java)
            intent.putExtra("DestinyID", id)
            it.context.startActivity(intent)
        }

        holder.container.apply(destinyModel[position].isExpanded)
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
        var favorite = userFavorites(userProfile.id, idPlace , cityId.toInt())
        val datasource = DbUserFavorites(context!!.applicationContext)
        val cursor =  datasource.added(favorite)
        if(cursor.count > 0) {
            datasource.deleteUserFavorite(favorite)
            Toast.makeText(context!!.applicationContext,"Eliminado de favoritos",
                Toast.LENGTH_SHORT).show()
        }
        else {
            datasource.newUserFavoritesPlaces(favorite)
            Toast.makeText(context!!.applicationContext,"Agregado a favoritos",
                Toast.LENGTH_SHORT).show()
        }
    }
}
package com.devchrisap.apptourism

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.content_layout.*
import kotlinx.android.synthetic.main.navview_principal.*

class NavviewPrincipal: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navview_principal)
        var actual : String = "Lista de ciudades"
        var next : String = ""

        setSupportActionBar(appbar)
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, CityListActivity())
            .commit()
        supportActionBar!!.title = "Lista de ciudades"

        navview.menu.getItem(0).isChecked = true

        navview.setNavigationItemSelectedListener { menuItem ->
            var fragmentTransation =  false
            var fragment : Fragment? =  null

            when(menuItem.itemId){

                R.id.listaCiudades -> {
                    next = menuItem.title.toString()
                        fragment = CityListActivity()
                        fragmentTransation =  true
                }

                R.id.salir -> {
                    finish()
                }

            }

            if(fragmentTransation){
                if(actual != next) {
                    actual = next
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment!!)
                        .commit()

                    menuItem.isChecked = true
                    Toast.makeText(applicationContext,"" + menuItem.title,
                        Toast.LENGTH_SHORT).show()
                    supportActionBar!!.title = menuItem.title
                }
            }

            drawer_layout.closeDrawers()
            true
        }
    }
}
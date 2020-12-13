package com.devchrisap.apptourism

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.devchrisap.apptourism.Services.ImageService
import kotlinx.android.synthetic.main.content_layout.*
import kotlinx.android.synthetic.main.header_navview.*
import kotlinx.android.synthetic.main.header_navview.view.*
import kotlinx.android.synthetic.main.navview_principal.*

var activityFather : Activity? = null
class NavviewPrincipal: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navview_principal)
        var actual : String = "Lista de ciudades"
        var next : String = ""
        activityFather = this

        setSupportActionBar(appbar)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, CityListActivity())
            .commit()
        supportActionBar!!.title = "Lista de ciudades"

        navview.menu.getItem(0).isChecked = true

        navview.getHeaderView(0).txtnavUsername.text = userProfile.userName
        if(userProfile.imgBase64 != "") {
            var imageService = ImageService()
            navview.getHeaderView(0).imgHeaderNav.setImageBitmap(imageService.bitmap(userProfile.imgBase64))
        }



        navview.setNavigationItemSelectedListener { menuItem ->
            var fragmentTransaction =  false
            var fragment : Fragment? =  null

            when(menuItem.itemId){

                R.id.listaCiudades -> {
                    next = menuItem.title.toString()
                    fragment = CityListActivity()
                    fragmentTransaction = true
                }

                R.id.actualizar -> {
                    next = menuItem.title.toString()
                    fragment = UserDetailFragment()
                    fragmentTransaction = true
                }

                R.id.salir -> {
                    finish()
                }

            }

            if(fragmentTransaction){
                if(actual != next) {
                    actual = next
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment!!)
                        .addToBackStack(null)
                        .commit()

                    menuItem.isChecked = true
                    supportActionBar!!.title = menuItem.title
                }
            }

            drawer_layout.closeDrawers()

            true
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            navview.getHeaderView(0).txtnavUsername.text = userProfile.userName
            if(userProfile.imgBase64 != "") {
                var imageService = ImageService()
                navview.getHeaderView(0).imgHeaderNav.setImageBitmap(imageService.bitmap(userProfile.imgBase64))
            }
            drawer_layout.openDrawer(Gravity.LEFT)
        }
        return true
    }
}
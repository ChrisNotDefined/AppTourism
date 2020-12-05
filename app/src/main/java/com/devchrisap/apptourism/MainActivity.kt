package com.devchrisap.apptourism

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.devchrisap.apptourism.Entities.User
import com.devchrisap.apptourism.Models.DbUsers
import kotlinx.android.synthetic.main.activity_main.*

var userProfile = User(0, "", "", "")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun login(view: View) {
        val datasource = DbUsers(this)
        val cursor =  datasource.login(txtUserName.text.toString(), txtPassword.text.toString())
        if(cursor.count > 0) {
            while(cursor.moveToNext()){
                userProfile = User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3))
            }
            Toast.makeText(applicationContext,"Bienvenido " + userProfile.userName,
                Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(applicationContext,"No existe el usuario",
                Toast.LENGTH_SHORT).show()
        }
    }
}
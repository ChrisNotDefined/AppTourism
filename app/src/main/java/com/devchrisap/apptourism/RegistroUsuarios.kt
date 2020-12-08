package com.devchrisap.apptourism

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ablanco.imageprovider.ImageProvider
import com.ablanco.imageprovider.ImageSource
import com.devchrisap.apptourism.Entities.User
import com.devchrisap.apptourism.Models.DbUsers
import com.devchrisap.apptourism.Services.ImageService
import kotlinx.android.synthetic.main.registro_usuarios.*
import kotlinx.android.synthetic.main.registro_usuarios.txtUserName
import java.lang.Exception

var AppContext : Context? = null
var imagen : Bitmap? = null

class RegistroUsuarios: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_usuarios)

        imgUser.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
                AppContext = applicationContext

                ImageProvider(this).getImage(ImageSource.CAMERA){ bitmap ->
                    imagen = bitmap
                    imgUser.setImageBitmap(bitmap)
                }

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    42424
                )
            }
        }
    }

    fun register(view: View) {
        if(txtPassword2.text.toString().isEmpty() || txtPassword3.text.toString().isEmpty() || txtUserName.text.toString().isEmpty() || imagen == null) {
            Toast.makeText(applicationContext,"Faltan datos",
                Toast.LENGTH_SHORT).show()
        }
        else {
            if(txtPassword2.text.toString().equals(txtPassword3.text.toString())){
                val datasource = DbUsers(this)
                val cursor =  datasource.detectUserName(txtUserName.text.toString())
                if(cursor.count > 0) {
                    Toast.makeText(applicationContext,"Nombre de usuario ya existente",
                        Toast.LENGTH_SHORT).show()
                }
                else {
                    var imageService = ImageService()
                    var user = User(0, txtUserName.text.toString(), txtPassword2.text.toString(), imageService.base64(imagen).toString())
                    datasource.newUser(user.userName, user.password, user.imgBase64)
                    userProfile = user
                    var intent = Intent(this, NavviewPrincipal::class.java)
                    startActivity(intent)
                }
            }
            else {
                Toast.makeText(applicationContext,"Contraseñas diferentes",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}
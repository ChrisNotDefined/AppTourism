package com.devchrisap.apptourism

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.ablanco.imageprovider.ImageProvider
import com.ablanco.imageprovider.ImageSource
import com.devchrisap.apptourism.Entities.User
import com.devchrisap.apptourism.Models.DbUsers
import com.devchrisap.apptourism.Services.ImageService
import kotlinx.android.synthetic.main.edit_usuarios.*
import kotlinx.android.synthetic.main.edit_usuarios.view.*
import kotlinx.android.synthetic.main.registro_usuarios.*

var imagen2 : Bitmap? = null
class UserDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var userDetailView: View = inflater.inflate(R.layout.edit_usuarios, container, false)
        var imageService = ImageService()
        if(!userProfile.imgBase64.equals("")) {
            userDetailView.imgUser2.setImageBitmap(imageService.bitmap(userProfile.imgBase64))
        }

        userDetailView.txtusername.setText(userProfile.userName)


        userDetailView.imgUser2.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(context!!.applicationContext, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
                AppContext = context!!.applicationContext

                ImageProvider(activityFather!!).getImage(ImageSource.CAMERA){ bitmap ->
                    imagen2 = bitmap
                    imgUser2.setImageBitmap(bitmap)
                }

            } else {
                ActivityCompat.requestPermissions(
                    activityFather!!,
                    arrayOf(Manifest.permission.CAMERA),
                    42424
                )
            }
        }


        userDetailView.button4.setOnClickListener {
            if(userDetailView.txtPassword4.text.toString().isEmpty() ||
                userDetailView.txtPassword5.text.toString().isEmpty() ||
                userDetailView.txtPassword6.text.toString().isEmpty() ||
                userDetailView.txtusername.text.toString().isEmpty()) {
                Toast.makeText(context!!.applicationContext,"Faltan datos",
                    Toast.LENGTH_SHORT).show()
            }
            else {
                if(userDetailView.txtPassword5.text.toString().equals(userDetailView.txtPassword6.text.toString())){
                    val datasource = DbUsers(context!!.applicationContext)
                    var cursor =  datasource.detectUserName(userDetailView.txtusername.text.toString())
                    if(cursor.count > 0 ) {
                        if(userDetailView.txtusername.text.toString().equals(userProfile.userName)) {
                            if(userDetailView.txtPassword4.text.toString().equals(userProfile.password)) {
                                var imageService = ImageService()
                                if(imagen2 != null) {
                                    userProfile.imgBase64 = imageService.base64(imagen2).toString()
                                }
                                var user = User(userProfile.id,
                                    userDetailView.txtusername.text.toString(),
                                    userDetailView.txtPassword5.text.toString(),
                                    userProfile.imgBase64)
                                datasource.updateUser(user)
                                userProfile = user
                                Toast.makeText(context!!.applicationContext,"Perfil actualizado",
                                    Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Toast.makeText(context!!.applicationContext,"Contraseña anterior incorrecta",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                        else {
                            Toast.makeText(context!!.applicationContext,"Nombre de usuario ya existente",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        if(userDetailView.txtPassword4.text.toString().equals(userProfile.password)) {
                            var imageService = ImageService()
                            if(imagen2 != null) {
                                userProfile.imgBase64 = imageService.base64(imagen2).toString()
                            }
                            var user = User(userProfile.id,
                                userDetailView.txtusername.text.toString(),
                                userDetailView.txtPassword5.text.toString(),
                                userProfile.imgBase64)
                            datasource.updateUser(user)
                            userProfile = user
                            Toast.makeText(context!!.applicationContext,"Perfil actualizado",
                                Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(context!!.applicationContext,"Contraseña anterior incorrecta",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    Toast.makeText(context!!.applicationContext,"Contraseñas diferentes",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

        userDetailView.btnDeleteUser.setOnClickListener {
            val builder = AlertDialog.Builder(userDetailView.context)
            builder.setMessage("¿Desea eliminar su perfil?")
                .setPositiveButton("CONTINUAR",
                    DialogInterface.OnClickListener { dialog, id ->
                        val datasource = DbUsers(context!!.applicationContext)
                        datasource.deleteUser(userProfile.id)
                        activityFather!!.finish()
                    })
                .setNegativeButton("CANCELAR",
                    DialogInterface.OnClickListener { dialog, id ->

                    })
            // Create the AlertDialog object and return it
            builder.create().show()

        }
        return userDetailView
    }

}
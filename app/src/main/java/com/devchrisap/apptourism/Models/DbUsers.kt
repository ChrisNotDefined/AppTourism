package com.devchrisap.apptourism.Models

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.devchrisap.apptourism.Entities.User

class DbUsers(context: Context) {
    private val openHelper: DBOpenHelper = DBOpenHelper(context)
    private val database: SQLiteDatabase

    init {
        database =  openHelper.writableDatabase
    }

    fun getUserById(id: Int): Cursor {
        return database.rawQuery("""
            select id, userName, password, imgBase64 
              from tblUsers
                where id =  $id
        """.trimIndent(),null)
        database.close()
    }

    fun login(userName: String, password: String): Cursor {
        return database.rawQuery("""
            select id, userName, password, imgBase64 
              from tblUsers
                where userName =  '$userName' and 
                password = '$password'
        """.trimIndent(),null)
        database.close()
    }

    fun newUser(userName: String, password: String, imageBase64: String){
        database.rawQuery("""
            insert into tblUsers values 
                        (null, '$userName', '$password', '$imageBase64')
        """.trimIndent(), null)
    }

    fun newUser(user: User){
        database.rawQuery(
            """
            update tblUsers set 
                        userName = '${user.userName}', password = '${user.password}', imgBase64 = '${user.imgBase64}' 
                        where id = ${user.id}
        """.trimIndent(), null
        )
    }

    fun deleteUser(id: Int){
        database.rawQuery(
            """
            delete from tblUsers 
                        where id = $id
        """.trimIndent(), null
        )
    }
}
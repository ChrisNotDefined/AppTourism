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
        database.execSQL("""
            insert into tblUsers values 
                        (null, '$userName', '$password', '$imageBase64')
        """.trimIndent())
        database.close()
    }

    fun updateUser(user: User){
        database.execSQL(
            """
            update tblUsers set 
                        userName = '${user.userName}', password = '${user.password}', imgBase64 = '${user.imgBase64}' 
                        where id = ${user.id}
        """.trimIndent()
        )
        database.close()
    }

    fun deleteUser(id: Int){
        database.execSQL(
            """
            delete from tblUsers 
                        where id = $id
        """.trimIndent()
        )
        database.close()
    }

    fun detectUserName(userName: String): Cursor {
        return database.rawQuery(
            """
            select * from tblUsers 
                        where userName = '$userName'
        """.trimIndent(), null
        )
        database.close()
    }

    fun getAllUsers(): Cursor {
        return database.rawQuery(
            """
            select * from tblUsers 
        """.trimIndent(), null
        )
        database.close()
    }
}
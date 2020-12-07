package com.devchrisap.apptourism.Models

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.devchrisap.apptourism.Entities.userFavorites

class DbUserFavorites(context: Context) {
    private val openHelper: DBOpenHelper = DBOpenHelper(context)
    private val database: SQLiteDatabase

    init {
        database =  openHelper.writableDatabase
    }

    fun getUserFavoritesPlaces(idUser: Int): Cursor {
        return database.rawQuery("""
            select * 
              from tblUserFavorites
                where idUser =  $idUser
        """.trimIndent(),null)
        database.close()
    }

    fun newUserFavoritesPlaces(userFavorite: userFavorites) {
        database.execSQL("""
            insert into tblUserFavorites values 
                (${userFavorite.idUser}, '${userFavorite.idPlace}', ${userFavorite.idCity})
        """.trimIndent())
        database.close()
    }

    fun deleteUserFavorite(userFavorite: userFavorites) {
        database.execSQL("""
            delete from tblUserFavorites where 
                idUser = ${userFavorite.idUser} and idPlace = '${userFavorite.idPlace}' and idCity = ${userFavorite.idCity}
        """.trimIndent())
        database.close()
    }
}
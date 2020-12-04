package com.devchrisap.apptourism.Models

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DBName = "destinies.db"
val DBversion = 1

class DBOpenHelper(context: Context) : SQLiteOpenHelper(context, DBName, null, DBversion) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
                """
                    create table tblUsers (
                    id integer primary key autoincrement,
                    userName text not null,
                    password text not null,
                    imgBase64 text not null
                )
                """.trimIndent()
        )

        db!!.execSQL(
                """
                    insert into tblUsers values 
                        (null, 'ChrisNotDefined', 'Chris123', ''),
                        (null, 'Madrajavi', 'jmo69192', ''),
                        (null, 'LaOtraAndrea', 'MeGustaMolestar', '')
                """.trimIndent()
                )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}
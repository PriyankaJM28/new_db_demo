package com.example.new_db_demo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyHelper(context:Context): SQLiteOpenHelper(context,"ACDB",null,1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE ACTABLE(_id integer primary key autoincrement,NAME TEXT,MEANING TEXT)")
        p0?.execSQL("INSERT INTO ACTABLE(NAME,MEANING)VALUES('AWS','AMEZON WEB SERVICES')")
        p0?.execSQL("INSERT INTO ACTABLE(NAME,MEANING)VALUES('DW','DATA WAREHOUSING')")
        p0?.execSQL("INSERT INTO ACTABLE(NAME,MEANING)VALUES('ASP','ACTIVE SERVER PAGES')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}
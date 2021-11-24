package com.example.new_db_demo

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import android.content.ContentValues as ContentValues

class MainActivity() : AppCompatActivity() {
    lateinit var adapter : SimpleCursorAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var edName = findViewById<EditText>(R.id.edName)
        var edMeaning = findViewById<EditText>(R.id.edMeaning)
        var btFirst = findViewById<Button>(R.id.btFirst)
        var btNext = findViewById<Button>(R.id.btNext)
        var btPrev = findViewById<Button>(R.id.btPrev)
        var btLast = findViewById<Button>(R.id.btLast)
        var btInsert = findViewById<Button>(R.id.btInsert)
        var btClear = findViewById<Button>(R.id.btClear)
        var btUpdate = findViewById<Button>(R.id.btUpdate)
        var btDelete = findViewById<Button>(R.id.btDelete)
        var btView = findViewById<Button>(R.id.btView)
        var SearchView3 = findViewById<SearchView>(R.id.searchView3)
        var ListView = findViewById<ListView>(R.id.ListView)

        var helper = MyHelper(applicationContext)
        var p0 = helper.writableDatabase
        var rs = p0.rawQuery("SELECT * FROM ACTABLE",null)

        btFirst.setOnClickListener {
            if(rs.moveToFirst())
            {
                edName.setText(rs.getString(1))
                edMeaning.setText(rs.getString(2))
            }
            else
            {
                Toast.makeText(applicationContext,"data not found",Toast.LENGTH_LONG).show()
            }
        }
        btNext.setOnClickListener {
            if(rs.moveToNext())
            {
                edName.setText(rs.getString(1))
                edMeaning.setText(rs.getString(2))
            }
            else if(rs.moveToLast())
            {
                edName.setText(rs.getString(1))
                edMeaning.setText(rs.getString(2))
            }
            else
            {
                Toast.makeText(applicationContext,"data not found",Toast.LENGTH_LONG).show()
            }
        }
        btPrev.setOnClickListener {
            if(rs.moveToPrevious())
            {
                edName.setText(rs.getString(1))
                edMeaning.setText(rs.getString(2))
            }
            else if(rs.moveToLast())
            {
                edName.setText(rs.getString(1))
                edMeaning.setText(rs.getString(2))
            }
            else
            {
                Toast.makeText(applicationContext,"data not found",Toast.LENGTH_LONG).show()
            }

        }
        btLast.setOnClickListener {
            if(rs.moveToLast())
            {
                edName.setText(rs.getString(1))
                edMeaning.setText(rs.getString(2))
            }
            else if(rs.moveToFirst())
            {
                edName.setText(rs.getString(1))
                edMeaning.setText(rs.getString(2))
            }
            else
            {
                Toast.makeText(applicationContext,"data not found",Toast.LENGTH_LONG).show()
            }

        }
        btInsert.setOnClickListener {

            var cv = ContentValues()
            cv.put("NAME",edName.text.toString())
            cv.put("MEANING",edMeaning.text.toString())
            p0.insert("ACTABLE",null,cv)
            rs.requery()
            //adapter.changeCursor(rs)
            adapter.notifyDataSetChanged()


            var ad = AlertDialog.Builder(this)
            ad.setTitle("Add record")
            ad.setMessage("your record added successfully..!!")
            ad.setPositiveButton("OK",DialogInterface.OnClickListener { dialogInterface, i ->

                edName.setText(" ")
                edMeaning.setText(" ")
                edName.requestFocus()
            })
            ad.show()
        }
        btClear.setOnClickListener {
            edName.setText(" ")
            edMeaning.setText(" ")
            edName.requestFocus()
        }
        btUpdate.setOnClickListener {
            var cv = ContentValues()
            cv.put("NAME",edName.toString())
            cv.put("MEANING",edMeaning.text.toString())
            p0.update("ACTABLE",cv,"_id = ?", arrayOf(rs.getString(0)))
            rs.requery()
            var ad = AlertDialog.Builder(this)
            ad.setTitle("Update record")
            ad.setMessage("your record updated successfully..!!")
            ad.setPositiveButton("OK",DialogInterface.OnClickListener { dialogInterface, i ->
                if(rs.moveToFirst())
                {
                    edName.setText(rs.getString(1))
                    edMeaning.setText(rs.getString(2))

                }
            })
            ad.show()
        }
        btDelete.setOnClickListener {
            p0.delete("ACTABLE","_id = ?", arrayOf(rs.getString(0)))
            rs.requery()
            var ad = AlertDialog.Builder(this)
            ad.setTitle("Delete Record")
            ad.setMessage("your record deleted successfully..!!")
            ad.setPositiveButton("OK",DialogInterface.OnClickListener { dialogInterface, i ->
                if(rs.moveToFirst())
                {
                    edName.setText(rs.getString(1))
                    edMeaning.setText(rs.getString(2))

                }
                else{
                    edName.setText("data is not avilable")
                    edMeaning.setText("data is not aviable")
                }
            })
            ad.show()
        }
        btView.setOnClickListener {
            SearchView3.visibility = View.VISIBLE
            ListView.visibility = View.VISIBLE
        }
        SearchView3.isIconified = true
        SearchView3.queryHint = "Search among ${rs.count} Records"


       adapter = SimpleCursorAdapter(applicationContext,android.R.layout.simple_expandable_list_item_2,rs,
                    arrayOf("NAME","MEANING"),
                    intArrayOf(android.R.id.text1,android.R.id.text2),0)
        ListView.adapter=adapter

    }
}
package com.complicated.goalsappice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private var idList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var lstView = findViewById<ListView>(R.id.lstDisplay)
        val add = findViewById<Button>(R.id.btnAdd)
        val display = findViewById<Button>(R.id.btnDisplay)
        val settings = findViewById<Button>(R.id.btnSettings)

        settings.setOnClickListener {

        }

        lstView.setOnItemClickListener { _, v, position, _ ->
            val intent = Intent(this, GoalEdit::class.java)
            intent.putExtra("id", idList[position])
            startActivity(intent)
        }

        add.setOnClickListener {
            val txt1 = findViewById<EditText>(R.id.txtTitle)
            val txt2 = findViewById<EditText>(R.id.txtDesc)

            val db = DBHelper(this, null)
            val title = txt1.text.toString()
            val desc = txt2.text.toString()

            db.addGoal(title, desc, "no")
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

            txt1.text.clear()
            txt2.text.clear()
        }

        display.setOnClickListener {

            var goals = ArrayList<String>()
            val db = DBHelper(this, null)
            val cursor = db.getData()

            cursor!!.moveToFirst()
            // loop through cursor data
            idList.clear()
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // data from the cursor
                    idList.add(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL)))
                    val title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.title))
                    val desc = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.description))
                    val isCompleted = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.is_completed))

                    // Format data to be displayed in the ListView
                    val goal = "Title: $title\nDescription: $desc\nCompleted: $isCompleted"
                    goals.add(goal)

                } while (cursor.moveToNext())
            }

            // Close the cursor
            cursor?.close()
            //listview
            val arrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, goals)
            lstView.adapter = arrayAdapter

        }

    }
    override fun onResume() {
        super.onResume()
        //fetching the stored data from SharedPreferences
        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val s1 = sh.getString("name", "")
        val a = sh.getInt("age", 0)

        //setting the fetched data in the EditText
        name.setText(s1)
        age.setText(a.toString())

    }
}
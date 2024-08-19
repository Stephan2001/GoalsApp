package com.complicated.goalsappice

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.findViewTreeViewModelStoreOwner

class MainActivity2 : AppCompatActivity() {
    val modes = arrayOf("Dark", "Light")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val saveSetting = findViewById<Button>(R.id.btnSaveSetting)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val chckNoti = findViewById<CheckBox>(R.id.chckNoti)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, modes)
            spinner.adapter = adapter
        }

        saveSetting.setOnClickListener {
            val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
            val myEdit = sharedPreferences.edit()
            var notif = if (chckNoti.isChecked){
                "yes"
            } else{
                "no"
            }
            var modeChoice = if (spinner.selectedItem == "Dark"){
                0
            } else{
                1
            }
            //write all the data entered by the user in sharedPreference and apply
            myEdit.putInt("mode", modeChoice)
            myEdit.putString("notification", notif)
            myEdit.apply()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        //fetching the stored data from SharedPreferences
        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val s1 = sh.getInt("mode", 0)
        val s2 = sh.getString("notification", "")

        //setting the fetched data
        val spinner = findViewById<Spinner>(R.id.spinner)
        val chckNoti = findViewById<CheckBox>(R.id.chckNoti)

        spinner.setSelection(s1)
        chckNoti.isChecked = s2 == "yes"
    }

}
package com.complicated.goalsappice

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GoalEdit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goal_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val editId = (intent.getStringExtra("id"))!!.toInt()
        val txt1 = findViewById<EditText>(R.id.txtGoalTitle)
        val txt2 = findViewById<EditText>(R.id.txtGoalDesc)
        val txt3 = findViewById<EditText>(R.id.txtGoalCompleted)
        val update = findViewById<Button>(R.id.btnGoalSave)
        val delete = findViewById<Button>(R.id.btnGoalDelete)
        val db = DBHelper(this, null)
        update.setOnClickListener {
            val newTitle = txt1.text.toString()
            val newDesc = txt2.text.toString()
            val newComplete = txt3.text.toString()

            if (txt1.text.isNotEmpty() && txt2.text.isNotEmpty() && txt3.text.isNotEmpty()){
                if (newComplete.lowercase() == "yes" || newComplete.lowercase() == "no"){
                    //update
                    val success = db.updateRecord(editId,newTitle,newDesc,newComplete)
                    if (success){
                        Log.d("tst", "Success update")
                        finish()
                    }
                    else
                        Toast.makeText(this, "Error updating record", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Invalid completion", Toast.LENGTH_SHORT).show()
                }
            }
            else
                Toast.makeText(this, "Not all field filled", Toast.LENGTH_SHORT).show()
        }

        delete.setOnClickListener {
            val builder = AlertDialog.Builder(this@GoalEdit)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val success = db.deleteRecord(editId)
                    if (success){
                        Log.d("tst", "Success delete")
                        finish()
                    }
                    else
                        Log.d("tst", "Error deleting record")

                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }
}
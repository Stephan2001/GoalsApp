package com.complicated.goalsappice
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    //CREATE TABLE goals (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, is_completed INTEGER DEFAULT 0);
    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                title + " TEXT," +
                is_completed + " TEXT, " +
                description + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addGoal(titl : String, desc : String, completed:String ){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(title, titl)
        values.put(is_completed, completed)
        values.put(description, desc)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)
        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getData(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }

    fun deleteRecord(id: Int): Boolean {
        val db = this.writableDatabase
        Log.d("tst", "Record to delete" + id)
        val result = db.delete(TABLE_NAME, "$ID_COL = ?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    fun updateRecord(id: Int, newTitle: String, newDesc: String, newIsCompleted: String): Boolean {
        val db = this.writableDatabase

        // Create a ContentValues object to store the new values
        val values = ContentValues()
        values.put(title, newTitle)
        values.put(description, newDesc)
        values.put(is_completed, newIsCompleted)

        // Update the database row with the given ID
        val result = db.update(TABLE_NAME, values, "$ID_COL = ?", arrayOf(id.toString()))

        db.close()
        // Check if the update was successful
        return result > 0
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "GEEKS_FOR_GEEKS"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "gfg_table"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val title = "name"

        val is_completed = "completed"
        // below is the variable for age column
        val description = "desc"
    }
}
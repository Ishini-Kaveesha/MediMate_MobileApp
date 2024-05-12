package com.example.crud_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "medication.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allMedications"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_FREQUENCY = "frequency"
        private const val COLUMN_START_DATE = "start_date"
        private const val COLUMN_DOSAGE = "dosage"
        private const val COLUMN_NOTE = "note"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_FREQUENCY TEXT, $COLUMN_START_DATE TEXT, $COLUMN_DOSAGE TEXT, $COLUMN_NOTE TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery) //remove the table that table name is exist
        onCreate(db) //create new table
    }

    fun insertMedi(medication: Medication){
        val db = writableDatabase
        val values = ContentValues().apply { //store the values associates with column names
            put(COLUMN_NAME, medication.name)
            put(COLUMN_FREQUENCY, medication.frequency)
            put(COLUMN_START_DATE, medication.startDate)
            put(COLUMN_DOSAGE, medication.dosage)
            put(COLUMN_NOTE, medication.note)
        }

        db.insert(TABLE_NAME, null, values) //insert that data to the database
        db.close()
    }

    fun getAllMedications(): List<Medication>{
        //get the data from the database
        val medicationsList = mutableListOf<Medication>()

        val db = readableDatabase

        val query = "SELECT * FROM $TABLE_NAME"

        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val freq = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FREQUENCY))
            val dosage = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOSAGE))
            val startdate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE))
            val note = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE))

            val medication = Medication(id, name, freq, dosage, startdate, note)
            medicationsList.add(medication)
        }

        cursor.close()
        db.close()
        return medicationsList

    }

    fun updateMedication(medication: Medication){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, medication.name)
            put(COLUMN_FREQUENCY, medication.frequency)
            put(COLUMN_START_DATE, medication.startDate)
            put(COLUMN_DOSAGE, medication.dosage)
            put(COLUMN_NOTE, medication.note)
        }

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(medication.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getMedicationById(medicationId: Int): Medication{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $medicationId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val freq = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FREQUENCY))
        val dosage = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOSAGE))
        val startdate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE))
        val note = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE))

        cursor.close()
        db.close()
        return Medication(id, name, freq, dosage, startdate, note)
    }

    fun deleteMedication(medicationId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(medicationId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}
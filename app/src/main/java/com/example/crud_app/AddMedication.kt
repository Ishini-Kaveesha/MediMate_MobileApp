package com.example.crud_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.crud_app.databinding.ActivityAddMedicationBinding

class AddMedication : AppCompatActivity() {

    private lateinit var binding: ActivityAddMedicationBinding
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMedicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val name = binding.titleEditText.text.toString()
            val frequency = binding.titleEditText1.text.toString()
            val start_date = binding.titleEditText2.text.toString()
            val dosage = binding.titleEditText3.text.toString()
            val note = binding.contentEditText.text.toString()
            val medi = Medication(0,name,frequency,start_date,dosage,note)
            db.insertMedi(medi)
            finish()
            Toast.makeText(this,"Medication Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
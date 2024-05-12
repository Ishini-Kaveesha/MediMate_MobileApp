package com.example.crud_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crud_app.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: DatabaseHelper
    private var medicationId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        medicationId = intent.getIntExtra("medication_id",-1)
        if(medicationId == -1){
            finish()
            return
        }

        val medication = db.getMedicationById(medicationId)
        binding.updatetitleEditText.setText(medication.name)
        binding.updatetitleEditText1.setText(medication.frequency)
        binding.updatetitleEditText2.setText(medication.dosage)
        binding.updatetitleEditText3.setText(medication.startDate)
        binding.updatecontentEditText.setText(medication.note)

        binding.editButton.setOnClickListener{
            val newName = binding.updatetitleEditText.text.toString()
            val newfrequency = binding.updatetitleEditText1.text.toString()
            val newstart_date = binding.updatetitleEditText2.text.toString()
            val newdosage = binding.updatetitleEditText3.text.toString()
            val newnote = binding.updatecontentEditText.text.toString()
            val updateMedication = Medication(medicationId, newName, newfrequency, newstart_date, newdosage, newnote)

            db.updateMedication(updateMedication)
            finish()
            Toast.makeText(this,"Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
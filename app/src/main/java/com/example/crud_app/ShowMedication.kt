package com.example.crud_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crud_app.databinding.ActivityShowMedicationBinding

class ShowMedication : AppCompatActivity() {

    private lateinit var binding: ActivityShowMedicationBinding
    private lateinit var db: DatabaseHelper
    private lateinit var medicationAdapter: MedicationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowMedicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        medicationAdapter = MedicationAdapter(db.getAllMedications(),this)

        binding.mediRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mediRecyclerView.adapter = medicationAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this,AddMedication::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        medicationAdapter.refreshData(db.getAllMedications())
    }
}
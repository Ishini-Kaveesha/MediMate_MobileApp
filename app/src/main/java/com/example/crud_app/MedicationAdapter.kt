package com.example.crud_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MedicationAdapter (private var medication: List<Medication>, context: Context) :
    RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>() {

    private val db: DatabaseHelper = DatabaseHelper(context)

    class MedicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val titleTextName: TextView = itemView.findViewById(R.id.titleTextView)
        val titleTextFrequency: TextView = itemView.findViewById(R.id.freqtextView)
        val titleTextViewDosage: TextView = itemView.findViewById(R.id.dosagetextView)
        val titleTextViewStartDate: TextView = itemView.findViewById(R.id.startdatetextView)
        val contentTextView: TextView = itemView.findViewById(R.id.notetextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton )
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton )



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.medication_item,parent, false)
        return  MedicationViewHolder(view)

    }

    override fun getItemCount(): Int = medication.size


    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        val medi = medication[position]
        holder.titleTextName.text = medi.name
        holder.titleTextFrequency.text = medi.frequency
        holder.titleTextViewDosage.text = medi.dosage
        holder.titleTextViewStartDate.text = medi.startDate
        holder.contentTextView.text = medi.note

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("medication_id", medi.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteMedication(medi.id)
            refreshData(db.getAllMedications())
            Toast.makeText(holder.itemView.context, "Medication Deleted", Toast.LENGTH_SHORT).show()

        }
    }

    fun refreshData(newMedications: List<Medication>){
        medication = newMedications
        notifyDataSetChanged()

    }
}
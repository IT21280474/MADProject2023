package com.example.app.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.example.app.models.DesignModel
import com.google.firebase.database.FirebaseDatabase

class DesignDetailsActivity : AppCompatActivity() {
    private lateinit var tvDesignId: TextView
    private lateinit var tvDesignName: TextView
    private lateinit var tvDesignDescription: TextView
    private lateinit var tvDesignPrice: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_design_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("designId").toString(),
                intent.getStringExtra("designName").toString()

            )
        }
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("designId").toString(),
            )
        }
    }
    //Deleting Function
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Designs").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Design data deleted",Toast.LENGTH_LONG).show()

            val intent = Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this,"Deleting Err ${error.message}",Toast.LENGTH_LONG).show()

        }
    }

    private fun initView(){
        tvDesignId = findViewById(R.id.tvDesignId)
        tvDesignName = findViewById(R.id.tvDesignName)
        tvDesignDescription = findViewById(R.id.tvDesignDescription)
        tvDesignPrice = findViewById(R.id.tvDesignPrice)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {

        tvDesignId.text = intent.getStringExtra("designId")
        tvDesignName.text = intent.getStringExtra("designName")
        tvDesignDescription.text = intent.getStringExtra("designDescription")
        tvDesignPrice.text = intent.getStringExtra("designPrice")

    }
    //Updating Function
    private fun openUpdateDialog(
        designId: String,
        designName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDialogView)

        val etDesignName = mDialogView.findViewById<EditText>(R.id.etDesignName)
        val etDesignDescription = mDialogView.findViewById<EditText>(R.id.etDesignDescription)
        val etDesignPrice = mDialogView.findViewById<EditText>(R.id.etDesignPrice)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etDesignName.setText(intent.getStringExtra("designName").toString())
        etDesignDescription.setText(intent.getStringExtra("designDescription").toString())
        etDesignName.setText(intent.getStringExtra("designPrice").toString())

        mDialog.setTitle("Updating $designName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateDesignData(
                designId,
                etDesignName.text.toString(),
                etDesignDescription.text.toString(),
                etDesignPrice.text.toString()

            )
            Toast.makeText(applicationContext,"Design Data Updated", Toast.LENGTH_LONG).show()


            //Update part
            tvDesignName.text = etDesignName.text.toString()
            tvDesignDescription.text = etDesignDescription.text.toString()
            tvDesignPrice.text = etDesignPrice.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateDesignData(
        id: String,
        name: String,
        description: String,
        price: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Designs").child(id)
        val designInfo = DesignModel(id, name, description,price)
        dbRef.setValue(designInfo)
    }
}
package com.example.app.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.models.DesignModel
import com.example.app.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity: AppCompatActivity() {

    private lateinit var  etDesignName: EditText
    private lateinit var  etDesignDescription: EditText
    private lateinit var  etDesignPrice: EditText

    //private lateinit var etDesignImg: ImageView

    //private lateinit var  btnChoose: Button
    private lateinit var  btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)
//Enter form items ids
        etDesignName = findViewById(R.id.etDesignName)
        etDesignDescription = findViewById(R.id.etDesignDescription)
        etDesignPrice = findViewById(R.id.etDesignPrice)
        btnSaveData = findViewById(R.id.btnSave)
        // btnChoose = findViewById(R.id.btnChoose)

        dbRef = FirebaseDatabase.getInstance().getReference("Designs")

        //button function
        btnSaveData.setOnClickListener{
            saveDesignsData()
        }

    }
//fnvc
    private fun saveDesignsData(){
        //getting values
        val designName = etDesignName.text.toString()
        val designDescription = etDesignDescription.text.toString()
        val designPrice = etDesignPrice.text.toString()

        //validations
        if(designName.isEmpty()){
            etDesignName.error = "Please Enter Design Name"
            return
        }
        if(designDescription.isEmpty()){
            etDesignDescription.error = "Please Enter Design Description"
            return
        }
        if(designPrice.isEmpty()){
            etDesignPrice.error = "Please Enter Design Price"
            return
        }
        val designId = dbRef.push().key!!

        val design = DesignModel(designId,designName,designDescription,designPrice)



        dbRef.child(designId).setValue(design)
            .addOnCompleteListener{
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show()

                etDesignName.text.clear()
                etDesignDescription.text.clear()
                etDesignPrice.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_SHORT).show()

            }

    }
}
package com.example.feedbacksandratings.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.feedbacksandratings.models.FeedbackModel
import com.example.feedbacksandratings.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etFeedbackCusName: EditText
    private lateinit var etFeedbackRate: EditText
    private lateinit var etFeedbackMessage: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etFeedbackCusName = findViewById(R.id.etFeedbackCusName)
        etFeedbackRate = findViewById(R.id.etFeedbackRate)
        etFeedbackMessage = findViewById(R.id.etFeedbackMessage)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Feedbacks")

        btnSaveData.setOnClickListener {
            saveFeedbacksData()
        }
    }

    private fun saveFeedbacksData() {

        //getting values
        val feedbackCusName = etFeedbackCusName.text.toString()
        val feedbackRate = etFeedbackRate.text.toString()
        val feedbackMessage = etFeedbackMessage.text.toString()

        if (feedbackCusName.isEmpty()) {
            etFeedbackCusName.error = "Please enter name"
        }
        if (feedbackRate.isEmpty()) {
            etFeedbackRate.error = "Please enter rate"
        }
        if (feedbackMessage.isEmpty()) {
            etFeedbackMessage.error = "Please enter feedback message"
        }

        val feedbackId = dbRef.push().key!!

        val feedback = FeedbackModel(feedbackId, feedbackCusName, feedbackRate, feedbackMessage)


        dbRef.child(feedbackId).setValue(feedback)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etFeedbackCusName.text.clear()
                etFeedbackRate.text.clear()
                etFeedbackMessage.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}
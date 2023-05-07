package com.example.feedbacksandratings.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.feedbacksandratings.R
import com.example.feedbacksandratings.models.FeedbackModel
import com.google.firebase.database.FirebaseDatabase

class FeedbackDetailsActivity : AppCompatActivity() {

    private lateinit var tvFeedbackId: TextView
    private lateinit var tvFeedbackCusName: TextView
    private lateinit var tvFeedbackRate: TextView
    private lateinit var tvFeedbackMessage: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("feedbackId").toString(),
                intent.getStringExtra("feedbackCusName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("feedbackId").toString()
            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Feedbacks").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Feedback data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvFeedbackId = findViewById(R.id.tvFeedbackId)
        tvFeedbackCusName = findViewById(R.id.tvFeedbackCusName)
        tvFeedbackRate = findViewById(R.id.tvFeedbackRate)
        tvFeedbackMessage = findViewById(R.id.tvFeedbackMessage)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvFeedbackId.text = intent.getStringExtra("feedbackId")
        tvFeedbackCusName.text = intent.getStringExtra("feedbackCusName")
        tvFeedbackRate.text = intent.getStringExtra("feedbackRate")
        tvFeedbackMessage.text = intent.getStringExtra("feedbackMessage")

    }



    private fun openUpdateDialog(
        feedbackId: String,
        feedbackCusName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etFeedbackCusName = mDialogView.findViewById<EditText>(R.id.etFeedbackCusName)
        val etFeedbackRate = mDialogView.findViewById<EditText>(R.id.etFeedbackRate)
        val etFeedbackMessage = mDialogView.findViewById<EditText>(R.id.etFeedbackMessage)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etFeedbackCusName.setText(intent.getStringExtra("feedbackCusName").toString())
        etFeedbackRate.setText(intent.getStringExtra("feedbackRate").toString())
        etFeedbackMessage.setText(intent.getStringExtra("feedbackMessage").toString())

        mDialog.setTitle("Updating $feedbackCusName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateFeedbackData(
                feedbackId,
                etFeedbackCusName.text.toString(),
                etFeedbackRate.text.toString(),
                etFeedbackMessage.text.toString()
            )

            Toast.makeText(applicationContext, "Feedback Data Updated", Toast.LENGTH_LONG).show()

            // updated data
            tvFeedbackCusName.text = etFeedbackCusName.text.toString()
            tvFeedbackRate.text = etFeedbackRate.text.toString()
            tvFeedbackMessage.text = etFeedbackMessage.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateFeedbackData(
        id: String,
        cusname: String,
        rate: String,
        message: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Feedbacks").child(id)
        val feedbackInfo = FeedbackModel(id, cusname, rate, message)
        dbRef.setValue(feedbackInfo)
    }

}
package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var tvuserId: TextView
    private lateinit var tvuserName: TextView
    private lateinit var tvuserEmail: TextView
    private lateinit var tvuserPhone: TextView
    private lateinit var tvuserJob: TextView
    private lateinit var tvuserDes: TextView
    private lateinit var btnprofileUpdate: Button
    private lateinit var btnprofileDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        initView()
        setValuesToViews()


        btnprofileUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("userId").toString(),
                intent.getStringExtra("useName").toString()
            )
        }

        btnprofileDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("userId").toString()
            )
        }
    }

    private fun initView() {
        tvuserId = findViewById(R.id.tvuserId)
        tvuserName = findViewById(R.id.tvuserName)
        tvuserEmail = findViewById(R.id.tvuserEmail)
        tvuserPhone = findViewById(R.id.tvuserPhone)
        tvuserJob = findViewById(R.id.tvuserJob)
        tvuserDes = findViewById(R.id.tvuserDes)

        btnprofileUpdate = findViewById(R.id.btnprofileUpdate)
        btnprofileDelete = findViewById(R.id.btnprofileDelete)
    }

    private fun setValuesToViews() {
        tvuserId.text = intent.getStringExtra("userId")
        tvuserName.text = intent.getStringExtra("useName")
        tvuserEmail.text = intent.getStringExtra("userEmail")
        tvuserPhone.text = intent.getStringExtra("userPhone")
        tvuserJob.text = intent.getStringExtra("userJob")
        tvuserDes.text = intent.getStringExtra("userDis")
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Users").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "user data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, viewActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        userId: String,
        useName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val profile_username = mDialogView.findViewById<EditText>(R.id.profile_username)
        val profile_email = mDialogView.findViewById<EditText>(R.id.profile_email)
        val profile_phone_number = mDialogView.findViewById<EditText>(R.id.profile_phone_number)
        val profile_job = mDialogView.findViewById<EditText>(R.id.profile_job)
        val profile_descreption = mDialogView.findViewById<EditText>(R.id.profile_descreption)
        val btnprofileUpdate = mDialogView.findViewById<Button>(R.id.SaveUpdateButton)

        profile_username.setText(intent.getStringExtra("useName").toString())
        profile_email.setText(intent.getStringExtra("userEmail").toString())
        profile_phone_number.setText(intent.getStringExtra("userPhone").toString())
        profile_job.setText(intent.getStringExtra("userJob").toString())
        profile_descreption.setText(intent.getStringExtra("userDis").toString())
        mDialog.setTitle("Updating $useName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnprofileUpdate.setOnClickListener {
            updateEmpData(
                userId,
                profile_username.text.toString(),
                profile_email.text.toString(),
                profile_phone_number.text.toString(),
                profile_job.text.toString(),
                profile_descreption.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvuserName.text = profile_username.text.toString()
            tvuserEmail.text = profile_email.text.toString()
            tvuserPhone.text = profile_phone_number.text.toString()
            tvuserJob.text = profile_job.text.toString()
            tvuserDes.text = profile_descreption.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        userId: String,
        useName: String,
        userEmail: String,
        userPhone: String,
        userJob: String,
        userDis: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Users").child(userId)
        val empInfo = UserModel(userId,useName,userEmail,userPhone,userJob,userDis)
        dbRef.setValue(empInfo)
    }


}
package com.example.firebase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {
    private lateinit var etUserName:EditText
    private lateinit var etEmail:EditText
    private lateinit var etPhoneNum:EditText
    private lateinit var etJob:EditText
    private lateinit var etDiscription:EditText
    private lateinit var btnSave:Button

    private lateinit var dbRef : DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        etUserName=findViewById(R.id.profile_username)
        etEmail=findViewById(R.id.profile_email)
        etPhoneNum=findViewById(R.id.profile_phone_number)
        etJob=findViewById(R.id.profile_job)
        etDiscription=findViewById(R.id.profile_descreption)

        btnSave=findViewById(R.id.SaveButton)
        dbRef= FirebaseDatabase.getInstance().getReference("Users")
        btnSave.setOnClickListener{
            saveUserData()


//            val buttonedit=findViewById<Button>(R.id.EditButton)
//            buttonedit.setOnClickListener{
//                val Intent=Intent(this,UserDetailsActivity::class.java)
//            }
        }
    }
    private fun saveUserData(){
        //get
        val useName=etUserName.text.toString()
        val userEmail=etEmail.text.toString()
        val userPhone=etPhoneNum.text.toString()
        val userJob=etJob.text.toString()
        val userDis=etDiscription.text.toString()

        if(useName.isEmpty()){
            etUserName.error="please enter name"
        }
        if(userEmail.isEmpty()){
            etEmail.error="please enter email"
        }
        if(userPhone.isEmpty()){
            etPhoneNum.error="please enter phone"
        }
        if(userJob.isEmpty()){
            etJob.error="please enter job catagory"
        }
        if(userDis.isEmpty()){
            etDiscription.error="please enter discription"
        }


        val userId=dbRef.push().key!!

        val user = UserModel(userId,useName,userEmail,userPhone,userJob,userDis)
        dbRef.child(userId).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(this,"Data Save Succcessfully",Toast.LENGTH_LONG).show()
            }.addOnFailureListener{
                err-> Toast.makeText(this,"Data Save unSucccessfully",Toast.LENGTH_LONG).show()
            }

    }
}
package com.example.firebase

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class viewActivity : AppCompatActivity() {
    private lateinit var useRecycleView:RecyclerView
    private lateinit var tvloading:TextView
    private lateinit var userList: ArrayList<UserModel>
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        supportActionBar?.title="View"

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        useRecycleView=findViewById(R.id.rvView)
        useRecycleView.layoutManager=LinearLayoutManager(this)
        useRecycleView.setHasFixedSize(true)
        tvloading=findViewById(R.id.tvloading)
        userList = arrayListOf<UserModel>()

        getUserData()
    }

    private fun getUserData() {

        useRecycleView.visibility = View.GONE
        tvloading.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(UserModel::class.java)
                        userList.add(empData!!)
                    }
                    val mAdapter = UserAdapter(userList)
                    useRecycleView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : UserAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@viewActivity, UserDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("userId", userList[position].userId)
                            intent.putExtra("useName", userList[position].useName)
                            intent.putExtra("userEmail", userList[position].userEmail)
                            intent.putExtra("userPhone", userList[position].userPhone)
                            intent.putExtra("userJob", userList[position].userJob)
                            intent.putExtra("userDis", userList[position].userDis)
                            startActivity(intent)
                        }

                    })

                    useRecycleView.visibility = View.VISIBLE
                    tvloading.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}

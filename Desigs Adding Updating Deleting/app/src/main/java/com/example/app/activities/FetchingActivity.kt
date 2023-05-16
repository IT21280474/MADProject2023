package com.example.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.models.DesignModel
import com.example.app.R
import com.example.app.R.*
import com.example.app.adapters.DesignAdapter
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity()  {

    private lateinit var designRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var designList: ArrayList<DesignModel>
    private lateinit var dbRef: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_fetching)

        designRecyclerView = findViewById(R.id.rvDesign)
        designRecyclerView.layoutManager = LinearLayoutManager(this)
        designRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(id.tvLoadingData)

        designList = arrayListOf<DesignModel>()
        getDesignsData()

    }
    private fun getDesignsData(){

        designRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Designs")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                designList.clear()
                if (snapshot.exists()) {
                    for (designSnap in snapshot.children) {
                        val designData = designSnap.getValue(DesignModel::class.java)
                        designList.add(designData!!)
                    }

                    val mAdapter = DesignAdapter(designList)
                    designRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : DesignAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, DesignDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("designId", designList[position].designId)
                            intent.putExtra("designName", designList[position].designName)
                            intent.putExtra("designDescription", designList[position].designDescription)
                            intent.putExtra("designPrice", designList[position].designPrice)
                            startActivity(intent)
                        }

                    })

                    designRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
              TODO("Not yet implemented")
            }

        })
    }
}
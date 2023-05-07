package com.example.feedbacksandratings.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedbacksandratings.R
import com.example.feedbacksandratings.adapters.FeedbackAdapter
import com.example.feedbacksandratings.models.FeedbackModel
import com.google.firebase.database.*


class FetchingActivity : AppCompatActivity() {

    private lateinit var feedbackRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var feedbackList: ArrayList<FeedbackModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        feedbackRecyclerView = findViewById(R.id.rvFeedback)
        feedbackRecyclerView.layoutManager = LinearLayoutManager(this)
        feedbackRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        feedbackList = arrayListOf<FeedbackModel>()

        getFeedbacksData()

    }

    private fun getFeedbacksData() {

        feedbackRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Feedbacks")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                feedbackList.clear()
                if (snapshot.exists()){
                    for (feedbackSnap in snapshot.children){
                        val feedbackData = feedbackSnap.getValue(FeedbackModel::class.java)
                        feedbackList.add(feedbackData!!)
                    }
                    val mAdapter = FeedbackAdapter(feedbackList)
                    feedbackRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : FeedbackAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, FeedbackDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("feedbackId", feedbackList[position].feedbackId)
                            intent.putExtra("feedbackCusName", feedbackList[position].feedbackCusName)
                            intent.putExtra("feedbackRate", feedbackList[position].feedbackRate)
                            intent.putExtra("feedbackMessage", feedbackList[position].feedbackMessage)
                            startActivity(intent)
                        }

                    })

                    feedbackRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}

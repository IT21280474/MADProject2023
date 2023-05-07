package com.example.feedbacksandratings.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedbacksandratings.R
import com.example.feedbacksandratings.models.FeedbackModel

class FeedbackAdapter(private val feedbackList: ArrayList<FeedbackModel>) :
    RecyclerView.Adapter<FeedbackAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.feedback_list, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFeedback = feedbackList[position]
        holder.tvFeedbackCusName.text = currentFeedback.feedbackCusName
    }

    override fun getItemCount(): Int {
        return feedbackList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvFeedbackCusName : TextView = itemView.findViewById(R.id.tvFeedbackCusName )

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}
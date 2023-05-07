package com.example.firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val userList : ArrayList<UserModel>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.quizes, parent, false)
        return ViewHolder(itemView, mListener)
    }


    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val currentEmp = userList[position]
        holder.tvEmpName.text = currentEmp.useName
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder (itemView: View, clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val tvEmpName : TextView = itemView.findViewById(R.id.tvEmpName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }



}
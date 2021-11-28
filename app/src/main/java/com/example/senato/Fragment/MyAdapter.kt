package com.example.senato.Fragment

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.senato.R

class MyAdapter(private val votingList: List<Voting>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.voting_item, parent, false)
        return MyViewHolder(itemView, mListener)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.render(votingList[position])
    }

    override fun getItemCount(): Int {
        return votingList.size
    }

    class MyViewHolder(val itemdView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemdView) {
        @RequiresApi(Build.VERSION_CODES.N)

        fun render(voting: Voting) {
            itemdView.findViewById<TextView>(R.id.votingTitle).text = voting.title
            itemdView.findViewById<TextView>(R.id.votingType).text = voting.type
            itemdView.findViewById<TextView>(R.id.votingCreator).text = voting.creator }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}
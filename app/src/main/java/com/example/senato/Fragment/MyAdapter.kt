package com.example.senato.Fragment

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.senato.R

class MyAdapter(private val votingList: List<Voting>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.voting_item, parent, false)
        return MyViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.render(votingList[position])
    }

    override fun getItemCount(): Int {
        return votingList.size
    }


    class MyViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView) {
        @RequiresApi(Build.VERSION_CODES.N)

        fun render(voting: Voting) {
            itemView.findViewById<TextView>(R.id.votingTitle).text = voting.title
            itemView.findViewById<TextView>(R.id.votingType).text = voting.type
            itemView.findViewById<TextView>(R.id.votingCreator).text = voting.creator
        }
    }
}
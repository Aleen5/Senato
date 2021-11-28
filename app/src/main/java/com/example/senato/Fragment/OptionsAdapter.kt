package com.example.senato.Fragment

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.senato.R

class OptionsAdapter(private val optionsList: List<VotingValues>):
    RecyclerView.Adapter<OptionsAdapter.OptionsViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.voting_item_options, parent, false)
        return OptionsViewHolder(itemView, mListener)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.render(optionsList[position])
    }

    override fun getItemCount(): Int {
        return optionsList.size
    }

    class OptionsViewHolder(val itemcView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemcView) {
        @RequiresApi(Build.VERSION_CODES.N)

        fun render(votingValue: VotingValues) {
            itemcView.findViewById<TextView>(R.id.votingOption).text = votingValue.option
            itemcView.findViewById<TextView>(R.id.votingCount).text = votingValue.value.toString()}

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}
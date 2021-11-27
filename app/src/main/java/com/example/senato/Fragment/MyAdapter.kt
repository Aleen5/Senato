package com.example.senato.Fragment

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.senato.R
import com.example.senato.databinding.VotingItemBinding
import android.content.Context
import android.widget.AdapterView
import androidx.fragment.app.FragmentActivity

class MyAdapter(private val votingList: List<Voting>, var context: Context, votingsID: MutableList<String>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val votingFragment: VotingFragment = VotingFragment.newInstance("Pocholo")
    val contextF: AppCompatActivity = context as AppCompatActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.voting_item, parent, false)
        return MyViewHolder(itemView).also {
            itemView.setOnClickListener {
                Toast.makeText(context, "Acceso a la votación", Toast.LENGTH_SHORT).show()
                replaceFragment(votingFragment)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.render(votingList[position])
    }

    override fun getItemCount(): Int {
        return votingList.size
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = contextF.supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    class MyViewHolder(val itemdView: View): RecyclerView.ViewHolder(itemdView) {
        @RequiresApi(Build.VERSION_CODES.N)

        fun render(voting: Voting) {
            itemdView.findViewById<TextView>(R.id.votingTitle).text = voting.title
            itemdView.findViewById<TextView>(R.id.votingType).text = voting.type
            itemdView.findViewById<TextView>(R.id.votingCreator).text = voting.creator }
        }

}
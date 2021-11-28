package com.example.senato.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.senato.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlin.math.absoluteValue

data class VotingValues (
    val option: String? = null,
    val value: Int? = null
        )

class VotingFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var optionsRecyclerView: RecyclerView
    private lateinit var votingOptions: MutableList<VotingValues>
    private lateinit var adapterD: OptionsAdapter
    private var docID = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_voting, container, false)
        votingOptions = mutableListOf()

        getVotingData(view)

        return view
    }

    fun getVotingData(view: View) {
        val db = FirebaseFirestore.getInstance()
        var dbVotings = db.collection("votings").document(docID)

        dbVotings.get().addOnSuccessListener {
            var viewTitle: TextView = view.findViewById(R.id.currentVotingTitle)
            viewTitle.text = it.get("title") as String

            var dbOptionNames = it.get("options") as MutableList<String>
            var dbOptionValues = it.get("votes") as MutableList<Long>

            for(i in 0..dbOptionNames.size - 1) {
                var objectV: VotingValues = VotingValues(dbOptionNames[i], dbOptionValues[i].toInt())
                votingOptions.add(objectV)
            }
            initRecyclerView(view)
        }


    }

    private fun initRecyclerView(view: View) {
        optionsRecyclerView = view.findViewById<RecyclerView>(R.id.voting_options_Recycler)
        optionsRecyclerView.layoutManager = LinearLayoutManager(activity)

        // Set the list of options to the adapter

        adapterD = OptionsAdapter(votingOptions)
        optionsRecyclerView.adapter = adapterD

        adapterD.setOnItemClickListener(object : OptionsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@VotingFragment.context, "You clicked on item no. $position", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("REPLACE WITH A STRING CONSTANT")?.let {
            docID = it
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(docID: String) = VotingFragment().apply {
            arguments = Bundle().apply {
                putString("REPLACE WITH A STRING CONSTANT", docID)
            }
        }
    }
}
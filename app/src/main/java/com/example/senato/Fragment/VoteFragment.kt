package com.example.senato.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.senato.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class VoteFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var adapterVoting: MyAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var dbVotings: MutableList<Voting>
    private lateinit var votingsID: MutableList<String>
    private val votingFragment = VotingFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_vote, container, false)
        dbVotings = mutableListOf()
        votingsID = mutableListOf()

        // Get the votings and pass the view to the function to initialize the recyclerview on finish

        getVotings(view)

        return view
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.voting_Recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // Set the list of votings to the adapter

        Log.d("VotingsID", votingsID.toString())

        recyclerView.adapter = MyAdapter(dbVotings, this.requireContext(), votingsID)
    }

    // Function to get the votings. It receives the view as an argument an initializes the recyclerview once the query is done

    private fun getVotings(view: View) {
        val db = FirebaseFirestore.getInstance()
        var dbVotings = db.collection("votings")

        dbVotings.get().addOnSuccessListener {
            it.documents.forEach {
                var objectV: Voting = it.toObject(Voting::class.java)!!
                addVoting(objectV, it.id)
            }
            initRecyclerView(view)
        }
    }

    private fun addVoting(objectV: Voting, id: String) {
        dbVotings.add(objectV)
        votingsID.add(id)
    }
}
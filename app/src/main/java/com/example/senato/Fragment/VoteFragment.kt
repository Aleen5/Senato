package com.example.senato.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.senato.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class VoteFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var adapterVoting: MyAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_vote, container, false)
        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.voting_Recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val members = listOf("Sainero", "Djessy", "Yeray").toMutableList()
        val options = listOf("Si", "No").toMutableList()
        val votes = listOf(0,0).toMutableList()
        val votings = listOf(
            Voting("Ivan", members, options, "¿Djessy es otaku?", "unica", votes)
        )
        adapterVoting = MyAdapter(votings)
        recyclerView.adapter = adapterVoting
    }
}
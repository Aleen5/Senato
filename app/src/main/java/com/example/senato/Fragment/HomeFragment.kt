package com.example.senato.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.senato.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val createVotingFragment = CreateVotingFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        val contextF: AppCompatActivity = context as AppCompatActivity
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val button: Button = view.findViewById(R.id.button_createVoting)
        button.setOnClickListener {
            val transaction = contextF.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, createVotingFragment)
            transaction.commit()
        }

        getUserName(view)

        return view
    }

    private fun getUserName(view: View) {
        // Get layout contents

        val homeUserName: TextView = view.findViewById(R.id.homeWelcomeName)

        // Get user's data

        val db = FirebaseFirestore.getInstance()
        val userDoc = db.collection("users").document(auth.uid!!)
        userDoc.get().addOnSuccessListener { document ->
            if (document != null) {
                homeUserName.text = document.getString("name")
            } else
                Log.d("SuccessF", "No such document")

        }
    }
}
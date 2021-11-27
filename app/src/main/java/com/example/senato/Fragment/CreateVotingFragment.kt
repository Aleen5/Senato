package com.example.senato.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.senato.R
import com.example.senato.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class CreateVotingFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var userNameSurname: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_voting, container, false)
        auth = Firebase.auth
        userNameSurname = ""
        val button: Button = view.findViewById(R.id.button_createVoting2)

        getUser()

        button.setOnClickListener {
            if (fieldsAreValid(view)) {

                var titleView: TextView = view.findViewById(R.id.newVotingTitle)
                var optionsView: TextView = view.findViewById(R.id.newVotingOptions)
                var typeView: Spinner = view.findViewById(R.id.newVotingType)

                var creator = userNameSurname
                var members = mutableListOf<String>("", "")
                var options: MutableList<String> = optionsView.text.split(",").toMutableList()
                var title = titleView.text.toString()
                var type = typeView.selectedItem.toString()
                var usersvoted = mutableListOf<String>()
                var votes = mutableListOf<Int>()
                options.forEach {
                    votes.add(0)
                }

                writeNewVoting(creator, members, options, title, type, usersvoted, votes)

                Toast.makeText(this.context, "Voting posted successfully", Toast.LENGTH_SHORT).show()

            } else
                Toast.makeText(this.context, "One or more fields are invalid", Toast.LENGTH_SHORT).show()
        }

        return view;
    }

    private fun fieldsAreValid(view: View): Boolean {
        val title: TextView = view.findViewById(R.id.createVotingTitle);
        val options: TextView = view.findViewById(R.id.newVotingOptions);

        if (title.text.isNullOrBlank() || options.text.isNullOrBlank())
            return false

        return true;
    }

    private fun getUser() {
        val db = FirebaseFirestore.getInstance()
        val userDoc = db.collection("users").document(auth.uid!!)
        userDoc.get().addOnSuccessListener { document ->
            if (document != null) {
                userNameSurname = "${document.getString("name")} ${document.getString("surname")}"
            } else
                Log.d("SuccessF", "No such document")

        }.addOnFailureListener { exception ->
            Log.d("SuccessF", "get failed with ", exception)
        }
    }

    private fun writeNewVoting(creator: String, members: MutableList<String>, options: MutableList<String>,
       title: String, type: String, usersvoted:MutableList<String>, votes: MutableList<Int>) {

        val voting = Voting(creator, members, options, title, type, usersvoted, votes)
        val db = FirebaseFirestore.getInstance()
        val usersRef = db.collection("votings")
        usersRef.document().set(voting).addOnSuccessListener { Log.d("DatabaseDocumentInsert", "Success") }
            .addOnFailureListener { Log.d("DatabaseDocumentInsert", "Failure") }
    }
}
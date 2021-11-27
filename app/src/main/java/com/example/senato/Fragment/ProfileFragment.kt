package com.example.senato.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.senato.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var userIDoc: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        auth = Firebase.auth
        getUser(view)

        return view
    }

    private fun getUser(view: View) {
        val db = FirebaseFirestore.getInstance()
        val userDoc = db.collection("users").document(auth.uid!!)

        var nameView: EditText = view.findViewById(R.id.profileName)
        var surnameView: EditText = view.findViewById(R.id.profileSurname)
        var emailView: EditText = view.findViewById(R.id.profileEmail)
        var phoneView: EditText = view.findViewById(R.id.profilePhone)

        userDoc.get().addOnSuccessListener { document ->
            if (document != null) {
                nameView.setText(document.getString("name"))
                surnameView.setText(document.getString("surname"))
                emailView.setText(document.getString("email"))
                phoneView.setText(document.getString("phone"))

            } else
                Log.d("SuccessF", "No such document")

        }.addOnFailureListener { exception ->
            Log.d("SuccessF", "get failed with ", exception)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(userID: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(userID, userIDoc)
                }
            }
    }
}
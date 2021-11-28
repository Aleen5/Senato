package com.example.senato.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

        var updateButton: Button = view.findViewById(R.id.profileUpdateButton)
        var resetButton: Button = view.findViewById(R.id.profileResetFieldsButton)

        updateButton.setOnClickListener {
            updateUser(view)
            Toast.makeText(this.context, "User data updated", Toast.LENGTH_SHORT).show()
        }

        resetButton.setOnClickListener {
            getUser(view)
            Toast.makeText(this.context, "Fields have been reestablished", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun updateUser(view: View) {
        var nameView: EditText = view.findViewById(R.id.profileName)
        var surnameView: EditText = view.findViewById(R.id.profileSurname)
        var emailView: EditText = view.findViewById(R.id.profileEmail)
        var phoneView: EditText = view.findViewById(R.id.profilePhone)

        if (nameView.text.isNullOrBlank() || surnameView.text.isNullOrBlank() || emailView.text.isNullOrBlank()
            || phoneView.text.isNullOrBlank()) {
            Toast.makeText(view.context, "Field(s) must not be blank", Toast.LENGTH_SHORT).show()
            return
        }

        val db = FirebaseFirestore.getInstance()
        val userDoc = db.collection("users").document(auth.uid!!)

        userDoc.update("name", nameView.text.toString())
        userDoc.update("surname", surnameView.text.toString())
        //userDoc.update("email", emailView.text.toString()) // To be implemented in the future due to voting politics
        userDoc.update("phone", phoneView.text.toString())
    }

    private fun getUser(view: View) {
        val db = FirebaseFirestore.getInstance()
        val userDoc = db.collection("users").document(auth.uid!!)

        var nameView: EditText = view.findViewById(R.id.profileName)
        var surnameView: EditText = view.findViewById(R.id.profileSurname)
        var emailView: EditText = view.findViewById(R.id.profileEmail)
        var phoneView: EditText = view.findViewById(R.id.profilePhone)

        userDoc.get().addOnSuccessListener {
            nameView.setText(it.getString("name"))
            surnameView.setText(it.getString("surname"))
            emailView.setText(it.getString("email"))
            phoneView.setText(it.getString("phone"))
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
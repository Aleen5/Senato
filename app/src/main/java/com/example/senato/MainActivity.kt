package com.example.senato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.senato.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.textoEmail.setText("Hola, " + auth.currentUser?.email.toString() + "!")
        binding.textoNombre.setText(auth.currentUser?.displayName)
    }
}
package com.example.senato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.senato.databinding.ActivityMainBinding
import com.example.senato.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ResetPassword : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.resetPassButton.setOnClickListener {
            if (binding.resetPassEmail.text.toString().isNotEmpty() && binding.resetPassEmail.text.toString() == binding.resetPassRepEmail.text.toString()) {
                Firebase.auth.sendPasswordResetEmail(binding.resetPassEmail.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            Toast.makeText(baseContext, "Email sent", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this, "Email not sent; email address is not registered", Toast.LENGTH_LONG).show()
                    }
            } else
                Toast.makeText(baseContext, "Email not sent; addresses don't match", Toast.LENGTH_LONG).show()
        }

        binding.resetBack.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
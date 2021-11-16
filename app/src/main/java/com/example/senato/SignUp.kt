package com.example.senato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.senato.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        db = Firebase.database.reference

        binding.signUpButton.setOnClickListener {
            if (binding.signUpName.text.toString().isNotEmpty()
                && binding.signUpSurname.text.toString().isNotEmpty()
                && binding.signUpEmail.text.toString().isNotEmpty()
                && binding.signUpPwd.text.toString().isNotEmpty()
                && binding.signUpRepeatPwd.text.toString().isNotEmpty()
                && binding.signUpPwd.text.toString().equals(binding.signUpRepeatPwd.text.toString()))
                    // Ejecutar registro
                    signIn(binding.signUpName.text.toString(), binding.signUpSurname.text.toString(),
                    binding.signUpEmail.text.toString(), binding.signUpPwd.text.toString())
        }

        binding.signUpBack.setOnClickListener {
            finish()
            //reload("signin")
        }
    }

    private fun signIn(name:String, surname:String, email:String, password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Log.d("RegistroFirebase", "createUserWithEmail:success")
                    val user = auth.currentUser

                    // Create user's database document

                    writeNewUser(
                        user!!.uid, user!!.email, binding.signUpName.text.toString(),
                        binding.signUpSurname.text.toString(), "623623623")
                        Log.d("Crear documento usuario", "Success?")

                    reload("main")
                } else {
                    // If sign in fails
                    Log.w("RegistroFirebase", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

    private fun writeNewUser(userId: String, email: String?, name: String, surname: String, phone:String) {
        val user = User(email, name, surname, phone)
        //val data = hashMapOf("users" to true)

        db.child("users").child(userId).setValue(user)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun reload(activity:String) {
        when(activity) {
            "main" -> this.startActivity(Intent(this, MainActivity::class.java))
            "signin" -> this.startActivity(Intent(this, SignIn::class.java))
        }
    }
}
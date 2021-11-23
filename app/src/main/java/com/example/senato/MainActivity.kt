package com.example.senato

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.senato.Fragment.HomeFragment
import com.example.senato.Fragment.SettingsFragment
import com.example.senato.Fragment.VoteFragment
import com.example.senato.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var bottom_navigation: BottomNavigationView
    private val homeFragment = HomeFragment()
    private val voteFragment = VoteFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        // Fragments code

        bottom_navigation = findViewById(R.id.bottom_navigation)
        replaceFragment(homeFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> replaceFragment(homeFragment)
                R.id.nav_vote -> replaceFragment(voteFragment)
                R.id.nav_settings -> replaceFragment(settingsFragment)
            }
            true
        }

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        // Get access to the navigation view to change things

        val navView : NavigationView  = findViewById(R.id.nav_view)
        val headerView : View = navView.getHeaderView(0)
        val navNameSurname : TextView = headerView.findViewById(R.id.nav_header_name_surname)
        val navEmail: TextView = headerView.findViewById(R.id.nav_header_email)

        // Get user's data

        val db = FirebaseFirestore.getInstance()
        val userDoc = db.collection("users").document(auth.uid!!)
        userDoc.get().addOnSuccessListener { document ->
            if (document != null) {
                navNameSurname.text = "${document.getString("name")} ${document.getString("surname")}"
                navEmail.text = document.getString("email")


            } else
                Log.d("SuccessF", "No such document")

        }.addOnFailureListener { exception ->
                Log.d("SuccessF", "get failed with ", exception)
            }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_item_profile -> Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show()
            R.id.nav_item_configuration -> Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show()
            R.id.nav_item_logout -> {
                Firebase.auth.signOut()
                reload("signin")
                finish()
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun reload(activity:String) {
        when(activity) {
            "signup" -> this.startActivity(Intent(this, MainActivity::class.java))
            "signin" -> this.startActivity(Intent(this, SignIn::class.java))
        }
    }
}
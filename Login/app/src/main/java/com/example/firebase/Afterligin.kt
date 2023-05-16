package com.example.firebase

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.firebase.databinding.ActivityAfterliginBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Afterligin : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityAfterliginBinding
    val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    companion object{
        lateinit var auth: FirebaseAuth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAfterliginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Login your profile"

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        binding.profilemy.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.veiwdata.setOnClickListener{
            startActivity(Intent(this, viewActivity::class.java))
        }

        binding.signOut.setOnClickListener{
            MainActivity.auth.signOut()
            finish() // Optional: Finish the current activity after signing out
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.nav_home -> Toast.makeText(applicationContext, "Home", Toast.LENGTH_LONG).show()
                R.id.signIn -> Toast.makeText(applicationContext, "Login ${MainActivity.auth.currentUser?.email}", Toast.LENGTH_LONG).show()
                R.id.signOut -> Toast.makeText(applicationContext, "Log Out ${MainActivity.auth.currentUser?.email}", Toast.LENGTH_LONG).show()
                R.id.nav_message -> Toast.makeText(applicationContext, "View Messages", Toast.LENGTH_LONG).show()
                R.id.nav_profile -> Toast.makeText(applicationContext, "Go to My Profile", Toast.LENGTH_LONG).show()
                R.id.nav_rate_us -> Toast.makeText(applicationContext, "Rate Us", Toast.LENGTH_LONG).show()
                R.id.nav_trash -> Toast.makeText(applicationContext, "Delete", Toast.LENGTH_LONG).show()
                R.id.nav_settings -> Toast.makeText(applicationContext, "Settings", Toast.LENGTH_LONG).show()
                R.id.nav_share -> Toast.makeText(applicationContext, "Share App", Toast.LENGTH_LONG).show()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        binding.userDetails.text = updateData()
    }

    private fun updateData(): String {
        return "Email:                   ${MainActivity.auth.currentUser?.email}"
    }
}

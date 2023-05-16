package com.example.firebase

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.firebase.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var myBroadcastReceiver: MyReceiver
    lateinit var toggle: ActionBarDrawerToggle

    val firebase: DatabaseReference= FirebaseDatabase.getInstance().getReference()

    private lateinit var binding: ActivityMainBinding
    companion object{
        lateinit var auth:FirebaseAuth
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth=FirebaseAuth.getInstance()

        if(auth.currentUser==null){
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        binding.profilemy.setOnClickListener{
//            startActivity(Intent(this,ProfileActivity::class.java))
//            startActivity(intent)
//
//        }
//        binding.veiwdata.setOnClickListener{
//            startActivity(Intent(this,viewActivity::class.java))
//            startActivity(intent)
//
//        }
        binding.signIn.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        binding.signOut.setOnClickListener{
            auth.signOut()
        }



        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home-> {
                    startActivity(Intent(this,Home::class.java))
                    drawerLayout.closeDrawers()
                    true
                    Toast.makeText(applicationContext,"Home", Toast.LENGTH_LONG).show()
                }
                R.id.signIn-> {
                    startActivity(Intent(this,LoginActivity::class.java))
                    drawerLayout.closeDrawers()
                    true
                    Toast.makeText(
                        applicationContext,
                        "Login ${auth.currentUser?.email}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                R.id.signOut-> {
                    auth.signOut()
                    Toast.makeText(
                        applicationContext,
                        "Log Out ${auth.currentUser?.email}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                R.id.nav_message->Toast.makeText(applicationContext,"View Massages", Toast.LENGTH_LONG).show()
                R.id.nav_profile-> {
                    startActivity(Intent(this,viewActivity::class.java))
                    Toast.makeText(applicationContext, "Go to My Profile", Toast.LENGTH_LONG).show()
                }
                R.id.nav_rate_us->Toast.makeText(applicationContext,"Rate Us", Toast.LENGTH_LONG).show()
                R.id.nav_trash->Toast.makeText(applicationContext,"Delete", Toast.LENGTH_LONG).show()
                R.id.nav_settings-> {
                    startActivity(Intent(this,SettingsActivity::class.java))
                    drawerLayout.closeDrawers()
                    true
                    Toast.makeText(applicationContext, "Settings", Toast.LENGTH_LONG).show()
                }

                R.id.nav_share-> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this amazing app!")
                    shareIntent.setPackage("com.whatsapp") // Set the package name of WhatsApp

                    if (shareIntent.resolveActivity(packageManager) != null) {
                        startActivity(shareIntent)
                    } else {
                        Toast.makeText(applicationContext, "WhatsApp is not installed on your device", Toast.LENGTH_SHORT).show()
                    }
                    true
                    Toast.makeText(applicationContext, "Share App", Toast.LENGTH_LONG).show()
                }
            }
            true
        }



//        BroadCast REsiver

        myBroadcastReceiver = MyReceiver()
        // Register the receiver to listen for broadcast intents with the custom action
        val filter = IntentFilter("com.example.lab8.MY_CUSTOM_ACTION")
        val listenToBroadcastsFromOtherApps = false
        val receiverFlags = if (listenToBroadcastsFromOtherApps) {
            ContextCompat.RECEIVER_EXPORTED
        } else {
            ContextCompat.RECEIVER_NOT_EXPORTED
        }
        ContextCompat.registerReceiver(this, myBroadcastReceiver,filter,receiverFlags)
        GlobalScope.launch {
            delay(1000)
            val intent = Intent()
            intent.action = "com.example.lab8.MY_CUSTOM_ACTION"
            sendBroadcast(intent)
        }




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        binding.userDetails.text=updateData()
    }
    private  fun updateData(): String{
        return "Email :" +
                "                   "+
                " ${auth.currentUser?.email} "
    }

}
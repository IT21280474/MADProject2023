package com.example.firebase

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var notificationSwitch: Switch
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.title="Setting"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        // Find views by their IDs
        notificationSwitch = findViewById(R.id.notificationSwitch)
        emailEditText = findViewById(R.id.emailEditText)
        saveButton = findViewById(R.id.saveButton)

        // Set click listener for the save button
        saveButton.setOnClickListener {
            saveSettings()
        }
    }

    private fun saveSettings() {
        val enableNotifications = notificationSwitch.isChecked
        val emailAddress = emailEditText.text.toString()

        // TODO: Implement saving the settings to your desired storage (e.g., SharedPreferences, database, etc.)

        // Display a message indicating that the settings have been saved
        showToast("Settings saved: Enable Notifications=$enableNotifications, Email=$emailAddress")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
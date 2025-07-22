package com.idunsa.idunsa_app.inicio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.evento.EventsNewActivityAdmin

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        val newEventsButton = findViewById<Button>(R.id.newEventsButton)

        logoutButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        newEventsButton.setOnClickListener {
            startActivity(Intent(this, EventsNewActivityAdmin::class.java)) // o EventsNewActivity
        }
    }
}

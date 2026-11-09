package com.example.fadli_myinventory

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fadli_myinventory.ui.activity.DashboardActivity
import com.example.fadli_myinventory.ui.activity.LoginActivity
import com.example.fadli_myinventory.utils.SessionManager042

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState042: Bundle?) {
        super.onCreate(savedInstanceState042)

        val sessionManager042 = SessionManager042(this)

        if (sessionManager042.isLoggedIn042()) {
            val intent042 = Intent(this, DashboardActivity::class.java)
            startActivity(intent042)
        } else {
            val intent042 = Intent(this, LoginActivity::class.java)
            startActivity(intent042)
        }

        finish()
    }
}

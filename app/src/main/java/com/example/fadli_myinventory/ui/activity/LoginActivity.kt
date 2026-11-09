package com.example.fadli_myinventory.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fadli_myinventory.databinding.ActivityLoginBinding
import com.example.fadli_myinventory.ui.viewmodel.AuthResult042
import com.example.fadli_myinventory.ui.viewmodel.AuthViewModel042
import com.example.fadli_myinventory.ui.viewmodel.ViewModelFactory042
import com.example.fadli_myinventory.utils.SessionManager042

class LoginActivity : AppCompatActivity() {

    private lateinit var binding042: ActivityLoginBinding
    private lateinit var sessionManager042: SessionManager042

    private val authViewModel042: AuthViewModel042 by viewModels {
        ViewModelFactory042.getInstance042(this)
    }

    override fun onCreate(savedInstanceState042: Bundle?) {
        super.onCreate(savedInstanceState042)
        enableEdgeToEdge()

        binding042 = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding042.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding042.root) { view042, insets042 ->
            val systemBars042 = insets042.getInsets(WindowInsetsCompat.Type.systemBars())
            view042.setPadding(systemBars042.left, systemBars042.top, systemBars042.right, systemBars042.bottom)
            insets042
        }

        sessionManager042 = SessionManager042(this)

        setupActionListeners042()
        observeViewModel042()
    }

    private fun setupActionListeners042() {
        binding042.btnLogin042.setOnClickListener {
            val username042 = binding042.etUsername042.text.toString()
            val password042 = binding042.etPassword042.text.toString()

            authViewModel042.loginUser042(username042, password042)
        }

        binding042.tvRegisterLink042.setOnClickListener {
            val intent042 = Intent(this, RegisterActivity::class.java)
            startActivity(intent042)
        }
    }

    private fun observeViewModel042() {
        authViewModel042.authResult042.observe(this) { result042 ->
            when (result042) {
                is AuthResult042.Success042 -> {
                    Toast.makeText(this, result042.message042, Toast.LENGTH_SHORT).show()
                    sessionManager042.saveUserSession042(
                        result042.user042.id042,
                        result042.user042.username042
                    )
                    authViewModel042.resetAuthResult042()

                    val intent042 = Intent(this, DashboardActivity::class.java)
                    startActivity(intent042)
                    finish()
                }
                is AuthResult042.Error042 -> {
                    Toast.makeText(this, result042.message042, Toast.LENGTH_SHORT).show()
                }
                null -> {}
            }
        }
    }
}

package com.example.fadli_myinventory.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fadli_myinventory.databinding.ActivityRegisterBinding
import com.example.fadli_myinventory.ui.viewmodel.AuthResult042
import com.example.fadli_myinventory.ui.viewmodel.AuthViewModel042
import com.example.fadli_myinventory.ui.viewmodel.ViewModelFactory042

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding042: ActivityRegisterBinding

    private val authViewModel042: AuthViewModel042 by viewModels {
        ViewModelFactory042.getInstance042(this)
    }

    override fun onCreate(savedInstanceState042: Bundle?) {
        super.onCreate(savedInstanceState042)
        enableEdgeToEdge()

        binding042 = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding042.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding042.root) { view042, insets042 ->
            val systemBars042 = insets042.getInsets(WindowInsetsCompat.Type.systemBars())
            view042.setPadding(systemBars042.left, systemBars042.top, systemBars042.right, systemBars042.bottom)
            insets042
        }

        setupActionListeners042()
        observeViewModel042()
    }

    private fun setupActionListeners042() {
        binding042.btnRegister042.setOnClickListener {
            val username042 = binding042.etUsername042.text.toString()
            val password042 = binding042.etPassword042.text.toString()
            val confirmPassword042 = binding042.etConfirmPassword042.text.toString()

            authViewModel042.registerUser042(username042, password042, confirmPassword042)
        }

        binding042.tvLoginLink042.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel042() {
        authViewModel042.authResult042.observe(this) { result042 ->
            when (result042) {
                is AuthResult042.Success042 -> {
                    Toast.makeText(this, result042.message042, Toast.LENGTH_SHORT).show()
                    authViewModel042.resetAuthResult042()
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

package com.example.fadli_myinventory.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fadli_myinventory.databinding.ActivityDashboardBinding
import com.example.fadli_myinventory.ui.viewmodel.InventoryViewModel042
import com.example.fadli_myinventory.ui.viewmodel.ViewModelFactory042
import com.example.fadli_myinventory.utils.CurrencyFormatter042
import com.example.fadli_myinventory.utils.SessionManager042

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding042: ActivityDashboardBinding
    private lateinit var sessionManager042: SessionManager042
    private var userId042: Int = -1

    private val inventoryViewModel042: InventoryViewModel042 by viewModels {
        ViewModelFactory042.getInstance042(this)
    }

    override fun onCreate(savedInstanceState042: Bundle?) {
        super.onCreate(savedInstanceState042)
        enableEdgeToEdge()

        binding042 = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding042.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding042.root) { view042, insets042 ->
            val systemBars042 = insets042.getInsets(WindowInsetsCompat.Type.systemBars())
            view042.setPadding(systemBars042.left, systemBars042.top, systemBars042.right, systemBars042.bottom)
            insets042
        }

        sessionManager042 = SessionManager042(this)
        userId042 = sessionManager042.getUserId042()

        if (userId042 == -1) {
            redirectToLogin042()
            return
        }

        setupUserInterface042()
        setupActionListeners042()
        observeDashboardData042()
    }

    private fun setupUserInterface042() {
        val username042 = sessionManager042.getUsername042()
        binding042.tvGreeting042.text = "Selamat Datang, $username042!"
    }

    private fun setupActionListeners042() {
        binding042.btnViewItems042.setOnClickListener {
            val intent042 = Intent(this, ItemListActivity::class.java)
            startActivity(intent042)
        }

        binding042.btnAddItem042.setOnClickListener {
            val intent042 = Intent(this, ItemFormActivity::class.java)
            startActivity(intent042)
        }

        binding042.btnLogout042.setOnClickListener {
            showLogoutConfirmationDialog042()
        }
    }

    private fun observeDashboardData042() {
        inventoryViewModel042.getTotalTypes042(userId042).observe(this) { totalTypes042 ->
            binding042.tvTotalTypes042.text = (totalTypes042 ?: 0).toString()
        }

        inventoryViewModel042.getTotalStock042(userId042).observe(this) { totalStock042 ->
            binding042.tvTotalStock042.text = (totalStock042 ?: 0).toString()
        }

        inventoryViewModel042.getTotalValue042(userId042).observe(this) { totalValue042 ->
            val value042 = totalValue042 ?: 0.0
            binding042.tvTotalValue042.text = CurrencyFormatter042.formatRupiah042(value042)
        }
    }

    private fun showLogoutConfirmationDialog042() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
            .setPositiveButton("Logout") { _, _ ->
                sessionManager042.clearSession042()
                Toast.makeText(this, "Berhasil logout", Toast.LENGTH_SHORT).show()
                redirectToLogin042()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun redirectToLogin042() {
        val intent042 = Intent(this, LoginActivity::class.java)
        intent042.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent042)
        finish()
    }
}

package com.example.fadli_myinventory.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fadli_myinventory.databinding.ActivityItemFormBinding
import com.example.fadli_myinventory.ui.viewmodel.InventoryViewModel042
import com.example.fadli_myinventory.ui.viewmodel.ViewModelFactory042
import com.example.fadli_myinventory.utils.SessionManager042

class ItemFormActivity : AppCompatActivity() {

    private lateinit var binding042: ActivityItemFormBinding
    private lateinit var sessionManager042: SessionManager042
    private var userId042: Int = -1
    private var itemId042: Int = -1

    private val inventoryViewModel042: InventoryViewModel042 by viewModels {
        ViewModelFactory042.getInstance042(this)
    }

    override fun onCreate(savedInstanceState042: Bundle?) {
        super.onCreate(savedInstanceState042)
        enableEdgeToEdge()

        binding042 = ActivityItemFormBinding.inflate(layoutInflater)
        setContentView(binding042.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding042.root) { view042, insets042 ->
            val systemBars042 = insets042.getInsets(WindowInsetsCompat.Type.systemBars())
            view042.setPadding(systemBars042.left, systemBars042.top, systemBars042.right, systemBars042.bottom)
            insets042
        }

        sessionManager042 = SessionManager042(this)
        userId042 = sessionManager042.getUserId042()

        if (userId042 == -1) {
            finish()
            return
        }

        itemId042 = intent.getIntExtra(EXTRA_ITEM_ID_042, -1)

        setupUIFormMode042()
        setupActionListeners042()
    }

    private fun setupUIFormMode042() {
        if (itemId042 != -1) {
            binding042.tvFormHeaderTitle042.text = "Edit Barang"
            binding042.tvFormSectionTitle042.text = "Edit Informasi Barang"
            binding042.btnSaveItem042.text = "PERBARUI DATA"

            inventoryViewModel042.getItemById042(itemId042) { item042 ->
                if (item042 != null) {
                    binding042.etItemName042.setText(item042.nama042)
                    binding042.etItemStock042.setText(item042.jumlah042.toString())
                    
                    val formattedPriceStr042 = if (item042.harga042 % 1.0 == 0.0) {
                        item042.harga042.toLong().toString()
                    } else {
                        item042.harga042.toString()
                    }
                    binding042.etItemPrice042.setText(formattedPriceStr042)
                }
            }
        } else {
            binding042.tvFormHeaderTitle042.text = "Tambah Barang Baru"
            binding042.tvFormSectionTitle042.text = "Tambah Informasi Barang"
            binding042.btnSaveItem042.text = "SIMPAN DATA"
        }
    }

    private fun setupActionListeners042() {
        binding042.btnBack042.setOnClickListener {
            finish()
        }

        binding042.btnCancelItem042.setOnClickListener {
            finish()
        }

        binding042.btnSaveItem042.setOnClickListener {
            saveOrUpdateItem042()
        }
    }

    private fun saveOrUpdateItem042() {
        val nama042 = binding042.etItemName042.text.toString()
        val jumlahStr042 = binding042.etItemStock042.text.toString()
        val hargaStr042 = binding042.etItemPrice042.text.toString()

        if (itemId042 != -1) {
            inventoryViewModel042.updateItem042(
                itemId042 = itemId042,
                userId042 = userId042,
                nama042 = nama042,
                jumlahStr042 = jumlahStr042,
                hargaStr042 = hargaStr042
            ) { success042, message042 ->
                Toast.makeText(this, message042, Toast.LENGTH_SHORT).show()
                if (success042) {
                    finish()
                }
            }
        } else {
            inventoryViewModel042.addItem042(
                userId042 = userId042,
                nama042 = nama042,
                jumlahStr042 = jumlahStr042,
                hargaStr042 = hargaStr042
            ) { success042, message042 ->
                Toast.makeText(this, message042, Toast.LENGTH_SHORT).show()
                if (success042) {
                    finish()
                }
            }
        }
    }

    companion object {
        const val EXTRA_ITEM_ID_042 = "extra_item_id_042"
    }
}

package com.example.fadli_myinventory.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fadli_myinventory.data.entity.Item042
import com.example.fadli_myinventory.databinding.ActivityItemListBinding
import com.example.fadli_myinventory.ui.adapter.ItemAdapter042
import com.example.fadli_myinventory.ui.viewmodel.InventoryViewModel042
import com.example.fadli_myinventory.ui.viewmodel.ViewModelFactory042
import com.example.fadli_myinventory.utils.SessionManager042

class ItemListActivity : AppCompatActivity() {

    private lateinit var binding042: ActivityItemListBinding
    private lateinit var sessionManager042: SessionManager042
    private lateinit var adapter042: ItemAdapter042
    private var userId042: Int = -1

    private val inventoryViewModel042: InventoryViewModel042 by viewModels {
        ViewModelFactory042.getInstance042(this)
    }

    override fun onCreate(savedInstanceState042: Bundle?) {
        super.onCreate(savedInstanceState042)
        enableEdgeToEdge()

        binding042 = ActivityItemListBinding.inflate(layoutInflater)
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

        setupRecyclerView042()
        setupSearchAndActions042()
        observeItemList042("")
    }

    private fun setupRecyclerView042() {
        adapter042 = ItemAdapter042(
            onEditClick042 = { item042 ->
                val intent042 = Intent(this, ItemFormActivity::class.java).apply {
                    putExtra(ItemFormActivity.EXTRA_ITEM_ID_042, item042.id042)
                }
                startActivity(intent042)
            },
            onDeleteClick042 = { item042 ->
                showDeleteConfirmationDialog042(item042)
            }
        )

        binding042.rvItems042.layoutManager = LinearLayoutManager(this)
        binding042.rvItems042.adapter = adapter042
    }

    private fun setupSearchAndActions042() {
        binding042.btnBack042.setOnClickListener {
            finish()
        }

        binding042.btnAddTop042.setOnClickListener {
            navigateToAddForm042()
        }

        binding042.fabAddItem042.setOnClickListener {
            navigateToAddForm042()
        }

        binding042.etSearch042.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s042: CharSequence?, start042: Int, count042: Int, after042: Int) {}
            override fun onTextChanged(s042: CharSequence?, start042: Int, before042: Int, count042: Int) {
                val query042 = s042?.toString() ?: ""
                observeItemList042(query042)
            }
            override fun afterTextChanged(s042: Editable?) {}
        })
    }

    private fun navigateToAddForm042() {
        val intent042 = Intent(this, ItemFormActivity::class.java)
        startActivity(intent042)
    }

    private fun observeItemList042(query042: String) {
        inventoryViewModel042.getItems042(userId042, query042).observe(this) { itemList042 ->
            adapter042.submitList(itemList042)

            if (itemList042.isNullOrEmpty()) {
                binding042.layoutEmptyState042.visibility = View.VISIBLE
                binding042.rvItems042.visibility = View.GONE
            } else {
                binding042.layoutEmptyState042.visibility = View.GONE
                binding042.rvItems042.visibility = View.VISIBLE
            }
        }
    }

    private fun showDeleteConfirmationDialog042(item042: Item042) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Barang")
            .setMessage("Apakah Anda yakin ingin menghapus '${item042.nama042}' dari inventaris?")
            .setPositiveButton("Hapus") { _, _ ->
                inventoryViewModel042.deleteItem042(item042) { success042, message042 ->
                    Toast.makeText(this, message042, Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}

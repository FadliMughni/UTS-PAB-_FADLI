package com.example.fadli_myinventory.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fadli_myinventory.data.entity.Item042
import com.example.fadli_myinventory.databinding.ItemInventory042Binding
import com.example.fadli_myinventory.utils.CurrencyFormatter042

class ItemAdapter042(
    private val onEditClick042: (Item042) -> Unit,
    private val onDeleteClick042: (Item042) -> Unit
) : ListAdapter<Item042, ItemAdapter042.ItemViewHolder042>(DIFF_CALLBACK_042) {

    inner class ItemViewHolder042(private val binding042: ItemInventory042Binding) :
        RecyclerView.ViewHolder(binding042.root) {

        fun bind042(item042: Item042) {
            binding042.tvItemName042.text = item042.nama042

            val formattedPrice042 = CurrencyFormatter042.formatRupiah042(item042.harga042)
            binding042.tvItemStockPrice042.text = "Stok: ${item042.jumlah042}  |  @ $formattedPrice042"

            val subtotal042 = item042.jumlah042 * item042.harga042
            val formattedSubtotal042 = CurrencyFormatter042.formatRupiah042(subtotal042)
            binding042.tvItemSubtotal042.text = "Subtotal: $formattedSubtotal042"

            binding042.btnEdit042.setOnClickListener {
                onEditClick042(item042)
            }

            binding042.btnDelete042.setOnClickListener {
                onDeleteClick042(item042)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder042 {
        val binding042 = ItemInventory042Binding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder042(binding042)
    }

    override fun onBindViewHolder(holder: ItemViewHolder042, position: Int) {
        val item042 = getItem(position)
        if (item042 != null) {
            holder.bind042(item042)
        }
    }

    companion object {
        private val DIFF_CALLBACK_042 = object : DiffUtil.ItemCallback<Item042>() {
            override fun areItemsTheSame(oldItem042: Item042, newItem042: Item042): Boolean {
                return oldItem042.id042 == newItem042.id042
            }

            override fun areContentsTheSame(oldItem042: Item042, newItem042: Item042): Boolean {
                return oldItem042 == newItem042
            }
        }
    }
}

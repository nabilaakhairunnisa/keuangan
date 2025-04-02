package com.nabila.ketapang

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nabila.ketapang.databinding.ItemFinanceBinding
import java.text.NumberFormat
import java.util.Locale

class FinanceAdapter(private val finances: List<Finance>) : RecyclerView.Adapter<FinanceAdapter.FinanceViewHolder>() {
    inner class FinanceViewHolder(val binding: ItemFinanceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinanceViewHolder {
        val binding = ItemFinanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinanceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FinanceViewHolder, position: Int) {
        val finance = finances[position]

        // Format angka agar ada titik setiap ribuan
        val formattedAmount = NumberFormat.getInstance(Locale("id", "ID")).format(finance.amount)

        holder.binding.tvDate.text = finance.date
        holder.binding.tvDescription.text = finance.description
        holder.binding.tvAmount.text = if (finance.isIncome) "+Rp $formattedAmount" else "-Rp $formattedAmount"

        // Warna teks berdasarkan tipe transaksi
        val color = if (finance.isIncome) Color.BLUE else Color.BLACK
        holder.binding.tvDescription.setTextColor(color)
        holder.binding.tvAmount.setTextColor(color)


    }

    override fun getItemCount() = finances.size
}

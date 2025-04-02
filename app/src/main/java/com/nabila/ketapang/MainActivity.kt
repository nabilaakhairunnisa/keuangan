package com.nabila.ketapang

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nabila.ketapang.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FinanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(FinanceViewModel::class.java)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allFinances.observe(this) { finances ->
            binding.recyclerView.adapter = FinanceAdapter(finances)

            // Hitung total saldo dari pemasukan dan pengeluaran
            val totalSaldo = finances.sumOf { if (it.isIncome) it.amount else -it.amount }

            // Format saldo dengan titik
            val formattedSaldo = NumberFormat.getInstance(Locale("id", "ID")).format(totalSaldo)

            // Tampilkan saldo di tvTotalBalance
            binding.tvTotalBalance.text = "Saldo: Rp $formattedSaldo"
        }

        binding.fab.setOnClickListener {
            val fragment = FinanceFragment()
            fragment.show(supportFragmentManager, "addFinance")
        }
    }
}

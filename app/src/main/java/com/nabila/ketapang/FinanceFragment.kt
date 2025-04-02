package com.nabila.ketapang

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.nabila.ketapang.databinding.FragmentAddFinanceBinding
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

class FinanceFragment : DialogFragment() {
    private lateinit var binding: FragmentAddFinanceBinding
    private lateinit var viewModel: FinanceViewModel
    private var isIncome = false
    private val calendar = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddFinanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(FinanceViewModel::class.java)

        binding.btnType.setOnClickListener {
            isIncome = !isIncome
            binding.btnType.text = if (isIncome) "Pemasukan" else "Pengeluaran"
        }

        // Set tanggal awal ke hari ini
        updateDateButton()

        // Saat btnDate diklik, munculkan DatePickerDialog
        binding.btnDate.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    updateDateButton()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        // Format angka otomatis saat pengguna mengetik
        binding.etAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                binding.etAmount.removeTextChangedListener(this)

                try {
                    val cleanString = editable.toString().replace(".", "").replace("Rp ", "")

                    val parsed = cleanString.toLongOrNull() ?: 0L
                    val formatted = NumberFormat.getNumberInstance(Locale("id", "ID")).format(parsed)

                    binding.etAmount.setText("Rp $formatted")
                    binding.etAmount.setSelection(binding.etAmount.text.length)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                binding.etAmount.addTextChangedListener(this)
            }
        })

        binding.btnSave.setOnClickListener {
            val description = binding.etDescription.text.toString()
            val amountText = binding.etAmount.text.toString().replace(".", "").replace("Rp ", "")
            val amount = amountText.toIntOrNull() ?: 0

            viewModel.addFinance(
                Finance(
                    date = binding.btnDate.text.toString(),
                    description = description,
                    amount = amount,
                    isIncome = isIncome
                )
            )

            dismiss()
        }
    }

    private fun updateDateButton() {
        val format = java.text.SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id"))
        binding.btnDate.text = format.format(calendar.time)
    }
}

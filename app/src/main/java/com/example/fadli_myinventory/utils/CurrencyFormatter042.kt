package com.example.fadli_myinventory.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyFormatter042 {
    fun formatRupiah042(amount042: Double): String {
        val localeId042 = Locale.forLanguageTag("id-ID")
        val formatter042 = NumberFormat.getCurrencyInstance(localeId042)
        return formatter042.format(amount042)
    }
}

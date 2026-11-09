package com.example.fadli_myinventory.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fadli_myinventory.data.entity.User042
import com.example.fadli_myinventory.data.repository.InventoryRepository042
import com.example.fadli_myinventory.utils.PasswordHasher042
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class AuthResult042 {
    data class Success042(val user042: User042, val message042: String) : AuthResult042()
    data class Error042(val message042: String) : AuthResult042()
}

class AuthViewModel042(private val repository042: InventoryRepository042) : ViewModel() {

    private val _authResult042 = MutableLiveData<AuthResult042?>()
    val authResult042: LiveData<AuthResult042?> = _authResult042

    fun resetAuthResult042() {
        _authResult042.value = null
    }

    fun registerUser042(username042: String, password042: String, confirmPassword042: String) {
        val trimmedUsername042 = username042.trim()
        val trimmedPassword042 = password042.trim()
        val trimmedConfirm042 = confirmPassword042.trim()

        if (trimmedUsername042.isEmpty()) {
            _authResult042.value = AuthResult042.Error042("Username tidak boleh kosong")
            return
        }

        if (trimmedPassword042.length < 6) {
            _authResult042.value = AuthResult042.Error042("Password minimal 6 karakter")
            return
        }

        if (trimmedPassword042 != trimmedConfirm042) {
            _authResult042.value = AuthResult042.Error042("Konfirmasi password tidak cocok")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val existingUser042 = repository042.getUserByUsername042(trimmedUsername042)
            if (existingUser042 != null) {
                withContext(Dispatchers.Main) {
                    _authResult042.value = AuthResult042.Error042("Username sudah terdaftar, gunakan username lain")
                }
                return@launch
            }

            val hashedPassword042 = PasswordHasher042.hashPassword042(trimmedPassword042)
            val newUser042 = User042(username042 = trimmedUsername042, password042 = hashedPassword042)
            val newId042 = repository042.registerUser042(newUser042)

            withContext(Dispatchers.Main) {
                if (newId042 > 0) {
                    val registeredUser042 = newUser042.copy(id042 = newId042.toInt())
                    _authResult042.value = AuthResult042.Success042(registeredUser042, "Registrasi berhasil! Silakan login.")
                } else {
                    _authResult042.value = AuthResult042.Error042("Registrasi gagal, coba lagi nanti")
                }
            }
        }
    }

    fun loginUser042(username042: String, password042: String) {
        val trimmedUsername042 = username042.trim()
        val trimmedPassword042 = password042.trim()

        if (trimmedUsername042.isEmpty()) {
            _authResult042.value = AuthResult042.Error042("Username tidak boleh kosong")
            return
        }

        if (trimmedPassword042.length < 6) {
            _authResult042.value = AuthResult042.Error042("Password minimal 6 karakter")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val user042 = repository042.getUserByUsername042(trimmedUsername042)
            val hashedPassword042 = PasswordHasher042.hashPassword042(trimmedPassword042)

            withContext(Dispatchers.Main) {
                if (user042 != null && user042.password042 == hashedPassword042) {
                    _authResult042.value = AuthResult042.Success042(user042, "Login berhasil!")
                } else {
                    _authResult042.value = AuthResult042.Error042("Username atau password salah")
                }
            }
        }
    }
}

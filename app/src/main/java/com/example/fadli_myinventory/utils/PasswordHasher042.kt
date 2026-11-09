package com.example.fadli_myinventory.utils

import java.security.MessageDigest

object PasswordHasher042 {
    fun hashPassword042(password042: String): String {
        val bytes042 = MessageDigest.getInstance("SHA-256").digest(password042.toByteArray())
        return bytes042.joinToString("") { "%02x".format(it) }
    }
}

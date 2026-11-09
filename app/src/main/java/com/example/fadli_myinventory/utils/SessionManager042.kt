package com.example.fadli_myinventory.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager042(context042: Context) {
    private val pref042: SharedPreferences = context042.getSharedPreferences(PREF_NAME_042, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME_042 = "user_session_042"
        private const val KEY_USER_ID_042 = "user_id_042"
        private const val KEY_USERNAME_042 = "username_042"
        private const val KEY_IS_LOGGED_IN_042 = "is_logged_in_042"
    }

    fun saveUserSession042(userId042: Int, username042: String) {
        val editor042 = pref042.edit()
        editor042.putInt(KEY_USER_ID_042, userId042)
        editor042.putString(KEY_USERNAME_042, username042)
        editor042.putBoolean(KEY_IS_LOGGED_IN_042, true)
        editor042.apply()
    }

    fun getUserId042(): Int {
        return pref042.getInt(KEY_USER_ID_042, -1)
    }

    fun getUsername042(): String {
        return pref042.getString(KEY_USERNAME_042, "") ?: ""
    }

    fun isLoggedIn042(): Boolean {
        return pref042.getBoolean(KEY_IS_LOGGED_IN_042, false)
    }

    fun clearSession042() {
        val editor042 = pref042.edit()
        editor042.clear()
        editor042.apply()
    }
}

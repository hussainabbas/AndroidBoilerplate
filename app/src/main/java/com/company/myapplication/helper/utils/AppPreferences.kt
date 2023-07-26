package com.company.myapplication.helper.utils

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private val appPreferences: SharedPreferences =
        context.getSharedPreferences(APP_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    fun getString(key: String): String {
        return appPreferences.getString(key, "") ?: ""
    }

    fun putString(key: String, value: String) {
        val editor = appPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        val editor = appPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String): Boolean {
        return appPreferences.getBoolean(key, false)
    }

    fun clearPreferences() {
        val editor = appPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        // TODO: CHANGE KEYS
        const val APP_PREFERENCES_FILE_NAME =
            "com.mmm.bachatapp.helper.utils.APP_PREFERENCES_FILE_NAME"
        const val TOKEN = "com.mmm.bachatapp.helper.utils.TOKEN"
        const val USER = "com.mmm.bachatapp.helper.utils.USER"
        const val NUMBER = "com.mmm.bachatapp.helper.utils.NUMBER"
        const val PASSWORD = "com.mmm.bachatapp.helper.utils.PASSWORD"
        const val IS_FINGERPRINT_LOGIN = "com.mmm.bachatapp.helper.utils.IS_FINGERPRINT_LOGIN"
        const val FCM_TOKEN = "com.mmm.bachatapp.helper.utils.FCM_TOKEN"

    }
}
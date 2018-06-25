package com.digzdigital.divinitytoday.data.session

import android.content.SharedPreferences
import javax.inject.Inject

class SessionManager @Inject constructor(private val preferences: SharedPreferences) {

    fun isRealmDbInUse() {
        preferences.getBoolean(REALM_DB, false)
    }

    fun setRealmDbAsInUse() {
        preferences.edit()
                .putBoolean(REALM_DB, true)
                .apply()
    }

    companion object {
        private const val REALM_DB = "realm_db"
    }
}
package com.digzdigital.divinitytoday.data.session

import android.content.SharedPreferences
import javax.inject.Inject

class SessionManager @Inject constructor(private val preferences: SharedPreferences) {

    fun shouldDoMigration()= preferences.getBoolean(migrate_db, false)

    fun setMigrationAsDone() {
        preferences.edit()
                .putBoolean(migrate_db, true)
                .apply()
    }

    companion object {
        private const val migrate_db = "migrate_db"
    }
}
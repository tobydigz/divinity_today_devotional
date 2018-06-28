package com.digzdigital.divinitytoday.data.session

import android.content.SharedPreferences
import javax.inject.Inject

class SessionManager @Inject constructor(private val preferences: SharedPreferences) {

    fun shouldDoMigration() = preferences.getBoolean(migrate_db, false)

    fun setMigrationAsDone() {
        preferences.edit()
                .putBoolean(migrate_db, true)
                .apply()
    }

    fun isFirstLoad() = preferences.getBoolean(first_load, true)

    fun setFirstLoad() {
        preferences.edit()
                .putBoolean(first_load, false)
                .apply()
    }

    companion object {
        private const val migrate_db = "migrate_db"
        private const val first_load = "first_load"
    }
}
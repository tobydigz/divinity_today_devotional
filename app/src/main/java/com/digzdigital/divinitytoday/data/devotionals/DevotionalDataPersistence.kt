package com.digzdigital.divinitytoday.data.devotionals

import com.digzdigital.divinitytoday.data.model.Devotional

interface DevotionalDataPersistence {

    fun save(devotional: Devotional)

    fun addToBookmarked(devotional: Devotional)

    fun removeFromBookmarked(devotionalId: String)
}
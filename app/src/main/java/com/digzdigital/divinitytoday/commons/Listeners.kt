package com.digzdigital.divinitytoday.commons

import com.digzdigital.divinitytoday.data.model.Devotional


interface DevotionalClickListener {
    fun onItemClick(devotional: Devotional)

    fun onBookmarkClick(devotional: Devotional)
}
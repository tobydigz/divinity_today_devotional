package com.digzdigital.divinitytoday.commons

import android.view.View
import com.digzdigital.divinitytoday.data.model.Devotional

interface MyClickListener{
    fun onItemClick(devotional: Devotional)
}
interface SavedDevotionalClickListener{
    fun onItemClick(devotional: Devotional)
    fun onDeleteClicked(devotional: Devotional)
}

interface OnDevotionalSelectedListener {
    fun onDevotionalSelected(devotional:Devotional, isOnline:Boolean)
}
package com.digzdigital.divinitytoday.ui.devlist.adapter

import android.view.View
import com.digzdigital.divinitytoday.data.model.Devotional

interface MyClickListener{
    fun onItemClick(devotional:Devotional)
}
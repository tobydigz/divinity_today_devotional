package com.digzdigital.divinitytoday.data.model

import com.digzdigital.divinitytoday.commons.adapter.AdapterConstants
import com.digzdigital.divinitytoday.commons.adapter.ViewType
import com.google.android.gms.ads.NativeExpressAdView
import com.squareup.moshi.Json

data class RemoteDevotional(@Json(name = "id") val id: String,
                            @Json(name = "date") val date: String,
                            @Json(name = "title") val title: DevotionalItem,
                            @Json(name = "content") val content: DevotionalItem,
                            @Json(name = "excerpt") val excerpt: DevotionalItem)

data class DevotionalItem(@Json(name = "rendered") val rendered: String)

data class Devotional(val id: String,
                      val title: String,
                      val date: String,
                      val content: String,
                      val excerpt: String,
                      val bookmarked: Boolean) : ViewType {
    override fun getViewType() = AdapterConstants.DEVOTIONAL

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Devotional) return false
        if (other.id == id) return true
        return false
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + excerpt.hashCode()
        result = 31 * result + bookmarked.hashCode()
        return result
    }
}
package com.digzdigital.divinitytoday.ui.devlist.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.digzdigital.divinitytoday.R
import com.digzdigital.divinitytoday.commons.adapter.ViewType
import com.digzdigital.divinitytoday.commons.adapter.ViewTypeDelegateAdapter
import com.digzdigital.divinitytoday.data.model.DevotionalAd
import xyz.digzdigital.keddit.commons.extensions.inflate

class AdDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup) = AdViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as AdViewHolder
        holder.bind(item as DevotionalAd)
    }

    class AdViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_ad)) {
        fun bind(item: ViewType) = with(itemView) {
            val devotionalAd = item as DevotionalAd
            val adView = devotionalAd.ad
            val adCardView = itemView as ViewGroup
            if (adCardView.childCount > 0) adCardView.removeAllViews()
            if (adView.parent != null) (adView.parent as ViewGroup).removeView(adView)
            adCardView.addView(adView)
        }
    }

}

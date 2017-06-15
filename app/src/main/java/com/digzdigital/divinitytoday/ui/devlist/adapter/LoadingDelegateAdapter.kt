package xyz.digzdigital.keddit.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import xyz.digzdigital.keddit.R
import xyz.digzdigital.keddit.commons.adapter.ViewType
import xyz.digzdigital.keddit.commons.adapter.ViewTypeDelegateAdapter
import xyz.digzdigital.keddit.commons.extensions.inflate

class LoadingDelegateAdapter:ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class LoadingViewHolder(parent: ViewGroup):RecyclerView.ViewHolder(parent.inflate(R.layout.item_loading))

}

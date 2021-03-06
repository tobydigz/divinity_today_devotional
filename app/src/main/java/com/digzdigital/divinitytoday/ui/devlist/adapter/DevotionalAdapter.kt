package com.digzdigital.divinitytoday.ui.devlist.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.digzdigital.divinitytoday.commons.MyClickListener
import com.digzdigital.divinitytoday.commons.adapter.AdapterConstants
import com.digzdigital.divinitytoday.commons.adapter.ViewType
import com.digzdigital.divinitytoday.commons.adapter.ViewTypeDelegateAdapter
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.data.model.DevotionalAd

class DevotionalAdapter(myClickListener: MyClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val loadingItem = object : ViewType {
        override fun getViewType() = AdapterConstants.LOADING
    }

    init {
        delegateAdapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConstants.DEVOTIONAL, DevotionalDelegateAdapter(myClickListener))
        delegateAdapters.put(AdapterConstants.AD, AdDelegateAdapter())
        items = java.util.ArrayList()
        items.add(loadingItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return this.items[position].getViewType()
    }

    fun addDevotionals(news: List<Devotional>) {
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition, items.size + 1)
    }

    fun addAds(position:Int, devotionalAd: DevotionalAd){
        items.add(position, devotionalAd)
        notifyItemInserted(position)
    }

    fun clearAndAddDevotionals(news: List<Devotional>) {
        val lastPosition = getLastPosition()
        items.clear()
        notifyItemRangeRemoved(0, lastPosition)

        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
        notifyDataSetChanged()
    }

    fun getDevotionals(): List<Devotional> {
        return items
                .filter { it.getViewType() == AdapterConstants.DEVOTIONAL }
                .map { it as Devotional }
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex


}
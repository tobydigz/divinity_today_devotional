package com.digzdigital.divinitytoday.ui.saveddevlist.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.digzdigital.divinitytoday.commons.SavedDevotionalClickListener
import com.digzdigital.divinitytoday.commons.adapter.AdapterConstants
import com.digzdigital.divinitytoday.commons.adapter.ViewType
import com.digzdigital.divinitytoday.commons.adapter.ViewTypeDelegateAdapter
import com.digzdigital.divinitytoday.data.model.Devotional

class SavedDevotionalAdapter(myClickListener: SavedDevotionalClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        delegateAdapters.put(AdapterConstants.DEVOTIONAL, SavedDevotionalDelegateAdapter(myClickListener))
        items = ArrayList()
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
        return this.items.get(position).getViewType()
    }

    fun addDevotionals(news:List<Devotional>){
        val initPosition = items.size - 1

        items.addAll(news)
        notifyItemRangeChanged(0, items.size + 1)
        notifyDataSetChanged()
    }

    fun removeDevotional(position:Int){
        items.removeAt(position)
        notifyItemRemoved(position)

        notifyItemRangeChanged(position, items.size + 1)
    }

    fun getDevotionals(): List<Devotional>{
        return items
                .filter { it.getViewType() == AdapterConstants.DEVOTIONAL }
                .map { it as Devotional }
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}
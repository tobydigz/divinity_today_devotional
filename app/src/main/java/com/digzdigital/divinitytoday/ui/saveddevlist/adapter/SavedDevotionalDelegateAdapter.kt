package com.digzdigital.divinitytoday.ui.saveddevlist.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.digzdigital.divinitytoday.R
import com.digzdigital.divinitytoday.commons.SavedDevotionalClickListener
import com.digzdigital.divinitytoday.commons.adapter.ViewType
import com.digzdigital.divinitytoday.commons.adapter.ViewTypeDelegateAdapter
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.data.commons.getCleanedTitle
import com.digzdigital.divinitytoday.data.commons.getFormattedDate
import kotlinx.android.synthetic.main.item_saved_devotional.view.*
import xyz.digzdigital.keddit.commons.extensions.inflate


class SavedDevotionalDelegateAdapter(val myClickListener: SavedDevotionalClickListener) : ViewTypeDelegateAdapter {


    override fun onCreateViewHolder(parent: ViewGroup) = DevotionalViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as DevotionalViewHolder
        holder.bind(item as Devotional, myClickListener)
    }


    class DevotionalViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_saved_devotional)) {
        fun bind(item: Devotional, myClickListener: SavedDevotionalClickListener) = with(itemView) {
            dev_title.text = item.getCleanedTitle()
            dev_date.text = item.getFormattedDate()
            dev_remove.setOnClickListener { myClickListener.onDeleteClicked(item) }
            itemView.setOnClickListener { myClickListener.onItemClick(item) }
        }


    }
}
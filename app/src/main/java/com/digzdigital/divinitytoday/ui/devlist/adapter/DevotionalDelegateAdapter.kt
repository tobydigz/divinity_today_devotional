package com.digzdigital.divinitytoday.ui.devlist.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.digzdigital.divinitytoday.R
import com.digzdigital.divinitytoday.commons.DevotionalClickListener
import com.digzdigital.divinitytoday.commons.adapter.ViewType
import com.digzdigital.divinitytoday.commons.adapter.ViewTypeDelegateAdapter
import com.digzdigital.divinitytoday.data.model.Devotional
import kotlinx.android.synthetic.main.item_devotional.view.*
import xyz.digzdigital.keddit.commons.extensions.inflate


class DevotionalDelegateAdapter(val myClickListener: DevotionalClickListener) : ViewTypeDelegateAdapter {


    override fun onCreateViewHolder(parent: ViewGroup) = DevotionalViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as DevotionalViewHolder
        holder.bind(item as Devotional)
    }


    inner class DevotionalViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_devotional)) {
        fun bind(item: Devotional) = with(itemView) {
            dev_title.text = item.title
            dev_date.text = item.date
            itemView.setOnClickListener { myClickListener.onItemClick(item) }
        }


    }
}
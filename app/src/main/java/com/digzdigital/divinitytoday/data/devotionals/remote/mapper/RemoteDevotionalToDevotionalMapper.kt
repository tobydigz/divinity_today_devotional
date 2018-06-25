package com.digzdigital.divinitytoday.data.devotionals.remote.mapper

import com.digzdigital.divinitytoday.data.commons.Mapper
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.data.model.RemoteDevotional

class RemoteDevotionalToDevotionalMapper : Mapper<RemoteDevotional, Devotional> {

    override fun map1(from: RemoteDevotional) = Devotional(
            id = from.id,
            title = from.title.rendered,
            date = from.date,
            content = from.content.rendered,
            excerpt = from.excerpt.rendered,
            bookmarked = false
    )

    override fun mapMany(fromCollection: Collection<RemoteDevotional>): Collection<Devotional> {
        val devotionals = ArrayList<Devotional>()
        fromCollection.mapTo(devotionals) { map1(it) }
        return devotionals
    }
}
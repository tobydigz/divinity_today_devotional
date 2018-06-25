package com.digzdigital.divinitytoday.data.devotionals.local.mapper

import com.digzdigital.divinitytoday.data.commons.Mapper
import com.digzdigital.divinitytoday.data.devotionals.local.model.DevotionalRealm
import com.digzdigital.divinitytoday.data.model.Devotional
import javax.inject.Inject

class DevotionalRealmToDevotionalMapper @Inject constructor() : Mapper<DevotionalRealm, Devotional> {

    override fun map1(from: DevotionalRealm) = Devotional(
            id = from.id,
            title = from.title,
            date = from.date,
            content = from.content,
            excerpt = from.excerpt
    )

    override fun mapMany(fromCollection: Collection<DevotionalRealm>): Collection<Devotional> {
        val devotionals = ArrayList<Devotional>()
        fromCollection.mapTo(devotionals) { map1(it) }
        return devotionals
    }
}
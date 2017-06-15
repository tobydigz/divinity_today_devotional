package com.digzdigital.divinitytoday.data.wp.retrofit

class DevotionalObject(val id: String,
                       val date: String,
                       val title: DevotionalItem,
                       val content: DevotionalItem)

class DevotionalItem(val rendered: String)
package com.digzdigital.divinitytoday.data.model

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist
import java.text.SimpleDateFormat
import java.util.*

fun Devotional.getFormattedDate(): String {
    val dateString1 = date.replace("T", " ")
    val dateString2 = dateString1.replace("-", "/")
    val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    var convertedDate = dateFormat.parse(dateString2)

    val dayOfTheWeek = android.text.format.DateFormat.format("EEEE", convertedDate) as String//Thursday
    val stringMonth = android.text.format.DateFormat.format("MMM", convertedDate) as String //Jun
//        String intMonth = (String) android.text.format.DateFormat.format("MM", date); //06
    val year = android.text.format.DateFormat.format("yyyy", convertedDate) as String //2013
    val day = android.text.format.DateFormat.format("dd", convertedDate) as String //20
    return "$dayOfTheWeek $day $stringMonth $year"
}

fun Devotional.getCleanedTitle(): String = Jsoup.parse(title).text()

fun Devotional.getCleanContent(): String {
    val document = Jsoup.parse(content)
    document.outputSettings(Document.OutputSettings().prettyPrint(false))//makes html() preserve linebreaks and spacing
    document.select("br").append("\\n")
    document.select("p").prepend("")

    var s = document.html().replace("\\\\n".toRegex(), "\n")

    s = Jsoup.clean(s, "", Whitelist.none(), Document.OutputSettings().prettyPrint(false))
    val st = s.replace("&nbsp;", "")
    return st
}


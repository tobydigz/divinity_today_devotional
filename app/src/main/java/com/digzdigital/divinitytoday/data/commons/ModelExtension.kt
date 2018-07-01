package com.digzdigital.divinitytoday.data.commons

import android.annotation.SuppressLint
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist
import java.text.SimpleDateFormat

object ModelExtension {
    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(date: String): Long {
        val dateString1 = date.replace("T", " ")
        val dateString2 = dateString1.replace("-", "/")
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val convertedDate = dateFormat.parse(dateString2)
        return convertedDate.time
    }

    fun getFriendlyDate(date: Long): String {
        val dayOfTheWeek = android.text.format.DateFormat.format("EEEE", date) as String//Thursday
        val stringMonth = android.text.format.DateFormat.format("MMM", date) as String //Jun
        val year = android.text.format.DateFormat.format("yyyy", date) as String //2013
        val day = android.text.format.DateFormat.format("dd", date) as String //20
        return "$dayOfTheWeek $day $stringMonth, $year"
    }

    fun getCleanedTitle(title: String): String = Jsoup.parse(title).text()

    fun getCleanedContent(content: String): String {
        val document = Jsoup.parse(content)
        document.outputSettings(Document.OutputSettings().prettyPrint(false))//makes html() preserve linebreaks and spacing
        document.select("br").append("\\n")
        document.select("p").prepend("")

        var s = document.html().replace("\\\\n".toRegex(), "\n")

        s = Jsoup.clean(s, "", Whitelist.none(), Document.OutputSettings().prettyPrint(false))
        val st = s.replace("&nbsp;", "")
        return st
    }
}
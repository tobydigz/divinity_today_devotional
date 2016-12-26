package com.digzdigital.divinitytoday.devlist;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * Created by Digz on 26/12/2016.
 */

public class ParseJSON {

    public ParseJSON() {

    }

    public static String html2ptesxt(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
        document.select("br").append("\\n");
        document.select("p").prepend("");

        String s = document.html().replaceAll("\\\\n", "\n");

        s = Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
        String st = s.replace("&nbsp;", "");
        return st;
    }
}

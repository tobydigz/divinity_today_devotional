package com.digzdigital.divinitytoday.devlist;

import com.digzdigital.divinitytoday.model.Devotional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Digz on 26/12/2016.
 */

public class ParseJSON {

    public ParseJSON() {

    }

    private static String html2ptesxt(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
        document.select("br").append("\\n");
        document.select("p").prepend("");

        String s = document.html().replaceAll("\\\\n", "\n");

        s = Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
        String st = s.replace("&nbsp;", "");
        return st;
    }

    public ArrayList<Devotional> createDev(String json) {
        ArrayList<Devotional> devotionals = new ArrayList<>();
        String[] postTitle;
        String[] postDate;
        int[] postId;
        String[] postContent;
        JSONObject jsonObject = null;
        JSONArray content = null;
        try {
            content = new JSONArray(json);

            postTitle = new String[content.length()];
            postDate = new String[content.length()];
            postId = new int[content.length()];
            postContent = new String[content.length()];

            for (int i = 0; i < content.length(); i++) {
                JSONObject jo = content.getJSONObject(i);
                postId[i] = jo.getInt("id");
                JSONObject title = jo.getJSONObject("title");
                postTitle[i] = Jsoup.parse((title.getString("rendered"))).text();
                postDate[i] = cleanDate(jo.getString("date"));
                JSONObject content1 = jo.getJSONObject("content");
                postContent[i] = html2ptesxt(content1.getString("rendered"));
                devotionals.add(new Devotional(postId[i], postTitle[i], postDate[i], postContent[i], false));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devotionals;
    }

    private String cleanDate(String dateString) {
        String dateString1 = dateString.replace("T", " ");
        String dateString2 = dateString1.replace("-", "/");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString2);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String dayOfTheWeek = (String) android.text.format.DateFormat.format("EEEE", convertedDate);//Thursday
        String stringMonth = (String) android.text.format.DateFormat.format("MMM", convertedDate); //Jun
//        String intMonth = (String) android.text.format.DateFormat.format("MM", date); //06
        String year = (String) android.text.format.DateFormat.format("yyyy", convertedDate); //2013
        String day = (String) android.text.format.DateFormat.format("dd", convertedDate); //20
        return dayOfTheWeek + " " + day + " " + stringMonth + " " + year;
    }
}

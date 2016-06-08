package com.digzdigital.dtd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON {
    public static String[] postTitle;
    public static String[] postDate;
    public static String[] postId;
    public static String[] postContent;
    public static Integer num;

//    public static final String JSON_ARRAY = "dev_array";
//    public static final String DEV_TITLE = "TITLE";
//    public static final String DEV_DATE = "DATE";

    private JSONArray content = null;

    private String json;

    public ParseJSON(String json) {this.json = json;}

    protected void parseJSON(){
        JSONObject jsonObject = null;
        try {
            content = new JSONArray(json);
//            content = jsonObject.getJSONArray(JSON_ARRAY);

            postTitle = new String[content.length()];
            postDate = new String[content.length()];
            postId = new String[content.length()];
            postContent = new String[content.length()];

            for (int i=0; i<content.length();i++){
                JSONObject jo = content.getJSONObject(i);
                postId[i] = jo.getString("id");
                JSONObject title = jo.getJSONObject("title");
                postTitle[i] = title.getString("rendered");
                postDate[i] = jo.getString("date");
                JSONObject content = jo.getJSONObject("content");
                postContent[i] = content.getString("rendered");
                num = i;
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}

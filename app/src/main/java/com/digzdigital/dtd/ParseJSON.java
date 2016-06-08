package com.digzdigital.dtd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON {
    public static String[] postTitle;
    public static String[] postDate;

    public static final String JSON_ARRAY = "dev_array";
    public static final String DEV_TITLE = "TITLE";
    public static final String DEV_DATE = "DATE";

    private JSONArray content = null;

    private String json;

    public ParseJSON(String json) {this.json = json;}

    protected void parseJSON(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            content = jsonObject.getJSONArray(JSON_ARRAY);

            postTitle = new String[content.length()];
            postDate = new String[content.length()];

            for (int i=0; i<content.length();i++){
                JSONObject jo = content.getJSONObject(i);
                postTitle[i] = jo.getString(DEV_TITLE);
                postDate[i] = jo.getString(DEV_DATE);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}

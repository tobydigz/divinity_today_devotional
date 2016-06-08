package com.digzdigital.dtd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON1 {
    public static String[] postContent;

    public static final String JSON_ARRAY = "dev_array";
    public static final String DEV_CONTENT = "CONTENT";

    private JSONArray content1 = null;

    private String json;

    public ParseJSON1(String json) {this.json = json;}

    protected void parseJSON1(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            content1 = jsonObject.getJSONArray(JSON_ARRAY);

            postContent = new String[content1.length()];

            for (int i=0; i<content1.length();i++){
                JSONObject jo = content1.getJSONObject(i);
                postContent[i] = jo.getString(DEV_CONTENT);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
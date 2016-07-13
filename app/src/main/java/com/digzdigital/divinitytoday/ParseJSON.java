package com.digzdigital.divinitytoday;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;

public class ParseJSON {
    public static String[] postTitle;
    public static String[] postDate;
    public static String[] postId;
    public static String[] postContent;
    public static Integer num;


    private JSONArray content = null;

    private String json;

    public ParseJSON(String json) {this.json = json;}

    protected void parseJSON(){
        JSONObject jsonObject = null;
        try {
            content = new JSONArray(json);

            postTitle = new String[content.length()];
            postDate = new String[content.length()];
            postId = new String[content.length()];
            postContent = new String[content.length()];

            for (int i=0; i<content.length();i++){
                JSONObject jo = content.getJSONObject(i);
                postId[i] = jo.getString("id");
                JSONObject title = jo.getJSONObject("title");
                postTitle[i] = title.getString("rendered");
                postDate[i] = cleanDate(jo.getString("date"));
                JSONObject content = jo.getJSONObject("content");
                postContent[i] = content.getString("rendered");
                num = i;
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
    public String cleanDate(String dateString) {
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

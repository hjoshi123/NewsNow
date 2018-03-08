package com.hemantjoshi.newsapp.newsmain;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hemantjoshi.newsapp.model.NewsModel;
import com.hemantjoshi.newsapp.utils.NotificationUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author HemantJ.
 * Helper class to do the tasks and set action of services to be done
 */

public class ReminderTasks {
    public static final String ACTION_TOI = "toi";
    public static final String ACTION_VERGE = "verge";
    private static final String URL_BASE = "https://newsapi.org/v2/top-headlines?sources=";
    private static final String API_KEY = "&apiKey=7294ae9e09eb42fea84f71d723c0dbba";
    private static String src;
    private static String url;
    private static RequestQueue mRequestQueue;

    public static void executeTasks(Context context, String action){
        if(action.equals(ACTION_TOI)){
            timesOfIndiaService(context);
        }else if(action.equals(ACTION_VERGE)){
            theVerge(context);
        }
    }

    private static void theVerge(Context context) {
        src = "the-verge";
        url = URL_BASE + src + API_KEY;
        requestJSONData(context, url);
    }

    private static void timesOfIndiaService(Context context){
        src = "the-times-of-india";
        url = URL_BASE + src + API_KEY;
        requestJSONData(context,url);
    }

    /**
     * This method gives the response from the url
     * @param context the activity context for setting the notifications
     * @param url the url of article
     */
    private static void requestJSONData(final Context context, String url){
        mRequestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MainActivity",response.toString());
                        parseJSON(context, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        mRequestQueue.add(objectRequest);
    }

    /**
     * This method parses JSON
     * @param context the activity context for notifications
     * @param response  JSON response from the newsapi provider
     */
    private static void parseJSON(Context context, JSONObject response) {
        try{
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray array = jsonObject.getJSONArray("articles");
            for(int i=0;i < 1; i++){
                JSONObject ob = array.getJSONObject(i);
                String title = ob.getString("title");
                String urlToText = ob.getString("url");
                NotificationUtils.remindUserAboutNews(context,title,urlToText);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}

package com.hemantjoshi.newsapp.newsmain;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.hemantjoshi.newsapp.R;
import com.hemantjoshi.newsapp.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * @presenter for MainActivity
 * @author HemantJ on 28/10/17.
 */

public class MainPresenter implements MainInterface {
    private FirebaseAuth mAuth;
    private TextView navName, navEmail;
    private MainActivity mainActivity;
    private RequestQueue mRequestQueue;
    private final String URL_BASE = "https://newsapi.org/v2/everything?sources=";
    private final String API_KEY = "&apiKey=7294ae9e09eb42fea84f71d723c0dbba";
    private String url;
    private ArrayList<NewsModel> news;
    private ProgressBar progressBar;
    private String src;

    /**
     * @constructor for the presenter class
     * @variable FirebaseAuth mAuth for auth provider
     * @variable TextView navbar name
     * @variable TextView navbar email
     * @variable MainActivity as context
     */
    MainPresenter(FirebaseAuth mAuth,TextView navName, TextView navEmail, MainActivity mainActivity){
        this.mAuth = mAuth;
        this.navEmail = navEmail;
        this.navName = navName;
        this.mainActivity = mainActivity;
        progressBar = (ProgressBar) mainActivity.findViewById(R.id.progressBar);
    }

    /*
     * Setting the nav bar name and email
     */
    @Override
    public void setUserData() {
        if(mAuth.getCurrentUser() != null){
            navName.setText(mAuth.getCurrentUser().getDisplayName());
            navEmail.setText(mAuth.getCurrentUser().getEmail());
        }
    }

    /**
     * Gets the news from the news API online
     * @variable mRequestQueue
     * @function <strong>requestJSONData(String url)</strong> sends the url for volley to send request and return JSON
     * object for further processing
     * @return void
     */
    @Override
    public void getNewsFromNewsAPI(int id) {
        progressBar.setVisibility(View.VISIBLE);
        mRequestQueue = Volley.newRequestQueue(mainActivity);
        if(id == R.id.politics){
            src = "the-times-of-india";
            url = URL_BASE + src + API_KEY;
            requestJSONData(url);
        }else if(id == R.id.tech){
            String URL_EDIT = "https://newsapi.org/v2/top-headlines?sources=";
            src = "the-verge";
            url = URL_EDIT + src + API_KEY;
            requestJSONData(url);
        }else if(id == R.id.sports){
            src = "espn";
            url = URL_BASE + src + API_KEY;
            requestJSONData(url);
        }else if(id == R.id.entertainment){
            src = "entertainment-weekly";
            url = URL_BASE + src + API_KEY;
            requestJSONData(url);
        }else if(id == R.id.buzz){
            src = "buzzfeed";
            url = URL_BASE + src + API_KEY;
            requestJSONData(url);
        }else if(id == R.id.science){
            src = "new-scientist";
            url = URL_BASE + src + API_KEY;
            requestJSONData(url);
        }else if(id == R.id.world){
            src = "reuters";
            url = URL_BASE + src + API_KEY;
            requestJSONData(url);
        }else if(id == R.id.business){
            src = "bloomberg";
            url = URL_BASE + src + API_KEY;
            requestJSONData(url);
        }
    }

    /*
     * Takes in the query of search from the user and asks Google News to give all suggestions
     * that fits the keyword
     */
    @Override
    public void getNewsFromNewsAPI(int id, String query) {
        progressBar.setVisibility(View.VISIBLE);
        mRequestQueue = Volley.newRequestQueue(mainActivity);
        if(id == 123){
            final String URL_EDIT = "https://newsapi.org/v2/everything";
            String queryParams = "?q=" + query;
            url = URL_EDIT + queryParams + API_KEY;
            requestJSONData(url);
        }
    }

    /**
     * @param url takes in the url to process the request
     * Calls {@code parseJSON(response)} to parse the JSON output got from volley
     * @return Nothing/void
     */
    private void requestJSONData(String url){
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MainActivity",response.toString());
                        parseJSON(response);
                        progressBar.setVisibility(View.GONE);
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
     * This method parses the JSON data
     * @param response is the JSON output got from requestJSONData(..)
     * @exception Exception for wrong JSON format
     * @variable title gets the title of news article
     * @variable urlToText gets the url of webpage linking it
     * @variable urlToImage gets the url of image of the article
     */
    private void parseJSON(JSONObject response) {
        try{
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray array = jsonObject.getJSONArray("articles");
            for(int i=0;i < array.length(); i++){
                JSONObject ob = array.getJSONObject(i);
                String title = ob.getString("title");
                String urlToText = ob.getString("url");
                String urlToImage = ob.getString("urlToImage");
                setNewsData(new NewsModel(title,urlToImage,urlToText));
                mainActivity.addNewsData(news);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setNewsData(NewsModel model) {
        news = new ArrayList<>();
        news.add(model);
    }
}

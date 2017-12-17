package com.hemantjoshi.newsapp.model;

/**
 * @author HemantJ on 05/11/17.
 * NewsModel is POJO class that is responsible for encapsulation of news data
 */

public class NewsModel {
    private String title;
    private String imageUrl;
    private String linkToWeb;

    /**
     * Empty required ctor for Firebase
     */
    public NewsModel(){

    }

    /**
     * Parametrized Constructor
     * @param title title of news article
     * @param imageUrl image url
     * @param linkToWeb web page link
     */
    public NewsModel(String title, String imageUrl, String linkToWeb){
        this.title = title;
        this.imageUrl = imageUrl;
        this.linkToWeb = linkToWeb;
    }

    /**
     * getters for outside classes
     * @return String
     */
    public String getTitle(){
        return this.title;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }

    public String getLinkToWeb(){
        return this.linkToWeb;
    }
}

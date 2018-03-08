package com.hemantjoshi.newsapp.newsmain;

import com.hemantjoshi.newsapp.model.NewsModel;

import java.util.ArrayList;

/**
 * @author HemantJ on 28/10/17.
 * Interface for presenter
 */

public interface MainInterface {

    void setUserData();

    /**
     * @param id takes the id from the onNavigationItemSelected()
     */
    void getNewsFromNewsAPI(int id);

    /**
     * @param id takes the id from the onCreateOptionsMenu() from the SearchView
     * @param query takes in the custom string entered by user to get all the news related to it
     */
    void getNewsFromNewsAPI(int id, String query);

    /**
     * @param model takes in model of type NewsModel
     */
    void setNewsData(NewsModel model);
}

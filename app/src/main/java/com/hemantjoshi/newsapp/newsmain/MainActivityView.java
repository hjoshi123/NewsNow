package com.hemantjoshi.newsapp.newsmain;

import com.hemantjoshi.newsapp.model.NewsModel;

import java.util.ArrayList;

/**
 * @author HemantJ on 06/11/17.
 */

public interface MainActivityView {
    void addNewsData(ArrayList<NewsModel> news);
}

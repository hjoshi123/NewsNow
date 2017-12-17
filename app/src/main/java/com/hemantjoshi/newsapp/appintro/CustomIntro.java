package com.hemantjoshi.newsapp.appintro;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.hemantjoshi.newsapp.R;

/**
 * @author HemantJ on 15/12/17.
 * @see AppIntro2 for more details
 */

public class CustomIntro extends AppIntro2 {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Adding new slides to the intro activity
         * using default fragment
         */
        addSlide(AppIntro2Fragment.newInstance("Get News Immediately",
                "Get news all through the world from different sources",R.drawable.news,
                getColor(R.color.colorPrimary)));
        addSlide(AppIntro2Fragment.newInstance("Search News",
                "Search topic and get news accordinly",
                R.drawable.ic_search_black_96dp,
                getColor(R.color.colorAccent)));
    }

    /**
     *
     * @param currentFragment the current fragment showing the details
     * Method determines what happens after the skip button is clicked during the slideshow
     */
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    /**
     * @param currentFragment the current fragment showing the details
     * Method determines what happens after done or the tick button is clicked
     */
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    /**
     * @param oldFragment the previous fragment
     * @param newFragment the next fragment
     * Method tells the behaviour to be followed when the next fragment is slided
     */
    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}

package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter;

import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.Article;

import java.util.ArrayList;

/**
 * Created by Greta Grigutė on 2018-11-01.
 */

public interface MainActivityContract {

    interface MainView {
        //for getting user input
        void onButtonClick (String search);
        void onDestroy ();
    }

    interface PresenterPushedSomeAction {
        //for showing search results
        void showNewsList (ArrayList<Article> news);
    }
}
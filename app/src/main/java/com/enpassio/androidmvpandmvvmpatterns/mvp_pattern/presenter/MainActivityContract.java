package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter;

import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.Article;

import java.util.List;

/**
 * Created by Greta Grigutė on 2018-11-01.
 */
public interface MainActivityContract {

    interface MainView {
        //for showing search results
        void showNewsList (List <Article> news);

        //for getting user input
        String getSearchPhrase();
    }

   interface Presenter {
       void onButtonClick ();
       void onDestroy ();
   }
}

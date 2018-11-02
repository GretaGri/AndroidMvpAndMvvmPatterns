package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.MvpByAbhi.presenter.mainscreen;

import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.MvpByAbhi.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.MvpByAbhi.presenter.base.RemoteView;

import java.util.List;

public interface ListContract {

    interface ViewActions {
        void onInitialListRequested(String searchQuery);

        void onTopicSearchedSearched(String searchQuery);
    }

    interface RecyclerView extends RemoteView {

        void showArticles(List<Article> characterList);

        void showSearchedTopicArticles(List<Article> characterList);
    }
}

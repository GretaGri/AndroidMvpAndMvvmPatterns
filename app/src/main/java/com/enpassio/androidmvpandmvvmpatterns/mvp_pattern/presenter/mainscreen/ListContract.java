package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter.mainscreen;

import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter.base.RemoteView;

import java.util.List;

public interface ListContract {

    interface ViewActions {
        void onInitialListRequested();

        void onTopicSearchedSearched(String searchQuery);
    }

    interface RecyclerView extends RemoteView {

        void showArticles(List<Article> characterList);

        void showSearchedTopicArticles(List<Article> characterList);
    }
}

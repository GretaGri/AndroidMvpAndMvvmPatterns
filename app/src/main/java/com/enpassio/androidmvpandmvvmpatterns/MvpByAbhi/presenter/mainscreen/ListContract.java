package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.presenter.mainscreen;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.presenter.base.RemoteView;

public interface ListContract {

    interface ViewActions {
        void onInitialListRequested(String searchQuery);

        void onTopicSearchedSearched(String searchQuery);
    }

    interface RecyclerView extends RemoteView {

        void getPagedListData(LiveData<PagedList<Article>> listLiveData);
    }
}

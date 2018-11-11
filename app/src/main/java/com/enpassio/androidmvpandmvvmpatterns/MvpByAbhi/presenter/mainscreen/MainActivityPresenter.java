package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.presenter.mainscreen;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.NewsRepository;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.NewsResponse;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.RemoteCallback;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.presenter.base.BasePresenter;


public class MainActivityPresenter extends BasePresenter<ListContract.RecyclerView> implements ListContract.ViewActions {

    private final NewsRepository mNewsRepository;

    public MainActivityPresenter(@NonNull NewsRepository newsRepository) {
        mNewsRepository = newsRepository;
    }

    @Override
    public void onInitialListRequested(String searchQuery) {
        getArticles(searchQuery);
    }


    @Override
    public void onTopicSearchedSearched(String searchQuery) {
        Log.d("my_tag", "inside presenter onTopicSearchedSearched called with: " + searchQuery);
        getArticles(searchQuery);
    }

    private void getArticles(final String searchQuery) {
        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        mNewsRepository.getNewsForQueriedParameter(searchQuery, new RemoteCallback<NewsResponse>() {
            @Override
            public void onSuccess(NewsResponse response) {
                Log.d("my_tag", "inside presenter onSuccess size is: " + response.getArticles().size());
                if (!isViewAttached()) return;
                mView.hideProgress();
                if (response.getArticles().isEmpty()) {
                    mView.showEmpty();
                    return;
                }

                if (TextUtils.isEmpty(searchQuery)) {
                    mView.showArticles(response.getArticles());
                } else {
                    mView.showSearchedTopicArticles(response.getArticles());
                }
            }

            @Override
            public void onUnauthorized() {
                if (!isViewAttached()) return;
                mView.hideProgress();
                mView.showUnauthorizedError();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d("my_tag", "inside presenter onFailed error is: " + throwable.getMessage());
                if (!isViewAttached()) return;
                mView.hideProgress();
                mView.showError(throwable.getMessage());
            }
        });
    }
}

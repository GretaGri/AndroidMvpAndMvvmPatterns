package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter.mainscreen;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network.NewsRepository;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network.RemoteCallback;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter.base.BasePresenter;

import java.util.List;

public class MainActivityPresenter extends BasePresenter<ListContract.RecyclerView> implements ListContract.ViewActions {

    private final NewsRepository mNewsRepository;

    public MainActivityPresenter(@NonNull NewsRepository newsRepository) {
        mNewsRepository = newsRepository;
    }

    @Override
    public void onInitialListRequested() {
        getArticles(null);
    }


    @Override
    public void onTopicSearchedSearched(String searchQuery) {
        getArticles(searchQuery);
    }

    private void getArticles(final String searchQuery) {
        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        mNewsRepository.getNewsForQueriedParameter(searchQuery, new RemoteCallback<List<Article>>() {
            @Override
            public void onSuccess(List<Article> response) {
                if (!isViewAttached()) return;
                mView.hideProgress();
                if (response.isEmpty()) {
                    mView.showEmpty();
                    return;
                }

                if (TextUtils.isEmpty(searchQuery)) {
                    mView.showArticles(response);
                } else {
                    mView.showSearchedTopicArticles(response);
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
                if (!isViewAttached()) return;
                mView.hideProgress();
                mView.showError(throwable.getMessage());
            }
        });
    }
}

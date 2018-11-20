package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.presenter.mainscreen;

import android.support.annotation.NonNull;

import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.NewsRepository;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.RemoteCallBack;
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
        getArticles(searchQuery);
    }

    private void getArticles(final String searchQuery) {
        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        mView.getPagedListData(mNewsRepository.getLiveDataOfPagedList(searchQuery, new RemoteCallBack() {
            @Override
            public void onFailure(String error) {
                mView.showError(error);
            }

            @Override
            public void onSuccess() {
                mView.hideProgress();
            }

            @Override
            public void onEmpty() {
                mView.showEmpty();
            }
        }));
    }
}

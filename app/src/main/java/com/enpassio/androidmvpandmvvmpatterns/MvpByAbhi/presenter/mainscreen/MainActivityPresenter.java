package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.presenter.mainscreen;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.NewsRepository;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.NewsResponse;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.RemoteCallback;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.presenter.base.BasePresenter;

import java.util.ArrayList;


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

    //handle view leak by using callback
    private void getArticles(final String searchQuery) {
        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        final ArrayList<Article> articleArrayList = new ArrayList<>();
        mView.getPagedListData(mNewsRepository.getLiveDataOfPagedLis(searchQuery, new RemoteCallback<NewsResponse>() {
            @Override
            public void onSuccess(NewsResponse response) {
                if (response != null) {
                    if (!(response.getArticles().size() > 0)) {
                        articleArrayList.addAll( response.getArticles());
                        mView.showEmpty();

                    } else {
                        mView.hideProgress();

                    }
                }
            }

            @Override
            public void onCallbackInitial(@NonNull PageKeyedDataSource.LoadInitialParams<Long> params, @NonNull PageKeyedDataSource.LoadInitialCallback<Long, Article> callback, int mPageNumber) {
                callback.onResult(articleArrayList, (long) mPageNumber, (long) mPageNumber + 1);
            }

            @Override
            public void onCallbackAfter(@NonNull PageKeyedDataSource.LoadParams<Long> params, @NonNull PageKeyedDataSource.LoadCallback<Long, Article> callback) {
                callback.onResult(articleArrayList, params.key + 1);
            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        }));
    }
}

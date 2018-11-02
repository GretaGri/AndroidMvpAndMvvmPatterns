package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network.NewsRepository;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network.RemoteCallBack;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.view.NewsAdapter;

import java.util.List;

public class MainActivityPresenter  extends BasePresenter<MainActivityContract.MainView> implements MainActivityContract.Presenter{
    MainActivityContract.MainView mainView;
    private final NewsRepository mNewsRepository;
    String searchPhrase;
    RecyclerView mRecyclerView;

    public MainActivityPresenter(@NonNull NewsRepository newsRepository, RecyclerView recyclerView) {
        mNewsRepository = newsRepository;
        mRecyclerView = recyclerView;
        }

    @Override
    public void onButtonClick() {
        getNewsList(searchPhrase);
    }


  private void getNewsList (final String searchPhrase) {
        if (!isViewAttached()) return;
        mNewsRepository.getNewsList(searchPhrase, new RemoteCallBack<List<Article>>() {
            @Override
            public void onSuccess(List<Article> response) {
                if (!isViewAttached()) return;
                List<Article> responseResults = response;
                if (responseResults.isEmpty()) {return;
                }
                else showNewsList(responseResults);
            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }

    @Override
    public void showNewsList(List<Article> news) {
        NewsAdapter mAdapter = new NewsAdapter(news);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }
}

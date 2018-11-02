package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network.NewsRepository;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network.RemoteCallBack;

import java.util.List;

public class MainActivityPresenter extends BasePresenter<MainActivityContract.MainView> implements MainActivityContract.Presenter{
    MainActivityContract.MainView mainView;
    private final NewsRepository mNewsRepository;

    public MainActivityPresenter(@NonNull NewsRepository newsRepository) {
        mNewsRepository = newsRepository;
    }

    @Override
    public void onButtonClick() {
        String searchPhrase = mainView.getSearchPhrase();
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
                else mainView.showNewsList(responseResults);
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
    public void onDestroy() {
        mainView = null;
    }
}

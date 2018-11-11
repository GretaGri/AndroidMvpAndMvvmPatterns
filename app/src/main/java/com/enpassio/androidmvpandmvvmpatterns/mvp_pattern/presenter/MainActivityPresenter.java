package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.NewsResponse;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network.NewsRepository;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network.RemoteCallBack;

import java.util.ArrayList;

public class MainActivityPresenter extends BasePresenter<MainActivityContract.PresenterPushedSomeAction> implements MainActivityContract.MainView {
    private final NewsRepository mNewsRepository;

    public MainActivityPresenter(@NonNull NewsRepository newsRepository) {
        mNewsRepository = newsRepository;
    }

    @Override
    public void onButtonClick(String searchPhrase) {
        getNewsList(searchPhrase);
    }

    @Override
    public void onDestroy() {
        detachView();
    }


    private void getNewsList(final String searchPhrase) {
        Log.d("my_tag", "getNewsList called");
        if (!isViewAttached()) {
            Log.d("my_tag", "getNewsList the view is not attached");
            return;
        }
        mNewsRepository.getNewsList(searchPhrase, new RemoteCallBack<NewsResponse>() {
            @Override
            public void onSuccess(NewsResponse response) {
                if (!isViewAttached()) {
                    Log.d("my_tag", "getNewsList onSuccess the view is not attached");
                    return;
                }
                ArrayList<Article> responseResults = (ArrayList<Article>) response.getArticles();
                if (responseResults.isEmpty()) {
                    Log.d("my_tag", "getNewsList onSuccess the response is empty");
                    return;
                } else {
                    Log.d("my_tag", "getNewsList onSuccess showNewsList called");
                    mView.showNewsList(responseResults);
                    Log.d("my_tag", "inside MainActivityPresenter, response size is: " + responseResults.size());
                }
            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d("my_tag", "inside MainActivityPresenter, onFailed, error message is: " + throwable.getMessage());
            }
        });
    }

}

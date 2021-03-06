package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.ArticlesRepository;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.FavoriteArticle;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragmentViewModel extends AndroidViewModel {

    private ArticlesRepository mRepository;

    public DetailsFragmentViewModel(Application application) {
        super(application);
        mRepository = new ArticlesRepository(application);
    }

    public long insertArticleToFavorite(FavoriteArticle article) {
        return mRepository.insertFavoriteArticle(article);
    }

    public int deleteArticleFromFavorite(FavoriteArticle article) {
        return mRepository.deleteFavoriteArticle(article.getUrl());
    }

    public LiveData<List<FavoriteArticle>> getArticlesListLiveData() {
        return mRepository.getArticlesListLiveData();
    }

    public LiveData<List<FavoriteArticle>> getFavoriteArticlesLiveData() {
        return mRepository.getFavoriteArticlesLiveData();
    }
    public MutableLiveData<ArrayList<FavoriteArticle>> getFavoriteArticleList() {
        return mRepository.getFavoriteArticleList();
    }
}

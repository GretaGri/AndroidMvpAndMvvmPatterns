package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.ArticlesRepository;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.FavoriteArticle;

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

    public boolean checkIfArticleIsFavorite(FavoriteArticle article) {
        return mRepository.checkIfArticleExistInDatabase(article.getUrl()) != -1;
    }
}

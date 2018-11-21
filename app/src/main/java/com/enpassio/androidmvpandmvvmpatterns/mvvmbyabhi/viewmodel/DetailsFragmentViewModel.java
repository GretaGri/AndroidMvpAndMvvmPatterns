package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.ArticlesRepository;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.Article;

public class DetailsFragmentViewModel extends AndroidViewModel {

    private ArticlesRepository mRepository;

    public DetailsFragmentViewModel(Application application) {
        super(application);
        mRepository = new ArticlesRepository(application);
    }

    public void insertArticleToFavorite(Article article) {
        mRepository.insert(article);
    }

    public void deleteArticleFromFavorite(Article article) {
        mRepository.delete(article.getUrl());
    }

    public boolean checkIfArticleIsFavorite(Article article) {
        mRepository.checkIfArticleExistInDatabase(article.getUrl());
        return false;
    }
}

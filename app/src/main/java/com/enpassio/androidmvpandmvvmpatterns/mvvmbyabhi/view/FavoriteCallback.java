package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.view;

import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.FavoriteArticle;

public interface FavoriteCallback {
    void favoriteStatus(boolean isFav, FavoriteArticle favoriteArticle);
}

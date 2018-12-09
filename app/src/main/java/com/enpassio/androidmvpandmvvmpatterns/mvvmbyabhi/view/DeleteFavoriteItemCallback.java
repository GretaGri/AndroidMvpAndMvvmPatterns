package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.view;

import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.FavoriteArticle;

public interface DeleteFavoriteItemCallback {
    void deleteFavoriteItem(FavoriteArticle favoriteArticle);
}

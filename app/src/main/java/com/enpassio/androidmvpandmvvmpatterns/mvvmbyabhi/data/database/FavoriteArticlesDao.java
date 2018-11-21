package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.FavoriteArticle;

import java.util.List;

@Dao
public interface FavoriteArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertArticle(FavoriteArticle article);

    @Query("DELETE FROM favoritearticlestable WHERE url = :urlOfArticle")
    int deleteArticle(String urlOfArticle);

    @Query("SELECT * FROM favoritearticlestable")
    List<FavoriteArticle> getArticleByUrl();
}

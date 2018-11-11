package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.Article;

import java.util.List;

@Dao
public interface ArticlesDao {

    @Query("SELECT * from articlestable")
    LiveData<List<Article>> getAllArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(Article article);

    @Query("DELETE FROM articlestable")
    void deleteAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateArticle(Article article);
}
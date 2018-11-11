package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article;

import java.util.List;

/**
 * Created by Greta Grigutė on 2018-11-09.
 */

@Dao
public interface NewsDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Query("DELETE FROM news_table")
    void deleteAll();

    @Query("SELECT * from news_table ORDER BY title ASC")
    LiveData<List<Article>> getAllNews();

}

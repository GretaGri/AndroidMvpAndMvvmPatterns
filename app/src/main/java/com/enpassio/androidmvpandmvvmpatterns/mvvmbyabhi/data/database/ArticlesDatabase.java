package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.Article;

@Database(entities = {Article.class}, version = 1)
public abstract class ArticlesDatabase extends RoomDatabase {
    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile ArticlesDatabase INSTANCE;

    public static ArticlesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ArticlesDatabase.class) {
                if (INSTANCE == null) {
                    String DATABASE_NAME = "articles_database";
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ArticlesDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ArticlesDao articlesDao();
}

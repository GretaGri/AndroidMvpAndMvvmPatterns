package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article;

/**
 * Created by Greta Grigutė on 2018-11-09.
 */

@Database(entities = {Article.class}, version = 1,  exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {

        // We define the DAOs that work with the database, add an abstract "getter" method for each @Dao.
        public abstract NewsDao newsDao();

        // Making the NewsRoomDatabase a singleton prevents having multiple instances of the DB opened at
        // the same time.
        private static NewsDatabase INSTANCE;

        public static NewsDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (NewsDatabase.class) {
                    if (INSTANCE == null) {

                        // Code that creates a database
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                NewsDatabase.class, "news_database")
                               // .addCallback(sRoomDatabaseCallback)
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
}

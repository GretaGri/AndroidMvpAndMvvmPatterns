package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network;

import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.database.NewsDao;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article;

import java.util.ArrayList;
import java.util.concurrent.Executor;

/**
 * Created by Greta Grigutė on 2018-11-18.
 */
public class LocalCache {
    private NewsDao newsDao;
    private Executor ioExecutor;

    public LocalCache(NewsDao newsDao, Executor ioExecutor) {
        this.newsDao = newsDao;
        this.ioExecutor = ioExecutor;

    }

    /**
     * Insert a list of repos in the database, on a background thread.
     */
    public Boolean insert(final ArrayList<Article> news) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("my_tag", "inserting" + news.size() + "articles");
                for (Article article : news) {
                    newsDao.insert(article);
                }
            }
        });
        return true;
    }
}

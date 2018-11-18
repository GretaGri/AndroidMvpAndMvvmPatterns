package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network;

import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.database.NewsDao;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article;

import java.util.List;
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
    public Boolean insert(final List<Article> news, Boolean insertFinished) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("my_tag", "inserting" + news.size() + "articles");
                int i;
                for (i = 0; i < news.size(); i++) {
                    newsDao.insert(news.get(i));
                }
            }
        });
        return true;
    }
}

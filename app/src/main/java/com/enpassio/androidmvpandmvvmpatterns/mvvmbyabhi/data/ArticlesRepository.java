package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.database.ArticlesDao;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.database.ArticlesDatabase;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.network.APIClient;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.network.NewsApiService;

import java.util.concurrent.Executor;

public class ArticlesRepository {

    private static final String LOG_TAG = ArticlesRepository.class.getSimpleName();
    private NewsApiService mNewsApiService;
    private ArticlesDao mArticlesDao;
    private Executor mExecutor;

    public ArticlesRepository(Application application) {
        mArticlesDao = ArticlesDatabase.getDatabase(application).articlesDao();
        //create the service
        mNewsApiService = APIClient.getClient().create(NewsApiService.class);
        mExecutor = AppExecutors.getInstance().diskIO();
    }

    public LiveData<PagedList<Article>> getLiveDataOfPagedList(String searchQuery) {

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mArticlesDao.deleteAll();

            }
        });
        ArticleBoundaryCallback articleBoundaryCallback = new ArticleBoundaryCallback(mNewsApiService,
                searchQuery, mExecutor, mArticlesDao);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(20 * 3)
                        .setPageSize(20)
                        .setPrefetchDistance(10)
                        .build();
        return (LiveData<PagedList<Article>>) (new LivePagedListBuilder(mArticlesDao.getAllArticlesPage(), pagedListConfig))
                .setFetchExecutor(mExecutor)
                .setBoundaryCallback(articleBoundaryCallback)
                .build();
    }

    public void insert(Article article) {
        mArticlesDao.insertArticle(article);
    }

}

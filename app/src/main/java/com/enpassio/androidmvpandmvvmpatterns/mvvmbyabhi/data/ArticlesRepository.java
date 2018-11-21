package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.database.ArticlesDao;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.database.ArticlesDatabase;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.database.FavoriteArticlesDao;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.FavoriteArticle;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.network.APIClient;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.network.NewsApiService;

import java.util.List;
import java.util.concurrent.Executor;

public class ArticlesRepository {

    private static final String LOG_TAG = ArticlesRepository.class.getSimpleName();
    private NewsApiService mNewsApiService;
    private ArticlesDao mArticlesDao;
    private FavoriteArticlesDao favoriteArticlesDao;
    private Executor mExecutor;
    private Executor favoriteExecutor;
    private Article favoriteArticle;
    private long idAfterInsert = -1;
    private int idAfterDelete = -1;
    private long idAfterQuery = -1;

    public ArticlesRepository(Application application) {
        mArticlesDao = ArticlesDatabase.getDatabase(application).articlesDao();
        favoriteArticlesDao = ArticlesDatabase.getDatabase(application).favoriteArticlesDao();
        //create the service
        mNewsApiService = APIClient.getClient().create(NewsApiService.class);
        mExecutor = AppExecutors.getInstance().diskIO();
        favoriteExecutor = AppExecutors.getInstance().networkIO();
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

    public long checkIfArticleExistInDatabase(String url) {
        favoriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<FavoriteArticle> articleArrayList = favoriteArticlesDao.getArticleByUrl();
                for (FavoriteArticle favoriteArticle : articleArrayList) {
                    if (favoriteArticle.getUrl().equals(url)) {
                        idAfterQuery = 0;
                        break;
                    }
                }
            }
        });
        Log.d("my_taggg", "repository checkIfArticleExistInDatabase called idAfterQuery is: " + idAfterQuery);
        return idAfterQuery;
    }

    public long insertFavoriteArticle(FavoriteArticle article) {
        favoriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                idAfterInsert = favoriteArticlesDao.insertArticle(article);
            }
        });
        Log.d("my_taggg", "repository insertFavoriteArticle called idAfterInsert is: " + idAfterInsert);
        return idAfterInsert;
    }

    public int deleteFavoriteArticle(String url) {
        favoriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                idAfterDelete = favoriteArticlesDao.deleteArticle(url);
            }
        });
        Log.d("my_taggg", "repository deleteFavoriteArticle called idAfterDelete is: " + idAfterDelete);
        return idAfterDelete;
    }
}

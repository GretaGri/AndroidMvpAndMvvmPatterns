package mvvm_pattern.mvvmbyabhi.data;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;

import java.util.concurrent.Executor;

import mvvm_pattern.mvvmbyabhi.data.database.ArticlesDao;
import mvvm_pattern.mvvmbyabhi.data.model.Article;
import mvvm_pattern.mvvmbyabhi.data.model.NewsResponse;
import mvvm_pattern.mvvmbyabhi.data.network.NewsApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleBoundaryCallback extends PagedList.BoundaryCallback<Article> {

    private NewsApiService mNewsApiService;
    private int mPageNumber;
    private String mSearchQuery;
    private Executor mExecutor;
    private ArticlesDao mArticlesDao;

    ArticleBoundaryCallback(NewsApiService newsApiService,
                            String searchQuery, Executor executor, ArticlesDao articlesDao) {
        this.mNewsApiService = newsApiService;
        this.mPageNumber = 1;
        this.mSearchQuery = searchQuery;
        this.mExecutor = executor;
        this.mArticlesDao = articlesDao;
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        mNewsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY, mSearchQuery, mPageNumber).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                Log.v("my_tag", "onZeroItemsLoaded onResponse called");
                if (response.body() != null) {
                    mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            for (Article aricle :
                                    response.body().getArticles()) {
                                if (aricle != null)
                                mArticlesDao.insertArticle(aricle);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable throwable) {

            }
        });
        mPageNumber += 1;
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull Article itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Article itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        mNewsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY, mSearchQuery, mPageNumber).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                Log.v("my_tag", "onItemAtEndLoaded onResponse called");
                if (response.body() != null) {
                    mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            for (Article aricle :
                                    response.body().getArticles()) {
                                if (aricle != null)
                                mArticlesDao.insertArticle(aricle);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable throwable) {

            }
        });
        mPageNumber += 1;
    }
}

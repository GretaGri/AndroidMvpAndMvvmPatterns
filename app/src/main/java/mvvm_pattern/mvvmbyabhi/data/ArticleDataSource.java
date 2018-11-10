package mvvm_pattern.mvvmbyabhi.data;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;

import mvvm_pattern.mvvmbyabhi.data.model.Article;
import mvvm_pattern.mvvmbyabhi.data.model.NewsResponse;
import mvvm_pattern.mvvmbyabhi.data.network.NewsApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleDataSource extends PageKeyedDataSource<Long, Article> {

    private NewsApiService mNewsApiService;
    private int mPageNumber;
    private String mSearchQuery;

    ArticleDataSource(NewsApiService newsApiService,
                      String searchQuery) {
        this.mNewsApiService = newsApiService;
        this.mPageNumber = 1;
        this.mSearchQuery = searchQuery;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull LoadInitialCallback<Long, Article> callback) {
        Log.d("my_tag", "before page number is " + mPageNumber);
        mNewsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY, mSearchQuery, mPageNumber).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                assert response.body() != null;
                Log.v("my_tag", "article title is: " + response.body().getArticles().get(0).getTitle());
                callback.onResult(response.body().getArticles(), (long) mPageNumber, (long) mPageNumber + 1);
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable throwable) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull
            LoadCallback<Long, Article> callback) {

    }

    @Override
    public void loadAfter(@NonNull PageKeyedDataSource.LoadParams<Long> params,
                          @NonNull LoadCallback<Long, Article> callback) {
        Log.d("my_tag", "after page number is " + params.key);
        mNewsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY, mSearchQuery, Integer.parseInt(params.key.toString())).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                assert response.body() != null;
                Log.v("my_tag", "article title is: " + response.body().getArticles().get(0).getTitle());
                callback.onResult(response.body().getArticles(), params.key + 1);
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable throwable) {

            }
        });
    }
}
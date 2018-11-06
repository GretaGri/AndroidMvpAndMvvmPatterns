package mvvm_pattern.mvvmbyabhi.data;

import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import mvvm_pattern.mvvmbyabhi.data.model.Article;
import mvvm_pattern.mvvmbyabhi.data.model.NewsResponse;
import mvvm_pattern.mvvmbyabhi.data.network.APIClient;
import mvvm_pattern.mvvmbyabhi.data.network.NewsApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private static final String LOG_TAG = NewsRepository.class.getSimpleName();
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static NewsRepository sInstance;
    private final NewsApiService mNewsApiService;

    private NewsRepository() {
        //create the service
        mNewsApiService = APIClient.getClient().create(NewsApiService.class);
    }

    public synchronized static NewsRepository getInstance() {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NewsRepository();
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    public List<Article> getNewsForQueriedParameter(String searchQuery) {
        final List<Article> articleLIst = new ArrayList<Article>();
        mNewsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY, searchQuery).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                assert response != null;
                articleLIst.addAll(response.body().getArticles());
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {

            }
        });
        return articleLIst;
    }
}

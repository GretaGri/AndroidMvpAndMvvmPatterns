package mvvm_pattern.mvvmbyabhi.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import mvvm_pattern.mvvmbyabhi.data.database.ArticlesDao;
import mvvm_pattern.mvvmbyabhi.data.database.ArticlesDatabase;
import mvvm_pattern.mvvmbyabhi.data.model.Article;
import mvvm_pattern.mvvmbyabhi.data.model.NewsResponse;
import mvvm_pattern.mvvmbyabhi.data.network.APIClient;
import mvvm_pattern.mvvmbyabhi.data.network.NewsApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesRepository {

    private static final String LOG_TAG = ArticlesRepository.class.getSimpleName();
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private ArticlesRepository sInstance;
    private NewsApiService mNewsApiService;
    private ArticlesDao mArticlesDao;
    private Executor mExecutor;

    public ArticlesRepository(Application application) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ArticlesRepository(application);
            }
        }
        mArticlesDao = ArticlesDatabase.getDatabase(application).articlesDao();
        //create the service
        mNewsApiService = APIClient.getClient().create(NewsApiService.class);
        mExecutor = AppExecutors.getInstance().diskIO();
    }

    public LiveData<List<Article>> getNewsForQueriedParameter(String searchQuery) {
        final List<Article> articleLIst = new ArrayList<Article>();
        mNewsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY, searchQuery).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                assert response.body() != null;
                articleLIst.addAll(response.body().getArticles());
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable throwable) {

            }
        });
        mExecutor.execute(() -> insertArticlesList(articleLIst));
        return mArticlesDao.getAllArticles();
    }

    private void insertArticlesList(List<Article> articleLIst) {
        for (int i = 0; i < articleLIst.size(); i++) {
            mArticlesDao.insertArticle(articleLIst.get(i));
        }
    }

    public void insert(Article article) {
        mArticlesDao.insertArticle(article);
    }
}

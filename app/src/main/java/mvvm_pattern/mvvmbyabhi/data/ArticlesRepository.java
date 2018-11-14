package mvvm_pattern.mvvmbyabhi.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import java.util.concurrent.Executor;

import mvvm_pattern.mvvmbyabhi.data.database.ArticlesDao;
import mvvm_pattern.mvvmbyabhi.data.database.ArticlesDatabase;
import mvvm_pattern.mvvmbyabhi.data.model.Article;
import mvvm_pattern.mvvmbyabhi.data.network.APIClient;
import mvvm_pattern.mvvmbyabhi.data.network.NewsApiService;

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
        LiveData<PagedList<Article>> articleLiveData = (new LivePagedListBuilder(mArticlesDao.getAllArticlesPage(), pagedListConfig))
                .setFetchExecutor(mExecutor)
                .setBoundaryCallback(articleBoundaryCallback)
                .build();
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.v("my_tag", "size of articles is: " + mArticlesDao.getAllArticles().size());

            }
        });

        return articleLiveData;
    }

    public void insert(Article article) {
        mArticlesDao.insertArticle(article);
    }

}

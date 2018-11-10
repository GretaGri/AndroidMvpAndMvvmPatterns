package mvvm_pattern.mvvmbyabhi.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import java.util.List;
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
        mExecutor = AppExecutors.getInstance().networkIO();
    }

    public LiveData<PagedList<Article>> getLiveDataOfPagedList(String searchQuery) {
        ArticleDataFactory feedDataFactory = new ArticleDataFactory(mNewsApiService, searchQuery);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();
        LiveData<PagedList<Article>> articleLiveData = (new LivePagedListBuilder(feedDataFactory, pagedListConfig))
                .setFetchExecutor(mExecutor)
                .build();

        if (articleLiveData != null && articleLiveData.getValue() != null)
            Log.v("my_tag", "loadInitial articles size is: " + articleLiveData.getValue().size());
        return articleLiveData;
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

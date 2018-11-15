package mvvm_pattern.mvvmbyabhi.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import mvvm_pattern.mvvmbyabhi.data.ArticlesRepository;
import mvvm_pattern.mvvmbyabhi.data.model.Article;

public class ArticlesViewModel extends AndroidViewModel {

    private ArticlesRepository mRepository;

    private LiveData<PagedList<Article>> articleLiveData;

    public ArticlesViewModel(Application application) {
        super(application);
        mRepository = new ArticlesRepository(application);
    }

    void insert(Article article) {
        mRepository.insert(article);
    }

    public LiveData<PagedList<Article>> getArticleLiveData(String searchQuery) {
        articleLiveData = mRepository.getLiveDataOfPagedList(searchQuery);
        return articleLiveData;
    }
    public int getSizeOfArticlesInDatabase(){
        return mRepository.getSizeOfArticlesListInDatabase();
    }
}

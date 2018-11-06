package mvvm_pattern.mvvmbyabhi.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import mvvm_pattern.mvvmbyabhi.data.ArticlesRepository;
import mvvm_pattern.mvvmbyabhi.data.database.ArticlesDao;
import mvvm_pattern.mvvmbyabhi.data.database.ArticlesDatabase;
import mvvm_pattern.mvvmbyabhi.data.model.Article;

public class ArticlesViewModel extends AndroidViewModel {

    private ArticlesRepository mRepository;
    private ArticlesDao mArticlesDao;

    public ArticlesViewModel(Application application) {
        super(application);
        mRepository = ArticlesRepository.getInstance();
        mArticlesDao = ArticlesDatabase.getDatabase(application).articlesDao();

    }

    LiveData<List<Article>> getNewsForQueriedParameter(String searchQuery) {
        return mRepository.getNewsForQueriedParameter(searchQuery);
    }

    void insert(Article article) {
        mRepository.insert(article);
    }
}

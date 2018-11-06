package mvvm_pattern.mvvmbyabhi.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import mvvm_pattern.mvvmbyabhi.data.ArticlesRepository;
import mvvm_pattern.mvvmbyabhi.data.model.Article;

public class ArticlesViewModel extends AndroidViewModel {

    private ArticlesRepository mRepository;

    public ArticlesViewModel(Application application) {
        super(application);
        mRepository = new ArticlesRepository(application);
    }

    LiveData<List<Article>> getNewsForQueriedParameter(String searchQuery) {
        return mRepository.getNewsForQueriedParameter(searchQuery);
    }

    void insert(Article article) {
        mRepository.insert(article);
    }
}

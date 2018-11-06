package mvvm_pattern.mvvmbyabhi.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import mvvm_pattern.mvvmbyabhi.data.NewsRepository;
import mvvm_pattern.mvvmbyabhi.data.model.NewsResponse;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository mRepository;
    private NewsResponse mAllWords;

    public NewsViewModel(Application application) {
        super(application);
        mRepository = NewsRepository.getInstance();
        mAllWords = mRepository.getNewsForQueriedParameter("cc");
    }

    NewsResponse getAllWords() {
        return mAllWords;
    }
}

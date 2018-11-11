package mvvm_pattern.mvvmbyabhi.data;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import mvvm_pattern.mvvmbyabhi.data.model.Article;

public class ArticleBoundaryCallback extends PagedList.BoundaryCallback<Article> {

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull Article itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Article itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
    }
}

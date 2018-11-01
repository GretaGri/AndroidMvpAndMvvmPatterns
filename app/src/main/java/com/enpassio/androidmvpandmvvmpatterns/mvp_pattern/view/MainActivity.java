package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.enpassio.androidmvpandmvvmpatterns.R;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network.NewsRepository;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter.mainscreen.ListContract;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter.mainscreen.MainActivityPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListContract.RecyclerView {

    Button button;
    private MainActivityPresenter mainActivityPresenter;
    /* Adapters for inflating different recyclerview */
    NewsAdapter mNewsAdapter;
    /* Set layout managers on those recycler views */
    LinearLayoutManager newslayoutmanager;

    RecyclerView mNewsrecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsrecyclerView = findViewById(R.id.main_recycler_view);
        /*
         * Setup layout manager for items with orientation
         * Also supports `LinearLayoutManager.HORIZONTAL`
         */
        newslayoutmanager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        /* Attach layout manager to the RecyclerView */
        mNewsrecyclerView.setLayoutManager(newslayoutmanager);

        button = findViewById(R.id.main_button);
        mainActivityPresenter = new MainActivityPresenter(NewsRepository.getInstance());
        mainActivityPresenter.attachView(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: show list in recyclerView with search results.
                mainActivityPresenter.onTopicSearchedSearched("obama");
            }
        });
    }

    @Override
    public void showArticles(List<Article> articleList) {

    }

    @Override
    public void showSearchedTopicArticles(List<Article> articleList) {
        Log.d("my_tag", "inside MainActivity, articles received size is: " + articleList.size());
        mNewsrecyclerView.setAdapter(new NewsAdapter(this, articleList));
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showUnauthorizedError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showMessageLayout(boolean show) {

    }
}

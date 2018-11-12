package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.NewsRepository;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.presenter.mainscreen.ListContract;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.presenter.mainscreen.MainActivityPresenter;
import com.enpassio.androidmvpandmvvmpatterns.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListContract.RecyclerView {

    Button button;
    /* Adapters for inflating different recyclerview */
    NewsAdapter mNewsAdapter;
    /* Set layout managers on those recycler views */
    LinearLayoutManager newslayoutmanager;
    RecyclerView mNewsrecyclerView;
    EditText searchQueryEditText;
    private MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mvp_abhi);

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
        mainActivityPresenter.onInitialListRequested("top news");
        mNewsAdapter = new NewsAdapter(this, new ArrayList<Article>());
        mNewsrecyclerView.setAdapter(mNewsAdapter);
        searchQueryEditText = findViewById(R.id.search_query_edit_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //COMPLETED: show list in recyclerView with search results.
                mainActivityPresenter.onTopicSearchedSearched(searchQueryEditText.getText().toString());
            }
        });
    }

    @Override
    public void showArticles(List<Article> articleList) {
        mNewsAdapter.onNewData((ArrayList<Article>) articleList);
    }

    @Override
    public void showSearchedTopicArticles(List<Article> articleList) {
        mNewsAdapter.onNewData((ArrayList<Article>) articleList);

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

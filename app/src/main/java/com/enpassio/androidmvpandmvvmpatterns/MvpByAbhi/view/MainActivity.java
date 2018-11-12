package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class MainActivity extends AppCompatActivity implements ListContract.RecyclerView {

    Button button;
    /* Set layout managers on those recycler views */
    LinearLayoutManager newslayoutmanager;
    RecyclerView mNewsrecyclerView;
    EditText searchQueryEditText;
    private MainActivityPresenter mainActivityPresenter;
    /* Adapters for inflating different recyclerview */
    ArticlePagedListAdapter mArticlePagedListAdapter;

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
        mArticlePagedListAdapter = new ArticlePagedListAdapter(this);
        mNewsrecyclerView.setAdapter(mArticlePagedListAdapter);

        button = findViewById(R.id.main_button);
        mainActivityPresenter = new MainActivityPresenter(NewsRepository.getInstance());
        mainActivityPresenter.attachView(this);
        mainActivityPresenter.onInitialListRequested("India");
        searchQueryEditText = findViewById(R.id.search_query_edit_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityPresenter.onTopicSearchedSearched(searchQueryEditText.getText().toString());
            }
        });
    }

    @Override
    public void getPagedListData(LiveData<PagedList<Article>> listLiveData) {
        listLiveData.observe(this, new Observer<PagedList<Article>>() {
            @Override
            public void onChanged(@Nullable PagedList<Article> articles) {
                mArticlePagedListAdapter.submitList(articles);
            }
        });
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

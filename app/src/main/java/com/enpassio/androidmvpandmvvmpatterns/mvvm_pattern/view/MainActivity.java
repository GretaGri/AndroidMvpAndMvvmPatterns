package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enpassio.androidmvpandmvvmpatterns.R;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.viewModel.NewsViewModel;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    String searchQuery;
    RecyclerView recyclerView;
    private NewsViewModel mNewsViewModel;
    private PagedList <Article> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.main_button);
        editText = findViewById(R.id.search_query_edit_text);
        recyclerView = findViewById(R.id.main_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        final NewsListAdapter adapter = new NewsListAdapter();
        recyclerView.setAdapter(adapter);

        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Toast.makeText(MainActivity.this, "Button clicked",Toast.LENGTH_LONG).show();
                mNewsViewModel.getAllNews(getSearchPhrase()).observe(MainActivity.this, new Observer<PagedList<Article>>() {
                        @Override
                    public void onChanged(@Nullable final PagedList<com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article> news) {
                        // Update the cached copy of the words in the adapter.
                        adapter.submitList(news);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


    public String getSearchPhrase() {
        searchQuery = editText.getText().toString().trim();
        return searchQuery;
    }
}

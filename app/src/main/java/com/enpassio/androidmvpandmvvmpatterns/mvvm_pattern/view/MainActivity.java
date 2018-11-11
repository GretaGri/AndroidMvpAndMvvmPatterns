package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enpassio.androidmvpandmvvmpatterns.R;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.viewModel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    String searchQuery;
    RecyclerView recyclerView;
    private NewsViewModel mNewsViewModel;
    private ArrayList <com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.main_button);
        editText = findViewById(R.id.search_query_edit_text);
        recyclerView = findViewById(R.id.main_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        final NewsListAdapter adapter = new NewsListAdapter(newsList);
        recyclerView.setAdapter(adapter);

        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Toast.makeText(MainActivity.this, "Button clicked",Toast.LENGTH_LONG).show();
                 getSearchPhrase();
                mNewsViewModel.getAllNews().observe(MainActivity.this, new Observer<List<com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article>>() {
                    @Override
                    public void onChanged(@Nullable final List<com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article> news) {
                        // Update the cached copy of the words in the adapter.
                        adapter.setNews(news);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


    public String getSearchPhrase() {
        searchQuery = editText.getText().toString();
        return searchQuery;
    }

    public void showNewsList(ArrayList<com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article> news) {
        Log.d("my_tag", "inside MainActivity, news size is :" + news.size());
        NewsListAdapter adapter = new NewsListAdapter(news);
        recyclerView.setAdapter(adapter);
    }
}

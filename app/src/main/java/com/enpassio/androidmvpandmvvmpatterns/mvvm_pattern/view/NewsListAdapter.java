package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enpassio.androidmvpandmvvmpatterns.R;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {
    private List<Article> mArticleList;

    public NewsListAdapter(List<Article> articleList) {
        this.mArticleList = articleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new NewsListAdapter.MyViewHolder(itemView);
    }

    public void onBindViewHolder(NewsListAdapter.MyViewHolder holder, int position) {
        Article article = mArticleList.get(position);
        holder.title.setText(article.getTitle());
        holder.url.setText(article.getUrl());


    }

    void setNews(List<Article> articles) {
        mArticleList = articles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mArticleList != null) return mArticleList.size();
        else return 0;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView url;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.list_item_title);
            url = view.findViewById(R.id.list_item_url);
        }
    }
}
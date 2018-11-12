package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.view;


import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.R;

public class ArticlePagedListAdapter extends PagedListAdapter<Article, RecyclerView.ViewHolder> {

    ArticlePagedListAdapter(Context context) {
        super(Article.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ArticlePagedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        /* Inflate the custom layout */
        View newsView = inflater.inflate(R.layout.list_item, parent, false);

        /* Return a new holder instance */
        return new ArticlePagedListAdapter.ViewHolder(newsView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((ViewHolder) viewHolder).bindTo(getItem(position));
    }

    /*
 Provide a direct reference to each of the views within a data item
 Used to cache the views within the item layout for fast access
 */
    class ViewHolder extends RecyclerView.ViewHolder {
        /*
        Your holder should contain a member variable
        for any view that will be set as you render a row
        */
        final TextView newsTitleTextView;
        final TextView newsLinkTextView;

        /*
        We also create a constructor that accepts the entire item row
        and does the view lookups to find each subview
        */
        ViewHolder(View itemView) {
            /*
            Stores the itemView in a public final member variable that can be used
            to access the context from any ViewHolder instance.
            */
            super(itemView);
            newsTitleTextView = itemView.findViewById(R.id.list_item_title);
            newsLinkTextView = itemView.findViewById(R.id.list_item_url);
        }

        void bindTo(Article article) {
            if (article != null) {
                newsTitleTextView.setText("" + article.getTitle());
                newsLinkTextView.setText(article.getUrl());
            }
        }
    }
}

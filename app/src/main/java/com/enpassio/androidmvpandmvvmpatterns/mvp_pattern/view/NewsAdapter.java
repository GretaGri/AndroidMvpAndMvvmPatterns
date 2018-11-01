package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enpassio.androidmvpandmvvmpatterns.R;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.Article;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    /* Store a member variable for the Articles */
    private static List<Article> articleArrayList;
    private Context mContext;

    NewsAdapter(Context context, List<Article> articles) {
        articleArrayList = articles;
        mContext = context;
    }

    /* Easy access to the context object in the recyclerview */
    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        /* Inflate the custom layout */
        View newsView = inflater.inflate(R.layout.list_item, parent, false);

        /* Return a new holder instance */
        return new ViewHolder(newsView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder viewHolder, int position) {
        /* Get the data model based on position */
        Article currentArticle = articleArrayList.get(position);
        /*
        Set item views based on your views and data model
         */
        viewHolder.newsTitleTextView.setText(currentArticle.getTitle());
        viewHolder.newsLinkTextView.setText(currentArticle.getUrl());

    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
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
    }
}

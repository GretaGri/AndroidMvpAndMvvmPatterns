package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enpassio.androidmvpandmvvmpatterns.R;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.FavoriteArticle;

import java.util.ArrayList;

public class FavoriteArticlesAdapter extends RecyclerView.Adapter<FavoriteArticlesAdapter.ViewHolder> {

    private ArrayList<FavoriteArticle> mFavoriteArticle;
    /* Store the context for easy access */
    private Context mContext;

    FavoriteArticlesAdapter(Context context,
                            ArrayList<FavoriteArticle> article) {
        mFavoriteArticle = article;
        mContext = context;
    }

    /* Easy access to the context object in the recyclerview */
    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public FavoriteArticlesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        /* Inflate the custom layout */
        View articleView = inflater.inflate(R.layout.item_activity_favorite, parent, false);
        /* Return a new holder instance */
        return new FavoriteArticlesAdapter.ViewHolder(articleView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteArticlesAdapter.ViewHolder viewHolder, int position) {
        bindTo(mFavoriteArticle.get(position), viewHolder);
    }

    private void bindTo(FavoriteArticle mArticle, FavoriteArticlesAdapter.ViewHolder viewHolder) {

        if (mArticle != null) {
            viewHolder.articleSourceTextView.setText(mArticle.getUrl());
        }
    }

    @Override
    public int getItemCount() {
        return mFavoriteArticle.size();
    }

    void setArticleData(ArrayList<FavoriteArticle> article) {
        mFavoriteArticle = article;
        notifyDataSetChanged();
    }

    /*
     Provide a direct reference to each of the views within a data item
     Used to cache the views within the item layout for fast access
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        /*
        Your holder should contain a member variable
        for any view that will be set as you render a row
        */
        final TextView articleSourceTextView;

        ViewHolder(View view) {
            /*
            Stores the itemView in a public final member variable that can be used
            to access the context from any ViewHolder instance.
            */
            super(view);
            articleSourceTextView = view.findViewById(R.id.url_favorite_article_source);
        }

    }
}

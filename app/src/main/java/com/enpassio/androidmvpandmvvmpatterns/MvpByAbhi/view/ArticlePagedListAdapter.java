package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.view;


import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.enpassio.androidmvpandmvvmpatterns.GlideApp;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.R;
import com.facebook.shimmer.ShimmerFrameLayout;

public class ArticlePagedListAdapter extends PagedListAdapter<Article, RecyclerView.ViewHolder> {

    ArticlePagedListAdapter(Context context) {
        super(Article.DIFF_CALLBACK);
    }

    private Context mContext;
    @NonNull
    @Override
    public ArticlePagedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        /* Inflate the custom layout */
        View newsView = inflater.inflate(R.layout.list_item_mvp_abhi, parent, false);

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
        final TextView newsAuthorTextView;
        final TextView newsPublishingDateTextView;
        final ImageView newsPosterImageView;
        final ShimmerFrameLayout container;


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
            newsAuthorTextView = itemView.findViewById(R.id.list_item_author);
            newsPublishingDateTextView = itemView.findViewById(R.id.list_item_published_date);
            newsPosterImageView = itemView.findViewById(R.id.list_item_image);
            container = itemView.findViewById(R.id.shimmer_view_container);
        }

        void bindTo(Article article) {
            if (article != null) {
                newsTitleTextView.setText("" + article.getTitle());
                newsTitleTextView.setSelected(true);
                newsAuthorTextView.setText(""+article.getAuthor());
                String date = article.getPublishedAt();
                String[] dateArray= date.split("T");

                newsPublishingDateTextView.setText(""+dateArray[0]+" "+dateArray[1].subSequence(0, dateArray[1].length()-1));
                newsAuthorTextView.setText(article.getUrl());
                newsAuthorTextView.setSelected(true);
                container.startShimmer();
                GlideApp
                        .with(mContext)
                        .load(article.getUrlToImage())
                        .centerCrop()
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                Log.d("my_tag", "image loaded");
                                container.stopShimmer();
                                return false;
                            }
                        })
                        .into(newsPosterImageView);

            }
        }
    }
}

package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
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
import com.enpassio.androidmvpandmvvmpatterns.R;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.FavoriteArticle;
import com.facebook.shimmer.ShimmerFrameLayout;

import me.biubiubiu.justifytext.library.JustifyTextView;
import saschpe.android.customtabs.CustomTabsHelper;
import saschpe.android.customtabs.WebViewFallback;

public class FavoriteFragmentAdapter extends RecyclerView.Adapter<FavoriteFragmentAdapter.ViewHolder> {

    private Article mArticle;
    /* Store the context for easy access */
    private Context mContext;
    private boolean mIsFav;
    private FavoriteCallback mFavoriteCallback;

    FavoriteFragmentAdapter(Context context,
                            Article article,
                            boolean isFav,
                            FavoriteCallback favoriteCallback) {
        mArticle = article;
        mContext = context;
        mIsFav = isFav;
        mFavoriteCallback = favoriteCallback;
    }

    /* Easy access to the context object in the recyclerview */
    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public FavoriteFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        /* Inflate the custom layout */
        View articleView = inflater.inflate(R.layout.item_fragment_details_mvvm_abhi, parent, false);
        /* Return a new holder instance */
        return new ViewHolder(articleView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteFragmentAdapter.ViewHolder viewHolder, int position) {
        bindTo(mArticle, viewHolder);
    }

    private void bindTo(Article mArticle, ViewHolder viewHolder) {
        viewHolder.shimmerFrameLayout.startShimmer();
        viewHolder.articleTitleTextView.setText(mArticle.getTitle());
        viewHolder.articleTitleTextView.setSelected(true);
        viewHolder.articleContentJustifiedTextView.setText(mArticle.getContent());

        int favoriteFilledId = mContext.getResources().getIdentifier("com.enpassio.androidmvpandmvvmpatterns:drawable/"
                + "ic_favorite_filled", null, null);
        int favoriteUnFilledId = mContext.getResources().getIdentifier("com.enpassio.androidmvpandmvvmpatterns:drawable/"
                + "ic_favorite_unfilled", null, null);
        if (mIsFav) {
            viewHolder.favoriteButton.setImageResource(favoriteFilledId);
        } else {
            viewHolder.favoriteButton.setImageResource(favoriteUnFilledId);
        }
        FavoriteArticle favoriteArticle = new FavoriteArticle(mArticle.getUrl());
        viewHolder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsFav) {
                    //set callback with not-favorite
                    mFavoriteCallback.favoriteStatus(false, favoriteArticle);
                    viewHolder.favoriteButton.setImageResource(favoriteUnFilledId);
                    mIsFav = false;
                } else {
                    //set callback with favorite
                    mFavoriteCallback.favoriteStatus(true, favoriteArticle);
                    viewHolder.favoriteButton.setImageResource(favoriteFilledId);
                    mIsFav = true;
                }
            }
        });

        Bitmap closeIcon = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.ic_close);

        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .setToolbarColor(viewHolder.articleTitleTextView.getContext().getResources().getColor(R.color.colorPrimary))
                .setShowTitle(true)
                .setCloseButtonIcon(closeIcon)
                .build();

        // This is optional but recommended
        CustomTabsHelper.addKeepAliveExtra(viewHolder.articleTitleTextView.getContext(), customTabsIntent.intent);

        if (mArticle.getAuthor() != null) {
            viewHolder.articleAuthorTextView.setText(String.format("Author: %s", mArticle.getAuthor()));
        }
        if (mArticle.getUrl() != null) {
            viewHolder.articleSourceTextView.setText(String.format(mArticle.getUrl()));
            viewHolder.articleSourceTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomTabsHelper.openCustomTab(viewHolder.articleTitleTextView.getContext(), customTabsIntent,
                            Uri.parse(mArticle.getUrl()),
                            new WebViewFallback());
                }
            });
        }

        GlideApp
                .with(viewHolder.articleSourceTextView.getContext())
                .load(mArticle.getUrlToImage())
                .centerCrop()
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model,
                                                Target<Drawable> target,
                                                boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource,
                                                   Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource,
                                                   boolean isFirstResource) {
                        viewHolder.shimmerFrameLayout.stopShimmer();
                        return false;
                    }
                })
                .into(viewHolder.articleBannerImageView);

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    void setArticleData(Article article, boolean isFav) {
        mArticle = article;
        mIsFav = isFav;
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
        final TextView articleTitleTextView;
        final JustifyTextView articleContentJustifiedTextView;
        final TextView articleAuthorTextView;
        final TextView articleSourceTextView;
        final ImageView articleBannerImageView;
        final ShimmerFrameLayout shimmerFrameLayout;
        final FloatingActionButton favoriteButton;

        ViewHolder(View view) {
            /*
            Stores the itemView in a public final member variable that can be used
            to access the context from any ViewHolder instance.
            */
            super(view);
            articleTitleTextView = view.findViewById(R.id.article_title_text_view);
            articleContentJustifiedTextView = view.findViewById(R.id.article_content_text_view);
            articleAuthorTextView = view.findViewById(R.id.article_author_text_view);
            articleSourceTextView = view.findViewById(R.id.article_source_text_view);
            articleBannerImageView = view.findViewById(R.id.article_banner_image);
            shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
            favoriteButton = view.findViewById(R.id.favorite_button);
        }

    }
}

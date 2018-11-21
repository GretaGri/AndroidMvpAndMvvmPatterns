package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.enpassio.androidmvpandmvvmpatterns.R;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.FavoriteArticle;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.viewmodel.DetailsFragmentViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import me.biubiubiu.justifytext.library.JustifyTextView;
import saschpe.android.customtabs.CustomTabsHelper;
import saschpe.android.customtabs.WebViewFallback;

public class DetailsFragmentMvvmAbhi extends Fragment {

    private DetailsFragmentViewModel detailsFragmentViewModel;

    public DetailsFragmentMvvmAbhi() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("my_tag", "onCreateView called");
        Bundle bundle = getArguments();
        Article article = bundle.getParcelable("key");

        //set the view for the fragment
        View view = inflater.inflate(R.layout.fragment_details_mvvm_abhi, container, false);

        detailsFragmentViewModel = ViewModelProviders.of(this).get(DetailsFragmentViewModel.class);

        TextView articleTitleTextView = view.findViewById(R.id.article_title_text_view);
        JustifyTextView articleContentJustifiedTextView = view.findViewById(R.id.article_content_text_view);
        TextView articleAuthorTextView = view.findViewById(R.id.article_author_text_view);
        TextView articleSourceTextView = view.findViewById(R.id.article_source_text_view);
        ImageView articleBannerImageView = view.findViewById(R.id.article_banner_image);
        ShimmerFrameLayout shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        FloatingActionButton favoriteButton = view.findViewById(R.id.favorite_button);
        shimmerFrameLayout.startShimmer();
        articleTitleTextView.setText(article.getTitle());
        articleTitleTextView.setSelected(true);
        articleContentJustifiedTextView.setText(article.getContent());

        int favoriteFilledId = getResources().getIdentifier("com.enpassio.androidmvpandmvvmpatterns:drawable/" + "ic_favorite_filled", null, null);
        int favoriteUnFilledId = getResources().getIdentifier("com.enpassio.androidmvpandmvvmpatterns:drawable/" + "ic_favorite_unfilled", null, null);

        FavoriteArticle favoriteArticle = new FavoriteArticle(article.getUrl());

        detailsFragmentViewModel.getArticlesListLiveData()
                .observe(this,
                        new Observer<List<FavoriteArticle>>() {
                            @Override
                            public void onChanged(@Nullable List<FavoriteArticle> favoriteArticles) {
                                for (FavoriteArticle favorite : favoriteArticles) {
                                    if (article.getUrl().equals(favorite.getUrl())) {
                                        favoriteButton.setImageResource(favoriteFilledId);
                                        break;
                                    } else {
                                        favoriteButton.setImageResource(favoriteUnFilledId);
                                    }
                                }
                            }
                        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favoriteButton.getTag() != null && favoriteButton.getTag().equals("fav")) {
                    deleteFromFavorite(favoriteArticle);
                    favoriteButton.setTag("unfav");
                } else {
                    favoriteButton.setTag("fav");
                    saveToFavorite(favoriteArticle);
                }
            }
        });
        Bitmap closeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_close);

        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .setToolbarColor(articleTitleTextView.getContext().getResources().getColor(R.color.colorPrimary))
                .setShowTitle(true)
                .setCloseButtonIcon(closeIcon)
                .build();

        // This is optional but recommended
        CustomTabsHelper.addKeepAliveExtra(articleTitleTextView.getContext(), customTabsIntent.intent);

        if (article.getAuthor() != null) {
            articleAuthorTextView.setText(String.format("Author: %s", article.getAuthor()));
        }
        if (article.getUrl() != null) {
            articleSourceTextView.setText(String.format(article.getUrl()));
            articleSourceTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomTabsHelper.openCustomTab(articleTitleTextView.getContext(), customTabsIntent,
                            Uri.parse(article.getUrl()),
                            new WebViewFallback());
                }
            });
        }
        GlideApp
                .with(articleSourceTextView.getContext())
                .load(article.getUrlToImage())
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
                        shimmerFrameLayout.stopShimmer();
                        return false;
                    }
                })
                .into(articleBannerImageView);
        return view;
    }

    private void saveToFavorite(FavoriteArticle article) {
        long id = detailsFragmentViewModel.insertArticleToFavorite(article);
        Log.d("my_taggg", "saveToFavorite called id is: " + id);
    }

    private void deleteFromFavorite(FavoriteArticle article) {
        int id = detailsFragmentViewModel.deleteArticleFromFavorite(article);
        Log.d("my_taggg", "deleteFromFavorite called id is: " + id);
    }
}
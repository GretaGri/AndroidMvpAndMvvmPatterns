package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
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
import com.facebook.shimmer.ShimmerFrameLayout;

import me.biubiubiu.justifytext.library.JustifyTextView;
import saschpe.android.customtabs.CustomTabsHelper;
import saschpe.android.customtabs.WebViewFallback;

public class DetailsFragmentMvvmAbhi extends Fragment {

    public DetailsFragmentMvvmAbhi() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        Article article = bundle.getParcelable("key");
        View view = inflater.inflate(R.layout.fragment_details_mvvm_abhi, container, false);
        TextView articleTitleTextView = view.findViewById(R.id.article_title_text_view);
        JustifyTextView articleContentJustifiedTextView = view.findViewById(R.id.article_content_text_view);
        TextView articleAuthorTextView = view.findViewById(R.id.article_author_text_view);
        TextView articleSourceTextView = view.findViewById(R.id.article_source_text_view);
        ImageView articleBannerImageView = view.findViewById(R.id.article_banner_image);
        ShimmerFrameLayout shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();
        articleTitleTextView.setText(article.getTitle());
        articleTitleTextView.setSelected(true);
        articleContentJustifiedTextView.setText(article.getContent());

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
}
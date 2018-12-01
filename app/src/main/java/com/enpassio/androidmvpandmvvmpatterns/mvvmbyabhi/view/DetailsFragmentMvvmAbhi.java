package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

import java.util.ArrayList;
import java.util.List;

public class DetailsFragmentMvvmAbhi extends Fragment implements FavoriteCallback {

    private Article mArticle;
    private DetailsFragmentViewModel detailsFragmentViewModel;
    private boolean mIsFav;
    private FavoriteCallback favoriteCallback;
    private FavoriteArticle mFavoriteArticle;
    private RecyclerView recyclerView;
    private FavoriteFragmentAdapter favoriteFragmentAdapter;
    private LinearLayoutManager mLayoutmanager;

    public DetailsFragmentMvvmAbhi() {
        // Required empty public constructor
    }

    static DetailsFragmentMvvmAbhi newInstance(Article article) {
        DetailsFragmentMvvmAbhi detailsFragmentMvvmAbhi = new DetailsFragmentMvvmAbhi();
        Bundle args = new Bundle();
        args.putParcelable("article", article);
        detailsFragmentMvvmAbhi.setArguments(args);
        return detailsFragmentMvvmAbhi;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArticle = getArguments() != null ? getArguments().getParcelable("article") : null;
        detailsFragmentViewModel = ViewModelProviders.of(this).get(DetailsFragmentViewModel.class);
        favoriteCallback = this;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("my_tag", "onCreateView called");
        //set the view for the fragment
        View view = inflater.inflate(R.layout.fragment_details_mvvm_abhi, container, false);

        ImageView articleBannerImageView = view.findViewById(R.id.article_banner_image);
        ShimmerFrameLayout shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();

        GlideApp
                .with(articleBannerImageView.getContext())
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
                        shimmerFrameLayout.stopShimmer();
                        return false;
                    }
                })
                .into(articleBannerImageView);


        ArrayList<FavoriteArticle> favoriteArticles = detailsFragmentViewModel.getFavoriteArticleList().getValue();
        if (favoriteArticles != null) {
            for (FavoriteArticle favoriteArticle : favoriteArticles) {
                if (favoriteArticle.getUrl().equals(mArticle.getUrl())) {
                    mIsFav = true;
                    break;
                }
            }
        }
        recyclerView = view.findViewById(R.id.recycler_view_fragment_details);
        mLayoutmanager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutmanager);
        favoriteFragmentAdapter = new FavoriteFragmentAdapter(view.getContext(), mArticle, mIsFav, favoriteCallback);
        recyclerView.setAdapter(favoriteFragmentAdapter);
        detailsFragmentViewModel.getArticlesListLiveData()
                .observe(this,
                        new Observer<List<FavoriteArticle>>() {
                            @Override
                            public void onChanged(@Nullable List<FavoriteArticle> favoriteArticles) {
                                for (FavoriteArticle favorite : favoriteArticles) {
                                    if (mArticle != null) {
                                        if (mArticle.getUrl().equals(favorite.getUrl())) {
                                            //is favorite
                                            favoriteFragmentAdapter.setArticleData(mArticle, true);
                                            return;
                                        } else {
                                            //is not favorite
                                            favoriteFragmentAdapter.setArticleData(mArticle, false);
                                        }
                                    }
                                }
                            }
                        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFavoriteArticle != null) {
            if (mIsFav) {
                saveToFavorite(mFavoriteArticle);
            } else {
                deleteFromFavorite(mFavoriteArticle);
            }
        }
    }

    @Override
    public void favoriteStatus(boolean isFav, FavoriteArticle favoriteArticle) {
        mIsFav = isFav;
        mFavoriteArticle = favoriteArticle;

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
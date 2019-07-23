package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.enpassio.androidmvpandmvvmpatterns.R;
import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.FavoriteArticle;

import java.util.ArrayList;

import saschpe.android.customtabs.CustomTabsHelper;
import saschpe.android.customtabs.WebViewFallback;

public class FavoriteArticlesAdapter extends RecyclerView.Adapter<FavoriteArticlesAdapter.ViewHolder> {

    private ArrayList<FavoriteArticle> mFavoriteArticle;
    /* Store the context for easy access */
    private Context mContext;
    private DeleteFavoriteItemCallback mDeleteFavoriteItemCallback;

    FavoriteArticlesAdapter(Context context,
                            ArrayList<FavoriteArticle> article, DeleteFavoriteItemCallback deleteFavoriteItemCallback) {
        mFavoriteArticle = article;
        mContext = context;
        mDeleteFavoriteItemCallback = deleteFavoriteItemCallback;
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
            viewHolder.articleTitlerTextView.setText(mArticle.getTitle());
            viewHolder.articleDescriptionTextView.setText(mArticle.getDescription());
            String date = mArticle.getPublishedAt();
            String[] dateArray = date.split("T");
            viewHolder.articlePublishingDateTextView.setText("" + dateArray[0] + " " + dateArray[1].subSequence(0, dateArray[1].length() - 1));
            viewHolder.articleTitlerTextView.setSelected(true);
            viewHolder.articleAuthorTextView.setText(mArticle.getAuthor());
            viewHolder.articleUrlTextView.setText(mArticle.getUrl());
            int favoriteFilledId = mContext.getResources().getIdentifier("com.enpassio.androidmvpandmvvmpatterns:drawable/"
                    + "ic_favorite_filled", null, null);
            viewHolder.favButton.setImageResource(favoriteFilledId);

            viewHolder.favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int favoriteUnFilledId = mContext.getResources().getIdentifier("com.enpassio.androidmvpandmvvmpatterns:drawable/"
                            + "ic_favorite_unfilled", null, null);
                    viewHolder.favButton.setImageResource(favoriteUnFilledId);
                    if (mArticle.getUrl() != null) {

                        mDeleteFavoriteItemCallback.deleteFavoriteItem(mArticle);
                    }
                }
            });
            setupChromeTabForDetailsActivity(viewHolder, mArticle);
        }
    }

    private void setupChromeTabForDetailsActivity(ViewHolder viewHolder, FavoriteArticle mArticle) {
        Bitmap closeIcon = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.ic_close);

        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .setToolbarColor(viewHolder.articlesContainerCardView.getContext().getResources().getColor(R.color.colorPrimary))
                .setShowTitle(true)
                .setCloseButtonIcon(closeIcon)
                .build();
        if (mArticle.getUrl() != null) {
            viewHolder.articlesContainerCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomTabsHelper.openCustomTab(viewHolder.articlesContainerCardView.getContext(), customTabsIntent,
                            Uri.parse(mArticle.getUrl()),
                            new WebViewFallback());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mFavoriteArticle.size();
    }

    void onNewData(ArrayList<FavoriteArticle> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffUtilCallback(newData, mFavoriteArticle));
        this.mFavoriteArticle.clear();
        this.mFavoriteArticle.addAll(newData);
        diffResult.dispatchUpdatesTo(this);
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
        final TextView articleTitlerTextView;
        final TextView articleDescriptionTextView;
        final TextView articlePublishingDateTextView;
        final TextView articleAuthorTextView;
        final TextView articleUrlTextView;
        final ImageButton favButton;
        final CardView articlesContainerCardView;

        ViewHolder(View view) {
            /*
            Stores the itemView in a public final member variable that can be used
            to access the context from any ViewHolder instance.
            */
            super(view);
            articleTitlerTextView = view.findViewById(R.id.list_item_title);
            articleDescriptionTextView = view.findViewById(R.id.list_item_description);
            articlePublishingDateTextView = view.findViewById(R.id.list_item_published_date);
            articleAuthorTextView = view.findViewById(R.id.list_item_author);
            articleUrlTextView = view.findViewById(R.id.url_favorite_article_source);
            favButton = view.findViewById(R.id.button_favorite);
            articlesContainerCardView = view.findViewById(R.id.articles_container_card_view);

        }

    }
}

package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.view;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enpassio.androidmvpandmvvmpatterns.R;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article;

public class NewsListAdapter extends PagedListAdapter<Article, NewsListAdapter.MyViewHolder> {

    NewsListAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new NewsListAdapter.MyViewHolder(itemView);
    }

    public void onBindViewHolder(NewsListAdapter.MyViewHolder holder, int position) {
        Article article = getItem(position);
        holder.title.setText(article.getTitle());
        holder.url.setText(article.getUrl());
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

    public static DiffUtil.ItemCallback<Article> DIFF_CALLBACK = new DiffUtil.ItemCallback<Article>() {
        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getUrl().equals(newItem.getUrl());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
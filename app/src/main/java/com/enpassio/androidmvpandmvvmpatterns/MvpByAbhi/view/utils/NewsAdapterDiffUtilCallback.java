package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.view.utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.Article;

import java.util.ArrayList;

public class NewsAdapterDiffUtilCallback extends DiffUtil.Callback {
    private ArrayList<Article> newList;
    private ArrayList<Article> oldList;

    public NewsAdapterDiffUtilCallback(ArrayList<Article> newList, ArrayList<Article> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = newList.get(newItemPosition).compareTo(oldList.get(oldItemPosition));
        return result == 0;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.FavoriteArticle;

import java.util.ArrayList;

public class MyDiffUtilCallback extends DiffUtil.Callback {

    ArrayList<FavoriteArticle> newList;
    ArrayList<FavoriteArticle> oldList;

    MyDiffUtilCallback(ArrayList<FavoriteArticle> newList, ArrayList<FavoriteArticle> oldList) {
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
        FavoriteArticle newArticle = newList.get(newItemPosition);
        FavoriteArticle oldArticle = oldList.get(oldItemPosition);

        Bundle diff = new Bundle();
        if (!newArticle.getUrl().equals(oldArticle.getUrl())) {
            diff.putString("url", newArticle.getUrl());
        }
        if (diff.size() == 0) {
            return null;
        }
        return diff;
    }
}
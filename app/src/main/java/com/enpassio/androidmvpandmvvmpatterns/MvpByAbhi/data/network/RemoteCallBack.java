package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network;

/**
 * Created by Greta Grigutė on 2018-11-01.
 */
public abstract class RemoteCallBack {

    public abstract void onFailure(String error);

    public abstract void onSuccess();

    public abstract void onEmpty();
}

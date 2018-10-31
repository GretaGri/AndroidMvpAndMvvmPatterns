package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter.base;

public interface RemoteView {

    void showProgress();

    void hideProgress();

    void showUnauthorizedError();

    void showEmpty();

    void showError(String errorMessage);

    void showMessageLayout(boolean show);
}

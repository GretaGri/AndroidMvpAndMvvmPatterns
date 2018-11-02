package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.presenter;

abstract class BasePresenter <View> {

    private View mView;

    /**
     * Check if the view is attached.
     * This checking is only necessary when returning from an asynchronous call
     */
   Boolean isViewAttached(){
        return mView != null;
   }

   View attachView(View view) {
    return mView = view;
    }

  public void detachView() {
        mView = null;
    }
}

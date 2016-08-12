package com.jraska.dialog;

import android.support.v4.app.FragmentActivity;

public final class LambdaDialogs {
  @SuppressWarnings("unchecked")
  public static <A extends FragmentActivity> LambdaDialog.Builder<A> builder(A activity) {
    if (activity == null) {
      throw new IllegalArgumentException("fragmentActivity cannot be null");
    }

    return new LambdaDialog.Builder(activity);
  }

  @SuppressWarnings("unchecked")
  public static <A extends FragmentActivity> DelegateDialog.BuilderNoDelegate<A> delegate(A activity) {
    if (activity == null) {
      throw new IllegalArgumentException("activity is null");
    }

    return new DelegateDialog.BuilderNoDelegate(activity);
  }

  public static <A extends FragmentActivity>
  DelegateDialog.Builder delegate(A activity, DialogDelegate<A> delegate) {

    if (activity == null) {
      throw new IllegalArgumentException("fragmentActivity cannot be null");
    }

    if (delegate == null) {
      throw new IllegalArgumentException("delegate cannot be null");
    }

    return new DelegateDialog.Builder(activity, delegate);
  }
}

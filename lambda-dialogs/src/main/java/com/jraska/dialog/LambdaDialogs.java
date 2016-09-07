package com.jraska.dialog;

import android.support.v4.app.FragmentActivity;

public final class LambdaDialogs {
  public static <A extends FragmentActivity> FieldsDialog.Builder<A> builder(A activity) {
    if (activity == null) {
      throw new IllegalArgumentException("fragmentActivity cannot be null");
    }

    return new FieldsDialog.Builder<>(activity);
  }

  public static <A extends FragmentActivity> DelegateDialog.BuilderNoDelegate<A> delegate(A activity) {
    if (activity == null) {
      throw new IllegalArgumentException("activity is null");
    }

    return new DelegateDialog.BuilderNoDelegate<>(activity);
  }
}

package com.jraska.dialog;

import android.support.v4.app.FragmentActivity;

public final class LambdaDialogs {
  private LambdaDialogs() {
  }

  public static <A extends FragmentActivity> FieldsDialog.Builder<A> builder(A activity) {
    Preconditions.argumentNotNull(activity, "activity");

    return new FieldsDialog.Builder<>(activity);
  }

  public static <A extends FragmentActivity> DelegateDialog.BuilderNoMethod<A> delegate(A activity) {
    Preconditions.argumentNotNull(activity, "activity");

    return new DelegateDialog.BuilderNoMethod<>(activity);
  }
}

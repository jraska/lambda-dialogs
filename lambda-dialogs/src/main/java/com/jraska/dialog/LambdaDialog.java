package com.jraska.dialog;

import android.support.v4.app.FragmentActivity;

public final class LambdaDialog {
  @SuppressWarnings("unchecked")
  public static <A extends FragmentActivity> LambdaDialogFragment.Builder<A> builder(A fragmentActivity) {
    if (fragmentActivity == null) {
      throw new IllegalArgumentException("fragmentActivity cannot be null");
    }

    return new LambdaDialogFragment.Builder(fragmentActivity);
  }
}

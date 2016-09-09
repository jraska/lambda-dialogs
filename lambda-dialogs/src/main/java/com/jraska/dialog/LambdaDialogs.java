package com.jraska.dialog;

import android.support.v4.app.FragmentActivity;

public final class LambdaDialogs {
  private LambdaDialogs() {
  }

  private static boolean validateEagerlyDefault;

  public static <A extends FragmentActivity> FieldsDialog.Builder<A> builder(A activity) {
    Preconditions.argumentNotNull(activity, "activity");

    return new FieldsDialog.Builder<>(activity)
        .validateEagerly(validateEagerlyDefault);
  }

  public static <A extends FragmentActivity> DelegateDialog.BuilderNoMethod<A> delegate(A activity) {
    Preconditions.argumentNotNull(activity, "activity");

    return new DelegateDialog.BuilderNoMethod<A>(activity)
        .validateEagerly(validateEagerlyDefault);
  }

  public static void validateEagerly(boolean validateEagerly) {
    validateEagerlyDefault = validateEagerly;
  }
}

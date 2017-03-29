package com.jraska.dialog.sample;

import android.support.v4.app.FragmentActivity;
import com.jraska.dialog.ActivityMethod;

import java.io.Serializable;

public final class DialogFields implements Serializable {
  public final CharSequence title;
  public final CharSequence message;
  public final int iconRes;
  public final ActivityMethod positiveAction;
  public final CharSequence positiveText;
  public final ActivityMethod neutralAction;
  public final CharSequence neutralText;
  public final ActivityMethod negativeAction;
  public final CharSequence negativeText;

  private DialogFields(Builder builder) {
    this.title = builder.title;
    this.message = builder.message;
    this.iconRes = builder.iconRes;
    this.positiveAction = builder.positiveAction;
    this.positiveText = builder.positiveText;
    this.neutralAction = builder.neutralAction;
    this.neutralText = builder.neutralText;
    this.negativeAction = builder.negativeAction;
    this.negativeText = builder.negativeText;
  }

  @SuppressWarnings("UnusedParameters") // for generics
  public static <A extends FragmentActivity> Builder<A> builder(A activity) {
    return new Builder<>();
  }

  static class Builder<A extends FragmentActivity> {
    private CharSequence title;
    private CharSequence message;
    private int iconRes;
    private ActivityMethod positiveAction;
    private CharSequence positiveText;
    private ActivityMethod neutralAction;
    private CharSequence neutralText;
    private ActivityMethod negativeAction;
    private CharSequence negativeText;

    private Builder() {
    }

    public Builder<A> title(CharSequence title) {
      this.title = title;
      return this;
    }

    public Builder<A> message(CharSequence message) {
      this.message = message;
      return this;
    }

    public Builder<A> iconRes(int iconRes) {
      this.iconRes = iconRes;
      return this;
    }

    public Builder<A> positiveMethod(ActivityMethod<A> positiveAction) {
      this.positiveAction = positiveAction;
      return this;
    }

    public Builder<A> positiveText(CharSequence positiveText) {
      this.positiveText = positiveText;
      return this;
    }

    public Builder<A> neutralMethod(ActivityMethod<A> neutralAction) {
      this.neutralAction = neutralAction;
      return this;
    }

    public Builder<A> neutralText(CharSequence neutralText) {
      this.neutralText = neutralText;
      return this;
    }

    public Builder<A> negativeMethod(ActivityMethod<A> negativeAction) {
      this.negativeAction = negativeAction;
      return this;
    }

    public Builder<A> negativeText(CharSequence negativeText) {
      this.negativeText = negativeText;
      return this;
    }

    public DialogFields build() {
      return new DialogFields(this);
    }
  }
}
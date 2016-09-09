package com.jraska.dialog;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public final class FieldsDialog {
  public static class Builder<A extends FragmentActivity> {
    private final FragmentActivity fragmentActivity;
    private final DialogFields.Builder fieldsBuilder;

    private boolean validateEagerly;
    private ActivityDialogMethodParam<A, DialogFields> dialogFactory = new AlertDialogFactory<>();
    private final SerializableValidator validator = new SerializableValidator();

    Builder(A fragmentActivity) {
      this.fragmentActivity = fragmentActivity;
      fieldsBuilder = DialogFields.builder();
    }

    private CharSequence string(@StringRes int res) {
      return fragmentActivity.getString(res);
    }

    private FragmentManager fragmentManager() {
      return fragmentActivity.getSupportFragmentManager();
    }

    public Builder<A> validateEagerly(boolean validate) {
      validateEagerly = validate;
      return this;
    }

    public Builder<A> dialogFactory(@NonNull ActivityDialogMethodParam<A, DialogFields> factory) {
      Preconditions.argumentNotNull(factory, "factory");

      this.dialogFactory = factory;
      return this;
    }

    public Builder<A> icon(@DrawableRes int res) {
      fieldsBuilder.iconRes(res);
      return this;
    }

    public Builder<A> title(CharSequence message) {
      fieldsBuilder.title(message);
      return this;
    }

    public Builder<A> title(@StringRes int res) {
      return title(string(res));
    }

    public Builder<A> cancelable(boolean cancelable) {
      fieldsBuilder.cancelable(cancelable);
      return this;
    }

    public Builder<A> message(CharSequence message) {
      fieldsBuilder.message(message);
      return this;
    }

    public Builder<A> message(@StringRes int res) {
      return message(string(res));
    }

    public Builder<A> positiveMethod(ActivityMethod<A> method) {
      fieldsBuilder.positiveAction(method);
      return this;
    }

    public Builder<A> positiveText(CharSequence text) {
      fieldsBuilder.positiveText(text);
      return this;
    }

    public Builder<A> positiveText(@StringRes int res) {
      return positiveText(string(res));
    }

    public Builder<A> neutralMethod(ActivityMethod<A> method) {
      fieldsBuilder.neutralAction(method);
      return this;
    }

    public Builder<A> neutralText(CharSequence text) {
      fieldsBuilder.neutralText(text);
      return this;
    }

    public Builder<A> neutralText(@StringRes int res) {
      return neutralText(string(res));
    }

    public Builder<A> negativeMethod(ActivityMethod<A> method) {
      fieldsBuilder.negativeAction(method);
      return this;
    }

    public Builder<A> negativeText(@StringRes int res) {
      return negativeText(string(res));
    }

    public Builder<A> negativeText(CharSequence text) {
      fieldsBuilder.negativeText(text);
      return this;
    }

    public DelegateDialog.Builder<DialogFields> build() {
      DialogFields dialogFields = fieldsBuilder.build();

      if (validateEagerly) {
        validator.validateSerializable(dialogFactory);
        validator.validateSerializable(dialogFields.positiveAction);
        validator.validateSerializable(dialogFields.neutralAction);
        validator.validateSerializable(dialogFields.negativeAction);
      }

      return new DelegateDialog.Builder<>(fragmentActivity, dialogFactory,
          new DialogFieldsBundleAdapter(), dialogFields)
          .validateEagerly(validateEagerly);
    }

    public void show() {
      build().show();
    }

    public void show(String tag) {
      Preconditions.argumentNotNull(tag, "tag");

      build().show(tag);
    }
  }

}

package com.jraska.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public final class FieldsDialog extends DialogFragment {
  public static final String TAG = FieldsDialog.class.getSimpleName();

  private static final String DIALOG_FACTORY = "factory";
  private static final String DISMISS_ACTION = "dismissAction";
  private static final String CANCEL_ACTION = "cancelAction";

  private final DialogFieldsBundleAdapter fieldsAdapter = new DialogFieldsBundleAdapter();

  private DialogFields fields() {
    return fieldsAdapter.get(getArguments());
  }

  @SuppressWarnings("unchecked")
  private <T> ActivityDialogMethodParam<FragmentActivity, T> factory() {
    return (ActivityDialogMethodParam) getArguments().getSerializable(DIALOG_FACTORY);
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DelegateDialog.validateDialogInstance(this);
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    return factory().onCreateDialog(getActivity(), fields());
  }

  @Override
  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
    callAction(CANCEL_ACTION);
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    callAction(DISMISS_ACTION);
  }

  @SuppressWarnings("unchecked")
  private void callAction(String key) {
    ActivityMethod action = (ActivityMethod) getArguments().getSerializable(key);
    FragmentActivity activity = getActivity();
    if (action != null && activity != null) {
      action.call(activity);
    }
  }

  public void show(FragmentManager fragmentManager) {
    show(fragmentManager, TAG);
  }

  public static class Builder<A extends FragmentActivity> {
    private final FragmentActivity fragmentActivity;
    private final DialogFields.Builder fieldsBuilder;
    private final DialogFieldsBundleAdapter fieldsBundleAdapter = new DialogFieldsBundleAdapter();

    private boolean validateEagerly;
    private ActivityDialogMethodParam<A, DialogFields> dialogFactory = new AlertDialogFactory<>();
    private final SerializableValidator validator = new SerializableValidator();
    private boolean cancelable = true;
    private ActivityMethod cancelAction;
    private ActivityMethod dismissAction;

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
      this.cancelable = cancelable;
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

    public Builder<A> cancelMethod(ActivityMethod<A> method) {
      this.cancelAction = method;
      return this;
    }

    public Builder<A> dismissMethod(ActivityMethod<A> method) {
      this.dismissAction = method;
      return this;
    }

    public FieldsDialog build() {
      DialogFields dialogFields = fieldsBuilder.build();

      if (validateEagerly) {
        validator.validateSerializable(dialogFactory);
        validator.validateSerializable(dialogFields.positiveAction);
        validator.validateSerializable(dialogFields.neutralAction);
        validator.validateSerializable(dialogFields.negativeAction);
      }

      Bundle arguments = new Bundle();
      arguments.putSerializable(DIALOG_FACTORY, dialogFactory);
      arguments.putSerializable(CANCEL_ACTION, cancelAction);
      arguments.putSerializable(DISMISS_ACTION, dismissAction);
      fieldsBundleAdapter.putTo(arguments, dialogFields);

      FieldsDialog dialog = new FieldsDialog();
      dialog.setArguments(arguments);
      dialog.setCancelable(cancelable);
      DelegateDialog.makeValidDialog(dialog);
      return dialog;
    }

    public void show() {
      build().show(fragmentManager());
    }

    public void show(String tag) {
      Preconditions.argumentNotNull(tag, "tag");

      build().show(fragmentManager(), tag);
    }
  }

}

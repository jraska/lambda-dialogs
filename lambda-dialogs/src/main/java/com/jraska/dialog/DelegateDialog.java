package com.jraska.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public final class DelegateDialog extends DialogFragment {
  static final String TAG = DelegateDialog.class.getSimpleName();

  private static final String DELEGATE = "delegate";

  private DialogDelegate delegate() {
    return (DialogDelegate) getArguments().getSerializable(DELEGATE);
  }

  @NonNull
  @Override @SuppressWarnings("unchecked")
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    return delegate().onCreateDialog(getActivity());
  }

  public void show(FragmentManager fragmentManager) {
    show(fragmentManager, TAG);
  }

  public static class BuilderNoDelegate<A extends FragmentActivity> {
    private final FragmentActivity fragmentActivity;

    BuilderNoDelegate(FragmentActivity fragmentActivity) {
      this.fragmentActivity = fragmentActivity;
    }

    public Builder method(DialogDelegate<A> delegate) {
      if (delegate == null) {
        throw new IllegalArgumentException("delegate cannot be null");
      }

      return new Builder(fragmentActivity, delegate);
    }
  }

  public static class Builder {
    private final FragmentActivity fragmentActivity;
    private final DialogDelegate delegate;

    private final SerializableValidator validator = new SerializableValidator();
    private boolean validateEagerly;

    Builder(FragmentActivity fragmentActivity, DialogDelegate delegate) {
      this.fragmentActivity = fragmentActivity;
      this.delegate = delegate;
    }

    public Builder validateEagerly(boolean validateEagerly) {
      this.validateEagerly = validateEagerly;
      return this;
    }

    public DelegateDialog build() {
      if (validateEagerly) {
        validate();
      }

      Bundle arguments = new Bundle();
      arguments.putSerializable(DELEGATE, delegate);

      DelegateDialog fragment = new DelegateDialog();
      fragment.setArguments(arguments);
      return fragment;
    }

    private void validate() {
      validator.validateSerializable(delegate);
    }

    private FragmentManager fragmentManager() {
      return fragmentActivity.getSupportFragmentManager();
    }

    public void show() {
      build().show(fragmentManager());
    }

    public void show(String tag) {
      build().show(fragmentManager(), tag);
    }
  }
}

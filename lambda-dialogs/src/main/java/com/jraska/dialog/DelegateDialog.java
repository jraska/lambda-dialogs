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
  private static final String PARAMETER_PROVIDER = "parameterProvider";

  private DialogDelegateParametrized delegate() {
    return (DialogDelegateParametrized) getArguments().getSerializable(DELEGATE);
  }

  private ParameterProvider parameterProvider() {
    return (ParameterProvider) getArguments().getSerializable(PARAMETER_PROVIDER);
  }

  @NonNull
  @Override @SuppressWarnings("unchecked")
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Object parameter = parameterProvider().get(getArguments());
    return delegate().onCreateDialog(getActivity(), parameter);
  }

  public void show(FragmentManager fragmentManager) {
    show(fragmentManager, TAG);
  }

  public static class BuilderNoDelegate<A extends FragmentActivity> {
    private final FragmentActivity fragmentActivity;

    BuilderNoDelegate(FragmentActivity fragmentActivity) {
      this.fragmentActivity = fragmentActivity;
    }

    public Builder method(DialogDelegate<A> method) {
      if (method == null) {
        throw new IllegalArgumentException("method cannot be null");
      }

      return new Builder<>(fragmentActivity, wrapDelegate(method), Empty.get(), null);
    }

    public BuilderWithStringDelegate<A> parameter(String value) {
      if (value == null) {
        throw new IllegalArgumentException("method cannot be null");
      }

      return new BuilderWithStringDelegate<>(fragmentActivity, value);
    }
  }

  public static class BuilderWithStringDelegate<A extends FragmentActivity> {
    private final FragmentActivity fragmentActivity;
    private final String value;

    BuilderWithStringDelegate(FragmentActivity fragmentActivity, String value) {
      this.fragmentActivity = fragmentActivity;
      this.value = value;
    }

    public Builder method(DialogDelegateParametrized<A, String> delegate) {
      if (delegate == null) {
        throw new IllegalArgumentException("delegate cannot be null");
      }

      return new Builder<>(fragmentActivity, delegate, new StringProvider(), value);
    }
  }

  public static class Builder<P> {
    private final FragmentActivity fragmentActivity;
    private final DialogDelegateParametrized delegate;
    private final ParameterProvider<P> parameterProvider;
    private final P parameter;

    private final SerializableValidator validator = new SerializableValidator();
    private boolean validateEagerly;

    Builder(FragmentActivity fragmentActivity, DialogDelegateParametrized delegate,
            ParameterProvider<P> parameterProvider, P parameter) {
      this.fragmentActivity = fragmentActivity;
      this.delegate = delegate;
      this.parameterProvider = parameterProvider;
      this.parameter = parameter;
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
      arguments.putSerializable(PARAMETER_PROVIDER, parameterProvider);
      parameterProvider.putTo(arguments, parameter);

      DelegateDialog fragment = new DelegateDialog();
      fragment.setArguments(arguments);
      return fragment;
    }

    private void validate() {
      validator.validateSerializable(delegate);
      validator.validateSerializable(parameterProvider);
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

  static <A extends FragmentActivity, P> DialogDelegateParametrized<A, P> wrapDelegate(
      DialogDelegate<A> delegate) {
    return (activity, parameter) ->
        delegate.onCreateDialog((A) activity);
  }
}

package com.jraska.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.io.Serializable;

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

    public <P extends Parcelable> BuilderWithParameterDelegate<A, P> parameter(P value) {
      if (value == null) {
        throw new IllegalArgumentException("method cannot be null");
      }

      return new BuilderWithParameterDelegate<>(fragmentActivity, ParcelableProvider.get(), value);
    }

    public <P extends Serializable> BuilderWithParameterDelegate<A, P> parameter(P value) {
      if (value == null) {
        throw new IllegalArgumentException("method cannot be null");
      }

      return new BuilderWithParameterDelegate<>(fragmentActivity, SerializableProvider.get(), value);
    }
  }

  public static class BuilderWithParameterDelegate<A extends FragmentActivity, P> {
    private final FragmentActivity fragmentActivity;
    private final ParameterProvider<P> provider;
    private final P value;

    BuilderWithParameterDelegate(FragmentActivity fragmentActivity,
                                 ParameterProvider<P> provider, P value) {
      this.fragmentActivity = fragmentActivity;
      this.provider = provider;
      this.value = value;
    }

    public Builder method(DialogDelegateParametrized<A, P> delegate) {
      if (delegate == null) {
        throw new IllegalArgumentException("delegate cannot be null");
      }

      return new Builder<>(fragmentActivity, delegate, provider, value);
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

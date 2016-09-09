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

  private static final String DELEGATE = "method";
  private static final String PARAMETER_PROVIDER = "parameterProvider";

  private ActivityDialogMethodParam delegate() {
    return (ActivityDialogMethodParam) getArguments().getSerializable(DELEGATE);
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

  public static class BuilderNoMethod<A extends FragmentActivity> {
    private final FragmentActivity activity;
    private boolean validateEagerly;

    BuilderNoMethod(FragmentActivity activity) {
      this.activity = activity;
    }

    public BuilderNoMethod<A> validateEagerly(boolean validateEagerly) {
      this.validateEagerly = validateEagerly;
      return this;
    }

    public Builder method(ActivityDialogMethod<A> method) {
      Preconditions.argumentNotNull(method, "method");

      return new Builder<>(activity, wrapMethod(method), Empty.get(), null)
          .validateEagerly(validateEagerly);
    }

    public <P extends Parcelable> BuilderWithParameter<A, P> parameter(P value) {
      Preconditions.argumentNotNull(value, "value");

      return new BuilderWithParameter<A, P>(activity, ParcelableProvider.get(), value)
          .validateEagerly(validateEagerly);
    }

    public <P extends Serializable> BuilderWithParameter<A, P> parameter(P value) {
      Preconditions.argumentNotNull(value, "value");

      return new BuilderWithParameter<A, P>(activity, SerializableProvider.get(), value)
          .validateEagerly(validateEagerly);
    }
  }

  public static class BuilderWithParameter<A extends FragmentActivity, P> {
    private final FragmentActivity activity;
    private final ParameterProvider<P> provider;
    private final P value;
    private boolean validateEagerly;

    BuilderWithParameter(FragmentActivity activity,
                         ParameterProvider<P> provider, P value) {
      this.activity = activity;
      this.provider = provider;
      this.value = value;
    }

    public BuilderWithParameter<A, P> validateEagerly(boolean validateEagerly) {
      this.validateEagerly = validateEagerly;
      return this;
    }

    public Builder method(ActivityDialogMethodParam<A, P> method) {
      Preconditions.argumentNotNull(method, "method");

      return new Builder<>(activity, method, provider, value)
          .validateEagerly(validateEagerly);
    }
  }

  public static class Builder<P> {
    private final FragmentActivity activity;
    private final ActivityDialogMethodParam method;
    private final ParameterProvider<P> parameterProvider;
    private final P parameter;

    private final SerializableValidator validator = new SerializableValidator();
    private boolean validateEagerly;

    Builder(FragmentActivity activity, ActivityDialogMethodParam method,
            ParameterProvider<P> parameterProvider, P parameter) {
      this.activity = activity;
      this.method = method;
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
      arguments.putSerializable(DELEGATE, method);
      arguments.putSerializable(PARAMETER_PROVIDER, parameterProvider);
      parameterProvider.putTo(arguments, parameter);

      DelegateDialog fragment = new DelegateDialog();
      fragment.setArguments(arguments);
      return fragment;
    }

    private void validate() {
      validator.validateSerializable(method);
      validator.validateSerializable(parameterProvider);
    }

    private FragmentManager fragmentManager() {
      return activity.getSupportFragmentManager();
    }

    public void show() {
      build().show(fragmentManager());
    }

    public void show(String tag) {
      build().show(fragmentManager(), tag);
    }
  }

  static <A extends FragmentActivity, P> ActivityDialogMethodParam<A, P> wrapMethod(
      ActivityDialogMethod<A> method) {
    return (activity, parameter) ->
        method.onCreateDialog((A) activity);
  }
}

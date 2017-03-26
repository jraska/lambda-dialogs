package com.jraska.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.io.Serializable;

public final class DelegateDialog extends DialogFragment {
  static final String TAG = DelegateDialog.class.getSimpleName();

  private static final String BUILDER_VALIDATION = "createdByBuilder";
  private static final String DELEGATE = "method";
  private static final String PARAMETER_PROVIDER = "parameterProvider";
  private static final String DISMISS_ACTION = "dismissAction";
  private static final String CANCEL_ACTION = "cancelAction";

  private ActivityDialogMethodParam delegate() {
    return (ActivityDialogMethodParam) getArguments().getSerializable(DELEGATE);
  }

  private ParameterProvider parameterProvider() {
    return (ParameterProvider) getArguments().getSerializable(PARAMETER_PROVIDER);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() == null || getArguments().get(BUILDER_VALIDATION) == null) {
      throw new IllegalStateException("You are creating " + DelegateDialog.class.getSimpleName()
          + " with empty constructor or you modify the arguments you naughty developer. " +
          "Please use LambdaDialogs.delegate() or LambdaDialogs.builder() methods.");
    }
  }

  @NonNull
  @Override @SuppressWarnings("unchecked")
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Object parameter = parameterProvider().get(getArguments());
    return delegate().onCreateDialog(getActivity(), parameter);
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

  static abstract class BaseBuilder<A extends FragmentActivity, T extends BaseBuilder> {
    protected final FragmentActivity activity;
    protected boolean validateEagerly;
    protected boolean cancelable = true;
    protected ActivityMethod cancelAction;
    protected ActivityMethod dismissAction;

    BaseBuilder(FragmentActivity activity) {
      this.activity = activity;
    }

    BaseBuilder(BaseBuilder previous) {
      this(previous.activity);
      validateEagerly = previous.validateEagerly;
      cancelable = previous.cancelable;
      cancelAction = previous.cancelAction;
      dismissAction = previous.dismissAction;
    }

    protected final FragmentManager fragmentManager() {
      return activity.getSupportFragmentManager();
    }

    @SuppressWarnings("unchecked")
    public final T validateEagerly(boolean validateEagerly) {
      this.validateEagerly = validateEagerly;
      return (T) this;
    }

    @SuppressWarnings("unchecked")
    public final T cancelMethod(ActivityMethod<A> method) {
      this.cancelAction = method;
      return (T) this;
    }

    @SuppressWarnings("unchecked")
    public final T dismissMethod(ActivityMethod<A> method) {
      this.dismissAction = method;
      return (T) this;
    }

    @SuppressWarnings("unchecked")
    public final T cancelable(boolean cancelable) {
      this.cancelable = cancelable;
      return (T) this;
    }
  }

  public final static class BuilderNoMethod<A extends FragmentActivity>
      extends BaseBuilder<A, BuilderNoMethod<A>> {
    BuilderNoMethod(FragmentActivity activity) {
      super(activity);
    }

    public Builder<A, ?> method(ActivityDialogMethod<A> method) {
      Preconditions.argumentNotNull(method, "method");

      return new Builder<A, Object>(this, wrapMethod(method), Empty.get(), null)
          .validateEagerly(validateEagerly);
    }

    public <P extends Parcelable> BuilderWithParameter<A, P> parameter(P value) {
      Preconditions.argumentNotNull(value, "value");

      return new BuilderWithParameter<>(this, ParcelableProvider.get(), value);
    }

    public <P extends Serializable> BuilderWithParameter<A, P> parameter(P value) {
      Preconditions.argumentNotNull(value, "value");

      return new BuilderWithParameter<>(this, SerializableProvider.get(), value);
    }
  }

  public static class BuilderWithParameter<A extends FragmentActivity, P>
      extends BaseBuilder<A, BuilderWithParameter<A, P>> {

    private final ParameterProvider<P> provider;
    private final P value;

    BuilderWithParameter(BaseBuilder builder,
                         ParameterProvider<P> provider, P value) {
      super(builder);
      this.provider = provider;
      this.value = value;
    }

    public Builder<A, P> method(ActivityDialogMethodParam<A, P> method) {
      Preconditions.argumentNotNull(method, "method");

      return new Builder<A, P>(this, method, provider, value)
          .validateEagerly(validateEagerly);
    }

  }

  public static class Builder<A extends FragmentActivity, P> extends BaseBuilder<A, Builder<A, P>> {
    private final ActivityDialogMethodParam method;
    private final ParameterProvider<P> parameterProvider;
    private final P parameter;

    private final SerializableValidator validator = new SerializableValidator();

    Builder(BaseBuilder builder, ActivityDialogMethodParam method,
            ParameterProvider<P> parameterProvider, P parameter) {
      super(builder);
      this.method = method;
      this.parameterProvider = parameterProvider;
      this.parameter = parameter;
    }

    public DelegateDialog build() {
      if (validateEagerly) {
        validate();
      }

      Bundle arguments = new Bundle();
      arguments.putSerializable(DELEGATE, method);
      arguments.putSerializable(PARAMETER_PROVIDER, parameterProvider);
      arguments.putSerializable(CANCEL_ACTION, cancelAction);
      arguments.putSerializable(DISMISS_ACTION, dismissAction);
      arguments.putString(BUILDER_VALIDATION, ""); //just put anything to be not null
      parameterProvider.putTo(arguments, parameter);

      DelegateDialog dialog = new DelegateDialog();
      dialog.setArguments(arguments);
      dialog.setCancelable(cancelable);
      return dialog;
    }

    private void validate() {
      validator.validateSerializable(method);
      validator.validateSerializable(parameterProvider);
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

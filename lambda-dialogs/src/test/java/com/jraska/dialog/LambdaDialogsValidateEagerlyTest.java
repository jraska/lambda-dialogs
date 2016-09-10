package com.jraska.dialog;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.NotSerializableException;

import static org.mockito.Mockito.mock;

public class LambdaDialogsValidateEagerlyTest {

  NotSerializable notSerializable;

  @Before
  public void setUp() throws Exception {
    notSerializable = new NotSerializable();
  }

  @After
  public void tearDown() throws Exception {
    LambdaDialogs.validateEagerly(false); // put it back to default state after each test
  }

  @Test(expected = NotSerializableException.class)
  public void givenStaticConfig_whenBuildPositiveMethod_thenCrashesOnSerialization() {
    LambdaDialogs.validateEagerly(true);

    LambdaDialogs.builder(mock(FragmentActivity.class))
        .positiveMethod(notSerializable)
        .build();
  }

  @Test(expected = NotSerializableException.class)
  public void givenStaticConfig_whenBuildNeutralMethod_thenCrashesOnSerialization() {
    LambdaDialogs.validateEagerly(true);

    LambdaDialogs.builder(mock(FragmentActivity.class))
        .neutralMethod(notSerializable)
        .build();
  }

  @Test(expected = NotSerializableException.class)
  public void givenStaticConfig_whenBuildNegativeMethod_thenCrashesOnSerialization() {
    LambdaDialogs.validateEagerly(true);

    LambdaDialogs.builder(mock(FragmentActivity.class))
        .negativeMethod(notSerializable)
        .build();
  }

  @Test
  public void givenStaticConfig_whenBuilderEagerValueOverwritten_thenPasses() {
    LambdaDialogs.validateEagerly(true);

    LambdaDialogs.builder(mock(FragmentActivity.class))
        .negativeMethod(notSerializable)
        .validateEagerly(false)
        .build();
  }

  @Test(expected = NotSerializableException.class)
  public void givenStaticConfig_whenBuildDialogFactory_thenCrashesOnSerialization() {
    LambdaDialogs.validateEagerly(true);

    LambdaDialogs.builder(mock(FragmentActivity.class))
        .dialogFactory(notSerializable)
        .build();
  }

  @Test(expected = NotSerializableException.class)
  public void givenStaticConfig_whenDelegateMethod_thenCrashesOnSerialization() {
    LambdaDialogs.validateEagerly(true);

    LambdaDialogs.delegate(mock(FragmentActivity.class))
        .method(notSerializable)
        .build();
  }

  @Test(expected = NotSerializableException.class)
  public void givenStaticConfig_whenDelegateParametrizedMethod_thenCrashesOnSerialization() {
    LambdaDialogs.validateEagerly(true);

    LambdaDialogs.delegate(mock(FragmentActivity.class))
        .parameter("parameter")
        .method(new NotSerializableStringParam())
        .build();
  }

  @Test
  public void givenStaticConfig_whenDelegateEagerValueOverwritten_thenPasses() {
    LambdaDialogs.validateEagerly(true);

    LambdaDialogs.delegate(mock(FragmentActivity.class))
        .parameter("parameter")
        .validateEagerly(false)
        .method(new NotSerializableStringParam())
        .build();
  }

  static class NotSerializable implements ActivityMethod<FragmentActivity>,
      ActivityDialogMethodParam<FragmentActivity, DialogFields>,
      ActivityDialogMethod<FragmentActivity> {
    @SuppressWarnings("unused")
    private final Object notSerializable = new Object();

    @Override public void call(FragmentActivity activity) {
      throw new UnsupportedOperationException("Not implemented!");
    }

    @Override public Dialog onCreateDialog(FragmentActivity activity, DialogFields parameter) {
      throw new UnsupportedOperationException("Not implemented!");
    }

    @Override public Dialog onCreateDialog(FragmentActivity activity) {
      throw new UnsupportedOperationException("Not implemented!");
    }
  }

  static class NotSerializableStringParam implements
      ActivityDialogMethodParam<FragmentActivity, String> {
    @SuppressWarnings("unused")
    private final Object notSerializable = new Object();

    @Override public Dialog onCreateDialog(FragmentActivity activity, String parameter) {
      throw new UnsupportedOperationException("Not implemented!");
    }
  }

  interface DialogStringParameter extends ActivityDialogMethodParam<FragmentActivity, String> {
  }
}
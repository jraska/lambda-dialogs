package com.jraska.dialog;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

  @Test(expected = IllegalArgumentException.class)
  public void givenStaticConfig_whenDelegateMethod_thenCrashesOnSerialization() {
    LambdaDialogs.validateEagerly(true);

    LambdaDialogs.delegate(mock(FragmentActivity.class))
        .method(notSerializable)
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
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
      ActivityDialogMethod<FragmentActivity> {
    @SuppressWarnings("unused")
    private final Object notSerializable = new Object();

    @Override public void call(FragmentActivity activity) {
      throw new UnsupportedOperationException("Not implemented!");
    }

    @Override public Dialog onCreateDialog(FragmentActivity activity) {
      throw new UnsupportedOperationException("Not implemented!");
    }
  }

  static class NotSerializableStringParam
      implements ActivityDialogMethodParam<FragmentActivity, String> {
    @SuppressWarnings("unused")
    private final Object notSerializable = new Object();

    @Override public Dialog onCreateDialog(FragmentActivity activity, String parameter) {
      throw new UnsupportedOperationException("Not implemented!");
    }
  }
}
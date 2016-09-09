package com.jraska.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class LambdaDialogsDelegateTest {
  TestActivity activity;

  @Mock ActivityDialogMethodParam<TestActivity, Object> verificationFactory;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    activity = Robolectric.setupActivity(TestActivity.class);
    activity.verificationFactory = verificationFactory;
    when(verificationFactory.onCreateDialog(any(), any()))
        .thenReturn(new Dialog(RuntimeEnvironment.application));
  }

  @Test
  public void whenShown_thenMethodCalled() {
    LambdaDialogs.delegate(activity)
        .method(TestActivity::createDialog)
        .show();

    verify(verificationFactory).onCreateDialog(any(), eq(null));
  }

  @Test
  public void whenShown_thenStringParameterProvided() {
    LambdaDialogs.delegate(activity)
        .parameter("text")
        .method(TestActivity::createDialog)
        .show();

    verify(verificationFactory).onCreateDialog(any(), eq("text"));
  }

  @Test
  public void whenShown_thenIntentParameterProvided() {
    Intent intent = new Intent(activity, TestActivity.class);
    LambdaDialogs.delegate(activity)
        .parameter(intent)
        .method(TestActivity::createDialog)
        .show();

    verify(verificationFactory).onCreateDialog(any(), eq(intent));
  }

  static class TestActivity extends FragmentActivity {
    ActivityDialogMethodParam<TestActivity, Object> verificationFactory;

    Dialog createDialog(Intent parcelableIntent) {
      return verificationFactory.onCreateDialog(this, parcelableIntent);
    }

    Dialog createDialog(String serializableString) {
      return verificationFactory.onCreateDialog(this, serializableString);
    }

    Dialog createDialog() {
      return verificationFactory.onCreateDialog(this, null);
    }
  }
}
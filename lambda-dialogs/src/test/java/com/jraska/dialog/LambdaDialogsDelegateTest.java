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
import org.robolectric.shadows.ShadowDialog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class LambdaDialogsDelegateTest {
  TestActivity activity;

  @Mock ActivityDialogMethodParam<TestActivity, Object> mockActivityWithParameterMethod;
  @Mock ActivityMethod<TestActivity> mockActivityMethod;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    activity = Robolectric.setupActivity(TestActivity.class);
    activity.verificationFactory = mockActivityWithParameterMethod;
    when(mockActivityWithParameterMethod.onCreateDialog(any(), any()))
        .thenReturn(new Dialog(RuntimeEnvironment.application));

    LambdaDialogs.validateEagerly(true);
  }

  @Test
  public void whenShown_thenMethodCalled() {
    LambdaDialogs.delegate(activity)
        .method(TestActivity::createDialog)
        .show();

    verify(mockActivityWithParameterMethod).onCreateDialog(any(), eq(null));
  }

  @Test
  public void whenShown_thenStringParameterProvided() {
    LambdaDialogs.delegate(activity)
        .parameter("text")
        .method(TestActivity::createDialog)
        .show();

    verify(mockActivityWithParameterMethod).onCreateDialog(any(), eq("text"));
  }

  @Test
  public void whenShown_thenIntentParameterProvided() {
    Intent intent = new Intent(activity, TestActivity.class);
    LambdaDialogs.delegate(activity)
        .parameter(intent)
        .method(TestActivity::createDialog)
        .show();

    verify(mockActivityWithParameterMethod).onCreateDialog(any(), eq(intent));
  }

  @Test
  public void whenCanceled_thenCancelMethodCalled() {
    DelegateDialog dialog = LambdaDialogs.delegate(activity)
        .method(TestActivity::createDialog)
        .cancelMethod(mockActivityMethod)
        .cancelable(true)
        .build();

    assertThat(dialog.isCancelable()).isTrue();

    dialog.show(activity.getSupportFragmentManager(), "tag");
    ShadowDialog.getLatestDialog().cancel();

    verify(mockActivityMethod).call(eq(activity));
  }

  @Test
  public void whenDismissed_thenDismissMethodCalled() {
    LambdaDialogs.delegate(activity)
        .method(TestActivity::createDialog)
        .dismissMethod(mockActivityMethod)
        .show("Tag");

    ShadowDialog.getLatestDialog().dismiss();

    verify(mockActivityMethod).call(eq(activity));
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
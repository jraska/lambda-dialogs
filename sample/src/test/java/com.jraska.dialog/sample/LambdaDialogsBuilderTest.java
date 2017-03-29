package com.jraska.dialog.sample;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import com.jraska.dialog.LambdaDialogs;
import org.assertj.android.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class LambdaDialogsBuilderTest {
  FragmentActivity activity;

  @Before
  public void setUp() {
    activity = Robolectric.setupActivity(FragmentActivity.class);
  }

  @Test
  public void whenShown_thenButtonsTextsCorrect() {
    String positive = "126kas";
    String neutral = "12asi";
    String negative = "98io8";

    DialogFields fields = DialogFields.builder(activity)
        .positiveText(positive)
        .neutralText(neutral)
        .negativeText(negative)
        .build();

    LambdaDialogs.delegate(activity)
        .parameter(fields)
        .method(new AlertDialogFactory<>())
        .show();

    AlertDialog dialog = (AlertDialog) ShadowDialog.getLatestDialog();
    Assertions.assertThat(dialog.getButton(AlertDialog.BUTTON_POSITIVE)).hasText(positive);
    Assertions.assertThat(dialog.getButton(AlertDialog.BUTTON_NEUTRAL)).hasText(neutral);
    Assertions.assertThat(dialog.getButton(AlertDialog.BUTTON_NEGATIVE)).hasText(negative);
  }
}
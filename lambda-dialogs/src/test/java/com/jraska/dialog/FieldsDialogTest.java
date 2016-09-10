package com.jraska.dialog;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class FieldsDialogTest {
  FragmentActivity testActivity;

  @Before
  public void setUp() {
    testActivity = Robolectric.setupActivity(FragmentActivity.class);
  }

  @Test(expected = IllegalStateException.class)
  public void givenFragmentCreatedWithNew_whenShown_thenIllegalStateException() {
    FieldsDialog fieldsDialog = new FieldsDialog();

    fieldsDialog.show(testActivity.getSupportFragmentManager());
  }

  @Test(expected = IllegalStateException.class)
  public void givenFragmentCreatedWithNewArguments_whenShown_thenIllegalStateException() {
    FieldsDialog fieldsDialog = new FieldsDialog();
    fieldsDialog.setArguments(new Bundle());

    fieldsDialog.show(testActivity.getSupportFragmentManager());
  }
}
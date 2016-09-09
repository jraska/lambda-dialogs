package com.jraska.dialog;

import android.os.Bundle;

final class DialogFieldsBundleAdapter implements ParameterProvider<DialogFields> {
  private static final String TITLE = "title";
  private static final String MESSAGE = "message";
  private static final String ICON_RES = "icon";
  private static final String POSITIVE_PROVIDER = "positiveAction";
  private static final String POSITIVE_TEXT = "positiveText";
  private static final String NEUTRAL_PROVIDER = "neutralMethod";
  private static final String NEUTRAL_TEXT = "neutralText";
  private static final String NEGATIVE_PROVIDER = "negativeMethod";
  private static final String NEGATIVE_TEXT = "negativeText";
  private static final String CANCELABLE = "cancelable";

  @Override
  public void putTo(Bundle bundle, DialogFields fields) {
    bundle.putCharSequence(TITLE, fields.title);
    bundle.putCharSequence(MESSAGE, fields.message);
    bundle.putInt(ICON_RES, fields.iconRes);
    bundle.putSerializable(POSITIVE_PROVIDER, fields.positiveAction);
    bundle.putCharSequence(POSITIVE_TEXT, fields.positiveText);
    bundle.putSerializable(NEUTRAL_PROVIDER, fields.neutralAction);
    bundle.putCharSequence(NEUTRAL_TEXT, fields.neutralText);
    bundle.putSerializable(NEGATIVE_PROVIDER, fields.negativeAction);
    bundle.putCharSequence(NEGATIVE_TEXT, fields.negativeText);
    bundle.putBoolean(CANCELABLE, fields.cancelable);
  }

  @Override
  public DialogFields get(Bundle bundle) {
    return DialogFields.builder()
        .title(bundle.getCharSequence(TITLE))
        .message(bundle.getCharSequence(MESSAGE))
        .iconRes(bundle.getInt(ICON_RES))
        .positiveAction((ActivityMethod) bundle.getSerializable(POSITIVE_PROVIDER))
        .positiveText(bundle.getCharSequence(POSITIVE_TEXT))
        .neutralAction((ActivityMethod) bundle.getSerializable(NEUTRAL_PROVIDER))
        .neutralText(bundle.getCharSequence(NEUTRAL_TEXT))
        .negativeAction((ActivityMethod) bundle.getSerializable(NEGATIVE_PROVIDER))
        .negativeText(bundle.getCharSequence(NEGATIVE_TEXT))
        .build();
  }
}

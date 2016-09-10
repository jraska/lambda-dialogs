package com.jraska.dialog;

import android.os.Bundle;

final class DialogFieldsBundleAdapter implements ParameterProvider<DialogFields> {
  private static final String TITLE = "title";
  private static final String MESSAGE = "message";
  private static final String ICON_RES = "icon";
  private static final String POSITIVE_ACTION = "positiveMethod";
  private static final String POSITIVE_TEXT = "positiveText";
  private static final String NEUTRAL_ACTION = "neutralMethod";
  private static final String NEUTRAL_TEXT = "neutralText";
  private static final String NEGATIVE_ACTION = "negativeMethod";
  private static final String NEGATIVE_TEXT = "negativeText";

  @Override
  public void putTo(Bundle bundle, DialogFields fields) {
    bundle.putCharSequence(TITLE, fields.title);
    bundle.putCharSequence(MESSAGE, fields.message);
    bundle.putInt(ICON_RES, fields.iconRes);
    bundle.putSerializable(POSITIVE_ACTION, fields.positiveAction);
    bundle.putCharSequence(POSITIVE_TEXT, fields.positiveText);
    bundle.putSerializable(NEUTRAL_ACTION, fields.neutralAction);
    bundle.putCharSequence(NEUTRAL_TEXT, fields.neutralText);
    bundle.putSerializable(NEGATIVE_ACTION, fields.negativeAction);
    bundle.putCharSequence(NEGATIVE_TEXT, fields.negativeText);
  }

  @Override
  public DialogFields get(Bundle bundle) {
    return DialogFields.builder()
        .title(bundle.getCharSequence(TITLE))
        .message(bundle.getCharSequence(MESSAGE))
        .iconRes(bundle.getInt(ICON_RES))
        .positiveAction((ActivityMethod) bundle.getSerializable(POSITIVE_ACTION))
        .positiveText(bundle.getCharSequence(POSITIVE_TEXT))
        .neutralAction((ActivityMethod) bundle.getSerializable(NEUTRAL_ACTION))
        .neutralText(bundle.getCharSequence(NEUTRAL_TEXT))
        .negativeAction((ActivityMethod) bundle.getSerializable(NEGATIVE_ACTION))
        .negativeText(bundle.getCharSequence(NEGATIVE_TEXT))
        .build();
  }
}

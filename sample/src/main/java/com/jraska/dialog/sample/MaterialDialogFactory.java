package com.jraska.dialog.sample;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.jraska.dialog.ActivityDialogMethodParam;
import com.jraska.dialog.ActivityMethod;
import com.jraska.dialog.DialogFields;

public class MaterialDialogFactory<A extends FragmentActivity>
    implements ActivityDialogMethodParam<A, DialogFields> {
  @Override
  public Dialog onCreateDialog(A activity, DialogFields fields) {
    return new MaterialDialog.Builder(activity)
        .iconRes(fields.iconRes)
        .title(fields.title)
        .content(fields.message)
        .positiveText(fields.positiveText)
        .onPositive(callback(activity, fields.positiveAction))
        .negativeText(fields.negativeText)
        .onNegative(callback(activity, fields.negativeAction))
        .neutralText(fields.neutralText)
        .onNeutral(callback(activity, fields.neutralAction))
        .build();
  }

  @SuppressWarnings("unchecked")
  private static SingleButtonCallback callback(FragmentActivity activity, ActivityMethod method) {
    if (method == null) {
      return null;
    } else {
      return (dialog, which) -> method.call(activity);
    }
  }
}

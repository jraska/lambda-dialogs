package com.jraska.dialog;

import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

public final class AlertDialogFactory<A extends FragmentActivity>
    implements ActivityDialogMethodParam<A, DialogFields> {

  @Override
  public final AlertDialog onCreateDialog(A activity, DialogFields fields) {
    return new AlertDialog.Builder(activity)
        .setTitle(fields.title)
        .setMessage(fields.message)
        .setIcon(fields.iconRes)
        .setPositiveButton(fields.positiveText, call(fields.positiveAction, activity))
        .setNeutralButton(fields.neutralText, call(fields.neutralAction, activity))
        .setNegativeButton(fields.negativeText, call(fields.negativeAction, activity))
        .create();
  }

  @SuppressWarnings("unchecked")
  private static OnClickListener call(ActivityMethod action, FragmentActivity activity) {
    if (action == null) {
      return null;
    } else {
      return (dialogInterface, i) -> action.call(activity);
    }
  }
}

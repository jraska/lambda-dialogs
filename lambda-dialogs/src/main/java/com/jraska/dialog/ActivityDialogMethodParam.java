package com.jraska.dialog;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;

import java.io.Serializable;

public interface ActivityDialogMethodParam<A extends FragmentActivity, Param>
    extends Serializable {
  Dialog onCreateDialog(A activity, Param parameter);
}

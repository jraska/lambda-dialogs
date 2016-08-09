package com.jraska.dialog;

import android.support.v4.app.FragmentActivity;

import java.io.Serializable;

public interface ActivityAction<T extends FragmentActivity> extends Serializable {
  void call(T activity);
}

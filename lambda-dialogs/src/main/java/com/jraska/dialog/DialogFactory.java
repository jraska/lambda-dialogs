package com.jraska.dialog;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import lombok.Builder;

import java.io.Serializable;

public interface DialogFactory<T extends FragmentActivity> extends Serializable {
  Dialog onCreateDialog(T activity, FactoryData factoryData);

  @Builder
  class FactoryData {
    public final DialogFields fields;
  }
}

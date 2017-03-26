package com.jraska.dialog.sample;

import android.app.Application;
import com.jraska.dialog.LambdaDialogs;

public final class SampleApp extends Application {
  @Override public void onCreate() {
    super.onCreate();
    LambdaDialogs.validateEagerly(BuildConfig.DEBUG);
  }
}

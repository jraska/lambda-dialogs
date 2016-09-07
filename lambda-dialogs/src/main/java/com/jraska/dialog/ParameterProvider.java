package com.jraska.dialog;

import android.os.Bundle;

import java.io.Serializable;

interface ParameterProvider<Param> extends Serializable {
  Param get(Bundle args);

  void putTo(Bundle bundle, Param param);
}

class Empty<P> implements ParameterProvider<P> {
  @SuppressWarnings("unchecked")
  static <P> ParameterProvider<P> get() {
    return new Empty();
  }

  @Override public P get(Bundle args) {
    return null;
  }

  @Override public void putTo(Bundle bundle, P o) {
  }
}

class StringProvider implements ParameterProvider<String> {
  @Override public String get(Bundle args) {
    return args.getString("text");
  }

  @Override public void putTo(Bundle bundle, String s) {
    bundle.putString("text", s);
  }
}

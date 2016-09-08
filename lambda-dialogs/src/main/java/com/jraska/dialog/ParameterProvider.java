package com.jraska.dialog;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

interface ParameterProvider<Param> extends Serializable {
  Param get(Bundle args);

  void putTo(Bundle bundle, Param param);
}

final class Empty<P> implements ParameterProvider<P> {
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

final class ParcelableProvider<P extends Parcelable> implements ParameterProvider<P> {
  private static final String KEY = "parcelable";

  @SuppressWarnings("unchecked")
  static <P> ParameterProvider<P> get() {
    return new ParcelableProvider();
  }

  @Override public P get(Bundle args) {
    return args.getParcelable(KEY);
  }

  @Override public void putTo(Bundle bundle, P parcelable) {
    bundle.putParcelable(KEY, parcelable);
  }
}

final class SerializableProvider<P extends Serializable> implements ParameterProvider<P> {

  private static final String KEY = "serializable";

  @SuppressWarnings("unchecked")
  static <P extends Serializable> SerializableProvider<P> get() {
    return new SerializableProvider();
  }

  @SuppressWarnings("unchecked")
  @Override public P get(Bundle args) {
    return (P) args.getSerializable(KEY);
  }

  @Override public void putTo(Bundle bundle, P serializable) {
    bundle.putSerializable(KEY, serializable);
  }
}
package com.jraska.dialog;

import android.os.Bundle;
import android.os.Parcelable;

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

class ParcelableProvider<P extends Parcelable> implements ParameterProvider<P> {
  @SuppressWarnings("unchecked")
  static <P> ParameterProvider<P> get() {
    return new ParcelableProvider();
  }

  @Override public P get(Bundle args) {
    return args.getParcelable("parcelable");
  }

  @Override public void putTo(Bundle bundle, P parcelable) {
    bundle.putParcelable("parcelable", parcelable);
  }
}

class SerializableProvider<P extends Serializable> implements ParameterProvider<P> {
  @SuppressWarnings("unchecked")
  static <P extends Serializable> SerializableProvider<P> get() {
    return new SerializableProvider();
  }

  @SuppressWarnings("unchecked")
  @Override public P get(Bundle args) {
    return (P) args.getSerializable("serializable");
  }

  @Override public void putTo(Bundle bundle, P serializable) {
    bundle.putSerializable("serializable", serializable);
  }
}
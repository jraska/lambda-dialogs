package com.jraska.dialog;

final class Preconditions {
  private Preconditions() {
  }

  static <T> T argumentNotNull(T reference, String name) {
    if (reference == null) {
      throw new IllegalArgumentException("Parameter " + name + " cannot be null");
    }
    return reference;
  }
}

package com.jraska.dialog;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.io.*;

@Builder(builderClassName = "Builder")
@EqualsAndHashCode
public final class DialogFields {
  public final CharSequence title;
  public final CharSequence message;
  public final int iconRes;
  public final ActivityMethod positiveAction;
  public final CharSequence positiveText;
  public final ActivityMethod neutralAction;
  public final CharSequence neutralText;
  public final ActivityMethod negativeAction;
  public final CharSequence negativeText;
  public final boolean cancelable;

  void validate() {
    validateSerializable(positiveAction);
    validateSerializable(neutralAction);
    validateSerializable(negativeAction);
  }

  @SneakyThrows
  static void validateSerializable(Serializable serializable) {
    if (serializable == null) {
      return;
    }

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);

    outputStream.writeObject(serializable);

    ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    Object deserialized = inputStream.readObject();

    if (deserialized == null) {
      throw new IllegalArgumentException(serializable.getClass() + " does not implement Serializable properly");
    }
  }
}

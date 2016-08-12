package com.jraska.dialog;

import lombok.SneakyThrows;

import java.io.*;

final class SerializableValidator {

  @SneakyThrows
  void validateSerializable(Serializable serializable) {
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

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

    ByteArrayInputStream byteStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    ObjectInputStream inputStream = new ObjectInputStream(byteStream);
    Object deserialized = inputStream.readObject();

    if (deserialized == null) {
      throw new IllegalArgumentException(serializable.getClass()
          + " is not Serializable");
    }
  }
}

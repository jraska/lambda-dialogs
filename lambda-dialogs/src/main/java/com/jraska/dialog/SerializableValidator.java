package com.jraska.dialog;

import lombok.SneakyThrows;

import java.io.*;

final class SerializableValidator {

  @SneakyThrows @SuppressWarnings("unchecked")
  <T extends Serializable> T validateSerializable(T serializable) {
    if (serializable == null) {
      return null;
    }

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);

    outputStream.writeObject(serializable);

    ByteArrayInputStream byteStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    ObjectInputStream inputStream = new ObjectInputStream(byteStream);
    T deserialized = (T) inputStream.readObject();

    if (deserialized == null) {
      throw new IllegalArgumentException(serializable.getClass()
          + " is not Serializable");
    }

    return deserialized;
  }
}

package com.jraska.dialog;


import java.io.*;

final class SerializableValidator {

  private static final Serializable NULL_IS_SERIALIZABLE = null;

  <T extends Serializable> T validateSerializable(T serializable) {
    try {
      return validateUnchecked(serializable);
    } catch (IOException | ClassNotFoundException e) {
      throw new IllegalArgumentException(serializable.getClass()
          + " is not Serializable", e);
    }
  }

  @SuppressWarnings("unchecked")
  private <T extends Serializable> T validateUnchecked(T serializable) throws IOException, ClassNotFoundException {
    if (serializable == null) {
      return (T) NULL_IS_SERIALIZABLE;
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

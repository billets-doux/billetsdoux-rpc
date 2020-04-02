package org.billetsdoux.common.serialization;

public interface Serializer {

    <T> byte[] writeObject(T obj);

    <T> T readObject(byte[] bytes,Class<T> clazz);
}

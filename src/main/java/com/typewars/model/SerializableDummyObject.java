package com.typewars.model;

import java.io.Serializable;

public class SerializableDummyObject implements Serializable {
    private static final SerializableDummyObject instance = new SerializableDummyObject();

    private SerializableDummyObject() {
    }

    public static SerializableDummyObject getInstance() {
        return instance;
    }
}

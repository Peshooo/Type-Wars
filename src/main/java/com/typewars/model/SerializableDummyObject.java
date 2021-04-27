package com.typewars.model;

import java.io.Serializable;

public class SerializableDummyObject implements Serializable {
    private static final SerializableDummyObject instance = new SerializableDummyObject();

    private SerializableDummyObject() {
    }

    public Integer getZero() {
        return 0;
    }

    public static SerializableDummyObject getInstance() {
        return instance;
    }
}

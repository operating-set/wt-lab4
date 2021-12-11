package com.application.entity;

public enum Role {

    ADMINISTRATOR (0),
    CLIENT (1),
    TOUROPERATOR (2);

    private int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Role{" +
                "value=" + value +
                "} " + super.toString();
    }
}

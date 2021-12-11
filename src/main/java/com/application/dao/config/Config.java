package com.application.dao.config;

public enum Config {
    DB_HOST("localhost"),
    DB_PORT ("3306"),
    DB_USER("website_hotels_app"),
    DB_PASS("website_hotels_password"),
    DB_NAME("website_hotels_db");

    private final String value;

    Config (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package com.application.controller.command.rout;

public class Rout {
    private final String pathToResource;
    private final RoutingType type;

    public Rout(String resource, RoutingType resultType) {
        this.pathToResource = resource;
        type = resultType;
    }

    public RoutingType getRoutingType() {
        return type;
    }

    public String getPathToResource() {
        return pathToResource;
    }
}

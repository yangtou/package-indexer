package com.example.packageindexer.model;

public enum CommandEnum {
    INDEX,
    REMOVE,
    QUERY;

    public static boolean contains(String command) {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (commandEnum.name().equals(command)) {
                return true;
            }
        }
        return false;
    }
}

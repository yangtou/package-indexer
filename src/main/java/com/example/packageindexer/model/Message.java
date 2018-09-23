package com.example.packageindexer.model;

public class Message {
    private CommandEnum command;
    private String targetPackage;
    private String[] dependencies;

    public Message(CommandEnum command, String targetPackage, String[] dependencies) {
        this.command = command;
        this.targetPackage = targetPackage;
        this.dependencies = dependencies;
    }

    public CommandEnum getCommand() {
        return command;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public String[] getDependencies() {
        return dependencies;
    }
}

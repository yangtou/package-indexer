package com.example.packageindexer.message;

import com.example.packageindexer.model.CommandEnum;

public interface CommandHandler {
    String processCommand(String targetPackage, String[] dependencies);

    CommandEnum commandHandled();
}

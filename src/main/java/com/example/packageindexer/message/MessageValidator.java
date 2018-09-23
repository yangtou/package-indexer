package com.example.packageindexer.message;

import com.example.packageindexer.Exceptions.InvalidCommandTypeException;
import com.example.packageindexer.Exceptions.InvalidMessageException;
import com.example.packageindexer.model.CommandEnum;
import com.example.packageindexer.model.Message;

public class MessageValidator {

    public static Message validateMessage(String message) throws InvalidMessageException, InvalidCommandTypeException {
        String[] splitMessage = message.split("\\|", -1);
        if(splitMessage.length != 3) {
            throw new InvalidMessageException();
        }

        if(!CommandEnum.contains(splitMessage[0])) {
            throw new InvalidCommandTypeException();
        }
        CommandEnum commandEnum = CommandEnum.valueOf(splitMessage[0]);
        String targetPackage = splitMessage[1];
        String[] dependencies = splitMessage[2].split(",");

        return new Message(commandEnum, targetPackage, dependencies);
    }
}

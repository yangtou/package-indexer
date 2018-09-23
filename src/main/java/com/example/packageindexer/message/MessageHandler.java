package com.example.packageindexer.message;

import com.example.packageindexer.Exceptions.InvalidCommandTypeException;
import com.example.packageindexer.Exceptions.InvalidMessageException;
import com.example.packageindexer.model.CommandEnum;
import com.example.packageindexer.model.Message;
import com.example.packageindexer.model.MessageHandleResult;
import com.example.packageindexer.model.MessageResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageHandler {
    private Map<CommandEnum, CommandHandler> commandHandlerMap;

    @Autowired
    public MessageHandler(Map<CommandEnum, CommandHandler> commandHandlerMap) {
        this.commandHandlerMap = commandHandlerMap;
    }

    public MessageHandleResult handleMessage(final String message){
        try {
            Message validatedMessage = MessageValidator.validateMessage(message);
            CommandHandler commandHandler = commandHandlerMap.get(validatedMessage.getCommand());

            String reason = commandHandler.processCommand(validatedMessage.getTargetPackage(), validatedMessage.getDependencies());
            return new MessageHandleResult(MessageResultEnum.Success, reason);

        } catch (InvalidMessageException ex) {
            return new MessageHandleResult(MessageResultEnum.Fail, "Invalid message format");
        } catch (InvalidCommandTypeException ex) {
            return new MessageHandleResult(MessageResultEnum.Fail, "Invalid command type");
        }
    }
}

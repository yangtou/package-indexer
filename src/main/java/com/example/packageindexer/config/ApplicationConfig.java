package com.example.packageindexer.config;


import com.example.packageindexer.message.CommandHandler;
import com.example.packageindexer.model.CommandEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfig {

    @Autowired
    private List<CommandHandler> commandHandlers;

    @Bean
    public Map<CommandEnum, CommandHandler> commandHandlerMap() {
        return commandHandlers.stream()
                .collect(Collectors.toMap(CommandHandler::commandHandled, Function.identity()));
    }
}

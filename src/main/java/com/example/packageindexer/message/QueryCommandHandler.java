package com.example.packageindexer.message;

import com.example.packageindexer.indexer.Indexer;
import com.example.packageindexer.model.CommandEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryCommandHandler implements CommandHandler {
    private Indexer indexer;

    @Autowired
    public QueryCommandHandler(Indexer indexer) {
        this.indexer = indexer;
    }

    @Override
    public String processCommand(String targetPackage, String[] dependencies) {
        List<String> queryPackage =  indexer.query(targetPackage);
        if(queryPackage == null) {
            return "Package " + targetPackage + " has not been indexed yet";
        } else {
            return "Package " + targetPackage + " found! Dependencies: " + queryPackage;
        }
    }

    @Override
    public CommandEnum commandHandled() {
        return CommandEnum.QUERY;
    }
}

package com.example.packageindexer.message;

import com.example.packageindexer.Exceptions.MissingDependencyException;
import com.example.packageindexer.Exceptions.PackageAlreadyIndexedException;
import com.example.packageindexer.indexer.Indexer;
import com.example.packageindexer.model.CommandEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class IndexCommandHandler implements CommandHandler {
    private Indexer indexer;

    @Autowired
    public IndexCommandHandler(Indexer indexer) {
        this.indexer = indexer;
    }

    @Override
    public String processCommand(String targetPackage, String[] dependencies) {
        try {
            indexer.index(targetPackage, dependencies);
            return "Successfully index pacakge " + targetPackage + " with dependencies: " + Arrays.asList(dependencies);
        } catch (MissingDependencyException e) {
            return "Cannot index package: " + targetPackage + ", Need to index following dependencies first: " + e.getMissingDependencies();
        } catch (PackageAlreadyIndexedException e) {
            return "Package " + targetPackage + " has already been indexed";
        }
    }

    @Override
    public CommandEnum commandHandled() {
        return CommandEnum.INDEX;
    }
}

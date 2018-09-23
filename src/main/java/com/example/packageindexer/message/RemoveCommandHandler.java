package com.example.packageindexer.message;

import com.example.packageindexer.Exceptions.DependencyStillExistException;
import com.example.packageindexer.Exceptions.PackageDoesNotExistException;
import com.example.packageindexer.indexer.Indexer;
import com.example.packageindexer.model.CommandEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoveCommandHandler implements CommandHandler {
    private Indexer indexer;

    @Autowired
    public RemoveCommandHandler(Indexer indexer) {
        this.indexer = indexer;
    }

    @Override
    public String processCommand(String targetPackage, String[] dependencies) {
        try {
            indexer.remove(targetPackage);
            return "Successfully removed package: " + targetPackage;
        } catch (PackageDoesNotExistException e) {
            return "Fail to remove pakcage because package:" + targetPackage + " has not been indexed yet";
        } catch (DependencyStillExistException e) {
            return "Fail to remove pacakge " + targetPackage + " because the following packages still depends on it, please remove those first: \n"
                    + e.getBlockingDependencies();
        }
    }

    @Override
    public CommandEnum commandHandled() {
        return CommandEnum.REMOVE;
    }
}

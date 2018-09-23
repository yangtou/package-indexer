package com.example.packageindexer.Exceptions;

import java.util.List;

public class DependencyStillExistException extends Exception {
    private List<String> blockingDependencies;

    public DependencyStillExistException(List<String> blockingDependencies) {
        super();
        this.blockingDependencies = blockingDependencies;
    }

    public List<String> getBlockingDependencies() {
        return blockingDependencies;
    }
}

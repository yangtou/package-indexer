package com.example.packageindexer.Exceptions;

import java.util.List;

public class MissingDependencyException extends Exception {
    private List<String > missingDependencies;

    public MissingDependencyException(List<String> missingDependencies) {
        super();
        this.missingDependencies = missingDependencies;
    }

    public List<String> getMissingDependencies() {
        return missingDependencies;
    }
}

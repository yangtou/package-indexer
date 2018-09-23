package com.example.packageindexer.indexer;

import com.example.packageindexer.Exceptions.DependencyStillExistException;
import com.example.packageindexer.Exceptions.MissingDependencyException;
import com.example.packageindexer.Exceptions.PackageAlreadyIndexedException;
import com.example.packageindexer.Exceptions.PackageDoesNotExistException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Indexer {
    private Map<String, List<String>> pacakgeToDependencyMap;
    private Map<String, List<String>> dependencyToPackageMap;

    public Indexer() {
        pacakgeToDependencyMap = new ConcurrentHashMap();
        dependencyToPackageMap = new ConcurrentHashMap();
    }

    public synchronized void index(final String targetPackage, final String[] dependencies) throws MissingDependencyException, PackageAlreadyIndexedException {
        if (pacakgeToDependencyMap.containsKey(targetPackage)) {
            throw new PackageAlreadyIndexedException();
        }

        // dependencies is a list of one empty string
        if(dependencies.length == 1 && dependencies[0].equals("")) {
            indexWithoutDependencies(targetPackage);
            return;
        }

        // throw exception if there's dependency need index first
        List<String> missingDependencies = new ArrayList<>();
        for(String dependency : dependencies) {
            if(pacakgeToDependencyMap.get(dependency) == null) {
                missingDependencies.add(dependency);
            }
        }
        if(!missingDependencies.isEmpty()) {
            throw new MissingDependencyException(missingDependencies);
        }


        pacakgeToDependencyMap.put(targetPackage, Arrays.asList(dependencies));
        for (String dependency : dependencies) {
            List<String> packages = dependencyToPackageMap.getOrDefault(dependency, new ArrayList<>());
            packages.add(targetPackage);
            dependencyToPackageMap.put(dependency, packages);
        }
    }

    private synchronized void indexWithoutDependencies(final String targetPackage) {
        pacakgeToDependencyMap.putIfAbsent(targetPackage, new ArrayList<>());
    }


    public synchronized List<String> query(final String targetPackage) {
        return pacakgeToDependencyMap.getOrDefault(targetPackage, null);
    }

    public synchronized void remove(final String targetPackage) throws PackageDoesNotExistException, DependencyStillExistException {
        if(!pacakgeToDependencyMap.containsKey(targetPackage)) {
            throw new PackageDoesNotExistException();
        }

        // throw the packages that depend on the targetPackage which makes it un-removable
        if(dependencyToPackageMap.containsKey(targetPackage)) {
            throw new DependencyStillExistException(dependencyToPackageMap.get(targetPackage));
        }

        // remove all dependency -> targetPackage mapping
        for(String dependency : pacakgeToDependencyMap.get(targetPackage)) {
            dependencyToPackageMap.get(dependency).remove(targetPackage);
        }

        pacakgeToDependencyMap.remove(targetPackage);
    }
}

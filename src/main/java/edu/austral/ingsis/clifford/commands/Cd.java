package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.Pair;
import edu.austral.ingsis.clifford.fileSystem.Directory;
import edu.austral.ingsis.clifford.result.Result;

public final class Cd implements Command {
    private final String path;

    public Cd(String path) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }
        this.path = path;
    }

    @Override
    public Result<Pair<String, FileSystemImplementation>> execute(FileSystemImplementation fs) {
        try {

            Directory targetDirectory = resolveTargetDirectory(fs, path);


            FileSystemImplementation newFs = fs.changeCurrentDirectory(targetDirectory);


            String output = "Moved to directory: '" + newFs.getCurrentDirectory().name() + "'";
            return new Result.Success<>(new Pair<>(output, newFs));
        } catch (IllegalArgumentException e) {
            return new Result.Error<>("Error: " + e.getMessage());
        }
    }

    private Directory resolveTargetDirectory(FileSystemImplementation fs, String path) {

        if (path.equals(".")) {
            return fs.getCurrentDirectory();
        }


        if (path.equals("..")) {
            String currentPath = fs.getCurrentDirectory().name();

            if (currentPath.equals("/root")) {
                return fs.getCurrentDirectory();
            }

            int lastIndex = currentPath.lastIndexOf('/');
            String parentPath = (lastIndex <= 0) ? "/" : currentPath.substring(0, lastIndex);

            Directory parentDirectory = fs.resolvePath(parentPath);
            if (parentDirectory == null) {
                throw new IllegalArgumentException("Directorio padre no encontrado: " + parentPath);
            }

            return parentDirectory;
        }


        Directory target = fs.resolvePath(path);
        if (target == null) {
            throw new IllegalArgumentException("Directory not found: " + path);
        }

        return target;
    }

    private static Directory getDirectory(FileSystemImplementation fs, String currentPath) {
        String parentPath = currentPath.substring(0, currentPath.lastIndexOf('/'));
        parentPath = parentPath.isEmpty() ? "/" : parentPath;

        Directory parentDirectory = fs.resolvePath(parentPath);
        if (parentDirectory == null) {
            throw new IllegalArgumentException("Parent directory not found: " + parentPath);
        }

        return parentDirectory;
    }


}
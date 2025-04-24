package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.fileSystem.Directory;
import edu.austral.ingsis.clifford.fileSystem.FileSystemItems;

import java.util.ArrayList;
import java.util.List;

public non-sealed class Rm implements Command {

    private final FileSystemImplementation fileSystem;
    private final String target;
    private final boolean recursive;

    public Rm(FileSystemImplementation fileSystem, String target, boolean recursive) {
        this.fileSystem = fileSystem;
        this.target = target;
        this.recursive = recursive;
    }

    @Override
    public String execute() {
        Directory currentDirectory = fileSystem.getCurrentDirectory();
        FileSystemItems item = currentDirectory.getItem(target);

        if (item == null) {
            return "Item '" + target + "' not found";
        }

        if (item.isDirectory()) {
            if (recursive) {
                deleteDirectoryRecursively((Directory) item);
                return "'" + item.getName() + "' removed";
            } else {
                return "Cannot remove '" + target + "', is a directory";
            }
        } else {
            currentDirectory.removeItem(item.getName());
            return "'" + item.getName() + "' removed";
        }
    }

    private void deleteDirectoryRecursively(Directory directory) {
        List<FileSystemItems> items = new ArrayList<>(directory.getFiles());
        for (FileSystemItems item : items) {
            if (item.isDirectory()) {
                deleteDirectoryRecursively((Directory) item);
            } else {
                directory.removeItem(item.getName());
            }
        }
        directory.getParent().removeItem(target);
    }
}

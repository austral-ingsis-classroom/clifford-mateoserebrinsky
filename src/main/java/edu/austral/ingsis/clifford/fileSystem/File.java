package edu.austral.ingsis.clifford.fileSystem;

import java.util.Map;

public class File implements FileSystemItems {

    private final String name;
    private Directory parent;
    private String path;

    public File(String name) {
        this.name = name;
        this.parent = null;
    }

    public File(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Directory getParent() {
        return parent;
    }


}

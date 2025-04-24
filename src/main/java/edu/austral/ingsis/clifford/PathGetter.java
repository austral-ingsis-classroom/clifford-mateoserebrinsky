package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.fileSystem.Directory;
import edu.austral.ingsis.clifford.fileSystem.FileSystemItems;

import java.nio.file.FileSystem;
import java.nio.file.Path;

public class PathGetter {

    public String getPath(FileSystemItems currentDir) {
        String path = "";
        if (currentDir != null) {
            path = getPath(currentDir.getParent())+"/"+currentDir.getName();
        }
        return path;
    }
}

package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.PathGetter;
import edu.austral.ingsis.clifford.fileSystem.Directory;

public non-sealed class Pwd implements Command{

    Directory currentDirectory;

    public Pwd(FileSystemImplementation fileSystem){
        this.currentDirectory = fileSystem.getCurrentDirectory();
    }

    @Override
    public String execute() {
        PathGetter pathGetter = new PathGetter();
        return (pathGetter.getPath(currentDirectory));
    }
}

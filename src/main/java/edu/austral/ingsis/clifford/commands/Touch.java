package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.fileSystem.Directory;
import edu.austral.ingsis.clifford.fileSystem.File;

public non-sealed class Touch implements Command {

    String name;
    Directory currentDirectory;

    public Touch(FileSystemImplementation fileSystem, String name) {
        this.name = name;
        this.currentDirectory = fileSystem.getCurrentDirectory();
    }

    @Override
    public String execute() {
        if(name.contains("/") || name.contains(" ")){
            return ("The file name cant contain spaces or /");
        }
        else{File newFile = new File(name, currentDirectory);
            currentDirectory.addFile(newFile);
            return ("'"+name+"'"+" file created");}
    }
}

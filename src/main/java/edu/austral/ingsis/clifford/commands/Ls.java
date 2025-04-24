package edu.austral.ingsis.clifford.commands;


import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.fileSystem.Directory;
import edu.austral.ingsis.clifford.fileSystem.FileSystemItems;

import java.util.Comparator;
import java.util.List;

public non-sealed class Ls implements Command {

    Directory curentDirectory;
    String flag;

    public Ls(FileSystemImplementation fileSystem, String flag) {
        this.curentDirectory = fileSystem.getCurrentDirectory();
        if(flag == null) {
            this.flag = "-";
        }
        else {
            this.flag = flag;
        }
    }


    @Override
    public String execute() {
        switch (flag) {
            case "-" -> {
                return curentDirectory.getFilesNames().toString();
            }
            case "--ord=asc" -> {
                List<String> files = curentDirectory.getFilesNames();
                Comparator<String> ascComparator = Comparator.naturalOrder();
                files.sort(ascComparator);
                return files.toString();
            }
            case "--ord=desc" -> {
                List<String> files = curentDirectory.getFilesNames();
                Comparator<String> ascComparator = Comparator.reverseOrder();
                files.sort(ascComparator);
                return files.toString();
            }
            default -> {
                return "Invalid flag";
            }
        }
    }
}

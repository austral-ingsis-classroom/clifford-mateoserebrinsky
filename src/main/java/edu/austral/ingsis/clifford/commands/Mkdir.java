package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.fileSystem.Directory;

public non-sealed class Mkdir implements Command {

  private final String dir;
  private final FileSystemImplementation fileSystem;

  public Mkdir(FileSystemImplementation fileSystem, String dir) {
    this.fileSystem = fileSystem;
    this.dir = dir;
  }

  @Override
  public String execute() {
    Directory newDir = new Directory(dir);
    Directory current = fileSystem.getCurrentDirectory();
    current.addFile(newDir);
    return ("Directory '" + dir + "' added to " + current.getName());
  }
}

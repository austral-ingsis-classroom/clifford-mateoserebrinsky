package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.fileSystem.Directory;

public class FileSystemImplementation {

  Directory root;
  Directory currentDirectory;

  public FileSystemImplementation() {
    this.root = new Directory("root");
    root.setParent(null);
    this.currentDirectory = this.root;
  }

  public Directory getCurrentDirectory() {
    return this.currentDirectory;
  }

  public Directory getRoot() {
    return this.root;
  }

  public void setCurrentDirectory(Directory currentDirectory) {
    this.currentDirectory = currentDirectory;
  }
}

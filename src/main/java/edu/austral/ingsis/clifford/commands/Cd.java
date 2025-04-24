package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.fileSystem.Directory;

public non-sealed class Cd implements Command {
  String dir;
  Directory currentDirectory;
  FileSystemImplementation fileSystem;

  public Cd(FileSystemImplementation fileSystem, String path) {
    this.dir = path;
    this.currentDirectory = fileSystem.getCurrentDirectory();
    this.fileSystem = fileSystem;
  }

  @Override
  public String execute() {
    return resolvePath(dir);
  }

  public String resolvePath(String path) {
    if (isInvalidPath(path)) return "No path provided";

    String specialCase = handleSpecialCases(path);
    if (specialCase != null) return specialCase;

    return navigatePath(path);
  }

  private boolean isInvalidPath(String path) {
    return path == null || path.isEmpty();
  }

  private String handleSpecialCases(String path) {
    switch (path) {
      case ".." -> {
        return moveToParent();
      }
      case "." -> {
        return stayInCurrentDirectory();
      }
      default -> {
        return null;
      }
    }
  }

  private String moveToParent() {
    if (currentDirectory.getParent() != null) {
      fileSystem.setCurrentDirectory(currentDirectory.getParent());
      return "Moved to directory: " + "/" + currentDirectory.getParent().getName();
    } else {
      return "Already at root directory";
    }
  }

  private String stayInCurrentDirectory() {
    fileSystem.setCurrentDirectory(currentDirectory);
    return "Staying in current directory: " + currentDirectory.getName();
  }

  private String navigatePath(String path) {
    String[] directoryKeys = path.split("/");
    Directory currentDir = currentDirectory;

    for (String directoryKey : directoryKeys) {
      if (directoryKey.isEmpty()) continue;
      Directory nextDirectory = currentDir.getDirectory(directoryKey);
      if (nextDirectory != null) {
        currentDir = nextDirectory;
      } else {
        return "Directory not found: " + directoryKey;
      }
    }

    fileSystem.setCurrentDirectory(currentDir);
    return "Moved to directory: " + currentDir.getName();
  }
}

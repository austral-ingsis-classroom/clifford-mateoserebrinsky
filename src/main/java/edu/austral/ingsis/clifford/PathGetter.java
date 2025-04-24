package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.fileSystem.FileSystemItems;

public class PathGetter {

  public String getPath(FileSystemItems currentDir) {
    String path = "";
    if (currentDir != null) {
      path = getPath(currentDir.getParent()) + "/" + currentDir.getName();
    }
    return path;
  }
}

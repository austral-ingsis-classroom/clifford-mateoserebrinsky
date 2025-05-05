package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.fileSystem.Directory;
import edu.austral.ingsis.clifford.fileSystem.File;
import edu.austral.ingsis.clifford.fileSystem.FileSystemItems;
import java.util.Map;
import java.util.Optional;

public record FileSystemImplementation(Directory root, Directory currentDirectory) {

  public FileSystemImplementation() {
    this(new Directory("/", Map.of()), new Directory("/")); // Raíz vacía
  }

  public Directory getCurrentDirectory() {
    return currentDirectory;
  }

  public FileSystemImplementation addFile(String name) {
    validateName(name);
    Directory updatedDir = currentDirectory.addItem(name, new File(name, ""));
    return updateDirectory(updatedDir);
  }

  public FileSystemImplementation addDirectory(String name) {
    validateName(name);
    Directory updatedDir = currentDirectory.addItem(name, new Directory(name));
    return updateDirectory(updatedDir);
  }

  public FileSystemImplementation remove(String name, boolean recursive) {
    FileSystemItems item =
        Optional.ofNullable(currentDirectory.getItem(name))
            .orElseThrow(() -> new IllegalArgumentException("Item not found: " + name));

    if (item.isDirectory() && !recursive) {
      Directory directory = (Directory) item;
      if (!directory.isEmpty()) {
        throw new IllegalArgumentException(
            "Cannot remove non-empty directory without recursive flag.");
      }
    }

    Directory updatedDir = currentDirectory.removeItem(name);
    return updateDirectory(updatedDir);
  }

  public FileSystemImplementation changeCurrentDirectory(Directory dir) {
    return new FileSystemImplementation(root, dir);
  }

  private FileSystemImplementation updateDirectory(Directory updatedDirectory) {
    Directory rebuiltRoot = rebuildDirectory(root, updatedDirectory);
    return new FileSystemImplementation(rebuiltRoot, updatedDirectory);
  }

  private Directory rebuildDirectory(Directory original, Directory updated) {
    if (original.equals(currentDirectory)) {
      return updated;
    }
    return original;
  }

  public boolean itemExists(String name) {
    return currentDirectory.getItem(name) != null;
  }

  public FileSystemItems resolveItem(String name) {
    return currentDirectory.getItem(name);
  }

  public Directory resolvePath(String path) {

    if (path.equals("/")) {
      return root;
    }

    Directory current = path.startsWith("/") ? root : currentDirectory;

    for (String part : path.split("/")) {
      if (part.isBlank() || part.equals(".")) {

        continue;
      }
      if (part.equals("..")) {

        String currentPath = current.name();
        if (!currentPath.equals("/")) {
          String parentPath = currentPath.substring(0, currentPath.lastIndexOf('/'));
          parentPath = parentPath.isEmpty() ? "/" : parentPath;
          current = resolvePath(parentPath);
        }
      } else {

        current =
            (Directory)
                Optional.ofNullable(current.getItem(part))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid path: " + path));
      }
    }

    return current;
  }

  private void validateName(String name) {
    if (name == null || name.isBlank() || name.contains("/"))
      throw new IllegalArgumentException("Invalid name: " + name);
  }

  public Directory getRoot() {
    return root;
  }
}

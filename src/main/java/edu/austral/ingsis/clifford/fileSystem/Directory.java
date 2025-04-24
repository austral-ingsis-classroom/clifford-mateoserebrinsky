package edu.austral.ingsis.clifford.fileSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Directory implements FileSystemItems {

  String name;
  String path;
  Directory parent;

  private final Map<String, FileSystemItems> files;

  public Directory(String name) {
    this.name = name;
    this.files = new HashMap<>();
  }

  public List<FileSystemItems> getFiles() {
    return new ArrayList<>(files.values());
  }

  public List<String> getFilesNames() {
    return new ArrayList<>(files.keySet());
  }

  public void addFile(FileSystemItems item) {
    item.setParent(this);
    files.put(item.getName(), item);
  }

  public Directory getDirectory(String name) {
    FileSystemItems item = files.get(name);
    if (item != null && item.isDirectory()) {
      return (Directory) item;
    }
    return null;
  }

  public FileSystemItems getItem(String name) {
    return files.get(name);
  }

  @Override
  public boolean isDirectory() {
    return true;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Directory getParent() {
    return parent;
  }

  @Override
  public void setParent(Directory parent) {
    this.parent = parent;
  }

  public void removeItem(String name) {
    files.remove(name);
  }
}

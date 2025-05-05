package edu.austral.ingsis.clifford.fileSystem;

import java.util.*;

public final class Directory implements FileSystemItems {
  private final String name;
  private final Map<String, FileSystemItems> items; // Representación de archivos y subdirectorios

  public Directory(String name, Map<String, FileSystemItems> items) {
    this.name = Objects.requireNonNull(name, "Name cannot be null");
    this.items = Map.copyOf(items);
  }

  public Directory(String name) {
    this.name = Objects.requireNonNull(name, "Name cannot be null");
    this.items = Collections.emptyMap();
  }

  public List<String> listItems() {
    return new ArrayList<>(items.keySet());
  }

  public String name() {
    return name;
  }

  @Override
  public boolean isDirectory() {
    return true;
  }

  @Override
  public String getPath() {
    return "";
  }

  public Map<String, FileSystemItems> getItems() {
    return items;
  }

  public boolean isEmpty(){
    return items.isEmpty();
  }



  public Directory addItem(String name, FileSystemItems item) {
    if (items.containsKey(name)) throw new IllegalArgumentException("Item already exists: " + name);
    Map<String, FileSystemItems> newItems = new HashMap<>(items);
    newItems.put(name, item);
    return new Directory(this.name, newItems);
  }


  public Directory removeItem(String name) {
    if (!items.containsKey(name)) throw new IllegalArgumentException("Item does not exist: " + name);
    Map<String, FileSystemItems> newItems = new HashMap<>(items);
    newItems.remove(name);
    return new Directory(this.name, newItems);
  }


  public FileSystemItems getItem(String name) {
    return items.get(name);
  }


  public List<String> getFilesNames(){
    return new ArrayList<>(items.keySet());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Directory directory)) return false;
    return name.equals(directory.name) && items.equals(directory.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, items);
  }
}
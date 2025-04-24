package edu.austral.ingsis.clifford.fileSystem;

public interface FileSystemItems {

  public boolean isDirectory();

  public String getName();

  public Directory getParent();

  public void setParent(Directory parent);
}

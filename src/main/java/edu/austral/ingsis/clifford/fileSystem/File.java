package edu.austral.ingsis.clifford.fileSystem;

import java.util.Objects;

public record File(String name, String content) implements FileSystemItems {
  public File(String name, String content) {
    this.name = Objects.requireNonNull(name, "Name cannot be null");
    this.content = Objects.requireNonNull(content, "Content cannot be null");
  }

  @Override
  public boolean isDirectory() {
    return false;
  }

  @Override
  public String getPath() {
    return "";
  }

  // Crear un nuevo archivo con contenido actualizado
  public File updateContent(String newContent) {
    return new File(this.name, newContent);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof File)) return false;
    File file = (File) o;
    return name.equals(file.name) && content.equals(file.content);
  }

}
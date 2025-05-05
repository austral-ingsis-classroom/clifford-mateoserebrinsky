package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.Pair;
import edu.austral.ingsis.clifford.result.Result;

public final class Touch implements Command {

  private final String fileName;

  public Touch(String fileName) {
    if (fileName == null || fileName.isBlank()) {
      throw new IllegalArgumentException("File name cannot be null or empty");
    }
    this.fileName = fileName;
  }

  @Override
  public Result<Pair<String, FileSystemImplementation>> execute(FileSystemImplementation fs) {
    try {
      if (fileName.contains("/") || fileName.contains(" ")) {
        return new Result.Error<>("File name cannot contain '/' or spaces");
      }

      FileSystemImplementation newFs = fs.addFile(fileName);
      String output = String.format("'%s' file created", fileName);

      return new Result.Success<>(new Pair<>(output, newFs));
    } catch (Exception e) {
      return new Result.Error<>("Error: " + e.getMessage());
    }
  }
}

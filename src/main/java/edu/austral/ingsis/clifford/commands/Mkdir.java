package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.Pair;
import edu.austral.ingsis.clifford.result.Result;

public final class Mkdir implements Command {
  private final String directoryName;

  public Mkdir(String directoryName) {
    if (directoryName == null || directoryName.isBlank()) {
      throw new IllegalArgumentException("Directory name cannot be null or empty");
    }
    this.directoryName = directoryName;
  }

  @Override
  public Result<Pair<String, FileSystemImplementation>> execute(FileSystemImplementation fs) {
    try {
      if (directoryName.contains("/") || directoryName.contains(" ")) {
        return new Result.Error<>("Directory name cannot contain '/' or spaces");
      }

      FileSystemImplementation newFs = fs.addDirectory(directoryName);
      String output = String.format("'%s' directory created", directoryName);

      return new Result.Success<>(new Pair<>(output, newFs));
    } catch (Exception e) {
      return new Result.Error<>("Error: " + e.getMessage());
    }
  }
}

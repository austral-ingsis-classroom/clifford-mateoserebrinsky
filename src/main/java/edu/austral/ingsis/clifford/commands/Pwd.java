package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.Pair;
import edu.austral.ingsis.clifford.fileSystem.Directory;
import edu.austral.ingsis.clifford.result.Result;

public final class Pwd implements Command {

  public Pwd() {}

  @Override
  public Result<Pair<String, FileSystemImplementation>> execute(FileSystemImplementation fs) {
    try {
      Directory currentDirectory = fs.getCurrentDirectory();

      String path = currentDirectory.name();

      return new Result.Success<>(new Pair<>(path, fs));
    } catch (Exception e) {
      return new Result.Error<>("Error fetching path: " + e.getMessage());
    }
  }
}

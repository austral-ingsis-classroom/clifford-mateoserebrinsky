package edu.austral.ingsis;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.Pair;
import edu.austral.ingsis.clifford.Terminal;
import edu.austral.ingsis.clifford.result.Result;
import java.util.ArrayList;
import java.util.List;

public class FileSystemRunnerImplementation implements FileSystemRunner {

  @Override
  public List<String> executeCommands(List<String> commands) {
    FileSystemImplementation fileSystem = new FileSystemImplementation();
    Terminal cmd = new Terminal(fileSystem);
    List<String> results = new ArrayList<>();

    for (String command : commands) {

      Result<Pair<String, FileSystemImplementation>> result = cmd.run(command);

      if (result.isSuccess()) {

        String successMessage = result.getValue().first();
        if (successMessage != null && !successMessage.isBlank()) {
          results.add(successMessage);
        }
      } else if (result.isError()) {

        results.add("Error: " + result.getErrorMessage());
      }
    }

    return results;
  }
}

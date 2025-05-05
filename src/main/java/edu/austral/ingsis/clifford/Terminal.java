package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.commands.*;
import edu.austral.ingsis.clifford.result.Result;

public class Terminal {
  private FileSystemImplementation system;

  public Terminal(FileSystemImplementation system) {
    this.system = system;
  }

  public Result<Pair<String, FileSystemImplementation>> run(String commandStr) {
    CommandParser commandParser = new CommandParser();
    Result<Command> parsingResult = commandParser.interpretCommand(commandStr);

    if (parsingResult.isError()) {
      String error = parsingResult.getErrorMessage();
      System.out.println("ERROR: " + error);
      return new Result.Error<>(error);
    }

    Result<Pair<String, FileSystemImplementation>> executionResult =
        parsingResult.getValue().execute(system);

    if (executionResult.isSuccess()) {
      this.system = executionResult.getValue().second();
    }

    if (executionResult.isSuccess()) {
      String output = executionResult.getValue().first();
      if (!output.isEmpty()) {
        System.out.println(output);
      }
    } else {
      System.out.println("ERROR: " + executionResult.getErrorMessage());
    }

    return executionResult;
  }
}

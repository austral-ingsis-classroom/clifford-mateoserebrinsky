package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.commands.*;
import edu.austral.ingsis.clifford.result.Result;

public class Terminal {

  private final FileSystemImplementation system;

  public Terminal(FileSystemImplementation system) {
    this.system = system;
  }

  public String run(String command) {
    CommandParser commandParser = new CommandParser();
    Result<Command> result = commandParser.interpretCommand(command, system);

    if (result.isSuccess()) {
      Command commandToExecute = result.getValue();
      String output = commandToExecute.execute();
      System.out.println(output);
      return output;
    } else {
      String errorMessage = result.getError();
      System.out.println(errorMessage);
      return errorMessage;
    }
  }
}

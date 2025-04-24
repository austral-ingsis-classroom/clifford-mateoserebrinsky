package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.commands.*;
import edu.austral.ingsis.clifford.result.Error;
import edu.austral.ingsis.clifford.result.Result;
import edu.austral.ingsis.clifford.result.Success;

public class CommandParser {

  public Result<Command> interpretCommand(String command, FileSystemImplementation system) {
    String[] items = command.trim().split("\\s+");
    if (items.length == 0 || items[0].isBlank()) {
      return new Error<>("Empty command");
    }

    String mainCommand = items[0];

    if (mainCommand.equals("touch")) {
      if (items.length < 2) return new Error<>("Not enough arguments for 'touch'");
      return new Success<>(new Touch(system, items[1]));
    }

    if (mainCommand.equals("cd")) {
      if (items.length < 2) return new Error<>("Not enough arguments for 'cd'");
      return new Success<>(new Cd(system, items[1]));
    }

    if (mainCommand.equals("ls")) {
      String path = (items.length >= 2) ? items[1] : "-";
      return new Success<>(new Ls(system, path));
    }

    if (mainCommand.equals("mkdir")) {
      if (items.length < 2) return new Error<>("Not enough arguments for 'mkdir'");
      return new Success<>(new Mkdir(system, items[1]));
    }

    if (mainCommand.equals("rm")) {
      if (items.length < 2) return new Error<>("Not enough arguments for 'rm'");
      String file = items.length > 2 && "--recursive".equals(items[1]) ? items[2] : items[1];
      boolean recursive = items.length > 2 && "--recursive".equals(items[1]);
      return new Success<>(new Rm(system, file, recursive));
    }

    if (mainCommand.equals("pwd")) {
      return new Success<>(new Pwd(system));
    }
    return new Error<>("'" + mainCommand + "' is not a recognized command");
  }
}

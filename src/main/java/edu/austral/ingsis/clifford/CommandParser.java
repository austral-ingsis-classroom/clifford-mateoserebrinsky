package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.commands.*;
import edu.austral.ingsis.clifford.result.Result;

public class CommandParser {

  public Result<Command> interpretCommand(String command) {
    String[] parts = command.trim().split("\\s+");
    if (parts.length == 0 || parts[0].isBlank()) {
      return new Result.Error<>("Empty command");
    }
    String[] items = command.trim().split("\\s+");
    String mainCommand = items[0];

    return switch (mainCommand) {
          case "mkdir" -> {
              if (parts.length < 2) {
                  yield new Result.Error<>("Usage: mkdir <directory_name>");
              }
              yield new Result.Success<>(new Mkdir(parts[1]));
          }
          case "cd" -> {
              if (parts.length < 2) {
                  yield new Result.Error<>("Usage: cd <directory_path>");
              }
              yield new Result.Success<>(new Cd(parts[1]));
          }
          case "ls" ->{
            String order = "";
            if (parts.length > 1 && parts[1].startsWith("--ord=")) {
              order = parts[1].substring(6);
            }
            yield new Result.Success<>(new Ls(order));
          }

          case "touch"->{
            if (parts.length < 2) {
              yield new Result.Error<>("Missing argument");
            }
            String name = parts[1];
            yield new Result.Success<>(new Touch(name));
          }

          case "rm"->{
            if (parts.length < 2) {
              yield new Result.Error<>("Usage: rm [--recursive] <target>");
            }

            boolean recursive = false;
            String target;

            if (parts.length > 2 && parts[1].equals("--recursive")) {
              recursive = true;
              target = parts[2];
            } else {
              target = parts[1];
            }

            yield new Result.Success<>(new Rm(target, recursive));
          }

          case "pwd" ->{
            if (parts.length > 2) {
              yield new Result.Error<>("More arguments than expected");
            }
            yield new Result.Success<>(new Pwd());
          }

          default -> new Result.Error<>("'" + mainCommand + "' is not a recognized command");
      };
    }


  }

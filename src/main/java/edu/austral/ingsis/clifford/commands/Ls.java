package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.Pair;
import edu.austral.ingsis.clifford.fileSystem.Directory;
import edu.austral.ingsis.clifford.result.Result;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Ls implements Command {
  private final String order;

  public Ls(String order) {
    this.order = validateOrder(order);
  }

  @Override
  public Result<Pair<String, FileSystemImplementation>> execute(FileSystemImplementation fs) {
    Directory currentDirectory = fs.getCurrentDirectory();
    List<String> items = new ArrayList<>(currentDirectory.getFilesNames());

    if ("asc".equals(order)) {
      Collections.sort(items);
    } else if ("desc".equals(order)) {
      items.sort(Collections.reverseOrder());
    } else {
      items.sort(Collections.reverseOrder());
    }

    StringBuilder output = new StringBuilder();
    if (!items.isEmpty()) {
      output.append(String.join(" ", items));
    }

    return new Result.Success<>(new Pair<>(output.toString(), fs));
  }

  private String validateOrder(String order) {
    if (order == null || order.isEmpty() || "asc".equals(order) || "desc".equals(order)) {
      return order;
    }
    throw new IllegalArgumentException("Invalid order parameter. Use 'asc' or 'desc'");
  }
}

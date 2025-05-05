package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.Pair;
import edu.austral.ingsis.clifford.result.Result;

public sealed interface Command permits Ls, Cd, Touch, Mkdir, Rm, Pwd {
  Result<Pair<String, FileSystemImplementation>> execute(FileSystemImplementation fs);
}

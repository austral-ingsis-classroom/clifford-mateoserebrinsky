package edu.austral.ingsis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.Pair;
import edu.austral.ingsis.clifford.Terminal;
import edu.austral.ingsis.clifford.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileSystemLimitCasesTest {

  Terminal cmd;
  FileSystemImplementation fsi;

  @BeforeEach
  void setUp() {
    fsi = new FileSystemImplementation();
    cmd = new Terminal(fsi);
  }

  @Test
  public void testForCdToTheSameDirectory() {
    Result<Pair<String, FileSystemImplementation>> result = cmd.run("cd .");
    String actual = result.getValue().first();
    String expected = "Moved to directory: '/'";
    assertEquals(expected, actual, "Expected to stay in the same directory with 'cd .'");
  }

  @Test
  public void testForACommandThatDoesNotExist() {

    Result<Pair<String, FileSystemImplementation>> result = cmd.run("remove");
    String actual = result.getErrorMessage();
    String expected = "'remove' is not a recognized command";
    assertEquals(expected, actual, "Expected an error message for an unrecognized command");
  }

  @Test
  public void getRootTest() {

    String actual = fsi.getRoot().name();
    String expected = "/";
    assertEquals(expected, actual, "Expected the root directory to be named '/'");
  }

  @Test
  public void cdWithLessParamsTest() {

    Result<Pair<String, FileSystemImplementation>> result = cmd.run("cd");
    String actual = result.getErrorMessage();
    String expected = "Usage: cd <directory_path>";
    assertEquals(expected, actual, "Expected an error message for missing arguments in 'cd'");
  }

  @Test
  public void testInvalidPathForCd() {
    Result<Pair<String, FileSystemImplementation>> result = cmd.run("cd fake_directory");
    String actual = result.getErrorMessage();
    String expected = "Error: Invalid path: fake_directory";
    assertEquals(expected, actual, "Expected an error message for cd into a non-existent directory");
  }

  @Test
  public void testHandlingOfEmptyCommand() {
    Result<Pair<String, FileSystemImplementation>> result = cmd.run("");
    String actual = result.getErrorMessage();
    String expected = "Empty command";
    assertEquals(expected, actual, "Expected an error message for no command being provided");
  }

  @Test
  public void testDoubleDotForCdInRoot() {
    Result<Pair<String, FileSystemImplementation>> result = cmd.run("cd ..");
    String actual = result.getValue().first();
    String expected = "Moved to directory: '/'";
    assertEquals(expected, actual, "Expected to stay at the root directory when typing 'cd ..' in root");
  }
}
package edu.austral.ingsis;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class FileSystemTests {

  private final FileSystemRunner runner = new FileSystemRunnerImplementation();

  /**
   * Método auxiliar para ejecutar comandos y validar resultados.
   *
   * @param commandsAndResults Lista de comandos y resultados esperados.
   */
  private void executeTest(List<Map.Entry<String, String>> commandsAndResults) {
    List<String> commands = commandsAndResults.stream().map(Map.Entry::getKey).toList();

    List<String> results = runner.executeCommands(commands);

    for (int i = 0; i < commandsAndResults.size(); i++) {
      String command = commandsAndResults.get(i).getKey();
      String expected = commandsAndResults.get(i).getValue();
      String actual = results.get(i);

      assertEquals(expected, actual, "Command: " + command);
    }
  }

  @Test
  public void test1() {

    executeTest(
        List.of(
            entry("mkdir testDir", "'testDir' directory created"),
            entry("touch testFile", "'testFile' file created"),
            entry("ls", "testFile testDir"),
            entry("cd testDir", "Moved to directory: 'testDir'"),
            entry("cd ..", "Moved to directory: '/'"),
            entry("cd .", "Moved to directory: '/'")));
  }

  @Test
  void test2() {

    executeTest(
        List.of(
            entry("", "Error: Empty command"),
            entry("random", "Error: 'random' is not a recognized command"),
            entry("cd", "Error: Usage: cd <directory_path>"),
            entry("cd nonExistentDir", "Error: Error: Invalid path: nonExistentDir"),
            entry("mkdir testDir", "'testDir' directory created"),
            entry("mkdir testDir", "Error: Error: Item already exists: testDir")));
  }

  @Test
  void test3() {

    executeTest(
        List.of(
            entry("mkdir dir1", "'dir1' directory created"),
            entry("mkdir dir2", "'dir2' directory created"),
            entry("cd dir1", "Moved to directory: 'dir1'"),
            entry("touch file1", "'file1' file created"),
            entry("ls", "file1"),
            entry("cd ..", "Moved to directory: '/'"),
            entry("cd dir2", "Moved to directory: 'dir2'"),
            entry("cd ..", "Moved to directory: '/'"),
            entry("ls", "dir2 dir1")));
  }

  @Test
  void test4() {

    executeTest(
        List.of(
            entry("cd ..", "Moved to directory: '/'"),
            entry("mkdir folder", "'folder' directory created"),
            entry("cd folder", "Moved to directory: 'folder'"),
            entry("cd ..", "Moved to directory: '/'"),
            entry("cd ..", "Moved to directory: '/'")));
  }

  @Test
  void test5() {

    executeTest(
        List.of(
            entry("touch item", "'item' file created"),
            entry("mkdir item", "Error: Error: Item already exists: item"),
            entry("mkdir folder", "'folder' directory created"),
            entry("touch folder", "Error: Error: Item already exists: folder"),
            entry("ls", "item folder")));
  }

  @Test
  void test6() {

    executeTest(
        List.of(
            entry("mkdir dirA", "'dirA' directory created"),
            entry("mkdir dirB", "'dirB' directory created"),
            entry("touch fileA", "'fileA' file created"),
            entry("ls", "fileA dirB dirA"),
            entry("cd dirA", "Moved to directory: 'dirA'"),
            entry("cd ..", "Moved to directory: '/'"),
            entry("cd dirB", "Moved to directory: 'dirB'"),
            entry("touch fileB", "'fileB' file created"),
            entry("ls", "fileB")));
  }

  @Test
  void test7() {

    executeTest(
        List.of(
            entry("mkdir projects", "'projects' directory created"),
            entry("cd projects", "Moved to directory: 'projects'"),
            entry("mkdir projectA", "'projectA' directory created"),
            entry("cd /projects", "Moved to directory: 'projects'"),
            entry("touch readme.txt", "'readme.txt' file created"),
            entry("ls", "readme.txt"),
            entry("cd ..", "Moved to directory: '/'"),
            entry("cd..", "Error: 'cd..' is not a recognized command")));
  }

  @Test
  void test8() {
    // Error al intentar sobreescribir root - Ajustado
    executeTest(
        List.of(
            entry("mkdir /", "Error: Directory name cannot contain '/' or spaces"),
            entry("touch /", "Error: File name cannot contain '/' or spaces")));
  }
}

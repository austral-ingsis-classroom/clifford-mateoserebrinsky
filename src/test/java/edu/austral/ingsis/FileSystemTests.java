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
   * @param commandsAndResults Lista de comandos y resultados esperados.
   */
  private void executeTest(List<Map.Entry<String, String>> commandsAndResults) {
    List<String> commands = commandsAndResults.stream()
            .map(Map.Entry::getKey)
            .toList();

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
    // Test básico con mkdir, touch y cd.
    executeTest(List.of(
            entry("mkdir testDir", "'testDir' directory created"),
            entry("touch testFile", "'testFile' file created"),
            entry("ls", "testFile testDir"),
            entry("cd testDir", "Moved to directory: 'testDir'"),
            entry("cd ..", "moved to directory: '/'"),
            entry("cd .", "staying in the current directory: '/'")
    ));
  }

  @Test
  void test2() {
    // Test de comandos inválidos y errores de argumentos
    executeTest(List.of(
            entry("", "Error: no command provided"),
            entry("random", "Error: 'random' is not a recognized command"),
            entry("cd", "Error: not enough arguments for 'cd'"),
            entry("cd nonExistentDir", "Error: 'nonExistentDir' directory does not exist"),
            entry("mkdir testDir", "'testDir' directory created"),
            entry("mkdir testDir", "Error: directory or file 'testDir' already exists")
    ));
  }

  @Test
  void test3() {
    // Test de navegación entre directorios múltiples
    executeTest(List.of(
            entry("mkdir dir1", "'dir1' directory created"),
            entry("mkdir dir2", "'dir2' directory created"),
            entry("cd dir1", "moved to directory: /dir1"),
            entry("touch file1", "'file1' file created"),
            entry("ls", "file1"),
            entry("cd ..", "moved to directory: /"),
            entry("cd dir2", "moved to directory: /dir2"),
            entry("ls", ""),
            entry("cd ..", "moved to directory: /"),
            entry("ls", "dir1 dir2")
    ));
  }

  @Test
  void test4() {
    // Validación de comportamiento especial en la raíz (/)
    executeTest(List.of(
            entry("cd ..", "Error: already at the root directory"),
            entry("mkdir folder", "'folder' directory created"),
            entry("cd folder", "moved to directory: /folder"),
            entry("cd ..", "moved to directory: /"),
            entry("cd ..", "Error: already at the root directory")
    ));
  }

  @Test
  void test5() {
    // Conflictos entre nombres de archivos y directorios
    executeTest(List.of(
            entry("touch item", "'item' file created"),
            entry("mkdir item", "Error: directory or file 'item' already exists"),
            entry("mkdir folder", "'folder' directory created"),
            entry("touch folder", "Error: directory or file 'folder' already exists"),
            entry("ls", "item folder")
    ));
  }

  @Test
  void test6() {
    // Crear múltiples archivos y directorios, y realizar listados (ls)
    executeTest(List.of(
            entry("mkdir dirA", "'dirA' directory created"),
            entry("mkdir dirB", "'dirB' directory created"),
            entry("touch fileA", "'fileA' file created"),
            entry("ls", "dirA dirB fileA"),
            entry("cd dirA", "moved to directory: /dirA"),
            entry("ls", ""),
            entry("cd ..", "moved to directory: /"),
            entry("cd dirB", "moved to directory: /dirB"),
            entry("touch fileB", "'fileB' file created"),
            entry("ls", "fileB")
    ));
  }

  @Test
  void test7() {
    // Prueba de secuencia compleja creando rutas anidadas
    executeTest(List.of(
            entry("mkdir projects", "'projects' directory created"),
            entry("cd projects", "moved to directory: /projects"),
            entry("mkdir projectA", "'projectA' directory created"),
            entry("cd projectA", "moved to directory: /projects/projectA"),
            entry("touch readme.txt", "'readme.txt' file created"),
            entry("ls", "readme.txt"),
            entry("cd ..", "moved to directory: /projects"),
            entry("cd..", "Error: 'cd..' is not a recognized command")
    ));
  }

  @Test
  void test8() {
    // Error específico al intentar sobreescribir root
    executeTest(List.of(
            entry("mkdir /", "Error: cannot create root directory"),
            entry("touch /", "Error: cannot create a file named '/'"),
            entry("ls", "")
    ));
  }
}
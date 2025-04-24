package edu.austral.ingsis;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class FileSystemTests {

  private final FileSystemRunner runner = new FileSystemRunnerImplementation();

  private void executeTest(List<Map.Entry<String, String>> commandsAndResults) {
    final List<String> commands = commandsAndResults.stream().map(Map.Entry::getKey).toList();
    final List<String> expectedResult =
            commandsAndResults.stream().map(Map.Entry::getValue).toList();

    final List<String> actualResult = runner.executeCommands(commands);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void test1() {


    executeTest(
            List.of(
                    entry("ls", "[]"),
                    entry("mkdir horace", "Directory 'horace' added to root"),
                    entry("ls", "[horace]"),
                    entry("mkdir emily", "Directory 'emily' added to root"),
                    entry("ls", "[horace, emily]"),
                    entry("ls --ord=asc", "[emily, horace]")));
  }

  @Test
  void test2() {
    executeTest(
            List.of(
                    entry("mkdir horace", "Directory 'horace' added to root"),
                    entry("mkdir emily", "Directory 'emily' added to root"),
                    entry("mkdir jetta", "Directory 'jetta' added to root"),
                    entry("ls", "[horace, jetta, emily]"),
                    entry("cd emily", "Moved to directory: emily"),
                    entry("pwd", "/root/emily"),
                    entry("touch elizabeth.txt", "'elizabeth.txt' file created"),
                    entry("mkdir t-bone", "Directory 't-bone' added to emily"),
                    entry("ls", "[t-bone, elizabeth.txt]")));
  }

  @Test
  void test3() {
    executeTest(
            List.of(
                    entry("mkdir horace", "Directory 'horace' added to root"),
                    entry("mkdir emily", "Directory 'emily' added to root"),
                    entry("mkdir jetta", "Directory 'jetta' added to root"),
                    entry("cd emily", "Moved to directory: emily"),
                    entry("touch elizabeth.txt", "'elizabeth.txt' file created"),
                    entry("mkdir t-bone", "Directory 't-bone' added to emily"),
                    entry("ls", "[t-bone, elizabeth.txt]"),
                    entry("rm t-bone", "Cannot remove 't-bone', is a directory"),
                    entry("rm --recursive t-bone", "'t-bone' removed"),
                    entry("ls", "[elizabeth.txt]"),
                    entry("rm elizabeth.txt", "'elizabeth.txt' removed"),
                    entry("ls", "[]")));
  }

  @Test
  void test4() {
    executeTest(
            List.of(
                    entry("mkdir horace", "Directory 'horace' added to root"),
                    entry("mkdir emily", "Directory 'emily' added to root"),
                    entry("cd horace", "Moved to directory: horace"),
                    entry("mkdir jetta", "Directory 'jetta' added to horace"),
                    entry("cd ..", "Moved to directory: /root"),
                    entry("cd horace/jetta", "Moved to directory: jetta"),
                    entry("pwd", "/root/horace/jetta"),
                    entry("cd /", "Moved to directory: jetta")));
  }

  @Test
  void test5() {
    executeTest(
            List.of(
                    entry("mkdir emily", "Directory 'emily' added to root"),
                    entry("cd horace", "Directory not found: horace")));
  }

  @Test
  void test6() {
    executeTest(List.of(
            entry("cd ..", "Already at root directory")));
  }

  @Test
  void test7() {
    executeTest(
            List.of(
                    entry("mkdir horace", "Directory 'horace' added to root"),
                    entry("cd horace", "Moved to directory: horace"),
                    entry("touch emily.txt", "'emily.txt' file created"),
                    entry("touch jetta.txt", "'jetta.txt' file created"),
                    entry("ls", "[jetta.txt, emily.txt]"),
                    entry("rm emily.txt", "'emily.txt' removed"),
                    entry("ls", "[jetta.txt]")));
  }

  @Test
  void test8() {
    executeTest(
            List.of(
                    entry("mkdir emily", "Directory 'emily' added to root"),
                    entry("cd emily", "Moved to directory: emily"),
                    entry("mkdir emily", "Directory 'emily' added to emily"),
                    entry("touch emily.txt", "'emily.txt' file created"),
                    entry("touch jetta.txt", "'jetta.txt' file created"),
                    entry("ls", "[jetta.txt, emily.txt, emily]"),
                    entry("rm --recursive emily", "'emily' removed"),
                    entry("ls", "[jetta.txt, emily.txt]"),
                    entry("ls --ord=desc", "[jetta.txt, emily.txt]")));
  }
}

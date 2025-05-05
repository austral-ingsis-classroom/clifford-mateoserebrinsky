package edu.austral.ingsis.clifford;

public class Main {
  public static void main(String[] args) {
    FileSystemImplementation fs = new FileSystemImplementation();
    Terminal terminal = new Terminal(fs);

    System.out.println("==File System implementation==");

    terminal.run("mkdir mateo");
    terminal.run("touch fotos");
    terminal.run("ls");
    terminal.run("cd mateo");
    terminal.run("mkdir mateo2");
    terminal.run("cd ..");
    terminal.run("cd .");
    terminal.run("touch fotos2");
    terminal.run("ls");
    terminal.run("rm --recursive mateo");
    terminal.run("ls");
    terminal.run("rm fotos");
    terminal.run("ls");
    terminal.run("pwd");
    terminal.run("cd /mateo/mateo2");
  }
}

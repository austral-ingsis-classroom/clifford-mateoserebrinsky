package edu.austral.ingsis;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.Terminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String expected = cmd.run("cd .");
        String actual = "Staying in current directory: root";
        assertEquals(expected, actual);
    }

    @Test
    public void testForACommandThatDoesNotExist() {
        String expected = cmd.run("remove");
        String actual = "'remove' is not a recognized command";
        assertEquals(expected, actual);
    }
    @Test
    public void getRootTest() {
        String actual = fsi.getRoot().getName();
        String expected = "root";
        assertEquals(expected, actual);
    }

    @Test
    public void cdWithLessParamsTest() {
        String actual = cmd.run("cd");
        String expected = "Not enough arguments for 'cd'";
        assertEquals(expected, actual);

    }
}

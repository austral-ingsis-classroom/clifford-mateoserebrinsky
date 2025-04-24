package edu.austral.ingsis;

import edu.austral.ingsis.clifford.CommandParser;
import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.Terminal;
import edu.austral.ingsis.clifford.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class FileSystemRunnerImplementation implements FileSystemRunner {

    @Override
    public List<String> executeCommands(List<String> commands) {
        FileSystemImplementation fileSystem = new FileSystemImplementation();
        Terminal cmd = new Terminal(fileSystem);
        List<String> commandList = new ArrayList<>();
        for (String command : commands) {
            String result = cmd.run(command);
            commandList.add(result);
        }
        return commandList;
    }
}

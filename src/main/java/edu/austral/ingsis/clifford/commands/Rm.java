package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystemImplementation;
import edu.austral.ingsis.clifford.Pair;
import edu.austral.ingsis.clifford.fileSystem.Directory;
import edu.austral.ingsis.clifford.fileSystem.FileSystemItems;
import edu.austral.ingsis.clifford.result.Result;

public final class Rm implements Command {
    private final String target;
    private final boolean recursive;

    public Rm(String target, boolean recursive) {
        if (target == null || target.isBlank()) {
            throw new IllegalArgumentException("Target cannot be null or empty");
        }
        this.target = target;
        this.recursive = recursive;
    }

    @Override
    public Result<Pair<String, FileSystemImplementation>> execute(FileSystemImplementation fs) {
        try {

            if (!fs.itemExists(target)) {
                return new Result.Error<>("Error: Item not found - " + target);
            }


            FileSystemItems item = fs.resolveItem(target);

            if (item.isDirectory()) {
                Directory directory = (Directory) item;
                if (!recursive && !directory.isEmpty()) {
                    return new Result.Error<>("Error: Cannot remove non-empty directory without using the --recursive option");
                }
            }
            FileSystemImplementation newFs = fs.remove(target, recursive);
            String output = String.format("'%s' successfully removed", target);

            return new Result.Success<>(new Pair<>(output, newFs));
        } catch (Exception e) {
            return new Result.Error<>("Error removing item: " + e.getMessage());
        }
    }
}
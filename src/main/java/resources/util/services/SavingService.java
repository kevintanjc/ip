package resources.util.services;

import resources.util.datastorage.Checklist;
import resources.util.tasks.Task;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static resources.util.constants.BotConstants.FILE_PATH;

public class SavingService extends Service {
    String filePath = FILE_PATH;
    Checklist checklist;

    @Override
    protected void executeService() throws IOException {
        Path path = Paths.get(filePath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("Your saved tasks:");
            writer.newLine();
            writer.newLine();
            for (int i = 0; i < checklist.getSize(); i++) {
                writer.write(checklist.getTaskByIndex(i).toString());
                writer.newLine();
            }
        }
        endService();
    }

    @Override
    protected void startService() throws IOException {
        System.out.println("Saving tasks to: " + filePath);
        executeService();
    }

    @Override
    protected void endService() {
        System.out.println("Tasks saved successfully! Shutting down...");
    }

    public SavingService(Checklist checklist) throws IOException {
        this.checklist = checklist;
        startService();
    }
}

package resources.util.services;

import resources.util.datastorage.Checklist;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static resources.util.constants.BotConstants.FILE_PATH;

/**
 * Service class responsible for saving tasks from a checklist to a file.
 * <p>
 * The {@link SavingService} writes tasks from a {@link Checklist} object to a specified filepath.
 * It ensures that the tasks are saved in a readable format, with each task on a new line.
 * <p>
 * Usage:
 * <pre>
 *     SavingService savingService = new SavingService(checklist);
 * </pre>
 * <p>
 * The {@link SavingService} class extends {@link Service} class and provides implementations for starting and ending
 * the service.
 *
 * @see Checklist
 *
 * @author Kevin Tan
 */
public class SavingService extends Service {
    String filePath = FILE_PATH;
    Checklist checklist;

    /**
     * Executes the saving service by writing tasks from the {@link Checklist} to a storage file.
     * <p>
     * This method creates or overwrites the specified file and writes each task from the checklist
     * into the file, ensuring that each task is on a new line. It also includes a header indicating
     * that these are the saved tasks.
     *
     * @throws IOException if an I/O error occurs while writing to the file.
     */
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

    /**
     * Starts the saving service by initializing the file path and executing the service.
     * <p>
     * This method prints a message indicating the file path where tasks will be saved,
     * and then calls the {@link #executeService()} method to perform the actual saving of tasks.
     *
     * @throws IOException if an I/O error occurs while starting the service.
     */
    @Override
    protected void startService() throws IOException {
        System.out.println("Saving tasks to: " + filePath);
        executeService();
    }

    /**
     * Ends the saving service by printing a confirmation message.
     * <p>
     * This method is called after the tasks have been successfully saved to the file,
     * and it notifies the user that the tasks have been saved.
     */
    @Override
    protected void endService() {
        System.out.println("Tasks saved successfully! Shutting down...");
    }

    public SavingService(Checklist checklist) throws IOException {
        this.checklist = checklist;
        startService();
    }
}

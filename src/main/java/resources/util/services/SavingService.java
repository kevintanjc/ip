package resources.util.services;

import static resources.util.constants.BotConstants.FILE_PATH;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import resources.util.datastorage.CheckList;

/**
 * Service class responsible for saving tasks from a checklist to a file.
 * <p>
 * The {@link SavingService} writes tasks from a {@link CheckList} object to a specified filepath.
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
 * @see CheckList
 *
 */
public class SavingService extends Service {
    private String filePath = FILE_PATH;
    private CheckList checkList;
    /**
     * Constructs a SavingService instance with the specified checklist.
     * @param checklist The {@link CheckList} containing tasks to be saved.
     */
    public SavingService(CheckList checklist) {
        this.checkList = checklist;
        startService();
    }
    /**
     * Executes the saving service by writing tasks from the {@link CheckList} to a storage file.
     * <p>
     * This method creates or overwrites the specified file and writes each task from the checklist
     * into the file, ensuring that each task is on a new line. It also includes a header indicating
     * that these are the saved tasks.
     * @return An empty string as no specific output is required after saving.
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    @Override
    public String executeService(String input) throws IOException {
        Path path = Paths.get(filePath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("Your saved tasks:");
            writer.newLine();
            writer.newLine();
            for (int i = 0; i < checkList.getSize(); i++) {
                writer.write(checkList.getTaskByIndex(i).toString());
                writer.newLine();
            }
        }
        return "";
    }
    /**
     * Starts the saving service by initializing the file path and executing the service.
     * <p>
     * This method prints a message indicating the file path where tasks will be saved.
     * @return A message indicating the file path for saving tasks.
     * @throws IOException if an I/O error occurs while starting the service.
     */
    @Override
    public String startService() {
        return "Saving tasks to: " + filePath;
    }
    /**
     * Ends the saving service by printing a confirmation message.
     * <p>
     * This method is called after the tasks have been successfully saved to the file,
     * and it notifies the user that the tasks have been saved.
     * @return A confirmation message indicating that tasks have been saved.
     */
    @Override
    public String endService() {
        return "Tasks saved successfully! Shutting down...";
    }
}

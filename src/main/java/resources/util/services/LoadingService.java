package resources.util.services;

import static resources.util.constants.BotConstants.EVENT_TASK_TYPE;
import static resources.util.constants.BotConstants.FILE_PATH;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import resources.util.datastorage.CheckList;
import resources.util.parsers.DateTimeUtil;
import resources.util.tasks.DeadlineTask;
import resources.util.tasks.EventTask;
import resources.util.tasks.Task;
import resources.util.tasks.ToDosTask;

/**
 * Service class responsible for loading tasks from a file into a checklist.
 * <p>
 * The {@link LoadingService} reads tasks from a specified file, parses them, and adds them to a Checklist object.
 * It supports different types of tasks including EventTask, ToDosTask, and DeadlineTask.
 * <p>
 * Usage:
 * <pre>
 *     LoadingService loadingService = new LoadingService();
 *     CheckList checkList = loadingService.getChecklist();
 * </pre>
 *
 * <p>
 * The {@link LoadingService} class extends {@link Service} class and provides implementations for starting and ending
 * the service.
 *
 * @author Kevin Tan
 * @see CheckList
 * @see Task
 * @see EventTask
 * @see ToDosTask
 * @see DeadlineTask
 */
public class LoadingService extends Service {
    private Scanner scanner;
    private CheckList checklist;
    /**
     * Constructs a LoadingService instance and initializes the scanner to read from the specified file.
     * @throws IOException if an I/O error occurs while accessing the file.
     */
    public LoadingService() throws IOException {
        Path filePath = Paths.get(FILE_PATH);
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        scanner = new Scanner(Files.newBufferedReader(filePath));
        startService();
    }
    /**
     * Executes the loading service by reading tasks from a storage file and adding them to the {@link CheckList}.
     * <p>
     * This method reads each line from the storage file, parses it into a {@link Task} object,
     * and adds it to the {@link CheckList}.
     * If the checklist is empty after loading, it notifies the user.
     * @return A message indicating the result of the loading operation.
     * @throws IOException if an I/O error occurs while reading the file.
     */
    @Override
    public String executeService(String... inputs) {
        checklist = new CheckList();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("[")) {
                checklist.addTask(parseLineToTask(line));
            }
        }
        if (checklist == null || checklist.isEmpty()) {
            return "There are no tasks in your checklist.";
        } else {
            return "Tasks have been loaded successfully\n" + "type 'list' to view your tasks.";
        }
    }
    /**
     * Starts the loading service.
     * @return A message indicating that the loading service has started.
     * @throws IOException if an I/O error occurs during service startup.
     */
    @Override
    public String startService() {
        return "Loading tasks from file...";
    }
    /**
     * Ends the loading service by closing the scanner resource.
     * @return A message indicating that the loading service has ended.
     */
    @Override
    public String endService() {
        scanner.close();
        return "Loading service ended.";
    }
    /**
     * Parses a {@link String} line from the file and converts it into a corresponding Task object.
     *
     * @param line the {@code String} from the file representing a task.
     * @return the Task object created from the line.
     * @throws IllegalArgumentException if the task type is invalid.
     */
    private Task parseLineToTask(String line) throws IllegalArgumentException {
        String taskType = line.substring(0, 3);
        if (taskType.equals(EVENT_TASK_TYPE)) {
            return createEventTask(line);
        } else if (taskType.equals("[T]")) {
            return createToDoTask(line);
        } else if (taskType.equals("[D]")) {
            return createDeadlineTask(line);
        } else {
            throw new IllegalArgumentException("Invalid task type: " + taskType);
        }
    }
    /**
     * Creates an EventTask from a given line.
     * @param line  the {@code String} from the file representing an EventTask.
     * @return {@link EventTask} - the created EventTask object.
     */
    private EventTask createEventTask(String line) {
        String[] parts = line.split(" \\(from: | to: |\\)");
        String description = parts[0].substring(7);
        String startDate = parts[1];
        String endDate = formatDate(parts[2]);
        boolean isCompleted = checkCompletedTask(parts[0].substring(3));
        EventTask output = new EventTask(description, DateTimeUtil.convertFormattedStringDateToLocalDate(startDate),
                DateTimeUtil.convertFormattedStringDateToLocalDate(endDate));
        if (isCompleted) {
            output.setCompleted();
        }
        return output;
    }
    /**
     * Creates a ToDosTask from a given line.
     * @param line  the {@code String} from the file representing a ToDosTask.
     * @return {@link ToDosTask} - the created ToDosTask object.
     */
    private ToDosTask createToDoTask(String line) {
        String[] parts = line.split("(?<!\\[)\\s+(?!\\])");
        String description = parts[1];
        boolean isCompleted = checkCompletedTask(parts[0].substring(3));
        ToDosTask output = new ToDosTask(description);
        if (isCompleted) {
            output.setCompleted();
        }
        return output;
    }
    /**
     * Creates a DeadlineTask from a given line.
     * @param line  the {@code String} from the file representing a DeadlineTask.
     * @return {@link DeadlineTask} - the created DeadlineTask object.
     */
    private Task createDeadlineTask(String line) {
        String[] parts = line.split(" \\(by: ");
        String description = parts[0].substring(7);
        String endDate = formatDate(parts[1]);
        boolean isCompleted = checkCompletedTask(parts[0].substring(3));
        DeadlineTask output = new DeadlineTask(description,
                DateTimeUtil.convertFormattedStringDateToLocalDate(endDate));
        if (isCompleted) {
            output.setCompleted();
        }
        return output;
    }
    /**
     * Checks if a task is completed based on its status string.
     * @param status    the symbol {@link String} of the task (e.g., "[X]" for completed, "[ ]" for not completed).
     * @return {@code boolean} - true if the task is completed, false otherwise.
     */
    private boolean checkCompletedTask(String status) {
        return status.equals("[X]");
    }
    /**
     * Formats a date string by removing parentheses.
     * @param date  the {@code String} representing a date with parentheses.
     * @return {@code String} - the formatted date string without parentheses.
     */
    private String formatDate(String date) {
        return date.replaceAll("[()]", "");
    }
    /**
     * Gets the checklist containing the loaded tasks.
     *
     * @return {@code Checklist} — the checklist with loaded tasks.
     */
    public CheckList getChecklist() {
        return checklist;
    }
}

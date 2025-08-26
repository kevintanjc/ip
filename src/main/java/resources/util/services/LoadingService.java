package resources.util.services;

import resources.util.tasks.DeadlineTask;
import resources.util.tasks.EventTask;
import resources.util.tasks.Task;
import resources.util.tasks.ToDosTask;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static resources.util.constants.BotConstants.EVENT_TASK_TYPE;
import static resources.util.constants.BotConstants.FILE_PATH;

public class LoadingService extends Service {

    private Scanner scanner;
    private List<Task> checklist;

    @Override
    protected void executeService() throws IOException {
        checklist = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("[")) {
                checklist.add(parseLineToTask(line));
            }
        }

        if (checklist == null || checklist.isEmpty()) {
            System.out.println("There are no tasks in your checklist.");
        } else {
            System.out.println("Tasks have been loaded successfully\n" + "type 'list' to view your tasks.");
            }
    }


    @Override
    protected void startService() throws IOException {
        System.out.println("Loading tasks from file...");
        executeService();
    }

    @Override
    protected void endService() {}

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

    private EventTask createEventTask(String line) {
        String[] parts = line.split("(?<!\\[)\\s+(?!\\])");
        String description = parts[1];
        String startDate = parts[3];
        String endDate = formatDate(parts[5]);
        boolean completed = checkCompletedTask(parts[0].substring(3));
        EventTask output = new EventTask(description, startDate, endDate);
        if (completed) {
            output.setCompleted();
        }
        return output;
    }

    private ToDosTask createToDoTask(String line) {
        String[] parts = line.split("(?<!\\[)\\s+(?!\\])");
        String description = parts[1];
        boolean completed = checkCompletedTask(parts[0].substring(3));
        ToDosTask output = new ToDosTask(description);
        if (completed) {
            output.setCompleted();
        }
        return output;
    }

    private Task createDeadlineTask(String line) {
        String[] parts = line.split("(?<!\\[)\\s+(?!\\])");
        String description = parts[1];
        String endDate = formatDate(parts[3]);
        boolean completed = checkCompletedTask(parts[0].substring(3));
        DeadlineTask output = new DeadlineTask(description, endDate);
        if (completed) {
            output.setCompleted();
        }
        return output;
    }

    private boolean checkCompletedTask(String status) {
        return status.equals("[X]");
    }

    private String formatDate(String date) {
        return date.replaceAll("[()]", "");
    }

    public List<Task> getChecklist() {
        return checklist;
    }

    public LoadingService() throws IOException {
        scanner = new Scanner(Files.newBufferedReader(Paths.get(FILE_PATH)));
        startService();
    }
}

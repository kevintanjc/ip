package resources.util.services;

import static resources.util.constants.BotConstants.DEADLINE_TASK_DESCRIPTION;
import static resources.util.constants.BotConstants.DELETE_COMMAND;
import static resources.util.constants.BotConstants.EVENT_TASK_DESCRIPTION;
import static resources.util.constants.BotConstants.EXIT_COMMAND;
import static resources.util.constants.BotConstants.FIND_COMMAND;
import static resources.util.constants.BotConstants.INDENT;
import static resources.util.constants.BotConstants.LIST_COMMAND;
import static resources.util.constants.BotConstants.MARK_COMMAND;
import static resources.util.constants.BotConstants.TODO_TASK_DESCRIPTION;
import static resources.util.constants.BotConstants.UNMARK_COMMAND;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import resources.util.datastorage.CheckList;
import resources.util.parsers.DateTimeUtil;
import resources.util.tasks.DeadlineTask;
import resources.util.tasks.EventTask;
import resources.util.tasks.Task;
import resources.util.tasks.ToDosTask;

/**
 * Represents the main service of the bot application.
 * <p>
 * The BotService class extends {@link Service} class and provides implementations for starting and ending
 * the service. It handles user input, processes commands, and manages a checklist of tasks.
 *
 * @author Kevin Tan
 */
public class BotService extends Service {
    private CheckList checkList;
    /**
     * Executes the main service loop, handling user input and processing commands.
     * <p>
     * The method continuously reads user input from the console, processes commands such as adding tasks,
     * marking tasks as completed, unmarking tasks, deleting tasks, and listing all tasks. The loop continues
     * until the user inputs the exit command.
     *
     * @throws IllegalStateException    if an invalid task type is provided.
     * @throws NullPointerException     if task creation fails due to null values.
     * @throws IndexOutOfBoundsException if an invalid task index is provided for marking or unmarking.
     * @throws IOException              if an I/O error occurs during service execution.
     */
    @Override
    public String executeService(String input) {
        String command = input.split(" ")[0];
        int taskType = getTask(input.split(" ")[0]);
        String output = "";

        if (command.equals(EXIT_COMMAND)) {
            output = endService();
        } else if (command.equals(LIST_COMMAND)) {
            output = checkList.printTasks();
        } else if (input.length() >= 6 && command.equals(MARK_COMMAND)) {
            try {
                Integer index = Integer.parseInt(input.split(" ")[1]) - 1;
                if (index < input.length()) {
                    output = checkList.markTask(index);
                } else {
                    output = INDENT + "Please provide a valid task number to mark.";
                }
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid task number format! Unable to parse as an integer.");
            }
        } else if (input.length() >= 8 && command.equals(UNMARK_COMMAND)) {
            try {
                Integer index = Integer.parseInt(input.split(" ")[1]) - 1;
                if (index < input.length()) {
                    output = checkList.unmarkTask(index);
                } else {
                    output = INDENT + "Please provide a valid task number to unmark.";
                }
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid task number format! Unable to parse as an integer.");
            }
        } else if (command.equals(DELETE_COMMAND)) {
            output = checkList.removeTaskByIndex(Integer.parseInt(input.split(" ")[1]) - 1);
        } else if (command.equals(FIND_COMMAND)) {
            output = checkList.searchAndPrintTasks(input.split(" ")[1]);
        } else {
            output = insertTaskIntoChecklist(taskType, input, checkList);
        }
        new SavingService(checkList);
        return output;
    }

    /**
     * Determines the type of {@link Task} based on the input string.
     *
     * @param str the input String representing the task command.
     * @return {@code Integer} - 1 for To-Do, 2 for Deadline, 3 for Event, -1 for invalid task type.
     */
    private Integer getTask(String str) {
        if (str.length() >= 4) {
            if (str.equals(TODO_TASK_DESCRIPTION)) {
                return 1;
            } else if (str.equals(DEADLINE_TASK_DESCRIPTION)) {
                return 2;
            } else if (str.equals(EVENT_TASK_DESCRIPTION)) {
                return 3;
            } else {
                return -1;
            }
        }
        return -1;
    }

    /**
     * Inserts {@link EventTask}, {@link ToDosTask} or {@link DeadlineTask} into the checklist
     * based on certain conditions.
     *
     * @param taskFlag  an Integer representing the type of task (1 for To-Do, 2 for Deadline, 3 for Event).
     * @param inputString the input String containing the task details.
     * @param checkList the Checklist object to which the task will be added.
     * @throws IllegalStateException    if an invalid task type is provided.
     * @throws NullPointerException     if task creation fails due to null values.
     */
    private String insertTaskIntoChecklist(Integer taskFlag, String inputString, CheckList checkList)
            throws IllegalStateException, NullPointerException {
        if (taskFlag == -1) {
            throw new IllegalStateException("Invalid task type! Please use 'todo', 'deadline', or 'event'.");
        }
        if (taskFlag == 1) {
            return checkList.addTask(initializeToDoTask(inputString));
        } else if (taskFlag == 2) {
            return checkList.addTask(initializeDeadlineTask(inputString));
        } else if (taskFlag == 3) {
            return checkList.addTask(initializeEventTask(inputString));
        } else {
            return "Task creation failed! Please check your input.";
        }
    }

    /**
     * Initializes a {@link ToDosTask} from an input String.
     *
     * @param inputStr the input String containing the task details.
     * @return {@code ToDosTask} - the initialized ToDosTask object.
     * @throws IllegalStateException if the task description is empty.
     */
    private ToDosTask initializeToDoTask(String inputStr) throws IllegalStateException {
        String description = inputStr.substring(5);
        ToDosTask task = new ToDosTask(description);
        if (description.isEmpty()) {
            throw new IllegalStateException("To-Do task description cannot be empty!");
        }
        return task;
    }

    /**
     * Initializes a {@link DeadlineTask} from an input String.
     *
     * @param inputStr the input String containing the task details.
     * @return {@code DeadlineTask} - the initialized DeadlineTask object.
     * @throws IllegalStateException    if the task description is empty or format is invalid.
     * @throws DateTimeParseException if the date format is invalid.
     */
    private DeadlineTask initializeDeadlineTask(String inputStr) throws IllegalStateException, DateTimeParseException {
        String[] parts = inputStr.substring(9).split(" /by ");
        for (String str : parts) {
            if (str.contains("/by")) {
                throw new IllegalStateException("Invalid format for Deadline task!"
                        + "Use 'deadline <description> /by <date>'.");
            }
        }
        if (parts.length == 0) {
            throw new IllegalStateException("Deadline task description cannot be empty!");
        } else if (parts.length == 1) {
            DeadlineTask task = new DeadlineTask(parts[0], null);
            System.out.println(INDENT + "Thanks for letting me know! I have added:\n"
                    + INDENT + task);
            return task;
        } else if (parts.length == 2) {
            DeadlineTask task = new DeadlineTask(parts[0], DateTimeUtil.convertStringToLocalDate(parts[1]));
            System.out.println(INDENT + "Thanks for letting me know! I have added:\n"
                    + INDENT + task);
            return task;
        } else {
            throw new IllegalStateException("Invalid format for Deadline task!"
                    + "Use 'deadline <description> /by <date>'.");
        }
    }

    /**
     * Initializes an {@link EventTask} from an input String.
     *
     * @param inputStr the input String containing the task details.
     * @return {@code EventTask} - the initialized EventTask object.
     * @throws IllegalStateException    if the task description is empty or format is invalid.
     * @throws DateTimeParseException if the date format is invalid.
     */
    private EventTask initializeEventTask(String inputStr) throws IllegalStateException, DateTimeParseException {
        String[] eventParts = inputStr.substring(6).split(" /from | /to ");
        for (String str : eventParts) {
            if (str.contains("/from") || eventParts[0].contains("/to")) {
                throw new IllegalStateException("Invalid format for Event task!"
                        + "Use 'event <description> /from <start date> /to <end date>'.");
            }
        }
        if (eventParts.length == 0) {
            throw new IllegalStateException("Event task description cannot be empty!");
        } else if (eventParts.length == 1) {
            return new EventTask(eventParts[0].trim(), null, null);
        } else if (eventParts.length == 2) {
            return new EventTask(eventParts[0].trim(), DateTimeUtil.convertStringToLocalDate(eventParts[1]),
                    null);
        } else if (eventParts.length == 3) {
            return new EventTask(eventParts[0].trim(),
                    DateTimeUtil.convertStringToLocalDate(eventParts[1]),
                    DateTimeUtil.convertStringToLocalDate(eventParts[2]));
        } else {
            throw new IllegalStateException("Invalid format for Event task! Use 'event <description>"
                    + "/from <start date> /to <end date>'.");
        }
    }

    /**
     * Starts the bot service by loading the {@link CheckList} and initiating the command execution loop.
     *
     * @throws IOException if an I/O error occurs during service startup.
     */
    @Override
    public String startService() {
        try {
            LoadingService load = new LoadingService();
            load.startService();
            load.executeService("");
            load.endService();
            this.checkList = load.getChecklist();
            return "Hello! I'm JavaBot\n" + "What can I do for you?";
        } catch (IOException e) {
            return "Error loading checklist: " + e.getMessage();
        }
    }

    /**
     * Ends the bot service by displaying a farewell message.
     */
    @Override
    public String endService() {
        return "See you next time!";
    }
}

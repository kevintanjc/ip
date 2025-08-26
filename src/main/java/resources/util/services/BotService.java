package resources.util.services;

import resources.util.datastorage.Checklist;
import resources.util.parsers.DateTimeUtil;
import resources.util.tasks.DeadlineTask;
import resources.util.tasks.EventTask;
import resources.util.tasks.ToDosTask;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static resources.util.constants.BotConstants.DEADLINE_TASK_DESCRIPTION;
import static resources.util.constants.BotConstants.DELETE_COMMAND;
import static resources.util.constants.BotConstants.EVENT_TASK_DESCRIPTION;
import static resources.util.constants.BotConstants.EXIT_COMMAND;
import static resources.util.constants.BotConstants.FIND_COMMAND;
import static resources.util.constants.BotConstants.INDENT;
import static resources.util.constants.BotConstants.LINE_SEPARATOR;
import static resources.util.constants.BotConstants.LIST_COMMAND;
import static resources.util.constants.BotConstants.MARK_COMMAND;
import static resources.util.constants.BotConstants.TODO_TASK_DESCRIPTION;
import static resources.util.constants.BotConstants.UNMARK_COMMAND;

public class BotService extends Service {

    private Scanner scanner;
    private Checklist checklist;

    protected void executeService() throws IllegalStateException, NullPointerException,
            IndexOutOfBoundsException, IOException {
        scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            String command = input.split(" ")[0];
            int taskType = getTask(input.split(" ")[0]);

            if (command.equals(EXIT_COMMAND)) {
                break;
            } else if (command.equals(LIST_COMMAND)) {
                checklist.printTasks();
            } else if (input.length() >= 6 && command.equals(MARK_COMMAND)) {
                try {
                    Integer index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index < input.length()) {
                        checklist.markTask(index);
                    } else {
                        System.out.println(INDENT + "Please provide a valid task number to mark.");
                    }
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid task number format! Unable to parse as an integer.");
                }
            } else if (input.length() >= 8 && command.equals(UNMARK_COMMAND)) {
                try {
                    Integer index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index < input.length()) {
                        checklist.unmarkTask(index);
                    } else {
                        System.out.println(INDENT + "Please provide a valid task number to unmark.");
                    }
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid task number format! Unable to parse as an integer.");
                }
            } else if (command.equals(DELETE_COMMAND)) {
                checklist.removeTaskByIndex(Integer.parseInt(input.split(" ")[1]) - 1);
            } else if (command.equals(FIND_COMMAND)) {
                checklist.searchAndPrintTasks(input.split(" ")[1]);
            } else {
                    insertTaskIntoChecklist(taskType, input, checklist);
            }
        }

        scanner.close();
        endService();
        new SavingService(checklist);
    }

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

    private void insertTaskIntoChecklist(Integer taskFlag, String inputString, Checklist checklist)
            throws IllegalStateException, NullPointerException {
        if (taskFlag == -1) {
            throw new IllegalStateException("Invalid task type! Please use 'todo', 'deadline', or 'event'.");
        }
        if (taskFlag == 1) {
            checklist.addTask(initializeToDoTask(inputString));
        } else if (taskFlag == 2) {
            checklist.addTask(initializeDeadlineTask(inputString));
        } else if (taskFlag == 3) {
            checklist.addTask(initializeEventTask(inputString));
        } else {
            throw new NullPointerException("Task creation failed! Please check your input.");
        }
    }

    private ToDosTask initializeToDoTask(String inputStr) throws IllegalStateException {
        String description = inputStr.substring(5);
        ToDosTask task = new ToDosTask(description);
        if (description.isEmpty()) {
            throw new IllegalStateException("To-Do task description cannot be empty!");
        }
        System.out.println(INDENT + "Thanks for letting me know! I have added:\n"
                + INDENT + task.toString());
        return task;
    }

    private DeadlineTask initializeDeadlineTask(String inputStr) throws IllegalStateException, DateTimeParseException {
        String[] parts = inputStr.substring(9).split(" /by ");
        for (String str : parts) {
            if (str.contains("/by")) {
                throw new IllegalStateException("Invalid format for Deadline task! Use 'deadline <description> /by <date>'.");
            }
        }
        if (parts.length == 0) {
            throw new IllegalStateException("Deadline task description cannot be empty!");
        } else if (parts.length == 1) {
            DeadlineTask task = new DeadlineTask(parts[0], null);
            System.out.println(INDENT + "Thanks for letting me know! I have added:\n"
                    + INDENT + task.toString());
            return task;
        } else if (parts.length == 2) {
            DeadlineTask task = new DeadlineTask(parts[0], DateTimeUtil.convertStringToLocalDate(parts[1]));
            System.out.println(INDENT + "Thanks for letting me know! I have added:\n"
                    + INDENT + task.toString());
            return task;
        } else {
            throw new IllegalStateException("Invalid format for Deadline task! Use 'deadline <description> /by <date>'.");
        }
    }

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
            return new EventTask(eventParts[0].trim(), DateTimeUtil.convertStringToLocalDate(eventParts[1]), null);
        } else if (eventParts.length == 3) {
            return new EventTask(eventParts[0].trim(),
                    DateTimeUtil.convertStringToLocalDate(eventParts[1]),
                    DateTimeUtil.convertStringToLocalDate(eventParts[2]));
        } else {
            throw new IllegalStateException("Invalid format for Event task! Use 'event <description>"
                    + "/from <start date> /to <end date>'.");
        }
    }

    @Override
    protected void startService() throws IOException {
        LoadingService load = new LoadingService();
        this.checklist = load.getChecklist();
        System.out.println(LINE_SEPARATOR + "\n" + "Hello! I'm JavaBot\n" + "What can I do for you?\n");
        executeService();
    }

    @Override
    protected void endService() {
        System.out.println("See you next time!\n" + LINE_SEPARATOR);
    }

    public BotService() throws IOException {
        startService();
    }
}

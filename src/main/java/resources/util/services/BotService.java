package resources.util.services;

import resources.util.tasks.DeadlineTask;
import resources.util.tasks.EventTask;
import resources.util.tasks.Task;
import resources.util.tasks.ToDosTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.Objects.isNull;
import static resources.util.constants.BotConstants.DEADLINE_TASK_DESCRIPTION;
import static resources.util.constants.BotConstants.DELETE_COMMAND;
import static resources.util.constants.BotConstants.EVENT_TASK_DESCRIPTION;
import static resources.util.constants.BotConstants.EXIT_COMMAND;
import static resources.util.constants.BotConstants.INDENT;
import static resources.util.constants.BotConstants.LINE_SEPARATOR;
import static resources.util.constants.BotConstants.LIST_COMMAND;
import static resources.util.constants.BotConstants.MARK_COMMAND;
import static resources.util.constants.BotConstants.NO_DATE_GIVEN;
import static resources.util.constants.BotConstants.TODO_TASK_DESCRIPTION;
import static resources.util.constants.BotConstants.UNMARK_COMMAND;

public class BotService extends Service {

    private Scanner scanner;
    private List<Task> checklist;

    protected void executeService() throws IllegalStateException, NullPointerException, IndexOutOfBoundsException, IOException {
        scanner = new Scanner(System.in);
        checklist = new ArrayList<>();

        while (true) {
            String input = scanner.nextLine();
            String command = input.split(" ")[0];
            int taskType = getTask(input.split(" ")[0]);

            if (command.equals(EXIT_COMMAND)) {
                break;
            } else if (command.equals(LIST_COMMAND)) {
                listTasks();
            } else if (input.length() >= 6 && command.equals(MARK_COMMAND)) {
                try {
                    Integer index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index < input.length()) {
                        markTask(index);
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
                        unmarkTask(index);
                    } else {
                        System.out.println(INDENT + "Please provide a valid task number to unmark.");
                    }
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid task number format! Unable to parse as an integer.");
                }
            } else if (command.equals(DELETE_COMMAND)) {
                deleteTaskFromChecklist(Integer.parseInt(input.split(" ")[1]) - 1);
            } else {
                insertTaskIntoChecklist(taskType, input, checklist);
            }
        }

        scanner.close();
        endService();
        new SavingService(checklist);
    }

    private void listTasks() {
        if (checklist.isEmpty()) {
            System.out.println(INDENT + "Uh oh...You currently have no tasks in your list! Add some tasks to get started!");
        }
        System.out.println(INDENT + "Certainly! Here are your inputs thus far:");
        for (int i = 0; i < checklist.size(); i++) {
            System.out.println(INDENT + (i + 1) + ". " + checklist.get(i).toString());
        }
        System.out.printf(INDENT + "You currently have %d items in your list!%n", checklist.size());
    }

    private void markTask(int index) {
        Task task = checklist.get(index);
        if (task.isCompleted()) {
            System.out.println(INDENT + "The following task has already been marked as done: " + task.getDescription());
        } else {
            task.setCompleted();
            System.out.println(INDENT + "Well done! I'll mark it as done for you.\n" + INDENT
                    + task);
        }
    }

    private void unmarkTask(int index) {
        Task task = checklist.get(index);
        if (!task.isCompleted()) {
            System.out.println(INDENT + "The following task has already been marked as not done: " + task.getDescription());
        } else {
            task.setCompleted();
            System.out.println(INDENT + "Okay! I'll mark it as not done for you.\n" + INDENT
                    + task);
        }
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

    private void insertTaskIntoChecklist(Integer taskFlag, String inputString, List<Task> checklist) throws IllegalStateException, NullPointerException {
        if (taskFlag == -1) {
            throw new IllegalStateException("Invalid task type! Please use 'todo', 'deadline', or 'event'.");
        }

        Task tasking = null;

        switch(taskFlag) {
        case 1: // To-Do task
            String description = inputString.substring(5);
            if (description.isEmpty()) {
                throw new IllegalStateException("To-Do task description cannot be empty!");
            }
            tasking = new ToDosTask(description);
            break;

        case 2: // Deadline task
            String[] parts = inputString.substring(9).split(" /by ");
            for (String str : parts) {
                if (str.contains("/by")) {
                    throw new IllegalStateException("Invalid format for Deadline task! Use 'deadline <description> /by <date>'.");
                }
            }
            if (parts.length == 0) {
                throw new IllegalStateException("Deadline task description cannot be empty!");
            } else if (parts.length == 1) {
                tasking = new DeadlineTask(parts[0], NO_DATE_GIVEN);
            } else if (parts.length == 2) {
                tasking = new DeadlineTask(parts[0], parts[1]);
            } else {
                throw new IllegalStateException("Invalid format for Deadline task! Use 'deadline <description> /by <date>'.");
            }
            break;

        case 3: // Event task
            String[] eventParts = inputString.substring(6).split(" /from | /to ");
            for (String str : eventParts) {
                if (str.contains("/from") || eventParts[0].contains("/to")) {
                    throw new IllegalStateException("Invalid format for Event task! Use 'event <description> /from <start date> /to <end date>'.");
                }
            }
            if (eventParts.length == 0) {
                throw new IllegalStateException("Event task description cannot be empty!");
            } else if (eventParts.length == 1) {
                tasking = new EventTask(eventParts[0].trim(), NO_DATE_GIVEN, NO_DATE_GIVEN);
            } else if (eventParts.length == 2) {
                tasking = new EventTask(eventParts[0].trim(), eventParts[1], NO_DATE_GIVEN);
            } else if (eventParts.length == 3) {
                tasking = new EventTask(eventParts[0].trim(), eventParts[1], eventParts[2]);
            } else {
                throw new IllegalStateException("Invalid format for Event task! Use 'event <description> /from <start date> /to <end date>'.");
            }
            break;
        }

        if (isNull(tasking)) {
            throw new NullPointerException("Task creation failed! Please check your input.");
        }
        checklist.add(tasking);
        System.out.println(INDENT + "Thanks for letting me know! I have added:\n"
                + INDENT + tasking.toString());
    }

    private void deleteTaskFromChecklist(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= checklist.size()) {
            throw new IndexOutOfBoundsException("Invalid task number! Please provide a valid task number to delete.");
        }
        Task removedTask = checklist.remove(index);
        System.out.println(INDENT + "Roger. The following task is removed:\n"
                + INDENT + removedTask.toString() + "\n" + INDENT + "You now have " + checklist.size() + " tasks in your list.");
    }

    @Override
    protected void startService() throws IOException {
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

package resources.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static resources.util.BotConstants.*;

public class BotService {

    private Scanner scanner;
    private List<Task> checklist;

    public void executeService() {
        scanner = new Scanner(System.in);
        checklist = new ArrayList<>();

        while (true) {
            String input = scanner.nextLine();
            int taskType = getTask(input.split(" ")[0]);

            if (input.equals(EXIT_COMMAND)) {
                break;
            } else if (input.equals(LIST_COMMAND)) {
                listTasks();
            } else if (input.length() >= 6 && input.substring(0, 4).equals(MARK_COMMAND)) {
                Integer index = Integer.parseInt(input.substring(5)) - 1;
                if (index < input.length()) {
                    markTask(index);
                } else {
                    System.out.println(INDENT + "Please provide a valid task number to mark.");
                }
            } else if (input.length() >= 8 && input.substring(0, 6).equals(UNMARK_COMMAND)) {
                Integer index = Integer.parseInt(input.substring(7)) - 1;
                if (index < input.length()) {
                    unmarkTask(index);
                } else {
                    System.out.println(INDENT + "Please provide a valid task number to unmark.");
                }
            } else {
                insertTaskIntoChecklist(taskType, input, checklist);
            }
        }

        scanner.close();
        endBotService();
    }

    private void listTasks() {
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
                    + COMPLETED_SYMBOL + " " + task.getDescription());
        }
    }

    private void unmarkTask(int index) {
        Task task = checklist.get(index);
        if (!task.isCompleted()) {
            System.out.println(INDENT + "The following task has already been marked as not done: " + task.getDescription());
        } else {
            task.setCompleted();
            System.out.println(INDENT + "Okay! I'll mark it as not done for you.\n" + INDENT + INCOMPLETE_SYMBOL
                    + " " + task.getDescription());
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

    private void insertTaskIntoChecklist(Integer taskFlag, String inputString, List<Task> checklist) throws IllegalStateException {
        if (taskFlag == -1) {
            System.out.println(INDENT + "I'm sorry, I don't understand that command.");
        }

        Task tasking = null;

        switch(taskFlag) {
            case 1: // To-Do task
                tasking = new ToDosTask(inputString.substring(5));
                break;
            case 2: // Deadline task
                String[] parts = inputString.substring(9).split(" /by ");
                if (parts.length < 2) {
                    tasking = new DeadlineTask(parts[0], NO_DATE_GIVEN);
                } else {
                    tasking = new DeadlineTask(parts[0], parts[1]);
                }
                break;
            case 3: // Event task
                String[] eventParts = inputString.substring(6).split(" /from | /to ");
                if (eventParts.length < 2) {
                    tasking = new EventTask(eventParts[0].trim(), NO_DATE_GIVEN, NO_DATE_GIVEN);
                } else if (eventParts.length < 3) {
                    tasking = new EventTask(eventParts[0].trim(), eventParts[1], NO_DATE_GIVEN);
                } else {
                    tasking = new EventTask(eventParts[0].trim(), eventParts[1], eventParts[2]);
                }
                break;
        }

        checklist.add(tasking);
        System.out.println(INDENT + "Thanks for letting me know! I have added:\n"
                + INDENT + tasking.toString());
    }


    private void startBotService() {
        System.out.println(LINE_SEPARATOR + "\n" + "Hello! I'm JavaBot\n" + "What can I do for you?\n");
    }

    private void endBotService() {
        System.out.println("See you next time!\n" + LINE_SEPARATOR);
    }

    public BotService() {
        startBotService();
    }
}

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
                Task tasking = new Task(input);
                checklist.add(tasking);
                System.out.println(INDENT + "Added: " + tasking.getDescription());
            }
        }

        scanner.close();
        endBotService();
    }

    private void listTasks() {
        System.out.println("Certainly! Here are your inputs thusfar:\n");
        for (int i = 0; i < checklist.size(); i++) {
            if (checklist.get(i).isCompleted()) {
                System.out.println(INDENT + (i + 1) + ". [X] " + checklist.get(i).getDescription());
            } else {
                System.out.println(INDENT + (i + 1) + ". [ ] " + checklist.get(i).getDescription());
            }
        }
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



    private void startBotService() {
        System.out.println(LINE_SEPARATOR + "\n" + "Hello! I'm JavaBot\n" + "What can I do for you?\n");
    }

    private void endBotService() {
        System.out.println("See you next time!\n" + LINE_SEPARATOR + "\n");
    }

    public BotService() {
        startBotService();
    }
}

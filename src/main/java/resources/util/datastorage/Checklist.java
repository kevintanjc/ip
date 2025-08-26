package resources.util.datastorage;

import resources.util.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static resources.util.constants.BotConstants.INDENT;

public class Checklist {

    private List<Task> checklist;

    public Checklist() {
        this.checklist = new ArrayList<>();
    }

    public void addTask(Task task) {
        checklist.add(task);
    }

    public void removeTaskByIndex(int index) {
        if (index < 0 || index >= this.getSize()) {
            throw new IndexOutOfBoundsException("Invalid task number! Please provide a valid task number to delete.");
        }
        Task removedTask = checklist.remove(index);
        System.out.println(INDENT + "Roger. The following task is removed:\n"
                + INDENT + removedTask.toString() + "\n" + INDENT + "You now have " + this.getSize() + " tasks in your list.");
    }

    public Task getTaskByIndex(int index) {
        return checklist.get(index);
    }

    public boolean isEmpty() {
        return checklist.isEmpty();
    }

    public int getSize() {
        return checklist.size();
    }

    public void printTasks() {
        if (checklist.isEmpty()) {
            System.out.println(INDENT + "Uh oh...You currently have no tasks in your list! Add some tasks to get started!");
        }
        System.out.println(INDENT + "Certainly! Here are your inputs thus far:");
        for (int i = 0; i < checklist.size(); i++) {
            System.out.println(INDENT + (i + 1) + ". " + checklist.get(i).toString());
        }
        System.out.printf(INDENT + "You currently have %d items in your list!%n", this.getSize());
    }

    public void markTask(int index) {
        Task task = checklist.get(index);
        if (task.isCompleted()) {
            System.out.println(INDENT + "The following task has already been marked as done: " + task.getDescription());
        } else {
            task.setCompleted();
            System.out.println(INDENT + "Well done! I'll mark it as done for you.\n" + INDENT
                    + task);
        }
    }

    public void unmarkTask(int index) {
        Task task = checklist.get(index);
        if (!task.isCompleted()) {
            System.out.println(INDENT + "The following task has already been marked as not done: " + task.getDescription());
        } else {
            task.setCompleted();
            System.out.println(INDENT + "Okay! I'll mark it as not done for you.\n" + INDENT
                    + task);
        }
    }

}

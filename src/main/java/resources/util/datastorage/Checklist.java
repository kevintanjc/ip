package resources.util.datastorage;

import resources.util.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static resources.util.constants.BotConstants.INDENT;

/**
 * Represents a checklist that manages a list of tasks.
 * <p>
 * The {@link Checklist} class provides methods to handle and manipulate a list of {@link Task} objects.
 *
 * @author Kevin Tan
 */
public class Checklist {

    private List<Task> checklist;

    public Checklist() {
        this.checklist = new ArrayList<>();
    }

    /**
     * Adds a task to the checklist.
     *
     * @param task The {@link Task} to be added to the checklist.
     */
    public void addTask(Task task) {
        checklist.add(task);
    }

    /**
     * Removes a task from the checklist by its index.
     *
     * @param index The index of the task to be removed (0-indexed).
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public void removeTaskByIndex(int index) {
        if (index < 0 || index >= this.getSize()) {
            throw new IndexOutOfBoundsException("Invalid task number! Please provide a valid task number to delete.");
        }
        Task removedTask = checklist.remove(index);
        System.out.println(INDENT + "Roger. The following task is removed:\n"
                + INDENT + removedTask.toString() + "\n" + INDENT + "You now have " + this.getSize() + " tasks in your list.");
    }

    /**
     * Retrieves a task from the checklist by its index.
     *
     * @param index The index of the task to be retrieved (0-indexed).
     * @return The {@link Task} at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public Task getTaskByIndex(int index) {
        return checklist.get(index);
    }

    /**
     * Checks if the checklist is empty.
     *
     * @return {@code true} if the checklist is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return checklist.isEmpty();
    }

    /**
     * Gets the number of tasks in the checklist.
     *
     * @return The size of the checklist.
     */
    public int getSize() {
        return checklist.size();
    }

    /**
     * Prints all tasks in the checklist to the console.
     * <p>
     * If the checklist is empty, it notifies the user.
     */
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

    /**
     * Marks a task as completed by its index.
     *
     * @param index The index of the task to be marked as completed (0-indexed).
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
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

    /**
     * Unmarks a task as not completed by its index.
     *
     * @param index The index of the task to be unmarked (0-indexed).
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
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

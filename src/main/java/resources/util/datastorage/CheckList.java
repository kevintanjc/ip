package resources.util.datastorage;

import static resources.util.constants.BotConstants.INDENT;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import resources.util.tasks.Task;

/**
 * Represents a checklist that manages a list of tasks.
 * <p>
 * The {@link CheckList} class provides methods to handle and manipulate a list of {@link Task} objects.
 * @author Kevin Tan
 */
public class CheckList {
    private List<Task> checkList;
    /**
     * Constructs an empty checklist.
     */
    public CheckList() {
        this.checkList = new ArrayList<>();
    }
    /**
     * Adds a task to the checklist.
     * @param task The {@link Task} to be added to the checklist.
     * @return A confirmation message indicating the task has been added.
     */
    public String addTask(Task task) {
        checkList.add(task);
        checkList.sort(Comparator.comparing(Task::getDescription));
        return INDENT + "Thanks for letting me know! I have added:\n" + INDENT + task;
    }
    /**
     * Removes a task from the checklist by its index.
     * @param index The index of the task to be removed (0-indexed).
     * @return A confirmation message indicating the task has been removed.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public String removeTaskByIndex(int index) {
        if (index < 0 || index >= this.getSize()) {
            throw new IndexOutOfBoundsException("Invalid task number! Please provide a valid task number to delete.");
        }
        Task removedTask = checkList.remove(index);
        return INDENT + "Roger. The following task is removed:\n" + INDENT + removedTask.toString() + "\n" + INDENT
                + "You now have " + this.getSize() + " tasks in your list.";
    }
    /**
     * Retrieves a task from the checklist by its index.
     * @param index The index of the task to be retrieved (0-indexed).
     * @return The {@link Task} at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public Task getTaskByIndex(int index) {
        return checkList.get(index);
    }
    /**
     * Checks if the checklist is empty.
     * @return {@code true} if the checklist is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return checkList.isEmpty();
    }
    /**
     * Gets the number of tasks in the checklist.
     * @return The size of the checklist.
     */
    public int getSize() {
        return checkList.size();
    }
    /**
     * Displays all tasks in the checklist with their respective indices.
     * If the checklist is empty, it notifies the user accordingly.
     * @return A formatted string listing all tasks or a message indicating the checklist is empty.
     */
    public String displayTasks() {
        StringBuilder output = new StringBuilder();
        if (checkList.isEmpty()) {
            output.append(INDENT + "Uh oh...You currently have no tasks in your list! Add some tasks to get started!");
        }
        output.append(INDENT + "Certainly! Here are your inputs thus far:\n");
        for (int i = 0; i < checkList.size(); i++) {
            output.append(INDENT).append(i + 1).append(". ").append(checkList.get(i).toString()).append("\n");
        }
        output.append(INDENT + "You currently have ").append(this.getSize()).append(" items in your list!");
        return output.toString();
    }
    /**
     * Marks the task at the specified index as completed.
     * @param index The index of the task to be marked as completed (0-based).
     * @return A confirmation message indicating the task has been marked as completed.
     */
    public String markTask(int index) {
        Task task = checkList.get(index);
        if (task.isCompleted()) {
            return INDENT + "The following task has already been marked as done: " + task.getDescription();
        } else {
            task.setCompleted();
            return INDENT + "Well done! I'll mark it as done for you.\n" + INDENT + task;
        }
    }
    /**
     * Marks the task at the specified index as not completed.
     * @param index The index of the task to be marked as not completed (0-based).
     * @return A confirmation message indicating the task has been marked as not completed.
     */
    public String unmarkTask(int index) {
        Task task = checkList.get(index);
        if (!task.isCompleted()) {
            return INDENT + "The following task has already been marked as not done: " + task.getDescription();
        } else {
            task.setCompleted();
            return INDENT + "Okay! I'll mark it as not done for you.\n" + INDENT + task;
        }
    }
    /**
     * Searches for tasks containing the specified keyword and prints them.
     * @param keyword The keyword to search for in task descriptions.
     * @return A formatted string listing all matching tasks or a message indicating no matches were found.
     */
    public String searchAndPrintTasks(String keyword) {
        List<Task> matchedTasks = new ArrayList<>();
        StringBuilder output = new StringBuilder();
        for (Task task : checkList) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchedTasks.add(task);
            }
        }
        if (matchedTasks.isEmpty()) {
            output.append(INDENT + "No matching tasks found for the keyword: " + keyword);
        } else {
            output.append(INDENT + "Here are the matching tasks in your list:");
            for (int i = 0; i < matchedTasks.size(); i++) {
                output.append(INDENT + (i + 1) + ". " + matchedTasks.get(i).toString());
            }
        }
        return output.toString();
    }
}

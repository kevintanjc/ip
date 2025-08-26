package resources.util.tasks;

import resources.util.constants.BotConstants;

/**
 * Represents a basic task.
 * <p>
 * A Task object contains a description of the task and a boolean flag indicating whether the task is completed.
 *
 * @author Kevin Tan
 */
public class Task {

    private String description;
    private boolean completed;

    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    /**
     * Gets the description of this task.
     *
     * @return {@code String} — the description of the task.
     */
    public String getDescription() {
        return description;
    }

    /** Gets whether the task is completed.
     *
     * @return {@code boolean} — true if the task is completed, false otherwise.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Toggles the completion status of the task.
     * If the task is completed, it will be marked as incomplete, and vice versa.
     *
     * @return
     */
    public void setCompleted() {
        completed = !completed;
    }

    /**
     * Returns a String representation of the task which includes its description and completion as a symbol.
     * <p>
     * The completion symbol is "[✗]" if the task is completed and "[ ]" if it is not.
     * <p>
     * Example: "[ ] read book" or "[✗] write code"
     *
     * @return {@code String} — a string representation of the task.
     */
    @Override
    public String toString() {
        String symbol = completed ? BotConstants.Symbol.COMPLETED.toString() : BotConstants.Symbol.INCOMPLETE.toString();
        return String.format("%s %s", symbol, description);
    }
}

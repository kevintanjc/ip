package resources.util.tasks;

import static resources.util.constants.BotConstants.TODO_TASK_TYPE;

/**
 * Represents a to-do task which has a description.
 * <p>
 * A ToDosTask object contains a description of the task.
 * It extends the {@link Task} class to represent a basic to-do task.
 *
 * @author Kevin Tan
 */
public class ToDosTask extends Task {

    private static final String TASK_TYPE = TODO_TASK_TYPE;

    public ToDosTask(String description) {
        super(description);
    }

    /**
     * Returns a String representation of the task which includes its description, completion as a symbol.
     * <p>
     * Example: "[T][ ] read book"
     *
     * @return {@code String} — a string representation of ToDosTask.
     */
    @Override
    public String toString() {
        return String.format("%s%s", TASK_TYPE, super.toString());
    }
}

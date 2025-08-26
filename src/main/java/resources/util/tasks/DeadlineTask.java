package resources.util.tasks;

import resources.util.parsers.DateTimeUtil;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static resources.util.constants.BotConstants.DEADLINE_TASK_TYPE;
import static resources.util.constants.BotConstants.NO_DATE_GIVEN;

/**
 * Represents a deadline task which has a description and an end date.
 * <p>
 * A DeadlineTask object contains a description of the task and an end date.
 * It extends the {@link Task} class to include a deadline for the task.
 *
 * @author Kevin Tan
 */
public class DeadlineTask extends Task {

    private LocalDateTime endDate;

    private static final String TASK_TYPE = DEADLINE_TASK_TYPE;

    public DeadlineTask(String description, LocalDateTime endDate) {
        super(description);
        this.endDate = endDate;
    }

    /**
     * Returns a String representation of the task which includes its description, completion as a symbol,
     * and end date.
     * <p>
     * Example: "[D][ ] read book (by: Jan 01 1990, 12:00 pm)"
     *
     * @return {@code String} — a string representation of DeadlineTask.
     */
    @Override
    public String toString() {
        String formattedEndDate = isNull(endDate)
                ? NO_DATE_GIVEN
                : DateTimeUtil.convertLocalDateToFormattedString(endDate);

        return String.format("%s%s (by: %s)", TASK_TYPE, super.toString(), formattedEndDate);
    }
}

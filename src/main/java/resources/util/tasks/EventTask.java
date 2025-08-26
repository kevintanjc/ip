package resources.util.tasks;

import java.time.LocalDateTime;
import resources.util.parsers.DateTimeUtil;

import static java.util.Objects.isNull;
import static resources.util.constants.BotConstants.EVENT_TASK_TYPE;
import static resources.util.constants.BotConstants.NO_DATE_GIVEN;

/**
 * Represents an event task which has a description, start date, and end date.
 * <p>
 * An EventTask object contains a description of the task, a start date, and an end date.
 * It extends the {@link Task} class to include start and end timings of a given event.
 *
 * @author Kevin Tan
 */
public class EventTask extends Task {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private static final String TASK_TYPE = EVENT_TASK_TYPE;

    public EventTask(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns a String representation of the task which includes its description, completion as a symbol,
     * start date, and end date.
     * <p>
     * Example: "[E][ ] read book (from: Jan 01 1990, 12:00 pm to: Jan 01 1990, 1:00 pm)"
     *
     * @return {@code String} — a string representation of EventTask.
     */
    @Override
    public String toString() {
        String formattedStartDate = isNull(startDate)
                ? NO_DATE_GIVEN
                : DateTimeUtil.convertLocalDateToFormattedString(startDate);
        String formattedEndDate = isNull(endDate)
                ? NO_DATE_GIVEN
                : DateTimeUtil.convertLocalDateToFormattedString(endDate);

        return String.format("%s%s (from: %s to: %s)", TASK_TYPE, super.toString(), formattedStartDate, formattedEndDate);
    }
}

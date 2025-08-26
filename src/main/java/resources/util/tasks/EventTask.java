package resources.util.tasks;

import java.time.LocalDateTime;
import resources.util.parsers.DateTimeUtil;

import static java.util.Objects.isNull;
import static resources.util.constants.BotConstants.EVENT_TASK_TYPE;
import static resources.util.constants.BotConstants.NO_DATE_GIVEN;

public class EventTask extends Task {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private static final String TASK_TYPE = EVENT_TASK_TYPE;

    public EventTask(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

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

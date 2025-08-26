package resources.util.tasks;

import resources.util.parsers.DateTimeUtil;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static resources.util.constants.BotConstants.DEADLINE_TASK_TYPE;
import static resources.util.constants.BotConstants.NO_DATE_GIVEN;

public class DeadlineTask extends Task {

    private LocalDateTime endDate;

    private static final String TASK_TYPE = DEADLINE_TASK_TYPE;

    public DeadlineTask(String description, LocalDateTime endDate) {
        super(description);
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        String formattedEndDate = isNull(endDate)
                ? NO_DATE_GIVEN
                : DateTimeUtil.convertLocalDateToFormattedString(endDate);

        return String.format("%s%s (by: %s)", TASK_TYPE, super.toString(), formattedEndDate);
    }
}

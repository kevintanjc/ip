package resources.util;

import static resources.util.BotConstants.DEADLINE_TASK_TYPE;

public class DeadlineTask extends Task {

    private String endDate;

    private static final String TASK_TYPE = DEADLINE_TASK_TYPE;

    public DeadlineTask(String description, String endDate) {
        super(description);
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return String.format("%s%s (by: %s)", TASK_TYPE, super.toString(), endDate);
    }
}

package resources.util;

public class EventTask extends Task {

    private String startDate;

    private String endDate;

    private static final String TASK_TYPE = "[E]";

    public EventTask(String description, String startDate, String endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return String.format("%s%s (from: %s to %s)", TASK_TYPE, super.toString(), startDate, endDate);
    }
}

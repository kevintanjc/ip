package resources.util;

public class ToDosTask extends Task {

    private static final String TASK_TYPE = "[T]";

    public ToDosTask(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return String.format("%s%s", TASK_TYPE, super.toString());
    }
}

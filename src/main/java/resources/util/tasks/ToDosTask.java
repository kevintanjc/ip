package resources.util.tasks;

import static resources.util.constants.BotConstants.TODO_TASK_TYPE;

public class ToDosTask extends Task {

    private static final String TASK_TYPE = TODO_TASK_TYPE;

    public ToDosTask(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return String.format("%s%s", TASK_TYPE, super.toString());
    }
}

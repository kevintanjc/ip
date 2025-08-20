package resources.util;

public class Task {

    private String description;
    private boolean completed;

    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() {
        completed = !completed;
    }

    public String toString() {
        String symbol = completed ? BotConstants.COMPLETED_SYMBOL : BotConstants.INCOMPLETE_SYMBOL;
        return String.format("%s %s", symbol, description);
    }
}

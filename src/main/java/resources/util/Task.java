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
        String symbol = completed ? BotConstants.Symbol.COMPLETED.toString() : BotConstants.Symbol.INCOMPLETE.toString();
        return String.format("%s %s", symbol, description);
    }
}

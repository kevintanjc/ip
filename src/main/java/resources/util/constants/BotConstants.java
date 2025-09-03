package resources.util.constants;

/**
 * Contains constant values used throughout the bot application.
 * <p>
 * The {@code BotConstants} class provides a centralized location for defining
 * constant values such as command keywords, file paths, and formatting strings.
 * This helps maintain consistency and makes it easier to manage these values
 * across the application.
 *
 * @author Kevin Tan
 */
public abstract class BotConstants {

    public static final String INDENT = "    ";

    public static final String EXIT_COMMAND = "bye";

    public static final String LIST_COMMAND = "list";

    public static final String MARK_COMMAND = "mark";

    public static final String UNMARK_COMMAND = "unmark";

    public static final String DELETE_COMMAND = "delete";

    public static final String FIND_COMMAND = "find";

    public static final String TODO_TASK_TYPE = "[T]";

    public static final String DEADLINE_TASK_TYPE = "[D]";

    public static final String EVENT_TASK_TYPE = "[E]";

    public static final String TODO_TASK_DESCRIPTION = "todo";

    public static final String DEADLINE_TASK_DESCRIPTION = "deadline";

    public static final String EVENT_TASK_DESCRIPTION = "event";

    public static final String NO_DATE_GIVEN = "I DUNNO >.<";

    public static final String FILE_PATH = "src/data/checklist.txt";

    public enum Symbol {
        COMPLETED("[X]"),
        INCOMPLETE("[ ]");

        private final String symbol;

        Symbol(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }
    }
}

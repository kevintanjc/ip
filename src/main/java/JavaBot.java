import resources.util.services.BotService;

/**
 * This serves as the entry point for the JavaBot application.
 * <p>
 * This class initializes the BotService which handles the core functionality of the bot.
 */
public class JavaBot {
    public static void main(String[] args) {
        try {
            new BotService();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

}
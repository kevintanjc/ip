import resources.util.services.BotService;

public class JavaBot {
    public static void main(String[] args) {
        try {
            new BotService();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

}
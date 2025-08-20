import java.util.Scanner;
import static resources.util.BotConstants.*;

public class JavaBot {
    public static void main(String[] args) {
        System.out.println(
                LINE_SEPARATOR + "\n"
                + "Hello! I'm JavaBot\n"
                + "What can I do for you?\n"
        );

        executeService();

        System.out.println(
                "See you next time!\n"
                + LINE_SEPARATOR + "\n");
    }

    public static void executeService() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals(EXIT_COMMAND)) {
                break;
            } else {
                System.out.println(input);
            }
        }
    }
}
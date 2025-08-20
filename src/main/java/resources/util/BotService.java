package resources.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static resources.util.BotConstants.*;

public class BotService {

    private Scanner scanner;
    private List<String> userInputs;

    public void executeService() {
        scanner = new Scanner(System.in);
        userInputs = new ArrayList<>();

        while (true) {
            String input = scanner.nextLine();
            if (input.equals(EXIT_COMMAND)) {
                break;
            } else if (input.equals(LIST_COMMAND)) {
                System.out.println("Certainly! Here are your inputs thusfar:\n");
                for (int i = 0; i < userInputs.size(); i++) {
                    System.out.println(INDENT + (i + 1) + ". " + userInputs.get(i));
                }
            } else {
                userInputs.add(input);
                System.out.println(INDENT + "Added: " + input);
            }
        }

        scanner.close();
        endBotService();
    }

    private void startBotService() {
        System.out.println(LINE_SEPARATOR + "\n"
                + "Hello! I'm JavaBot\n"
                + "What can I do for you?\n"
        );
    }

    private void endBotService() {
        System.out.println("See you next time!\n"
                + LINE_SEPARATOR + "\n"
        );
    }

    public BotService() {
        startBotService();
    }
}

package app;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    /**
     * Launches the JavaBot application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Application.launch(JavaBot.class, args);
    }
}

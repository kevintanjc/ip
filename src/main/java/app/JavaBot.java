package app;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resources.ui.windows.MainWindow;
import resources.util.services.BotService;

/**
 * This serves as the entry point for the JavaBot application.
 * <p>
 * This class initializes the BotService which handles the core functionality of the bot.
 */
public class JavaBot extends Application {
    private BotService botService = new BotService();
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(JavaBot.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            ap.getStyleClass().add("main-window");
            Scene scene = new Scene(ap);
            scene.getStylesheets().add(
                    Objects.requireNonNull(
                            JavaBot.class.getResource("/styles/styles.css")).toExternalForm());
            fxmlLoader.<MainWindow>getController().setBotService(botService);
            stage.setTitle("Java Bot");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

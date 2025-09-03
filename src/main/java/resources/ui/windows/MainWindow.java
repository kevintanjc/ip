package resources.ui.windows;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import resources.ui.boxes.DialogBox;
import resources.util.services.BotService;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    private BotService botService;
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/bart.png"));
    private final Image robotImage = new Image(this.getClass().getResourceAsStream("/images/robot.png"));
    /**
     * Initializes the main window.
     * This method is automatically called after the FXML file has been loaded.
     * Sets up the scroll pane to always scroll to the bottom when new content is added.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getStyleClass().add("scroll-pane");
    }
    /**
     * Sets the bot service for this controller.
     * Also displays the opening message from the bot.
     * @param s The bot service to be used by this controller.
     */
    public void setBotService(BotService s) {
        botService = s;
        String greeting = s.startService();
        DialogBox openingBox = DialogBox.getBotDialog(greeting, robotImage);
        openingBox.getStyleClass().add("bot-dialog");
        dialogContainer.getChildren().add(openingBox);
    }
    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     * If the user input the exit command, the application will exit after a short delay.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = botService.executeService(input);
        DialogBox userBox = DialogBox.getUserDialog(input, userImage);
        DialogBox botBox = DialogBox.getBotDialog(response, robotImage);
        userBox.getStyleClass().add("user-dialog");
        botBox.getStyleClass().add("bot-dialog");
        dialogContainer.getChildren().addAll(userBox, botBox);
        userInput.clear();

        if ("bye".equalsIgnoreCase(input.trim())) {
            // Close after 2 seconds so the goodbye is visible
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}

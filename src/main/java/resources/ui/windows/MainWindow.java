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

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getStyleClass().add("scroll-pane");
    }

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

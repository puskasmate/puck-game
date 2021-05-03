package puckgame.javafx.controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * The controller class for the launch scene.
 */
@Slf4j
public class LaunchController {

    @FXML
    private TextField p1name;

    @FXML
    private TextField p2name;

    @FXML
    private Button startButton;

    @FXML
    private ImageView image;

    /**
     * The initialize method that is called when the scene is opened.
     */
    @FXML
    public void initialize() {
        startButton.disableProperty().bind(
                Bindings.isEmpty(p1name.textProperty())
                .or(Bindings.isEmpty(p2name.textProperty()))
                .or(Bindings.equal(p1name.textProperty(), p2name.textProperty()))
        );
        image.setImage(new Image(getClass().getResource("/images/pucks.png").toExternalForm()));
    }

    /**
     * A method that is called when a player clicks on start button.
     * @param actionEvent the current action event
     * @throws IOException if the wanted scene can not be found
     */
    @FXML
    public void startGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
        Parent root = loader.load();
        GameController gameController = loader.<GameController>getController();
        gameController.setPlayersName(p1name.getText(), p2name.getText());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("The names has been set to {} and {}, loading game scene", p1name.getText(), p2name.getText());
    }

    /**
     * A method that is called when a player clicks on the exit button.
     * @param actionEvent the current action event
     */
    public void exitGame(ActionEvent actionEvent) {
        log.debug("{} button is pressed", ((Button)actionEvent.getSource()).getText());
        log.info("Exiting...");
        Platform.exit();
    }

    /**
     * A method that is called when a player clicks on the high scores button.
     * @param actionEvent the current action event
     * @throws IOException if the wanted scene can not be found
     */
    public void handleHighScoreButton(ActionEvent actionEvent) throws IOException {
        log.debug("{} button has been pressed.", ((Button)actionEvent.getSource()).getText());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/highscores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Loading highscores..");
    }

}

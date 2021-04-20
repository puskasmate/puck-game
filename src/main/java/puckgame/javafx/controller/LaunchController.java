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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class LaunchController {

    @FXML
    private Pane pane;

    @FXML
    private TextField p1name;

    @FXML
    private TextField p2name;

    @FXML
    private Button startButton;

    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
        startButton.disableProperty().bind(
                Bindings.isEmpty(p1name.textProperty())
                .or(Bindings.isEmpty(p2name.textProperty()))
        );
    }

    @FXML
    public void startGame(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void exitGame() {
        Platform.exit();
    }

}

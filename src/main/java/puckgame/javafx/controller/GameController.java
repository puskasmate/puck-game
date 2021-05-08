package puckgame.javafx.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import puckgame.results.GameResult;
import puckgame.results.GameResultDao;
import puckgame.state.GameState;
import puckgame.state.Player;
import puckgame.javafx.Puck;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * The controller class for the game scene.
 */
@Slf4j
public class GameController {

    @FXML
    private Pane pane;

    @FXML
    private Text p1nameText;

    @FXML
    private Text p2nameText;

    @FXML
    private Text p1steps;

    @FXML
    private Text p2steps;

    @FXML
    private Label winnerLabel;

    @FXML
    private Label stopWatchLabel;

    private GameResultDao gameResultDao;

    private int size = 600;
    private int spots = 5;
    private int squareSize = size / spots;
    private ArrayList<Puck> pucks;
    private GameState gameState;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private int prevX;
    private int prevY;
    private boolean gameOver;
    private String p1name;
    private String p2name;
    private Instant startTime;
    private Timeline stopWatchTimeline;

    /**
     * The initialize method that runs when the scene is opened.
     */
    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            p1nameText.setText(p1name);
            p2nameText.setText(p2name);
            player1.setName(p1name);
            player2.setName(p2name);
        });
        initGame();
    }

    /**
     * A method that creates and runs the stopwatch.
     */
    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }

    /**
     * A method that initialize the starting state.
     */
    private void initGame() {
        p1nameText.setFill(Color.GREEN);
        p2nameText.setFill(Color.BLACK);
        startTime = Instant.now();
        createStopWatch();
        gameOver = false;
        prevX = 0;
        prevY = 0;
        gameState = new GameState();
        player1 = new Player(p1nameText.getText(), 1, 0);
        player2 = new Player(p2nameText.getText(), 2, 0);
        p1steps.setText("Steps: " + player1.getStepCount());
        p2steps.setText("Steps: " + player2.getStepCount());
        winnerLabel.setText("");
        gameState.setBluePlayer(player1);
        gameState.setRedPlayer(player2);
        currentPlayer = player1;
        displayGrid();
    }

    /**
     * A method that draws the board to the scene.
     */
    private void displayGrid() {
        for (int i = 0; i < size; i += squareSize) {
            for (int j = 0; j < size; j += squareSize) {
                Rectangle r = new Rectangle(i, j, squareSize, squareSize);
                r.setFill(Color.WHITE);
                r.setStroke(Color.BLACK);
                pane.getChildren().addAll(r);
            }
        }
        pucks = new ArrayList<>();
        for (int i = 0; i < gameState.getGrid().length; i++) {
            for (int j = 0; j < gameState.getGrid().length; j++) {
                if (gameState.getGrid()[j][i] == 1) {
                    Circle c = new Circle();
                    c.setFill(Color.BLUE);
                    double radius = squareSize / 3.0;
                    int x = squareSize / 2 + squareSize * (0+i);
                    int y = squareSize / 2 + squareSize * (0+j);
                    Puck puck = new Puck(x, y, radius, c);
                    pucks.add(puck);
                    setListeners(c, puck);
                    pane.getChildren().add(c);
                    puck.draw();
                }
                else {
                    if (gameState.getGrid()[j][i] == 2) {
                        Circle c = new Circle();
                        c.setFill(Color.RED);
                        double radius = squareSize / 3.0;
                        int x = squareSize / 2 + squareSize * (0+i);
                        int y = squareSize / 2 + squareSize * (0+j);
                        Puck puck = new Puck(x, y, radius, c);
                        pucks.add(puck);
                        setListeners(c, puck);
                        pane.getChildren().add(c);
                        puck.draw();
                    }
                }
            }
        }
    }

    /**
     * A method that switches the current player after he/she took a step.
     */
    private void switchCurrentPlayer() {
        if (currentPlayer.equals(player1)) {
            currentPlayer = player2;
            this.p2nameText.setFill(Color.GREEN);
            this.p1nameText.setFill(Color.BLACK);
        }
        else {
            currentPlayer = player1;
            this.p1nameText.setFill(Color.GREEN);
            this.p2nameText.setFill(Color.BLACK);
        }
    }

    /**
     * A method that sets listeners to a puck.
     * @param c the circle on the scene
     * @param puck the puck object that will be moved
     */
    private void setListeners(Circle c, Puck puck) {
        c.setOnMousePressed(mouseEvent -> mousePressed(mouseEvent, puck));
        c.setOnMouseDragged(mouseEvent -> mouseDragged(mouseEvent, puck));
        c.setOnMouseReleased(mouseEvent -> mouseReleased(mouseEvent, puck));
    }

    /**
     * A mousePressed method that is invoked when the mouse is pressed on a puck.
     * @param mouseEvent the current mouse event
     * @param puck the puck that has been pressed
     */
    private void mousePressed(MouseEvent mouseEvent, Puck puck) {
        if (!gameOver) {
            prevX = (int) puck.getX() / squareSize;
            prevY = (int) puck.getY() / squareSize;
        }
    }

    /**
     * A mouseDragged method that is invoked when the mouse is dragged on a puck.
     * @param mouseEvent the current mouse event
     * @param puck the puck that has been dragged
     */
    private void mouseDragged(MouseEvent mouseEvent, Puck puck) {
        if (!gameOver) {
            puck.setX(puck.getX() + mouseEvent.getX());
            puck.setY(puck.getY() + mouseEvent.getY());
            puck.draw();
        }
    }

    /**
     * A mouseReleased method that is invoked when a puck is being released.
     * @param mouseEvent the current mouse event
     * @param puck the puck that has been dragged and released
     */
    private void mouseReleased(MouseEvent mouseEvent, Puck puck) {
        if (!gameOver) {
            int gridX = (int) puck.getX() / squareSize;
            int gridY = (int) puck.getY() / squareSize;
            int dir;
            if (gridX == prevX + 1 && gridY == prevY) {
                dir = 0;
            } else {
                if (gridX == prevX - 1 && gridY == prevY) {
                    dir = 1;
                } else {
                    if (gridY == prevY - 1 && gridX == prevX) {
                        dir = 2;
                    } else {
                        if (gridY == prevY + 1 && gridX == prevX) {
                            dir = 3;
                        } else {
                            dir = -1;
                        }
                    }
                }
            }
            if (puck.getX() < 0 || puck.getY() < 0) {
                dir = -2;
            }
            try {
                if (gameState.isValidMove(currentPlayer, prevY, prevX, dir)) {
                        gameState.move(currentPlayer, prevY, prevX, dir);
                        increasePlayerSteps(currentPlayer);
                        switchCurrentPlayer();
                        if (gameState.isGameOver()) {
                            log.info("Congratulations {}, you have won the game in {} steps!", gameState.getWinner().getName(), gameState.getWinner().getStepCount());
                            winnerLabel.setText(gameState.getWinner().getName() + " won the game!");
                            gameOver = true;
                            stopWatchTimeline.stop();
                            log.debug("Saving result to database..");
                            gameResultDao = new GameResultDao();
                            gameResultDao.persist(createGameResult());
                        }
                    }
            } catch (IndexOutOfBoundsException e) {
                puck.remove();
            }
            puck.remove();
            displayGrid();
        }
    }

    /**
     * A method that creates the result of the game.
     * @return A {@code GameResult} object that stores the result of the game
     */
    private GameResult createGameResult() {
        String color = "";
        if (gameState.getWinner().getPlayerId() == 1) {
            color = "Blue";
        }
        else {
            color = "Red";
        }
        return GameResult.builder()
                .name(gameState.getWinner().getName())
                .color(color)
                .steps(gameState.getWinner().getStepCount())
                .duration(Duration.between(startTime, Instant.now()))
                .opponentName(gameState.getLoser().getName())
                .build();
    }

    /**
     * A method that increases the player's steps.
     * @param currentPlayer the player who moved in the current round
     */
    private void increasePlayerSteps(Player currentPlayer) {
        currentPlayer.setStepCount(currentPlayer.getStepCount()+1);
        if (currentPlayer.equals(player1)) {
            p1steps.setText("Steps: " + player1.getStepCount());
        }
        else {
            p2steps.setText("Steps: " + player2.getStepCount());
        }
    }


    /**
     * A method that sets the player's name to {@code p1name} and {@code p2name}.
     * @param p1name the name of Player One (Blue)
     * @param p2name the name of Player Two (Red)
     */
    public void setPlayersName(String p1name, String p2name) {
        this.p1name = p1name;
        this.p2name = p2name;
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
     * A method that is called when a player clicks on the reset button.
     * @param actionEvent the current action event
     */
    public void resetGame(ActionEvent actionEvent) {
        log.debug("{} button is pressed", ((Button)actionEvent.getSource()).getText());
        log.info("Resetting game...");
        initGame();
    }

    /**
     * A method that is called when a player clicks on the high score button.
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

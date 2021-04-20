package puckgame.javafx.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import puckgame.model.GameLogic;
import puckgame.model.Player;
import puckgame.model.Puck;

import java.util.ArrayList;

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
    private Button exitButton;

    @FXML
    private Button resetButton;

    private int size = 400;
    private int spots = 5;
    private int squareSize = size / spots;
    private ArrayList<Puck> pucks;
    private GameLogic gameLogic;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private int prevX;
    private int prevY;
    private boolean gameOver;
    private String p1name;
    private String p2name;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            p1nameText.setText(p1name);
            p2nameText.setText(p2name);
            player1.setName(p1name);
            player2.setName(p2name);
        });
        initGame();
    }

    public void initGame() {
        gameOver = false;
        prevX = 0;
        prevY = 0;
        gameLogic = new GameLogic();
        player1 = new Player(p1nameText.getText(), 1, 0);
        player2 = new Player(p2nameText.getText(), 2, 0);
        p1steps.setText("Steps: " + player1.getStepCount());
        p2steps.setText("Steps: " + player2.getStepCount());
        gameLogic.setBluePlayer(player1);
        gameLogic.setRedPlayer(player2);
        currentPlayer = player1;
        displayGrid();
    }

    public void displayGrid() {
        for (int i = 0; i < size; i += squareSize) {
            for (int j = 0; j < size; j += squareSize) {
                Rectangle r = new Rectangle(i, j, squareSize, squareSize);
                r.setFill(Color.WHITE);
                r.setStroke(Color.BLACK);
                pane.getChildren().addAll(r);
            }
        }
        pucks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Circle c = new Circle();
                if (i == 4 && j == 0 || i == 2 && j == 2 || i == 0 && j == 4) {
                    c.setFill(Color.BLUE);
                    c.setStroke(Color.BLACK);

                    double radius = squareSize / 3.0;
                    int x = squareSize / 2 + squareSize * (0+i);
                    int y = squareSize / 2 + squareSize * (0+j);
                    Puck puck = new Puck(x, y, radius, c, 1);
                    pucks.add(puck);
                    dealWithClick(c, puck);
                    pane.getChildren().add(c);
                    puck.draw();

                }
                else {
                    c.setFill(Color.RED);
                    c.setStroke(Color.BLACK);

                    double radius = squareSize / 3.0;
                    int x = squareSize / 2 + squareSize * (0+i);
                    int y = squareSize / 2 + squareSize * (0+j);
                    Puck puck = new Puck(x, y, radius, c, 2);
                    pucks.add(puck);
                    dealWithClick(c, puck);
                    pane.getChildren().add(c);
                    puck.draw();

                }
            }
        }
    }

    public void switchCurrentPlayer() {
        if (currentPlayer.equals(player1)) {
            currentPlayer = player2;
        }
        else {
            currentPlayer = player1;
        }
    }

    public void dealWithClick(Circle c, Puck puck) {
        c.setOnMousePressed(mouseEvent -> mousePressed(mouseEvent, puck));
        c.setOnMouseDragged(mouseEvent -> mouseDragged(mouseEvent, puck));
        c.setOnMouseReleased(mouseEvent -> mouseReleased(mouseEvent, puck));
    }

    public void mousePressed(MouseEvent mouseEvent, Puck puck) {
        if (!gameOver) {
            prevX = (int) puck.getX() / squareSize;
            prevY = (int) puck.getY() / squareSize;
        }
    }

    public void mouseDragged(MouseEvent mouseEvent, Puck puck) {
        if (!gameOver) {
            puck.setX(puck.getX() + mouseEvent.getX());
            puck.setY(puck.getY() + mouseEvent.getY());
            puck.draw();
        }
    }

    public void mouseReleased(MouseEvent mouseEvent, Puck puck) {
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
            if (gameLogic.isValidMove(currentPlayer, prevY, prevX, dir)) {
                int dirX = (int) (squareSize / 2) + squareSize * gridX;
                int dirY = (int) (squareSize / 2) + squareSize * gridY;
                gameLogic.move(currentPlayer, prevY, prevX, dir);
                if (puck.getPlayerId() == 1) {
                    for (int i = 0; i < pucks.size(); i++) {
                        removeRedPuck(pucks.get(i), dirX, dirY);
                    }
                }
                puck.setX(dirX);
                puck.setY(dirY);
                puck.draw();
                increasePlayerSteps(currentPlayer);
                switchCurrentPlayer();
                if (gameLogic.isGameOver()) {
                    if (gameLogic.hasBlueWon()) {
                        gameLogic.setWinner(player1);
                    } else {
                        gameLogic.setWinner(player2);
                    }
                    log.info("Congratulations {}, you have won the game in {} steps!", gameLogic.getWinner().getName(), gameLogic.getWinner().getStepCount());
                    gameOver = true;
                }
            } else {
                puck.setX(squareSize / 2 + squareSize * prevX);
                puck.setY(squareSize / 2 + squareSize * prevY);
                puck.draw();
            }
        }
    }

    private void increasePlayerSteps(Player currentPlayer) {
        currentPlayer.setStepCount(currentPlayer.getStepCount()+1);
        if (currentPlayer.equals(player1)) {
            p1steps.setText("Steps: " + player1.getStepCount());
        }
        else {
            p2steps.setText("Steps: " + player2.getStepCount());
        }
    }

    public void removeRedPuck(Puck puck, int dirX, int dirY) {
        if (puck.getPlayerId() == 2 && puck.getX() == dirX && puck.getY() == dirY) {
            puck.remove();
        }
    }

    public void setPlayersName(String p1name, String p2name) {
        this.p1name = p1name;
        this.p2name = p2name;
    }

    public void exitGame(ActionEvent actionEvent) {
        log.debug("{} button is pressed", ((Button)actionEvent.getSource()).getText());
        log.info("Exiting...");
        Platform.exit();
    }

    public void resetGame(ActionEvent actionEvent) {
        log.debug("{} button is pressed", ((Button)actionEvent.getSource()).getText());
        log.info("Resetting game...");
        initGame();
    }

}

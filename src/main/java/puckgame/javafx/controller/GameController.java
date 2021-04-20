package puckgame.javafx.controller;

import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;
import puckgame.model.GameLogic;
import puckgame.model.Player;
import puckgame.model.Puck;

import java.util.ArrayList;

@Slf4j
public class GameController {

    @FXML
    private Pane pane;

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

    @FXML
    public void initialize() {
        initGame();
    }

    public void initGame() {
        prevX = 0;
        prevY = 0;
        gameLogic = new GameLogic();
        player1 = new Player("p1", 1, 0);
        player2 = new Player("p2", 2, 0);
        currentPlayer = player1;
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
        prevX = (int) puck.getX() / squareSize;
        prevY = (int) puck.getY() / squareSize;
    }

    public void mouseDragged(MouseEvent mouseEvent, Puck puck) {
        puck.setX(puck.getX() + mouseEvent.getX());
        puck.setY(puck.getY() + mouseEvent.getY());
        puck.draw();
    }

    public void mouseReleased(MouseEvent mouseEvent, Puck puck) {
        int gridX = (int) puck.getX() / squareSize;
        int gridY = (int) puck.getY() / squareSize;
        int dir;
        if (gridX == prevX + 1 && gridY == prevY) {
            dir = 0;
        } else {
            if (gridX == prevX -1 && gridY == prevY) {
                dir = 1;
            } else {
                if (gridY == prevY -1 && gridX == prevX) {
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
            int dirX = (int)(squareSize / 2) + squareSize * gridX;
            int dirY = (int)(squareSize / 2) + squareSize * gridY;
            gameLogic.move(currentPlayer, prevY, prevX, dir);
            if (puck.getPlayerId() == 1) {
                for (int i = 0; i < pucks.size(); i++) {
                    removeRedPuck(pucks.get(i), dirX, dirY);
                }
            }
            puck.setX(dirX);
            puck.setY(dirY);
            puck.draw();
            switchCurrentPlayer();
        } else {
            puck.setX(squareSize / 2 + squareSize * prevX);
            puck.setY(squareSize / 2 + squareSize * prevY);
            puck.draw();
        }

    }

    public void removeRedPuck(Puck puck, int dirX, int dirY) {
        if (puck.getPlayerId() == 2 && puck.getX() == dirX && puck.getY() == dirY) {
            puck.remove();
        }
    }


}

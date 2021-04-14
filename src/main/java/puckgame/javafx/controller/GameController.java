package puckgame.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import puckgame.model.Puck;

import java.util.ArrayList;

public class GameController {

    @FXML
    private Pane pane;

    private int size = 400;
    private int spots = 5;
    private int squareSize = size / spots;
    private ArrayList<Puck> pucks;

    @FXML
    public void initialize() {
        initGame();
    }

    public void initGame() {
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
                    pane.getChildren().add(c);
                    puck.draw();

                }



            }
        }
    }

}

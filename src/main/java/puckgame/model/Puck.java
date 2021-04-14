package puckgame.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Puck {

    private double x;
    private double y;
    private double radius;

    private Circle c;
    private int playerId;

    public Puck(double x, double y, double radius, Circle c, int playerId) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.c = c;
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setColor(Color color) {
        c.setFill(color);
    }

    public void draw() {
        c.setRadius(radius);
        c.setTranslateX(x);
        c.setTranslateY(y);
    }

}

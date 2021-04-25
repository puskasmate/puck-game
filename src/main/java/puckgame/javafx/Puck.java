package puckgame.state;

import javafx.scene.shape.Circle;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class that stores the information about a puck.
 */
@Data
@AllArgsConstructor
public class Puck {

    /**
     * Double value that stores the x coordinate of the field where the puck has to be drawn to.
     */
    private double x;

    /**
     * Double value that stores the y coordinate of the field where the puck has to be drawn to.
     */
    private double y;

    /**
     * Double value that stores the radius of the puck.
     */
    private double radius;

    /**
     * A Circle object that stores the circle with de wanted color.
     */
    private Circle c;

    /**
     * An integer value that stores the player.
     * The player with the blue pucks is represented as 1, the player with the red pucks is represented as 2.
     */
    private int playerId;


    /**
     * A method that draws a puck to the grid.
     */
    public void draw() {
        c.setRadius(radius);
        c.setTranslateX(x);
        c.setTranslateY(y);
    }

    /**
     * A method that removes a puck from the grid.
     */
    public void remove() {
        c.setRadius(0);
        c.setTranslateX(0);
        c.setTranslateY(0);
    }

}

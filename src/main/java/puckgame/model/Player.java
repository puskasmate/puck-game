package puckgame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that stores information about a player.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    /**
     * String value that stores the player's name.
     */
    private String name;

    /**
     * Integer value that stores the player's id.
     * The player with the blue puck is represented as 1, the player with the red puck is represented as 2.
     */
    private int playerId;

    /**
     * Integer value that stores how many steps has the player done.
     */
    private int stepCount;

}

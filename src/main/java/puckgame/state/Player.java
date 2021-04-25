package puckgame.state;

import lombok.Data;

/**
 * Class that stores information about a player.
 */
@Data
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

    /**
     * No args constructor.
     */
    public Player() {
    }

    /**
     * All args constructor.
     * @param name the name of the player.
     * @param playerId the id of the player. Blue player's id has to be 1, red player's has to be 2.
     * @param stepCount the number of the player's steps.
     */
    public Player(String name, int playerId, int stepCount) {
        this.name = name;
        this.playerId = playerId;
        this.stepCount = stepCount;
    }

}

package puckgame.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * Class representing the state of the game and the board.
 */
@Data
@Slf4j
public class GameState {

    /**
     * Integer value that represents the direction of Right.
     */
    private static final int RIGHT = 0;

    /**
     * Integer value that represents the direction of Left.
     */
    private static final int LEFT = 1;

    /**
     * Integer value that represents the direction of Up.
     */
    private static final int UP = 2;

    /**
     * Integer value that represents the direction of Down.
     */
    private static final int DOWN = 3;

    /**
     * Boolean value that stores if the logging enabled or not.
     */
    @Setter(AccessLevel.PUBLIC)
    private boolean logEnabled = true;

    /**
     * Player object that stores the player with the blue pucks.
     */
    @Setter(AccessLevel.PUBLIC)
    private Player bluePlayer;

    /**
     * Player object that stores the player with the red pucks.
     */
    @Setter(AccessLevel.PUBLIC)
    private Player redPlayer;

    /**
     * Player object that stores the winner of the game.
     */
    @Getter(AccessLevel.PUBLIC)
    private Player winner;

    /**
     * 2D array representing the state of the game.
     * The blue pucks are represented as 1's, the red pucks are represented as 2's, and the empty spaces as 0's.
     */
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int [][] grid = {
            {2, 2, 2, 2, 1},
            {2, 2, 2, 2, 2},
            {2, 2, 1, 2, 2},
            {2, 2, 2, 2, 2},
            {1, 2, 2, 2, 2}
    };

    public GameState() {}

    public GameState(int [][] grid) {
        this.grid = grid;
    }

    /**
     * A method that checks if the current move is enabled or not.
     * @param currentPlayer The player who has to move at the current state.
     * @param row The row where the the current puck stands on the table.
     * @param col The column where the current puck stands on the table.
     * @param direction The direction where the player wants to move to.
     * @return {@code true} iff the player can move to the wanted direction, {@code false} otherwise.
     */
    public boolean isValidMove(Player currentPlayer, int row, int col, int direction) {

        boolean isValid = false;

        if (currentPlayer.getPlayerId() == 1 && grid[row][col] == 2 || !(currentPlayer.getPlayerId() == 1) && grid[row][col] == 1) {
            if (logEnabled) {
                log.info("You can not move, because it is {}'s turn!", currentPlayer.getName());
            }
            isValid = false;
        }

        else {
            try {
                if (currentPlayer.getPlayerId() == 1) {
                    switch (direction) {
                        case RIGHT: {
                            if (grid[row][col + 1] == 2) {
                                isValid = true;
                            } else {
                                if (logEnabled) {
                                    log.info("You can not move there, because there is no red puck on field ({}, {})!", (col + 1) + 1, row + 1);
                                }
                            }
                            break;
                        }
                        case LEFT: {
                            if (grid[row][col - 1] == 2) {
                                isValid = true;
                            } else {
                                if (logEnabled) {
                                    log.info("You can not move there, because there is no red puck on field ({}, {})!", (col - 1) + 1, row + 1);
                                }
                            }
                            break;
                        }
                        case UP: {
                            if (grid[row - 1][col] == 2) {
                                isValid = true;
                            } else {
                                if (logEnabled) {
                                    log.info("You can not move there, because there is no red puck on field ({}, {})!", col + 1, (row - 1) + 1);
                                }
                            }
                            break;
                        }
                        case DOWN: {
                            if (grid[row + 1][col] == 2) {
                                isValid = true;
                            } else {
                                if (logEnabled) {
                                    log.info("You can not move there, because there is no red puck on field ({}, {})!", col + 1, (row + 1) + 1);
                                }
                            }
                            break;
                        }
                        default:
                            if (logEnabled) {
                                log.info("Invalid direction, you can not move to that space!");
                            }
                            isValid = false;
                    }
                } else {
                    if (currentPlayer.getPlayerId() == 2) {
                        switch (direction) {
                            case RIGHT: {
                                if (grid[row][col + 1] == 0) {
                                    isValid = true;
                                } else {
                                    if (logEnabled) {
                                        log.info("You can not move there, because the ({}, {}) field is not empty!", (col + 1) + 1, row + 1);
                                    }
                                }
                                break;
                            }
                            case LEFT: {
                                if (grid[row][col - 1] == 0) {
                                    isValid = true;
                                } else {
                                    if (logEnabled) {
                                        log.info("You can not move there, because the ({}, {}) field is not empty!", (col - 1) + 1, row + 1);
                                    }
                                }
                                break;
                            }
                            case UP: {
                                if (grid[row - 1][col] == 0) {
                                    isValid = true;
                                } else {
                                    if (logEnabled) {
                                        log.info("You can not move there, because the ({}, {}) field is not empty!", col + 1, (row - 1) + 1);
                                    }
                                }
                                break;
                            }
                            case DOWN: {
                                if (grid[row + 1][col] == 0) {
                                    isValid = true;
                                } else {
                                    if (logEnabled) {
                                        log.info("You can not move there, because the ({}, {}) field is not empty!", col + 1, (row + 1) + 1);
                                    }
                                }
                                break;
                            }
                            default:
                                if (logEnabled) {
                                    log.info("Invalid direction, you can not move to that space!");
                                }
                                isValid = false;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                if (logEnabled) {
                    log.warn("You can not move out of the field!");
                }
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * Method to change the array if the move wanted move is valid.
     * @param currentPlayer The player who has to move at the current state.
     * @param row The row where the the current puck stands on the table.
     * @param col The column where the current puck stands on the table.
     * @param direction The direction where the player wants to move to.
     */
    public void move(Player currentPlayer, int row, int col, int direction) {

        if (isValidMove(currentPlayer, row, col, direction)) {
            switch (direction) {
                case RIGHT: {
                    grid[row][col+1] = grid[row][col];
                    grid[row][col] = 0;
                    log.info("{} moved from ({}, {}) to ({}, {}).", currentPlayer.getName(), col+1, row+1, (col+1)+1, row+1);
                    break;
                }
                case LEFT: {
                    grid[row][col-1] = grid[row][col];
                    grid[row][col] = 0;
                    log.info("{} moved from ({}, {}) to ({}, {}).", currentPlayer.getName(), col+1, row+1, (col-1)+1, row+1);
                    break;
                }
                case UP: {
                    grid[row-1][col] = grid[row][col];
                    grid[row][col] = 0;
                    log.info("{} moved from ({}, {}) to ({}, {}).", currentPlayer.getName(), col+1, row+1, col+1, (row-1)+1);
                    break;
                }
                case DOWN: {
                    grid[row+1][col] = grid[row][col];
                    grid[row][col] = 0;
                    log.info("{} moved from ({}, {}) to ({}, {}).", currentPlayer.getName(), col+1, row+1, col+1, (row+1)+1);
                    break;
                }

            }
        }

    }

    /**
     * A method that checks if the player with the red pucks has won the game.
     * @return {@code true} if the player with the red pucks has won the game, {@code false} otherwise.
     */
    public boolean hasRedWon() {
        boolean duplicates = false;

        ArrayList<Integer> xCors = new ArrayList<>();
        ArrayList<Integer> yCors = new ArrayList<>();
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid.length; col++) {
                if (grid[row][col] == 1) {
                    xCors.add(row);
                    yCors.add(col);
                }
            }
        }

        for(int i = 0; i < xCors.size(); i++) {
            for(int j = i+1; j < xCors.size(); j++) {
                if(i != j && xCors.get(i) == xCors.get(j) || yCors.get(i) == yCors.get(j)) {
                    duplicates = true;
                }
            }
        }
        if (duplicates) {
            winner = redPlayer;
        }
        return duplicates;
    }

    /**
     * A method that checks if the player with the blue pucks has won the game.
     * @return {@code true} if the player with the blue pucks has won the game, {@code false} otherwise.
     */
    public boolean hasBlueWon() {
        logEnabled = false;
        int count = 0;
        ArrayList<Integer> xCors = new ArrayList<>();
        ArrayList<Integer> yCors = new ArrayList<>();

        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid.length; col++) {
                if(grid[row][col] == 1) {
                    xCors.add(row);
                    yCors.add(col);
                }
            }
        }
        for(int i = 0; i < 3; i++) {
            if (!isValidMove(bluePlayer, xCors.get(i), yCors.get(i), 0)
            && !isValidMove(bluePlayer, xCors.get(i), yCors.get(i), 1)
            && !isValidMove(bluePlayer, xCors.get(i), yCors.get(i), 2)
            && !isValidMove(bluePlayer, xCors.get(i), yCors.get(i), 3)) {
                count++;
            }
        }

        if (count == 3) {
            winner = bluePlayer;
            return true;
        }

        else {
            logEnabled = true;
            return false;
        }
    }

    /**
     * A method that checks if the game is over.
     * @return {@code true} if the game is over, {@code false} otherwise.
     */
    public boolean isGameOver() {
        if (hasBlueWon() || hasRedWon()) {
            return true;
        }
        else {
            return false;
        }
    }


}

package puckgame.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Data
@Slf4j
public class GameLogic {

    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    @Setter(AccessLevel.PUBLIC)
    private boolean logEnabled = true;

    @Setter(AccessLevel.PUBLIC)
    private Player bluePlayer;

    @Setter(AccessLevel.PUBLIC)
    private Player redPlayer;

    @Getter(AccessLevel.PUBLIC)
    private Player winner;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int [][] grid = {
            {2, 2, 2, 2, 1},
            {2, 2, 2, 2, 2},
            {2, 2, 1, 2, 2},
            {2, 2, 2, 2, 2},
            {1, 2, 2, 2, 2}
    };

    public GameLogic() {}

    public GameLogic(int [][] grid) {
        this.grid = grid;
    }

    public boolean isValidMove(Player player, int row, int col, int direction) {

        boolean isValid = false;

        if (player.getPlayerId() == 1 && grid[row][col] == 2 || !(player.getPlayerId() == 1) && grid[row][col] == 1) {
            if (logEnabled) {
                log.info("You can not move, because it is {}'s turn!", player.getName());
            }
            isValid = false;
        }

        else {
            try {
                if (player.getPlayerId() == 1) {
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
                    if (player.getPlayerId() == 2) {
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

    public void move(Player player, int row, int col, int direction) {

        if (isValidMove(player, row, col, direction)) {
            switch (direction) {
                case RIGHT: {
                    grid[row][col+1] = grid[row][col];
                    grid[row][col] = 0;
                    log.info("{} moved from ({}, {}) to ({}, {}).", player.getName(), col+1, row+1, (col+1)+1, row+1);
                    break;
                }
                case LEFT: {
                    grid[row][col-1] = grid[row][col];
                    grid[row][col] = 0;
                    log.info("{} moved from ({}, {}) to ({}, {}).", player.getName(), col+1, row+1, (col-1)+1, row+1);
                    break;
                }
                case UP: {
                    grid[row-1][col] = grid[row][col];
                    grid[row][col] = 0;
                    log.info("{} moved from ({}, {}) to ({}, {}).", player.getName(), col+1, row+1, col+1, (row-1)+1);
                    break;
                }
                case DOWN: {
                    grid[row+1][col] = grid[row][col];
                    grid[row][col] = 0;
                    log.info("{} moved from ({}, {}) to ({}, {}).", player.getName(), col+1, row+1, col+1, (row+1)+1);
                    break;
                }

            }
        }

    }

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

        return duplicates;
    }

    public boolean hasBlueWon() {
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
            return true;
        }

        else {
            return false;
        }
    }

    public boolean isGameOver() {
        if (hasBlueWon() || hasRedWon()) {
            return true;
        }
        else {
            return false;
        }
    }


}

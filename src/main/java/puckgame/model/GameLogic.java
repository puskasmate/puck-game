package puckgame.model;

public class GameLogic {

    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

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
            isValid = false;
        }

        else {
            if (player.getPlayerId() == 1) {
                switch (direction) {
                    case RIGHT: {
                        if (col != grid.length-1 && grid[row][col+1] == 2) {
                            isValid = true;
                        }
                        break;
                    }
                    case LEFT: {
                        if (col != 0 && grid[row][col-1] == 2) {
                            isValid = true;
                        }
                        break;
                    }
                    case UP: {
                        if (row != 0 && grid[row-1][col] == 2) {
                            isValid = true;
                        }
                        break;
                    }
                    case DOWN: {
                        if (row != grid.length-1 && grid[row+1][col] == 2) {
                            isValid = true;
                        }
                        break;
                    }
                    default:
                        isValid = false;
                }
            }
            else {
                if (player.getPlayerId() == 2) {
                    switch (direction) {
                        case RIGHT: {
                            if (col != grid.length-1 && grid[row][col+1] == 0) {
                                isValid = true;
                            }
                            break;
                        }
                        case LEFT: {
                            if (col != 0 && grid[row][col-1] == 0) {
                                isValid = true;
                            }
                            break;
                        }
                        case UP: {
                            if (row != 0 && grid[row-1][col] == 0) {
                                isValid = true;
                            }
                            break;
                        }
                        case DOWN: {
                            if (row != grid.length-1 && grid[row+1][col] == 0) {
                                isValid = true;
                            }
                            break;
                        }
                        default:
                            isValid = false;
                    }
                }
            }

        }
        return isValid;
    }


}

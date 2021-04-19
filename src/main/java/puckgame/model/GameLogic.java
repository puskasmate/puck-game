package puckgame.model;

import java.util.ArrayList;

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

    public void move(Player player, int row, int col, int direction) {

        if (isValidMove(player, row, col, direction)) {
            switch (direction) {
                case RIGHT: {
                    grid[row][col+1] = grid[row][col];
                    grid[row][col] = 0;
                    break;
                }
                case LEFT: {
                    grid[row][col-1] = grid[row][col];
                    grid[row][col] = 0;
                    break;
                }
                case UP: {
                    grid[row-1][col] = grid[row][col];
                    grid[row][col] = 0;
                    break;
                }
                case DOWN: {
                    grid[row+1][col] = grid[row][col];
                    grid[row][col] = 0;
                    break;
                }

            }
        }
        else {
            System.out.println("You can't move there!");
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

    public boolean hasBlueWon(Player player1) {
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
            if (!isValidMove(player1, xCors.get(i), yCors.get(i), 0)
            && !isValidMove(player1, xCors.get(i), yCors.get(i), 1)
            && !isValidMove(player1, xCors.get(i), yCors.get(i), 2)
            && !isValidMove(player1, xCors.get(i), yCors.get(i), 3)) {
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


}

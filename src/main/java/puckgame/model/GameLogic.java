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

}

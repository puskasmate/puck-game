package puckgame;

import org.junit.jupiter.api.Test;
import puckgame.model.GameLogic;
import puckgame.model.Player;

import static org.junit.jupiter.api.Assertions.*;

public class GameLogicTest {

    GameLogic gameLogic;
    Player player1;
    Player player2;

    @Test
    void testIsValidMoveAtStartingState() {
        gameLogic = new GameLogic();
        player1 = new Player("p1", 1, 0);
        player2 = new Player("p2", 2, 0);

        //middle blue
        assertTrue(gameLogic.isValidMove(player1, 2, 2, 0));
        assertTrue(gameLogic.isValidMove(player1, 2, 2, 1));
        assertTrue(gameLogic.isValidMove(player1, 2, 2, 2));
        assertTrue(gameLogic.isValidMove(player1, 2, 2, 3));
        assertFalse(gameLogic.isValidMove(player1, 2, 2, 4));
        assertFalse(gameLogic.isValidMove(player2, 2, 2, 0));

        //top blue
        assertFalse(gameLogic.isValidMove(player1, 0, 4, 0));
        assertTrue(gameLogic.isValidMove(player1, 0, 4, 1));
        assertFalse(gameLogic.isValidMove(player1, 0, 4, 2));
        assertTrue(gameLogic.isValidMove(player1, 0, 4, 3));
        assertFalse(gameLogic.isValidMove(player1, 0, 4, 4));
        assertFalse(gameLogic.isValidMove(player2, 0, 4, 1));

        //bottom blue
        assertTrue(gameLogic.isValidMove(player1, 4, 0, 0));
        assertFalse(gameLogic.isValidMove(player1, 4, 0, 1));
        assertTrue(gameLogic.isValidMove(player1, 4, 0, 2));
        assertFalse(gameLogic.isValidMove(player1, 4, 0, 3));
        assertFalse(gameLogic.isValidMove(player1, 4, 0, 4));
        assertFalse(gameLogic.isValidMove(player2, 4, 0, 0));

        //random red puck
        assertFalse(gameLogic.isValidMove(player2, 0, 0 ,0));
        assertFalse(gameLogic.isValidMove(player2, 0, 0 ,1));
        assertFalse(gameLogic.isValidMove(player2, 0, 0 ,2));
        assertFalse(gameLogic.isValidMove(player2, 0, 0 ,3));
        assertFalse(gameLogic.isValidMove(player2, 0, 0 ,7));
        assertFalse(gameLogic.isValidMove(player1, 0, 0 ,0));
    }

    @Test
    void testIsValidMove() {
        player1 = new Player("p1", 1 , 0);
        player2 = new Player("p2", 2, 0);
        gameLogic = new GameLogic(new int[][]{
                {2, 2, 2, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 2, 0, 2, 2},
                {2, 0, 2, 0, 0},
                {1, 2, 0, 2, 2}
        });

        assertTrue(gameLogic.isValidMove(player1, 0, 4, 1));
        assertFalse(gameLogic.isValidMove(player1, 0, 4, 3));
        assertFalse(gameLogic.isValidMove(player2, 2, 4, 1));
        assertFalse(gameLogic.isValidMove(player1, 0, 4, 0));
        assertTrue(gameLogic.isValidMove(player2, 2, 1, 2));
        assertFalse(gameLogic.isValidMove(player1, 2, 1, 2));
        assertTrue(gameLogic.isValidMove(player1, 4, 0, 0));
        assertFalse(gameLogic.isValidMove(player1, 4, 0, 1));
        assertFalse(gameLogic.isValidMove(player2, 4, 0, 1));
    }

    @Test
    void testHasBlueWon() {
        player1 = new Player("p1", 1, 0);
        gameLogic = new GameLogic(new int[][]{
                {2, 2, 2, 0, 1},
                {2, 0, 0, 2, 0},
                {2, 0, 1, 0, 2},
                {0, 2, 0, 2, 0},
                {1, 0, 0, 2, 2}
        });

        assertTrue(gameLogic.hasBlueWon(player1));
        gameLogic = new GameLogic(new int[][]{
                {2, 2, 2, 0, 1},
                {2, 2, 0, 1, 0},
                {0, 2, 0, 0, 2},
                {0, 2, 2, 2, 0},
                {1, 0, 2, 2, 2}
        });
        assertTrue(gameLogic.hasBlueWon(player1));

        gameLogic = new GameLogic(new int[][]{
                {2, 2, 2, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 2, 0, 2, 2},
                {2, 0, 2, 0, 0},
                {1, 2, 0, 2, 2}
        });
        assertFalse(gameLogic.hasBlueWon(player1));

        gameLogic = new GameLogic(new int[][]{
                {2, 2, 2, 0, 1},
                {1, 0, 2, 0, 0},
                {0, 2, 0, 2, 2},
                {0, 2, 2, 0, 0},
                {1, 0, 0, 2, 0}
        });
        assertFalse(gameLogic.hasBlueWon(player1));
    }
}

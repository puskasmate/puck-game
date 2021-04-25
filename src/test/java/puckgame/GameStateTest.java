package puckgame;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puckgame.model.GameState;
import puckgame.model.Player;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class GameStateTest {

    GameState gameState;
    Player player1;
    Player player2;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        player1 = new Player("p1", 1, 0);
        player2 = new Player("p2", 2, 0);
    }

    @Test
    void testIsValidMoveAtStartingState() {
        //middle blue
        assertTrue(gameState.isValidMove(player1, 2, 2, 0));
        assertTrue(gameState.isValidMove(player1, 2, 2, 1));
        assertTrue(gameState.isValidMove(player1, 2, 2, 2));
        assertTrue(gameState.isValidMove(player1, 2, 2, 3));
        assertFalse(gameState.isValidMove(player1, 2, 2, 4));
        assertFalse(gameState.isValidMove(player2, 2, 2, 0));

        //top blue
        assertFalse(gameState.isValidMove(player1, 0, 4, 0));
        assertTrue(gameState.isValidMove(player1, 0, 4, 1));
        assertFalse(gameState.isValidMove(player1, 0, 4, 2));
        assertTrue(gameState.isValidMove(player1, 0, 4, 3));
        assertFalse(gameState.isValidMove(player1, 0, 4, 4));
        assertFalse(gameState.isValidMove(player2, 0, 4, 1));

        //bottom blue
        assertTrue(gameState.isValidMove(player1, 4, 0, 0));
        assertFalse(gameState.isValidMove(player1, 4, 0, 1));
        assertTrue(gameState.isValidMove(player1, 4, 0, 2));
        assertFalse(gameState.isValidMove(player1, 4, 0, 3));
        assertFalse(gameState.isValidMove(player1, 4, 0, 4));
        assertFalse(gameState.isValidMove(player2, 4, 0, 0));

        //random red puck
        assertFalse(gameState.isValidMove(player2, 0, 0 ,0));
        assertFalse(gameState.isValidMove(player2, 0, 0 ,1));
        assertFalse(gameState.isValidMove(player2, 0, 0 ,2));
        assertFalse(gameState.isValidMove(player2, 0, 0 ,3));
        assertFalse(gameState.isValidMove(player2, 0, 0 ,7));
        assertFalse(gameState.isValidMove(player1, 0, 0 ,0));
    }

    @Test
    void testIsValidMove() {
        gameState = new GameState(new int[][]{
                {2, 2, 2, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 2, 0, 2, 2},
                {2, 0, 2, 0, 0},
                {1, 2, 0, 2, 2}
        });

        assertTrue(gameState.isValidMove(player1, 0, 4, 1));
        assertFalse(gameState.isValidMove(player1, 0, 4, 3));
        assertFalse(gameState.isValidMove(player2, 2, 4, 1));
        assertFalse(gameState.isValidMove(player1, 0, 4, 0));
        assertTrue(gameState.isValidMove(player2, 2, 1, 2));
        assertFalse(gameState.isValidMove(player1, 2, 1, 2));
        assertTrue(gameState.isValidMove(player1, 4, 0, 0));
        assertFalse(gameState.isValidMove(player1, 4, 0, 1));
        assertFalse(gameState.isValidMove(player2, 4, 0, 1));
    }

    @Test
    void testHasBlueWon() {
        gameState = new GameState(new int[][]{
                {2, 2, 2, 0, 1},
                {2, 0, 0, 2, 0},
                {2, 0, 1, 0, 2},
                {0, 2, 0, 2, 0},
                {1, 0, 0, 2, 2}
        });
        gameState.setBluePlayer(player1);
        assertTrue(gameState.hasBlueWon());

        gameState = new GameState(new int[][]{
                {2, 2, 2, 0, 1},
                {2, 2, 0, 1, 0},
                {0, 2, 0, 0, 2},
                {0, 2, 2, 2, 0},
                {1, 0, 2, 2, 2}
        });
        gameState.setBluePlayer(player1);
        assertTrue(gameState.hasBlueWon());

        gameState = new GameState(new int[][]{
                {2, 2, 2, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 2, 0, 2, 2},
                {2, 0, 2, 0, 0},
                {1, 2, 0, 2, 2}
        });
        gameState.setBluePlayer(player1);
        assertFalse(gameState.hasBlueWon());

        gameState = new GameState(new int[][]{
                {2, 2, 2, 0, 1},
                {1, 0, 2, 0, 0},
                {0, 2, 0, 2, 2},
                {0, 2, 2, 0, 0},
                {1, 0, 0, 2, 0}
        });
        gameState.setBluePlayer(player1);
        assertFalse(gameState.hasBlueWon());
    }

    @Test
    void testHasRedWon() {
        gameState = new GameState(new int[][]{
                {2, 2, 2, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 2, 0, 2, 2},
                {2, 0, 2, 0, 0},
                {1, 2, 0, 2, 2}
        });
        assertFalse(gameState.hasRedWon());

        gameState = new GameState(new int[][]{
                {2, 2, 2, 2, 1},
                {2, 0, 0, 0, 0},
                {0, 2, 0, 2, 2},
                {1, 0, 2, 0, 0},
                {1, 2, 0, 2, 2}
        });
        assertTrue(gameState.hasRedWon());

        gameState = new GameState(new int[][]{
                {2, 2, 2, 2, 1},
                {2, 0, 0, 0, 0},
                {0, 2, 0, 2, 2},
                {2, 0, 2, 0, 0},
                {0, 1, 1, 2, 2}
        });
        assertTrue(gameState.hasRedWon());

        gameState = new GameState(new int[][]{
                {2, 2, 2, 2, 1},
                {2, 0, 1, 0, 0},
                {0, 2, 1, 2, 2},
                {2, 0, 2, 0, 0},
                {0, 2, 0, 2, 2}
        });
        assertTrue(gameState.hasRedWon());

        gameState = new GameState(new int[][]{
                {2, 2, 2, 2, 1},
                {2, 0, 0, 2, 2},
                {0, 1, 0, 1, 2},
                {2, 0, 2, 0, 0},
                {0, 2, 0, 2, 2}
        });
        assertTrue(gameState.hasRedWon());

        gameState = new GameState(new int[][]{
                {2, 2, 2, 2, 2},
                {2, 0, 0, 1, 0},
                {0, 2, 1, 2, 2},
                {2, 0, 2, 0, 0},
                {0, 2, 0, 2, 1}
        });

        assertFalse(gameState.hasRedWon());
    }

    @Test
    void testIsGameOver() {
        gameState = new GameState(new int[][]{
                {2, 2, 2, 2, 2},
                {2, 0, 0, 1, 0},
                {0, 2, 1, 2, 2},
                {2, 0, 2, 0, 0},
                {0, 2, 0, 2, 1}
        });
        gameState.setBluePlayer(player1);
        assertFalse(gameState.isGameOver());

        gameState = new GameState(new int[][]{
                {2, 2, 2, 0, 1},
                {2, 2, 0, 1, 0},
                {0, 2, 0, 0, 2},
                {0, 2, 2, 2, 0},
                {1, 0, 2, 2, 2}
        });
        gameState.setBluePlayer(player1);
        assertTrue(gameState.isGameOver());

        gameState = new GameState(new int[][]{
                {2, 2, 2, 2, 1},
                {2, 0, 0, 2, 2},
                {0, 1, 0, 1, 2},
                {2, 0, 2, 0, 0},
                {0, 2, 0, 2, 2}
        });
        gameState.setBluePlayer(player1);
        assertTrue(gameState.isGameOver());

        gameState = new GameState(new int[][]{
                {2, 2, 2, 0, 1},
                {1, 0, 2, 0, 0},
                {0, 2, 0, 2, 2},
                {0, 2, 2, 0, 0},
                {1, 0, 0, 2, 0}
        });
        gameState.setBluePlayer(player1);
        assertTrue(gameState.isGameOver());

        gameState = new GameState(new int[][]{
                {2, 2, 2, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 2, 0, 2, 2},
                {2, 0, 2, 0, 0},
                {1, 2, 0, 2, 2}
        });
        gameState.setBluePlayer(player1);
        assertFalse(gameState.isGameOver());
    }

    @Test
    void testMove() {
        gameState.move(player1, 2, 2, 1);
        assertArrayEquals(new int[][] {{2, 2, 2, 2, 1},
                {2, 2, 2, 2, 2},
                {2, 1, 0, 2, 2},
                {2, 2, 2, 2, 2},
                {1, 2, 2, 2, 2}}, gameState.getGrid());
        gameState.move(player2, 2, 3, 1);
        assertArrayEquals(new int[][] {{2, 2, 2, 2, 1},
                {2, 2, 2, 2, 2},
                {2, 1, 2, 0, 2},
                {2, 2, 2, 2, 2},
                {1, 2, 2, 2, 2}}, gameState.getGrid());
        gameState.setGrid(new int[][] {{2, 2, 0, 2, 1},
                {2, 0, 0, 2, 2},
                {2, 0, 0, 0, 2},
                {2, 2, 1, 2, 2},
                {1, 2, 2, 0, 0}});
        gameState.move(player1, 3, 2, 2);
        assertArrayEquals(new int[][] {{2, 2, 0, 2, 1},
                {2, 0, 0, 2, 2},
                {2, 0, 0, 0, 2},
                {2, 2, 1, 2, 2},
                {1, 2, 2, 0, 0}}, gameState.getGrid());
        gameState.move(player2, 0, 4, 1);
        assertArrayEquals(new int[][] {{2, 2, 0, 2, 1},
                {2, 0, 0, 2, 2},
                {2, 0, 0, 0, 2},
                {2, 2, 1, 2, 2},
                {1, 2, 2, 0, 0}}, gameState.getGrid());
        gameState.move(player1, 3, 2, 0);
        assertArrayEquals(new int[][] {{2, 2, 0, 2, 1},
                {2, 0, 0, 2, 2},
                {2, 0, 0, 0, 2},
                {2, 2, 0, 1, 2},
                {1, 2, 2, 0, 0}}, gameState.getGrid());
        gameState.move(player2, 1, 3, 3);
        assertArrayEquals(new int[][] {{2, 2, 0, 2, 1},
                {2, 0, 0, 0, 2},
                {2, 0, 0, 2, 2},
                {2, 2, 0, 1, 2},
                {1, 2, 2, 0, 0}}, gameState.getGrid());
    }

}

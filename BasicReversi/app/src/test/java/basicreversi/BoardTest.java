/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package basicreversi;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test void appHasAGreeting() {
        Board classUnderTest = new Board();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }
    @Test void testSetPlayers(){
        Board b = new Board();
        b.setPlayers(1, "Player 1", 2, "Player 2");
        assertEquals("Player 1", b.getPlayers().get(1));
    }

    @Test void gameOverTest(){
        Board b = new Board();
        assertFalse(b.gameOver());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                b.setPiece(i, j, 1);
            }
        }
        assertTrue(b.gameOver());
    }

    @Test void turnStateTest(){
        Board b = new Board();
        assertEquals(22, b.turnState(1));
        b.setPiece(4, 4, 1);
        b.setPiece(3, 3, 1);
        b.setPiece(3, 4, 2);
        b.setPiece(4, 3, 2);
        assertEquals(21, b.turnState(2));
    }

    @Test void multiDirectionalTest(){
        Board b = new Board();
        b.setPiece(2, 3, 2);
        b.setPiece(2, 4, 1);
        b.setPiece(3, 2, 2);
        b.setPiece(3, 3, 2);
        b.setPiece(3, 4, 1);
        b.setPiece(4, 2, 1);
        b.setPiece(4, 3, 1);
        b.setPiece(4, 4, 2);
        b.turnState(1);
        assertEquals(2, b.getBoard()[2][3]);
        assertEquals(2, b.getBoard()[3][2]);
        assertEquals(11, b.place(2, 2, 1));
        assertEquals(1, b.getBoard()[2][3]);
        assertEquals(1, b.getBoard()[3][2]);
    }
}

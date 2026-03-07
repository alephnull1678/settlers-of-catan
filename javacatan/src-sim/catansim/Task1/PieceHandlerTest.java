package catansim.Task1;

import static org.junit.Assert.*;
import org.junit.Test;

import catansim.PieceHandler;
import catansim.PieceTypes;
import catansim.Piece;
import catansim.PlayerID;

/**
 * Unit tests for PieceHandler.
 * 
 */
public class PieceHandlerTest {

    // Tests that a new PieceHandler starts with the correct maximum number of pieces
    @Test
    public void test_initialPieceCounts() {

        PieceHandler handler = new PieceHandler(PlayerID.BLUE);

        assertEquals("Roads should start at maximum", PieceHandler.MAX_ROADS, handler.getAvailable(PieceTypes.ROAD));
        
        assertEquals("Settlements should start at maximum", PieceHandler.MAX_SETTLEMENTS, handler.getAvailable(PieceTypes.SETTLEMENT));

        assertEquals("Cities should start at maximum", PieceHandler.MAX_CITIES, handler.getAvailable(PieceTypes.CITY));
    }

    // Tests that using a piece decreases the available count by one
    @Test
    public void test_usePiece_decreasesCount() {

        PieceHandler handler = new PieceHandler(PlayerID.BLUE);

        int before = handler.getAvailable(PieceTypes.ROAD);

        Piece piece = handler.usePiece(PieceTypes.ROAD);

        assertNotNull("usePiece should return a piece when availbile", piece);
        assertEquals("Available road count shoulder decrease by one", before - 1, handler.getAvailable(PieceTypes.ROAD));
    }
}
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
}
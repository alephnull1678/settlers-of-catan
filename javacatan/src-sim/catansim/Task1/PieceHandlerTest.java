package catansim.Task1;

import static org.junit.Assert.*;
import org.junit.Test;

import catansim.PieceHandler;
import catansim.PieceTypes;
import catansim.Piece;
import catansim.PlayerID;

/**
 * Unit tests for PieceHandler.
 * These tests verify intialization, piece usage,
 * and refund behaviour
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

        assertNotNull("usePiece should return a piece when available", piece);
        assertEquals("Available road count should decrease by one", before - 1, handler.getAvailable(PieceTypes.ROAD));
    }

    // Tests that refunding a piece restores the available count
    @Test
    public void test_refundPiece_restoresCount() {

        PieceHandler handler = new PieceHandler(PlayerID.BLUE);

        handler.usePiece(PieceTypes.SETTLEMENT);

        int afterUse = handler.getAvailable(PieceTypes.SETTLEMENT);

        handler.refundPiece(PieceTypes.SETTLEMENT);

        assertEquals("Refunding a piece should increase available count by one", afterUse + 1, handler.getAvailable(PieceTypes.SETTLEMENT));
    }
}
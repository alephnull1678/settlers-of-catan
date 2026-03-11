package catansim.Task3UnitTests;

import static org.junit.Assert.*;
import org.junit.Test;
import catansim.*;

public class ParserTest {

    @Test
    public void parseRollReturnsMatchingLegalRollAction() {
        Action[] legal = {
            new Action(ActionTypes.ROLL),
            new Action(ActionTypes.GO)
        };

        Action result = Parser.parse("Roll", legal);

        assertNotNull(result);
        assertEquals(legal[0], result);
    }

    @Test
    public void parseGoIgnoresCaseAndWhitespace() {
        Action[] legal = {
            new Action(ActionTypes.GO)
        };

        Action result = Parser.parse("   gO   ", legal);

        assertNotNull(result);
        assertEquals(legal[0], result);
    }

    @Test
    public void parseListReturnsMatchingLegalListAction() {
        Action[] legal = {
            new Action(ActionTypes.LIST)
        };

        Action result = Parser.parse("List", legal);

        assertNotNull(result);
        assertEquals(legal[0], result);
    }

    @Test
    public void parseBuildSettlementReturnsMatchingLegalAction() {
        Action[] legal = {
            new BuildAction(new Node(7), PieceTypes.SETTLEMENT)
        };

        Action result = Parser.parse("Build settlement 7", legal);

        assertNotNull(result);
        assertEquals(legal[0], result);
    }

    @Test
    public void parseBuildCityReturnsMatchingLegalAction() {
        Action[] legal = {
            new BuildAction(new Node(12), PieceTypes.CITY)
        };

        Action result = Parser.parse("Build city 12", legal);

        assertNotNull(result);
        assertEquals(legal[0], result);
    }

    @Test
    public void parseBuildRoadReturnsMatchingLegalAction() {
        Action[] legal = {
            new BuildAction(new Node(3), new Node(8), PieceTypes.ROAD)
        };

        Action result = Parser.parse("Build road 3,8", legal);

        assertNotNull(result);
        assertEquals(legal[0], result);
    }

    @Test
    public void parseBuildRoadAllowsExtraWhitespace() {
        Action[] legal = {
            new BuildAction(new Node(10), new Node(11), PieceTypes.ROAD)
        };

        Action result = Parser.parse("  Build   road   10 , 11  ", legal);

        assertNotNull(result);
        assertEquals(legal[0], result);
    }

    @Test
    public void parseReturnsNullForMalformedCommand() {
        Action[] legal = {
            new Action(ActionTypes.ROLL)
        };

        Action result = Parser.parse("Build settlement", legal);

        assertNull(result);
    }

    @Test
    public void parseReturnsNullWhenCommandIsWellFormedButNotLegal() {
        Action[] legal = {
            new BuildAction(new Node(4), PieceTypes.SETTLEMENT)
        };

        Action result = Parser.parse("Build settlement 9", legal);

        assertNull(result);
    }

    @Test
    public void parseReturnsNullForNullInput() {
        Action[] legal = {
            new Action(ActionTypes.ROLL)
        };

        Action result = Parser.parse(null, legal);

        assertNull(result);
    }

    @Test
    public void parseReturnsNullForNullLegalActions() {
        Action result = Parser.parse("Roll", null);
        assertNull(result);
    }
}
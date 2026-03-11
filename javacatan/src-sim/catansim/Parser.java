package catansim;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Parser {

	//All regex patterns to check off of
    private static final Pattern ROLL_PATTERN =
            Pattern.compile("^\\s*Roll\\s*$", Pattern.CASE_INSENSITIVE);

    private static final Pattern GO_PATTERN =
            Pattern.compile("^\\s*Go\\s*$", Pattern.CASE_INSENSITIVE);

    private static final Pattern LIST_PATTERN =
            Pattern.compile("^\\s*List\\s*$", Pattern.CASE_INSENSITIVE);

    private static final Pattern BUILD_SETTLEMENT_PATTERN =
            Pattern.compile("^\\s*Build\\s+settlement\\s+(\\d+)\\s*$", Pattern.CASE_INSENSITIVE);

    private static final Pattern BUILD_CITY_PATTERN =
            Pattern.compile("^\\s*Build\\s+city\\s+(\\d+)\\s*$", Pattern.CASE_INSENSITIVE);

    private static final Pattern BUILD_ROAD_PATTERN =
            Pattern.compile("^\\s*Build\\s+road\\s+(\\d+)\\s*,\\s*(\\d+)\\s*$", Pattern.CASE_INSENSITIVE);

    private Parser() {
    }

    
    //Parse action from string using pattern, and check if it is a legal action
    public static Action parse(String input, Action[] legalActions) {
        if (input == null || legalActions == null) {
            return null;
        }

        //Pattern matching part
        Action candidate = parseCandidate(input.trim());
        if (candidate == null) {
            return null;
        }

        //Legal action return part
        for (Action legalAction : legalActions) {
            if (candidate.equals(legalAction)) {
                return legalAction;
            }
        }

        return null;
    }

    private static Action parseCandidate(String input) {
        Matcher matcher;

        if (ROLL_PATTERN.matcher(input).matches()) {
            return new Action(ActionTypes.ROLL);
        }

        if (GO_PATTERN.matcher(input).matches()) {
            return new Action(ActionTypes.GO);
        }

        if (LIST_PATTERN.matcher(input).matches()) {
            return new Action(ActionTypes.LIST);
        }

        matcher = BUILD_SETTLEMENT_PATTERN.matcher(input);
        if (matcher.matches()) {
            int nodeID = Integer.parseInt(matcher.group(1));
            return new BuildAction(
                    ActionTypes.BUILD,
                    new Node(nodeID),
                    PieceTypes.SETTLEMENT
            );
        }

        matcher = BUILD_CITY_PATTERN.matcher(input);
        if (matcher.matches()) {
            int nodeID = Integer.parseInt(matcher.group(1));
            return new BuildAction(
                    ActionTypes.BUILD,
                    new Node(nodeID),
                    PieceTypes.CITY
            );
        }

        matcher = BUILD_ROAD_PATTERN.matcher(input);
        if (matcher.matches()) {
            int fromNodeID = Integer.parseInt(matcher.group(1));
            int toNodeID = Integer.parseInt(matcher.group(2));

            return new BuildAction(
                    ActionTypes.BUILD,
                    new Node(fromNodeID),
                    new Node(toNodeID),
                    PieceTypes.ROAD
            );
        }

        return null;
    }
}
package catansim;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GamePythonVisualizer implements Visualizer {

    private Board board;

    private List<String> roads = new ArrayList<>();
    private List<String> buildings = new ArrayList<>();

    private Set<String> seenRoads = new HashSet<>();
    private Map<Integer, String> seenBuildings = new HashMap<>();

    public GamePythonVisualizer(Board board) {
        this.board = board;
        render();
    }

    @Override
    public void onStateChange() {

        Node[] nodes = board.getNodes();

        // detect new roads
        for (Node node : nodes) {

            for (Road road : node.getConnectedRoads()) {
                if (road == null) continue;

                for (Node neighbour : node.getNeighbours()) {

                    for (Road r : neighbour.getConnectedRoads()) {

                        if (r == road) {

                            int a = Math.min(node.getNodeID(), neighbour.getNodeID());
                            int b = Math.max(node.getNodeID(), neighbour.getNodeID());

                            String key = a + "-" + b;

                            if (!seenRoads.contains(key)) {

                                seenRoads.add(key);

                                roads.add(
                                    "{ \"a\": " + a +
                                    ", \"b\": " + b +
                                    ", \"owner\": \"" + road.getOwnerPlayerID() + "\" }"
                                );
                            }
                        }
                    }
                }
            }
        }

        // detect settlements / cities
        for (Node node : nodes) {

            if (node.getBuilding() != null) {

                int id = node.getNodeID();
                String type = node.getBuilding().getType().toString();

                if (!seenBuildings.containsKey(id)) {

                    // first time seeing a building here (settlement normally)
                    seenBuildings.put(id, type);

                    buildings.add(
                        "{ \"node\": " + id +
                        ", \"owner\": \"" + node.getBuilding().getOwnerPlayerID() +
                        "\", \"type\": \"" + type + "\" }"
                    );

                } else if (!seenBuildings.get(id).equals(type)) {

                    // settlement upgraded to city
                    seenBuildings.put(id, type);

                    buildings.add(
                        "{ \"node\": " + id +
                        ", \"owner\": \"" + node.getBuilding().getOwnerPlayerID() +
                        "\", \"type\": \"" + type + "\" }"
                    );
                }
            }
        }

        render();
    }

    private void render() {

        try (FileWriter writer = new FileWriter("state.json")) {

            StringBuilder json = new StringBuilder();

            json.append("{\n");

            json.append("  \"roads\": [\n");
            for (int i = 0; i < roads.size(); i++) {
                json.append("    ").append(roads.get(i));
                if (i < roads.size() - 1) json.append(",");
                json.append("\n");
            }
            json.append("  ],\n");

            json.append("  \"buildings\": [\n");
            for (int i = 0; i < buildings.size(); i++) {
                json.append("    ").append(buildings.get(i));
                if (i < buildings.size() - 1) json.append(",");
                json.append("\n");
            }
            json.append("  ]\n");

            json.append("}\n");

            writer.write(json.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
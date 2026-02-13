// --------------------------------------------------------
// Manual implementation
// --------------------------------------------------------

package catansim;

import java.util.Random;

/************************************************************/
/**
 * 
 */
public class HardWiredBoard extends Board {
	
	private PlayerID longestRoadHolder = null;
	
	private int longestRoadLength = 0;
	
	private void connect(Node[] nodes, int a, int b) {
	    nodes[a].connectNode(nodes[b]);
	    nodes[b].connectNode(nodes[a]);
	}

	private void assignTileToNodes(Tile tile, Node[] nodes, int... nodeIDs) {
	    for (int id : nodeIDs) {
	        Tile[] nodeTiles = nodes[id].getTiles();
	        for (int i = 0; i < nodeTiles.length; i++) {
	            if (nodeTiles[i] == null) {
	                nodeTiles[i] = tile;
	                break;
	            }
	        }
	    }
	}
	
	public HardWiredBoard() {

	    // --------------------------------------------------
	    // 1️⃣ CREATE TILES (MATCHING IMAGE EXACTLY)
	    // --------------------------------------------------

	    Tile[] tiles = new Tile[19];

	    tiles[13] = new Tile(Resource.BRICK, 9, 13);
	    tiles[14] = new Tile(Resource.BRICK, 8, 14);
	    tiles[15] = new Tile(Resource.WHEAT, 4, 15);

	    tiles[12] = new Tile(Resource.WOOD, 5, 12);
	    tiles[4]  = new Tile(Resource.SHEEP, 11, 4);
	    tiles[5]  = new Tile(Resource.SHEEP, 5, 5);
	    tiles[16] = new Tile(null, 0, 16); // DESERT

	    tiles[11] = new Tile(Resource.WHEAT, 9, 11);
	    tiles[3]  = new Tile(Resource.ORE, 3, 3);
	    tiles[0]  = new Tile(Resource.WOOD, 10, 0);
	    tiles[6]  = new Tile(Resource.SHEEP, 12, 6);
	    tiles[17] = new Tile(Resource.WOOD, 2, 17);

	    tiles[10] = new Tile(Resource.ORE, 6, 10);
	    tiles[2]  = new Tile(Resource.BRICK, 8, 2);
	    tiles[1]  = new Tile(Resource.WHEAT, 11, 1);
	    tiles[18] = new Tile(Resource.SHEEP, 10, 18);

	    tiles[9]  = new Tile(Resource.WOOD, 4, 9);
	    tiles[8]  = new Tile(Resource.ORE, 6, 8);
	    tiles[7]  = new Tile(Resource.WHEAT, 3, 7);

	    // --------------------------------------------------
	    // 2️⃣ CREATE NODES
	    // --------------------------------------------------

	    Node[] nodes = new Node[54];

	    for (int i = 0; i < 54; i++) {
	        nodes[i] = new Node(i, new Tile[3]);
	    }

	    // --------------------------------------------------
	    // 3️⃣ MANUAL NODE WIRING (MATCH IMAGE)
	    // --------------------------------------------------

	    nodes[42].connectNode(nodes[41]);
	    nodes[42].connectNode(nodes[40]);

	    nodes[41].connectNode(nodes[42]);
	    nodes[41].connectNode(nodes[39]);

	    nodes[40].connectNode(nodes[42]);
	    nodes[40].connectNode(nodes[44]);
	    nodes[40].connectNode(nodes[18]);

	    nodes[44].connectNode(nodes[40]);
	    nodes[44].connectNode(nodes[43]);

	    nodes[43].connectNode(nodes[44]);
	    nodes[43].connectNode(nodes[21]);

	    nodes[45].connectNode(nodes[43]);
	    nodes[45].connectNode(nodes[47]);

	    nodes[47].connectNode(nodes[45]);
	    nodes[47].connectNode(nodes[46]);

	    nodes[46].connectNode(nodes[47]);
	    nodes[46].connectNode(nodes[48]);

	    nodes[48].connectNode(nodes[46]);
	    nodes[48].connectNode(nodes[49]);

	    nodes[49].connectNode(nodes[48]);
	    nodes[49].connectNode(nodes[50]);

	    nodes[50].connectNode(nodes[49]);
	    nodes[50].connectNode(nodes[51]);

	    nodes[51].connectNode(nodes[50]);
	    nodes[51].connectNode(nodes[52]);

	    nodes[52].connectNode(nodes[51]);
	    nodes[52].connectNode(nodes[53]);

	    nodes[53].connectNode(nodes[52]);

	    nodes[39].connectNode(nodes[41]);
	    nodes[39].connectNode(nodes[38]);

	    nodes[38].connectNode(nodes[39]);
	    nodes[38].connectNode(nodes[37]);

	    nodes[37].connectNode(nodes[38]);
	    nodes[37].connectNode(nodes[36]);

	    nodes[36].connectNode(nodes[37]);
	    nodes[36].connectNode(nodes[35]);

	    nodes[35].connectNode(nodes[36]);
	    nodes[35].connectNode(nodes[34]);

	    nodes[34].connectNode(nodes[35]);
	    nodes[34].connectNode(nodes[33]);

	    nodes[33].connectNode(nodes[34]);
	    nodes[33].connectNode(nodes[32]);

	    nodes[32].connectNode(nodes[33]);
	    nodes[32].connectNode(nodes[31]);

	    nodes[31].connectNode(nodes[32]);
	    nodes[31].connectNode(nodes[30]);

	    nodes[30].connectNode(nodes[31]);
	    nodes[30].connectNode(nodes[29]);

	    nodes[29].connectNode(nodes[30]);
	    nodes[29].connectNode(nodes[28]);

	    nodes[28].connectNode(nodes[29]);
	    nodes[28].connectNode(nodes[27]);

	    nodes[27].connectNode(nodes[28]);
	    nodes[27].connectNode(nodes[26]);

	    nodes[26].connectNode(nodes[27]);
	    nodes[26].connectNode(nodes[25]);

	    nodes[25].connectNode(nodes[26]);
	    nodes[25].connectNode(nodes[24]);

	    nodes[24].connectNode(nodes[25]);
	    nodes[24].connectNode(nodes[23]);

	    nodes[23].connectNode(nodes[24]);
	    nodes[23].connectNode(nodes[22]);

	    nodes[22].connectNode(nodes[23]);
	    nodes[22].connectNode(nodes[20]);

	    nodes[20].connectNode(nodes[22]);
	    nodes[20].connectNode(nodes[19]);

	    nodes[19].connectNode(nodes[20]);
	    nodes[19].connectNode(nodes[21]);

	    nodes[21].connectNode(nodes[19]);
	    nodes[21].connectNode(nodes[43]);

	    nodes[18].connectNode(nodes[40]);
	    nodes[18].connectNode(nodes[17]);
	    nodes[18].connectNode(nodes[16]);

	    nodes[17].connectNode(nodes[18]);
	    nodes[17].connectNode(nodes[15]);

	    nodes[16].connectNode(nodes[18]);
	    nodes[16].connectNode(nodes[5]);

	    nodes[15].connectNode(nodes[17]);
	    nodes[15].connectNode(nodes[14]);

	    nodes[14].connectNode(nodes[15]);
	    nodes[14].connectNode(nodes[13]);

	    nodes[13].connectNode(nodes[14]);
	    nodes[13].connectNode(nodes[12]);

	    nodes[12].connectNode(nodes[13]);
	    nodes[12].connectNode(nodes[11]);

	    nodes[11].connectNode(nodes[12]);
	    nodes[11].connectNode(nodes[10]);

	    nodes[10].connectNode(nodes[11]);
	    nodes[10].connectNode(nodes[9]);

	    nodes[9].connectNode(nodes[10]);
	    nodes[9].connectNode(nodes[8]);

	    nodes[8].connectNode(nodes[9]);
	    nodes[8].connectNode(nodes[7]);

	    nodes[7].connectNode(nodes[8]);
	    nodes[7].connectNode(nodes[6]);

	    nodes[6].connectNode(nodes[7]);
	    nodes[6].connectNode(nodes[1]);

	    nodes[5].connectNode(nodes[16]);
	    nodes[5].connectNode(nodes[4]);
	    nodes[5].connectNode(nodes[0]);

	    nodes[4].connectNode(nodes[5]);
	    nodes[4].connectNode(nodes[3]);

	    nodes[3].connectNode(nodes[4]);
	    nodes[3].connectNode(nodes[2]);

	    nodes[2].connectNode(nodes[3]);
	    nodes[2].connectNode(nodes[1]);

	    nodes[1].connectNode(nodes[2]);
	    nodes[1].connectNode(nodes[6]);
	    nodes[1].connectNode(nodes[0]);

	    nodes[0].connectNode(nodes[1]);
	    nodes[0].connectNode(nodes[5]);

	    // --------------------------------------------------
	    // 4️⃣ ASSIGN CENTER TILE (Tile ID 0)
	    // --------------------------------------------------

	    nodes[0].addTile(tiles[0]);
	    nodes[1].addTile(tiles[0]);
	    nodes[5].addTile(tiles[0]);
	    nodes[6].addTile(tiles[0]);
	    nodes[16].addTile(tiles[0]);
	    nodes[18].addTile(tiles[0]);
	}

	
	private int dfsLongest(Node node, PlayerID playerID, java.util.Set<Road> visited) {

	    int maxLength = 0;

	    for (Road road : node.getConnectedRoads()) {

	        if (road.getOwnerPlayerID() != playerID) {
	            continue;
	        }

	        if (visited.contains(road)) {
	            continue;
	        }

	        visited.add(road);

	        for (Node neighbour : node.getNeighbours()) {

	            for (Road neighbourRoad : neighbour.getConnectedRoads()) {

	                if (neighbourRoad == road) {

	                    int length = 1 + dfsLongest(neighbour, playerID, visited);

	                    if (length > maxLength) {
	                        maxLength = length;
	                    }

	                    break;
	                }
	            }
	        }

	        visited.remove(road); // backtrack
	    }

	    return maxLength;
	}


	
	@Override
	public PlayerID checkLongestRoad() {

	    PlayerID newHolder = longestRoadHolder;
	    int newLongest = longestRoadLength;

	    for (PlayerID player : PlayerID.values()) {

	        int playerMax = 0;

	        for (Node node : getNodes()) {
	            int length = dfsLongest(node, player, new java.util.HashSet<Road>());
	            if (length > playerMax) {
	                playerMax = length;
	            }
	        }

	        // Must be at least 5 to qualify
	        if (playerMax >= 5) {

	            if (longestRoadHolder == null || playerMax > newLongest) {
	                newHolder = player;
	                newLongest = playerMax;
	            }

	            // If equal, do nothing — current holder keeps it
	        }
	    }

	    longestRoadHolder = newHolder;
	    longestRoadLength = newLongest;

	    return longestRoadHolder;
	}


	private boolean canPlaceBuilding(BuildingTypes buildingType, PlayerID playerID, Node node) {
		// check if placing a city on settlement or settlement on settlement
		if (node.getBuilding() != null || buildingType == BuildingTypes.CITY) {
			return false;
		} else if (buildingType == BuildingTypes.SETTLEMENT && node.getBuilding().getOwnerPlayerID() != playerID) {
			return false;
		}
		
		boolean hasRoad = true;
		
		// check for nearby road
		for (Road road : node.getConnectedRoads()) {
			if (road.getOwnerPlayerID() == playerID) {
				hasRoad = true;
				break;
			} else {
				hasRoad = false;
			}
		}
		
		if (!hasRoad) {
			return false; // no player road nearby
		}
		
		// check if can place piece based on if nearby node has a settlement
		for (Node curNode : node.getNeighbours()) {
			if (curNode.getBuilding() == null) {
				continue;
			} else {
				return false; // (settlement a node away)
			}
		}
		
		return true;
	}
	
	private boolean canPlaceRoad(PlayerID playerID, Node nodeStart, Node nodeEnd) {
	    boolean hasConnectedRoad = false;

	    for (Road curRoad : nodeStart.getConnectedRoads()) { // check that there is a root road
	        if (curRoad.getOwnerPlayerID() == playerID) {
	        	hasConnectedRoad = true;
	            break;
	        }
	    }
	    
	    if (!hasConnectedRoad) {
	        return false;
	    }
	    
	    boolean isNeighbor = true; 
	    
	    for (Node neighbor : nodeStart.getNeighbours()) { // check if nodes are neighbors
	    	if (neighbor == nodeEnd) {
	    		isNeighbor = true;
	    		break;
	    	} else {
	    		isNeighbor = false;
	    	}
	    }
	    
	    if (!isNeighbor) {
	    	return false;
	    }

	    for (Road startNodeRoad : nodeStart.getConnectedRoads()) { // must be last operation (O(n^2)) checks if roads already exist
	        for (Road endNodeRoad : nodeEnd.getConnectedRoads()) {
	            if (startNodeRoad == endNodeRoad) {
	                return false; // road already exists
	            }
	        }
	    }
	    
	    return true;
	}
	
	public boolean canPlace(BuildingTypes placedbuildingType, PlayerID playerID, Node nodeStart, Node nodeEnd) {
		return canPlaceRoad(playerID, nodeStart, nodeEnd);
	}
	
	public boolean canPlace(BuildingTypes placedbuildingType, PlayerID playerID, Node nodeToPlaceOn) {
		return canPlaceBuilding(placedbuildingType, playerID, nodeToPlaceOn);
	}

	@Override
	public Catalog<Resource> collect(int diceNum, PlayerID playerID) {
		Catalog<Resource> buildingCollection = new MapCatalog<Resource>();
		
		for (Node curNode : getNodes()) {
			if (curNode.getBuilding() != null) {
				if (curNode.getBuilding().getOwnerPlayerID() == playerID) {
					for (Tile tile : curNode.getTiles()) {
						if (tile.getDiceNum() == diceNum) {
							buildingCollection.add(tile.getResource(), curNode.getBuilding().getResourceAmount());
						}
					}
				}
			}
		}
		
		return buildingCollection;
	}
	
	@Override
	public Catalog<Resource> collectFirst(PlayerID playerID, Node node) {
		Catalog<Resource> buildingCollection = new MapCatalog<Resource>();
		
		for (Tile tile : node.getTiles()) {
			buildingCollection.add(tile.getResource(), node.getBuilding().getResourceAmount());
		}
		
		return buildingCollection;
	}
	
	@Override
	public void placePiece(Road road, PlayerID playerID, Node nodeOne, Node nodeTwo) {
		nodeOne.addRoad(road);
		nodeTwo.addRoad(road);
	}
	
	@Override
	public void placePiece(Building building, PlayerID playerID, Node node) {
		node.placeBuilding(building);
	}
}

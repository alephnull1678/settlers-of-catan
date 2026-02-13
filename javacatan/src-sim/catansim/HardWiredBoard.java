// --------------------------------------------------------
// Manual implementation
// --------------------------------------------------------

package catansim;

import java.util.random.*;

/************************************************************/
/**
 * 
 */
public class HardWiredBoard extends Board {
	
	private PlayerID longestRoadHolder = null;
	
	private int longestRoadLength = 0;

	
	public HardWiredBoard() {
		
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
	public Catalog collect(PlayerID playerID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void placePiece(Road road, PlayerID playerID, Node nodeOne, Node nodeTwo) {
		nodeOne.addRoad(road);
		nodeTwo.addRoad(road);
	}
	
	public void placePiece(Building building, PlayerID playerID, Node node) {
		node.placeBuilding(building);
	}
}

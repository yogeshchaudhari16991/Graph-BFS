
import java.util.List;

import GeographicPoint;

public class MapNode {
	private GeographicPoint location;
	private List<MapEdge> neighbors;
	
	public MapNode(){
		//construct new empty MapNode
	}
	
	public MapNode(GeographicPoint location, List<MapEdge> neighbors){
		this.location = location;
		this.neighbors = neighbors;
	}
	
	public GeographicPoint getLocation(){
		return location;
	}
	
	public List<MapEdge> getNeighbor(){
		return neighbors;
	}
	
	public boolean addNeighbor(MapEdge edge){
		if(! neighbors.contains(edge))
			if(neighbors.add(edge))
				return true;
		return false;
	}
	
	public int getNumOfNeighbors(){
		return neighbors.size();
	}
}

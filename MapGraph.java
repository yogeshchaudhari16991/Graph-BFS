
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Vector;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import GeographicPoint;



public class MapGraph {
	
	private HashMap<GeographicPoint, MapNode> vertices;
	private Vector visited;
	private HashMap<GeographicPoint, GeographicPoint> parent;
	private List<GeographicPoint> path;
	private PriorityQueue<GeographicPoint> queue;
	
	
	public MapGraph()
	{
		vertices = new HashMap<GeographicPoint, MapNode>();
		visited = new Vector();
		parent = new HashMap<GeographicPoint, GeographicPoint>();
		path = new ArrayList<GeographicPoint>();
		queue = new PriorityQueue<GeographicPoint>();
	}
	
	public int getNumVertices()
	{
		return vertices.size();
	}
	
	public Set<GeographicPoint> getVertices()
	{
		return vertices.keySet();
	}
	
	public int getNumEdges()
	{
		Set<GeographicPoint> verticesSet = getVertices();
		int numberOfVertices = getNumVertices();
		int numEdges = 0;
		for(GeographicPoint location : verticesSet){
			MapNode vertex = vertices.get(location);
			numEdges += vertex.getNumOfNeighbors();
		}
		return numEdges;
	}

	
	public boolean addVertex(GeographicPoint location)
	{
		if(location != null){
			if(! vertices.containsKey(location)){
				MapNode vertex = new MapNode(location, new ArrayList<MapEdge>());
				vertices.put(location, vertex);
				return true;
			}
		}
		return false;
	}
	
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
			if(from != null && to !=  null && roadName != null && roadType != null
					&& vertices.containsKey(from) && vertices.containsKey(to) && length > 0){
				MapEdge edge = new MapEdge(from, to, roadName, length, roadType);
				MapNode vertex = vertices.get(from);
				vertex.addNeighbor(edge);
				vertices.put(from, vertex);
			}
			else{
				throw new IllegalArgumentException();
			}
	}
	

	

	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal)
	{
		if(start.equals(goal)){
			path.add(goal);
			return getPath();
		}
		else
		{
			path.add(start);
			visited.add(start);
			MapNode current = vertices.get(start);
			for(MapEdge neighbor : current.getNeighbor()){
				GeographicPoint next = neighbor.getEnd();
				if(! visited.contains(next)){
					//add to queue
					queue.add(next);
					//add parent
					parent.put(next, start);
				}
			}
			if(!queue.isEmpty()){
				GeographicPoint nextVertex = queue.remove();
				return bfs(nextVertex, goal, nodeSearched);
			}
			path.remove(start);
			return path;
		}
	}
	
	public List<GeographicPoint> getPath(){
		List<GeographicPoint> pathToVertex = new ArrayList<GeographicPoint>();
		GeographicPoint goal = path.get(path.size()-1);
		pathToVertex.add(goal);
		for(int i=(path.size()-1); i >= 0; i--){
			//backtracking path from goal to start
			if(!pathToVertex.contains(parent.get(path.get(i))) && parent.get(path.get(i)) != null){
				pathToVertex.add(parent.get(path.get(i)));
			}
		}
		//reverse the path to get Path from start to goal
		List<GeographicPoint> pathToGoal = new ArrayList<GeographicPoint>();
		for(int i = pathToVertex.size()-1; i>=0; i--) {
			pathToGoal.add(pathToVertex.get(i));
		}
		return pathToGoal;
	}

	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		//add graph details to map and call bfs.
		
	}
	
}

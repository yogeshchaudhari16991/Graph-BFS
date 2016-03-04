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
	private Vector<GeographicPoint> visited;
	private HashMap<GeographicPoint, GeographicPoint> parent;
	private List<GeographicPoint> path;
	private PriorityQueue<GeographicPoint> queue;
	/** 
	 * Create a new empty MapGraph 
	 */
	
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
		     GeographicPoint goal){
		visited.add(start);
		queue.add(start);
		while(! queue.isEmpty()){
			GeographicPoint current = queue.remove();
			visited.add(current);
			if(current.equals(goal)){
				return getPath();
			}
			for(MapEdge e : vertices.get(current).getNeighbor()){
				GeographicPoint next = e.getEnd();
				if((! visited.contains(next)) && (! queue.contains(next))){
					parent.put(next, current);
					queue.add(next);
				}
			}
		}
		return null;
	}
	
	public List<GeographicPoint> getPath(){
		List<GeographicPoint> pathToVertex = new ArrayList<GeographicPoint>();
		GeographicPoint goal = visited.get((visited.size()-1));
		pathToVertex.add(goal);
		for(int i=(visited.size()-1); i >= 0; i--){
			if(!pathToVertex.contains(parent.get(visited.get(i))) && parent.get(visited.get(i)) != null){
				pathToVertex.add(parent.get(visited.get(i)));
			}
		}
		//reversing path, so it starts from start vertex and ends at goal vertex
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
		System.out.print("DONE. \n Load the map...Call BFS(Start, Goal)");
	}
	
}

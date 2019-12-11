import java.util.*;

public class Nodo {
	
	private int ID;
	private List<Nodo> shortestPath = new LinkedList<>();
	private Integer distance = Integer.MAX_VALUE;
	Map<Nodo, Integer> adjacentNodes = new HashMap<>();
	
	public void addDestino(Nodo destino, int dist){
		adjacentNodes.put(destino,  dist);
	}
	
	public void setDistance(Integer dist){
		this.distance = dist;
	}
	
	public Integer getDistance(){
		return distance;
	}
	public Map<Nodo, Integer> getAdjacentNodos(){
		return adjacentNodes;
	}
	
	public List<Nodo> getShortestPath(){
		return shortestPath;
	}
	
	public void setShortestPath(List<Nodo> newShortest){
		this.shortestPath = newShortest;
	}
	public Nodo(int ID) {
		this.ID = ID;
	}
}

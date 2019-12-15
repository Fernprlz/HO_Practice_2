package parte_2;
import java.util.*;
public class Grafo {
	
	private Set<Nodo> nodos = new HashSet<>();
	
	public Grafo() {
		
	}
	
	public void addNodo(Nodo nodo) {
		nodos.add(nodo);
	}
	
	/*
	 * byte[][] grafo;
	public boolean isAdjacent(int sourceP, int targetP) {
		return grafo[sourceP][targetP] > 0;
	}
	
	public ArrayList<Integer>  getAdjacents(int sourceID) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int ii = 0; ii < grafo.length; ii++) {
			if(grafo[sourceID][ii] > 0) {
				result.add(ii);
			}
		}
		return result;
	}
	*/
	
	
}

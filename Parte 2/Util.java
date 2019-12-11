import java.util.*;
public class Util {

	/**
	 * 
	 * @param estado
	 * @return
	 */
	public static int calcHeuristic(Estado estado) {
		int costeC1 = estado.getGuagua().getC1() * calcDist();
		int costeC2 = estado.getGuagua().getC1() * calcDist();
		int costeC3 = estado.getGuagua().getC1() * calcDist();

		return  costeC1 + costeC2 + costeC3;
	}


	public static int calcDist(){




		return 1;
	}


	public static Grafo calculateShortestPathFromSource(Grafo graph, Nodo source) {
		source.setDistance(0);
		Set<Nodo> settledNodos = new HashSet<>();
		Set<Nodo> unsettledNodos = new HashSet<>();
		unsettledNodos.add(source);
		while (unsettledNodos.size() != 0) {
			Nodo currentNodo = getLowestDistanceNodo(unsettledNodos);
			unsettledNodos.remove(currentNodo);
			for (Map.Entry<Nodo, Integer> adjacencyPair: 
				currentNodo.getAdjacentNodos().entrySet()) {
				Nodo adjacentNodo = adjacencyPair.getKey();
				Integer edgeWeight = adjacencyPair.getValue();
				if (!settledNodos.contains(adjacentNodo)) {
					calculateMinimumDistance(adjacentNodo, edgeWeight, currentNodo);
					unsettledNodos.add(adjacentNodo);
				}
			}
			settledNodos.add(currentNodo);
		}
		return graph;
	}

	private static Nodo getLowestDistanceNodo(Set < Nodo > unsettledNodos) {
		Nodo lowestDistanceNodo = null;
		int lowestDistance = Integer.MAX_VALUE;
		for (Nodo node: unsettledNodos) {
			int nodeDistance = node.getDistance();
			if (nodeDistance < lowestDistance) {
				lowestDistance = nodeDistance;
				lowestDistanceNodo = node;
			}
		}
		return lowestDistanceNodo;
	}

	private static void calculateMinimumDistance(Nodo evaluationNodo,
			Integer edgeWeigh, Nodo sourceNodo) {
		Integer sourceDistance = sourceNodo.getDistance();
		if (sourceDistance + edgeWeigh < evaluationNodo.getDistance()) {
			evaluationNodo.setDistance(sourceDistance + edgeWeigh);
			LinkedList<Nodo> shortestPath = new LinkedList<>(sourceNodo.getShortestPath());
			shortestPath.add(sourceNodo);
			evaluationNodo.setShortestPath(shortestPath);
		}
	}

	/*---------------------------- I N P U T  P A R S E R ----------------------------*/
	
	

}

package parte_2;
import java.util.*;
public class Util {

	static final int INFINITE = -1;
	static int NUM_PARADAS;
	static int NUM_COLEGIOS;
	static int[][] costesAdyacentes;
	static int[] indexParadaColegio;

	// TODO: Cambiar input2 a input cuando terminemos para pasarle el argumento
	public static Estado initState(String input2) {
		// TODO: DE MOMENTO USAMOS LA STRING COMO VARIABLE EN LUGAR DE COMO ENTRADA
		String input = "    P1  P2  P3  P4  P5  P6  P7  P8  P9\r\n" +
				"P1  --  12  --  --  --  --  --  --   9\r\n" +
				"P2  12  --  13  --  10  --  --  --  --\r\n" +
				"P3  --  13  --  13  --  --  --  --   6\r\n" +
				"P4  --  --  13  --   8  --  --   9  --\r\n" +
				"P5  --  10  --   8  --  14  --   5  --\r\n" +
				"P6  --  --  --  --  14  --   7  --  --\r\n" +
				"P7  --  --  --  --  --   7  --   6  --\r\n" +
				"P8  --  --  --   9   5  --   6  --  --\r\n" +
				"P9   9  --   6  --  --  --  --  --  --\r\n" +
				"\r\n" +
				"\r\n" +
				"C1: P6; C2: P3; C3: P8\r\n" +
				"P2: 1 C2, 1 C3; P4: 1 C3; P5: 1 C2; P7: 2 C1, 1 C2\r\n" +
				"B: P1 5";

		// Aplicamos el primer tratamiento convirtiendo toda la string a lowerCase
		input = input.toLowerCase();

		// Asumimos que la heuristica a ejecutar se encuentra en el segundo argumento.
		//String heuristica = args[1];

		// Para inicializar la matriz con sus dimensiones, contamos las P's hasta el \n
		char readChar;
		int index = 0;
		int counter = 0;
		do{
			readChar = input.charAt(index);
			if (readChar == 'p'){
				counter++;
			}
			index++;
		}while(readChar != '\n');
		NUM_PARADAS = counter;

		// Inicializamos la matriz de costes de los adyacentes
		costesAdyacentes = new int [NUM_PARADAS][NUM_PARADAS];


		// Con este dato podemos inicializar el objeto

		// Separamos la string en 2: la matriz de costes y los datos de los colegios
		String stringOtrosDatos = input.substring(input.indexOf('c'));
		String stringCostes = input.substring(0, input.indexOf('c'));
		// Elimino los headers de la matriz
		stringCostes = stringCostes.replaceAll("[a-zA-Z]+\\d{1}", "");
		stringCostes = stringCostes.trim();
		// Creo un array con las entradas de la matriz
		String[] splitCostes = stringCostes.split("[ \\r\\n]+");

		int splitCostesIndex = 0;
		for (int row = 0; row < costesAdyacentes.length; row++) {
			for (int col = 0; col < costesAdyacentes[row].length; col++, splitCostesIndex++) {
				// Si encontramos un "--" grabamos un "infinito" (m�ximo numero representable)
				// Si el n�mero es v�lido lo guardamos.
				costesAdyacentes[row][col] = (splitCostes[splitCostesIndex].equals("--")) ? INFINITE : Integer.parseInt(splitCostes[splitCostesIndex]);
			}
		}

		// Ya tenemos los datos de los costes, ahora parseamos los datos de los colegios
		// Primero separamos en lineas, cada una guarda una informacion distinta.
		String[] lineasOtrosDatos = stringOtrosDatos.split("[\\r\\n]+");

		// Parseamos la primera, que nos dice en qu� parada se encuentra cada colegio.
		lineasOtrosDatos[0] = lineasOtrosDatos[0].replaceAll("[ a-z]+", "");

		String[] ubicacionColegios = lineasOtrosDatos[0].split(";");

		// El tama�o de esta array nos dir� cuantos colegios hay, con lo que podremos inicializar las arrays de colegios de TODO PARADA Y UTIL
		NUM_COLEGIOS = ubicacionColegios.length;

		// Inicializamos el array de indices para acceder a las paradas que contienen los colegios m�s facilmente.
		indexParadaColegio = new int[NUM_COLEGIOS];


		// Definimos el array de paradas que se guardar� en el estado a devolver
		Parada[] paradas = new Parada[NUM_PARADAS];

		int indexPC = 0;
		// Llenamos los datos de cada parada
		for (int indexParada = 0; indexParada < paradas.length; indexParada++) {
			// Creamos el array de colegios en la parada actual a partir de la
			// primera linea de datos
			boolean[] colegiosEnParada = new boolean[NUM_COLEGIOS];
			for (int parada = 0; parada < ubicacionColegios.length; parada++) {
				if(Character.getNumericValue(ubicacionColegios[parada].charAt(2) - 1) == indexParada) {
					indexParadaColegio[indexPC] = indexParada;
					indexPC++;
					colegiosEnParada[(Character.getNumericValue(ubicacionColegios[parada].charAt(0)) - 1)] = true;
				}
			}

			// Creamos el array de alumnos por colegio en la parada actual a
			// partir de la segunda linea de datos
			int[] alumnosPorColegio = new int[NUM_COLEGIOS];

			// Tratamos la string eliminando tanto las P's como los espacios, pero dejando las
			lineasOtrosDatos[1] = lineasOtrosDatos[1].replaceAll("[d-z ]+", "");

			String[] alumnosEnParada = lineasOtrosDatos[1].split(";");

			// El primer caracter nos dice la parada a la que se refiere, as� que buscamos la correspondiente
			// a indexParada
			for(int ii = 0; ii < alumnosEnParada.length; ii++) {
				if(Character.getNumericValue(alumnosEnParada[ii].charAt(0) - 1) == indexParada) {
					// Creamos otra array para agrupar el n�mero de alumnos con su colegio
					String[] aux = alumnosEnParada[ii].substring(alumnosEnParada[ii].indexOf(':') + 1).split(",");
					for (int jj = 0; jj < aux.length; jj++) {
						alumnosPorColegio[Character.getNumericValue(aux[jj].charAt(2) - 1)] = Character.getNumericValue(aux[jj].charAt(0));
					}
				}
			}

			// Creamos el objeto parada que ira en el indice actual del array de paradas
			Parada parada = new Parada(alumnosPorColegio, colegiosEnParada);

			// Guardamos la parada en el array
			paradas[indexParada] = parada;

		}
		// A partir de la tercera linea, creamos un array de 2 posiciones, la primera representa
		// la parada en la que se encuentra la guagua, la segunda su capacidad m�xima.
		String[] datosGuagua = lineasOtrosDatos[2].replaceAll("b: p", "").split(" ");

		// Creamos la guagua asignandole la parada inicial como parada actual
		Guagua guagua = new Guagua(Integer.parseInt(datosGuagua[0]) - 1, NUM_COLEGIOS);

		// Le damos el valor a la CAP de la guagua
		Guagua.CAP = Integer.parseInt(datosGuagua[1]);

		return new Estado(paradas, guagua);
	}
	
	// Metodo para construir el estado final a partir del inicial
	public static Estado finalState(Estado estadoInicial) {
		Parada[] paradas = new Parada [NUM_PARADAS];
		for (int i = 0; i < paradas.length; i++) {
			boolean[] finalColegios = estadoInicial.paradas[i].colegiosEnParada.clone();
			int[] finalAlumnosParada = new int [NUM_COLEGIOS];
			paradas[i]= new Parada(finalAlumnosParada, finalColegios);		
		}
		
		int posicion = estadoInicial.guagua.indexParadaActual;
		Guagua guagua = new Guagua(posicion, NUM_COLEGIOS);
		return new Estado (paradas, guagua);
	}




	/**
	 *
	 * @param estado
	 * @return
	 */
	public static int calcHeuristic(Parada[] paradas, Guagua guagua) {
		/*int costeC1 = estado.getGuagua().getC1() * calcDist();
		int costeC2 = estado.getGuagua().getC1() * calcDist();
		int costeC3 = estado.getGuagua().getC1() * calcDist();

		return  costeC1 + costeC2 + costeC3;*/
		return 1;
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


	public static boolean isInList(Estado estado, ArrayList<Estado> lista){
		boolean isInList = false;
		for (int ii=0; ii<lista.size(); ii++){
			if (lista.get(ii).compararEstadoCon(estado)==true) {
				isInList = true;
			}
		}
		return isInList;
	}

	public static void quickSort(ArrayList<Estado> list) {
	}

}
package parte_2;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Util {

	static final int INFINITE = -1;
	static int NUM_PARADAS;
	static int NUM_COLEGIOS;
	static int[][] costesAdyacentes;
	static int[] indexParadaColegio;
	static int HEURISTICA = 2;
	static int[][] FWMatrix;

	// TODO: Cambiar input2 a input cuando terminemos para pasarle el argumento

	/**
	 * Parsea el archivo de entrada y genera el estado inicial.
	 * @param input2
	 * @return Estado inicial con los datos del archivo de entrada
	 */
	public static Estado initState(String input2) {
		// TODO: DE MOMENTO USAMOS LA STRING COMO VARIABLE EN LUGAR DE COMO ENTRADA
		String input = "";
		
		try {
		input = Files.readString(Paths.get(input2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(input);
		
		/*String input = "    P1  P2  P3  P4  P5  P6  P7  P8  P9\r\n" +
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
				// Si el numero es valido lo guardamos.
				costesAdyacentes[row][col] = (splitCostes[splitCostesIndex].equals("--")) ? INFINITE : Integer.parseInt(splitCostes[splitCostesIndex]);
			}
		}

		// Ya tenemos los datos de los costes, ahora parseamos los datos de los colegios
		// Primero separamos en lineas, cada una guarda una informacion distinta.
		String[] lineasOtrosDatos = stringOtrosDatos.split("[\\r\\n]+");

		// Parseamos la primera, que nos dice en que parada se encuentra cada colegio.
		lineasOtrosDatos[0] = lineasOtrosDatos[0].replaceAll("[ a-z]+", "");

		String[] ubicacionColegios = lineasOtrosDatos[0].split(";");

		// El tama�o de esta array nos dira cuantos colegios hay, con lo que podremos inicializar las arrays de colegios de TODO PARADA Y UTIL
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
		
		// Creamos la matriz de costes entre cada par de Paradas
		FloydWarshall();
		return new Estado(paradas, guagua);*/
		return new Estado();
	}


	/**
	 * Construye el estado final reutilizando el array de colegios por parada del estado inicial.
	 * @param estadoInicial
	 * @return Estado final
	 */
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
	 * Calcula una heur�stica
	 * @param estado
	 * @return
	 */
	public static int calcHeuristic(Parada[] paradas, Guagua guagua) {
		int coste = 0;
		// La heuristica numero 1 se har� con la matriz de Floyd-Warshall y los alumnos en la guagua
		if (HEURISTICA == 1) {
			for (int j = 0; j < guagua.alumnosPorColegio.length; j++) {
				coste += guagua.alumnosPorColegio[j] * FWMatrix[guagua.indexParadaActual][Util.indexParadaColegio[j]];		
			}		
			
			for (int i = 0; i < paradas.length; i++) {
				for (int j = 0; j < NUM_COLEGIOS; j++) {
					coste += paradas[i].alumnosPorColegio[j] * FWMatrix[guagua.indexParadaActual][Util.indexParadaColegio[j]];
				}
			}
		}
		// La heuristica numero 2 ser� la que toma el n�mero de alumnos restantes por entregar
		if (HEURISTICA == 2) {
			for (int i = 0; i < NUM_PARADAS; i++) {
				for (int j = 0; j < NUM_COLEGIOS; j++) {
					coste += paradas[i].alumnosPorColegio[j];
				}
			}
			for (int i = 0; i < NUM_COLEGIOS; i++) {
				coste += guagua.alumnosPorColegio[i];
			}
		}

		return coste;
	}

	/**
	 * Floyd-Warshall Algorythm
	 * @return
	 */
	public static void FloydWarshall() {
		FWMatrix = costesAdyacentes.clone();
		for (int i = 0; i < FWMatrix.length; i++) {
			for (int j = 0; j < FWMatrix[i].length; j++) {
				if (i==j) {
					FWMatrix[i][j] = 0;
				}
				else if (FWMatrix[i][j] == INFINITE && i!=j) {
					FWMatrix[i][j] = 500;
				}
			}
		}
		for (int i = 0; i < FWMatrix.length; i++) {
			for (int j = 0; j < FWMatrix.length; j++) {
				for (int k = 0; k < FWMatrix.length; k++) {
					FWMatrix[j][k] = Math.min(FWMatrix[j][k], FWMatrix[j][i] + FWMatrix[i][k]);
				}	
			}
		}
		//Main.printMatrix(FWMatrix);
		
	}





	/**
	 * Comprueba que un estado ya est� en la lista, pero aparece con mayor heur�stica. 
	 * En ese caso la elimina para que pueda a�adirse la mejor versi�n.
	 * @param estado
	 * @param lista
	 * @return
	 */
	public static boolean isButBetter(Estado estado, ArrayList<Estado> lista){
		boolean isInList = false;
		ByHeuristics heur = new ByHeuristics();
		// Buscamos por fuerza bruta el estado en la lista de estados.
		int coincidenceIndex = 0;
		for (; coincidenceIndex < lista.size(); coincidenceIndex++){
			if (lista.get(coincidenceIndex).compararEstadoCon(estado)==true) {
				isInList = true;
				break;
			}
		}

		// Si lo hemos encontrado, comparamos las heur�sticas
		if(isInList) {
			int diff = heur.compare(lista.get(coincidenceIndex), estado);
			// Si la heur�stica del nuevo estado es mejor, eliminamos su anterior iteracion de la lista 
			// No introducimos aqui el nuevo estado, pues se introducira desde el algoritmo
			if (diff > 0) {
				lista.remove(coincidenceIndex);
			}
		}

		return isInList;
	}

	//TODO: Es eficiente?
	/**
	 * Comprueba que un estado ya est� en la lista comparando todos los campos del objeto
	 * @param estado
	 * @param lista
	 * @return
	 */
	public static boolean isInList(Estado estado, ArrayList<Estado> lista){
		boolean isInList = false;
		for (int ii=0; ii<lista.size(); ii++){
			if (lista.get(ii).compararEstadoCon(estado)==true) {
				isInList = true;
				break;
			}
		}
		return isInList;
	}

	/**
	 * Ordena una lista de estados seg�n la heur�stica
	 * @param Lista a ordenar
	 */
	public static void sort(ArrayList<Estado> list) {
		Collections.sort(list, new ByHeuristics());
	}

	/**
	 * Introduce los elementos de la lista guest en la lista host maneteniendo el orden creciente de heuristicas de estado.
	 * @param host
	 * @param guest
	 */
	public static void mergeListsInOrder(ArrayList<Estado> host, ArrayList<Estado> guest) {
		// Instancio la clase ByHeuristics para aprovechar el metodo compare
		ByHeuristics heur = new ByHeuristics();

		// Recorro la lista peque�a y voy insertando uno a uno sus elementos en la grande, ordenadamente.
		for (int guestIndex = 0; guestIndex < guest.size(); guestIndex++) {
			// Comparo con cada uno de los elementos de la lista grande hasta encontrar su lugar.
			boolean added = false;
			for (int hostIndex = 0; hostIndex < host.size(); hostIndex++) {
				// Hallo la diferencia entre las heur�sticas
				int diff = heur.compare(host.get(hostIndex), guest.get(guestIndex));
				// Si la diferencia es positiva, el estado de la lista peque�a deber� ocupar el actual lugar
				// del estado en el indice hostIndex de la lista grande.
				if (diff > 0) {
					host.add(hostIndex, guest.get(guestIndex));
					// Tras a�adirla no necesito seguir recorriendo la lista.
					added = true;
					break;
				} 
			}
			// Si tras analizar toda la lista no hemos encontrado sitio, la a�adimos al final
			if (added == false) {
				host.add(guest.get(guestIndex));
			}
		}
	}
}

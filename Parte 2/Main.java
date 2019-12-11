
public class Main {

	static int NUM_PARADAS;
	
	public static void main(String[] args) {
		
		// Asumimos que el input se encuentra en el primer argumento 
		//String input = args[0];
		String input = "    P1  P2  P3  P4  P5  P6\nP1  --   8  --  --  20   5\nP2   8  --  10  --  --  --\nP3  --  10  --   3  --  --\nP4  --  --   3  --  15  --\nP5  20  --  --   15 --   3\nP6   5  --  --  --   3  --\nC1: P3; C2: P4\nP2: 1 C1, 1 C2; P5: 2 C1\nB: P1 5";
		
		// Aplicamos el primer tratamiento convirtiendo toda la string a lowerCase
		input = input.toLowerCase();
		
		// Asumimos que la heuristica a ejecutar se encuentra en el segundo argumento.
		//String heuristica = args[1];
		
		/* De momento guardamos aqui la matriz que nos viene en el input tal cual */
		int[][] weightedAdjMatrix;
			
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
		weightedAdjMatrix = new int [NUM_PARADAS][NUM_PARADAS];
		
		// Vamos, casilla por casilla de la matriz, guardando los costes en la matriz de costes de adyacencia;
		for (int i = 0; i < weightedAdjMatrix.length; i++) {
			
		}
		
		
		// Implementar el modelo
		
		// Implementar A*
		
		System.out.println(counter);

	
	}

}

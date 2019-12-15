
import java.util.ArrayList;
import java.io.*;

public class Main {


	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();

		// Creamos el estado inicial y le asignamos valores sacados del archivo de entrada
		Estado estadoInicial = Util.initState(args[0], args[1]);

		//printMatrix(Util.costesAdyacentes);

		printMatrix(Util.FWMatrix);

		// Generar estado final a partir del inicial
		Estado estadoFinal = Util.finalState(estadoInicial);

		System.out.println("estado inicial");
		System.out.println(estadoInicial.toString());
		System.out.println("Heuristica: " + estadoInicial.h);
		System.out.println();
		System.out.println("Estado final:");
		System.out.println(estadoFinal.toString());

		String camino = "";

		// Implementar A*
		ArrayList<Estado> abierta = new ArrayList<Estado>();
		ArrayList<Estado> cerrada = new ArrayList<Estado>();

		//Lista de sucesores
		ArrayList<Estado> sucesores = new ArrayList<Estado>();

		boolean exito = false;
		Estado nuevoEstado;
		Estado actualEstado;
		abierta.add(estadoInicial);

		int estadosExpandidos = 0;
		int estadosGenerados = 0;
		int costeFinal = 0;

		while (!abierta.isEmpty()) {
			estadosExpandidos++;
			actualEstado = abierta.get(0);

			System.out.println("-------------------------------------------------");

			camino += "P" + (actualEstado.guagua.indexParadaActual+1) +" ";
			System.out.println("ESTADO ACTUAL");
			System.out.println(actualEstado.toString());
			System.out.println("Heuristica: " + actualEstado.h);

			// Comprobar si el estado expandido es el final y en caso de haber encontrado el estado
			// final, parar el while.
			if (actualEstado.compararEstadoCon(estadoFinal) == true) {
				System.out.println("Estado final alcanzado");
				exito = true;
				break;
			} else {
				//  Mover estado actual a la lista cerrada
				cerrada.add(actualEstado);
				abierta.remove(0);
			}

			// Mover guagua: mover(int source, int target)
			// Asignar como source la parada actual de la guagua
			int source = actualEstado.guagua.indexParadaActual;

			// Comprobar todos los posibles desplazamientos
			for (int target=0; target < Util.NUM_PARADAS; target++) {
				// Si las paradas son adyacentes, la guagua se puede mover entre ellas
				if (Util.costesAdyacentes[source][target] > 0){
					nuevoEstado = new Estado (actualEstado, "mover", source, target);
					System.out.println("Moviendo guagua a parada numero " + (target+1));
					System.out.println("NUEVO ESTADO:");
					System.out.println(nuevoEstado.toString());
					System.out.println("Heuristica: " + nuevoEstado.h);
					// Comprobar si el estado generado esta en la lista cerrada
					if (!Util.isInList(nuevoEstado, cerrada)) {
						// Comprobar si el estado generado esta en la lista abierta. Si lo est�, se comparan las funciones de coste
						if (!Util.isButBetter(nuevoEstado, abierta)) {
							// Actualizar variable de coste
							costeFinal =+ Util.costesAdyacentes[source][target];

							System.out.println("no en lista");
							sucesores.add(nuevoEstado);
							estadosGenerados++;
						}
					}
				}
			}

			// Recoger alumno: recoger(int parada, int colegio)
			// Comprueba los alumnos que puede recoger en la parada actual
			for (int colegio = 0; colegio < Util.NUM_COLEGIOS; colegio++) {
				System.out.println("RECOGER: parada " + (source+1) + ", colegio: " + (colegio+1));
				if (actualEstado.paradas[source].alumnosPorColegio[colegio]>0 && actualEstado.guagua.getCapacidadActual()>0) {
					nuevoEstado = new Estado (actualEstado, "recoger", source, colegio);
					System.out.println(nuevoEstado.toString());
					// Comprobar si el estado generado esta en la lista cerrada
					if (!Util.isInList(nuevoEstado, cerrada)) {
						// Comprobar si el estado generado esta en la lista abierta. Si lo est�, se comparan las funciones de coste
						if (!Util.isButBetter(nuevoEstado, abierta)) {
							System.out.println("no en lista");
							sucesores.add(nuevoEstado);
							estadosGenerados++;
						}
					}
				}
			}


			// Entregar alumno: entregar(int parada, int colegio)
			// Comprueba que alumnos puede dejar en la parada actual
			for (int colegio=0; colegio < Util.NUM_COLEGIOS; colegio++) {
				System.out.println("ENTREGAR: parada " + (source+1) + ", colegio: " + (colegio+1));
				if (actualEstado.paradas[source].colegiosEnParada[colegio] == true && actualEstado.guagua.alumnosPorColegio[colegio]>0) {
					System.out.println("ENTREGANDO");
					nuevoEstado = new Estado (actualEstado, "entregar", source, colegio);
					System.out.println(nuevoEstado.toString());
					// Comprobar si el estado generado esta en la lista cerrada
					if (!Util.isInList(nuevoEstado, cerrada)) {
						// Comprobar si el estado generado esta en la lista abierta. Si lo est�, se comparan las funciones de coste
						if (!Util.isButBetter(nuevoEstado, abierta)) {
							sucesores.add(nuevoEstado);
							estadosGenerados++;
						}
					} else System.out.println("Estado en lista cerrada");
				}
			}

			// Ordenar lista de sucesores
			Util.sort(sucesores);

			// Mergear sucesores con la lista abierta. Se introduciran en orden
			Util.mergeListsInOrder(abierta, sucesores);

			// Limpiar la lista de sucesores
			sucesores.clear();
			System.out.println("Lista vacia: " + abierta.isEmpty());
			costeFinal = actualEstado.h;

		}


		//System.out.println("Abierta:");
		//System.out.println(abierta.toString());
		if (exito) {
			System.out.println("Solucion encontrada");
		} else {
			System.out.println("Solucion no encontrada ");
		}
		long execTime = System.currentTimeMillis() - startTime;
		//System.out.println("Paradas visitadas: " + paradasVisitadas);
		String stats = "";
		stats += "Coste total: " + costeFinal + "\n";
		stats += "Estados generados: " + estadosGenerados + "\n";
		stats += "Estados expandidos: " + estadosExpandidos + "\n";
		stats += "Coste total: " + costeFinal + "\n";
		stats += "Tiempo: " + execTime + " ms";

		System.out.println(stats);


		// Crear archivo de salida con el camino seguido por el algoritmo

		try {
		BufferedWriter writer1 = new BufferedWriter(new FileWriter(args[0]+".output"));
		writer1.write(camino);
		BufferedWriter writer2 = new BufferedWriter(new FileWriter(args[0]+".statistics"));
		writer2.write(stats);
		writer1.close();
		writer2.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void printMatrix(int[][] matrix) {
		System.out.print("\t");
		for (int row = 0; row <= matrix.length; row++) {
			for (int col = 0; col <= matrix[0].length; col++) {

				if(row == 0 && col > 0) {
					System.out.print("P"+col+"\t");
				} else if (row > 0 && col == 0) {
					System.out.print("P"+row+"\t");
				} else if (row > 0 && col > 0) {
					System.out.print(matrix[row - 1][col - 1]+"\t");
				}

			}
			System.out.println();
		}
	}

	public static void printArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " - ");

		}
	}

	public static void printArray(boolean[] array) {
		for (int i = 0; i < array.length; i++) {

			System.out.print(array[i] + " - ");

		}
	}

}

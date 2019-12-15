package parte_2;
import java.util.ArrayList;

public class Main {


	public static void main(String[] args) {
		// Creamos el estado inicial y le asignamos valores sacados del archivo de entrada
		Estado estadoInicial = Util.initState("s");

		// Generar estado final a partir del inicial
		Estado estadoFinal = Util.finalState(estadoInicial);
		
		System.out.println("estado inicial");
		System.out.println(estadoInicial.toString());
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
		
		int counter = 0;
		int estadosGenerados = 0;
		
		while (!abierta.isEmpty()) {
			counter++;
			actualEstado = abierta.get(0);
			
			System.out.println("-------------------------------------------------");
			
			camino += "P" + (actualEstado.guagua.indexParadaActual+1) +"\t";
			System.out.println("ESTADO ACTUAL");
			System.out.println(actualEstado.toString());
			
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

			//	Mover guagua: mover(int source, int target)
			// 	Asignar como source la parada actual de la guagua
			int source = actualEstado.guagua.indexParadaActual;
			
			// Comprobar todos los posibles desplazamientos
			for (int target=0; target < Util.NUM_PARADAS; target++) {
				// Si las paradas son adyacentes, la guagua se puede mover entre ellas
				if (Util.costesAdyacentes[source][target] > 0){			
					nuevoEstado = new Estado (actualEstado, "mover", source, target);
					System.out.println("Moviendo guagua a parada numero " + (target+1));
					System.out.println("heuristica: " + nuevoEstado.h);
					System.out.println("NUEVO ESTADO:");
					System.out.println(nuevoEstado.toString());
					if (!Util.isInList(nuevoEstado, cerrada)) {
						if (!Util.isInList(nuevoEstado, abierta)) {
							System.out.println("no en lista");
							sucesores.add(nuevoEstado);
							estadosGenerados++;
						}
					}
				}
			}
			
			//Recoger alumno: recoger(int parada, int colegio)
			// Comprueba los alumnos que puede recoger en la parada actual
			for (int colegio = 0; colegio < Util.NUM_COLEGIOS; colegio++) {
				System.out.println("RECOGER: parada " + (source+1) + ", colegio: " + (colegio+1));
				if (actualEstado.paradas[source].alumnosPorColegio[colegio]>0 && actualEstado.guagua.getCapacidadActual()>0) {
					nuevoEstado = new Estado (actualEstado, "recoger", source, colegio);
					System.out.println(nuevoEstado.toString());
					if (!Util.isInList(nuevoEstado, cerrada)) {
						if (!Util.isInList(nuevoEstado, abierta)) {
							System.out.println("no en lista");
							sucesores.add(nuevoEstado);
							estadosGenerados++;
						}
					}
				}
			}


			//Entregar alumno: entregar(int parada, int colegio)
			// Comprueba que alumnos puede dejar
			for (int colegio=0; colegio < Util.NUM_COLEGIOS; colegio++) {
				System.out.println("ENTREGAR: parada " + (source+1) + ", colegio: " + (colegio+1));
				if (actualEstado.paradas[source].colegiosEnParada[colegio] == true && actualEstado.guagua.alumnosPorColegio[colegio]>0) {
					System.out.println("ENTREGANDO");
					nuevoEstado = new Estado (actualEstado, "entregar", source, colegio);
					System.out.println(nuevoEstado.toString());
					if (!Util.isInList(nuevoEstado, cerrada)) {
						if (!Util.isInList(nuevoEstado, abierta)) {
							sucesores.add(nuevoEstado);					
							estadosGenerados++;
						}
					} else System.out.println("Estado en lista cerrada");
				}
			}
			
			System.out.println("LISTA DE SUCESORES");
			System.out.println("Tamaño de lista de sucesores: " + sucesores.size());
			System.out.println(sucesores.toString());
			Util.sort(sucesores);

			
			//abierta.remove(0);
			Util.mergeListsInOrder(abierta, sucesores);
			//abierta.addAll(sucesores);
			sucesores.clear();
			System.out.println("Lista vacia: " + abierta.isEmpty());
		
		}
		
		//System.out.println("Abierta:");
		//System.out.println(abierta.toString());
		if (exito) {
			System.out.println("Solución encontrada");
		} else {
			System.out.println("Solución no encontrada ");
		}
		System.out.println("Estados generados: " + estadosGenerados);
		System.out.println("Estados expandidos: " + counter);
		System.out.println("Camino: " + camino);
		System.out.println("Cerrada:");
		System.out.println(cerrada.toString());
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

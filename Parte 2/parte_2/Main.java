package parte_2;
import java.util.ArrayList;

public class Main {


	public static void main(String[] args) {
		// Creamos el estado inicial y le asignamos valores sacados del archivo de entrada
		Estado estadoInicial = Util.initState("s");
		Estado estadoFinal = Util.finalState(estadoInicial);
		
		System.out.println("estado inicial");
		System.out.println(estadoInicial.toString());

		System.out.println();
		System.out.println("Estado final:");
		System.out.println(estadoFinal.toString());
		// Implementar el modelo
		

		// Implementar A*
		ArrayList<Estado> abierta = new ArrayList<Estado>();
		ArrayList<Estado> cerrada = new ArrayList<Estado>();
		boolean exito = false;
		Estado nuevoEstado;
		Guagua nuevaGuagua;
		Parada nuevaParada;

		Estado actualEstado;
		abierta.add(estadoInicial);
		
		
		while (!abierta.isEmpty()) {
			actualEstado = abierta.get(0);
			//Comprobar si el estado expandido es el final
			if (actualEstado.compararEstadoCon(estadoFinal) == true) {
				exito = true;
			}
			//En caso de haber encontrado el estado final, parar el while
			if (exito) break;
			
			//Generar sucesores
			
			ArrayList<Estado> sucesores = new ArrayList<Estado>();
			//Mover guagua: mover(int source, int target)
			// Asignar como source la parada actual de la guagua
			int source = actualEstado.guagua.indexParadaActual;
			for (int target=0; target < Util.NUM_PARADAS; target++) {
				// Si las paradas son adyacentes, la guagua se puede mover entre ellas
				if (Util.costesAdyacentes[source][target] > 0){			
					nuevoEstado = new Estado (actualEstado, "mover", source, target);
					System.out.println("Moviendo guagua a parada numero " + (target+1));
					System.out.println("heuristica: " + nuevoEstado.h);
					if (!Util.isInList(nuevoEstado, abierta)) {
						System.out.println("no en lista");
						sucesores.add(nuevoEstado);
					}
				}
			}

			//Recoger alumno: recoger(int parada, int colegio)
			for (int parada=0; parada < Util.NUM_PARADAS; parada++) {
				for (int colegio=0; colegio < Util.NUM_COLEGIOS; colegio++) {
					if (actualEstado.paradas[parada].alumnosPorColegio[colegio]>0) {
						nuevoEstado = new Estado (actualEstado, "recoger", source, colegio);
						if (!Util.isInList(nuevoEstado, abierta)) {
							sucesores.add(nuevoEstado);
						}
					}
				}
			}

			//TODO:Entregar alumno: entregar(int parada, int colegio)
			for (int parada=0; parada < Util.NUM_PARADAS; parada++) {
				for (int colegio=0; colegio < Util.NUM_COLEGIOS; colegio++) {
					if (actualEstado.paradas[parada].colegiosEnParada[colegio] == true && actualEstado.guagua.alumnosPorColegio[colegio]>0) {
						nuevoEstado = new Estado (actualEstado, "entregar", source, colegio);
						if (!Util.isInList(nuevoEstado, abierta)) {
							sucesores.add(nuevoEstado);
						}
					}
				}
			}
			Util.sort(sucesores);
			abierta.remove(0);
			abierta.addAll(sucesores);
			System.out.println("Lista vacia: " + abierta.isEmpty());
			cerrada.add(actualEstado);
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

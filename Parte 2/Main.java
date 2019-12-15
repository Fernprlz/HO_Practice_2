import java.util.ArrayList;

public class Main {


	public static void main(String[] args) {
		// Creamos el estado inicial y le asignamos valores sacados del archivo de entrada
		Estado estadoInicial = Util.initState("s");
		
		/*Testear comparador
		Estado estadoInicial2 = new Estado (estadoInicial, "mover", 0, 1);
		System.out.println("Alumnos por colegio estado1:");
		for (int i = 0; i < estadoInicial.guagua.alumnosPorColegio.length; i++) {
			System.out.print(estadoInicial.guagua.alumnosPorColegio[i] + "\t");
		} 
		
		System.out.println();
		
		System.out.println("Posicion de la guagua estado1:");
		System.out.print(estadoInicial.guagua.indexParadaActual + "\t");
		
		System.out.println();
		
		System.out.println("Alumnos por colegio estado2:");
		for (int i = 0; i < estadoInicial2.guagua.alumnosPorColegio.length; i++) {
			System.out.print(estadoInicial2.guagua.alumnosPorColegio[i] + "\t");
		} 
		System.out.println();
		
		System.out.println("Posicion de la guagua estado2:");
		System.out.print(estadoInicial2.guagua.indexParadaActual + "\t");

		System.out.println();
		System.out.println(estadoInicial.compararEstadoCon(estadoInicial2));
		*/
		
		///////////////////////////////////////////////////
		System.out.println("-------GUAGUA--------");
		System.out.println(estadoInicial.guagua.indexParadaActual);
		for (int i = 0; i < Util.NUM_COLEGIOS; i++) {
			System.out.println(estadoInicial.guagua.alumnosPorColegio[i]);
		}
		System.out.println("-------PARADA--------");

		for (int p = 0; p < Util.NUM_PARADAS; p++) {
			System.out.println("-- PARADA " + p);
			for (int i = 0; i < Util.NUM_COLEGIOS; i++) {
				System.out.println(estadoInicial.paradas[p].alumnosPorColegio[i]);
			}
			for (int i = 0; i < Util.NUM_COLEGIOS; i++) {
				System.out.println(estadoInicial.paradas[p].colegiosEnParada[i]);
			}
		}

		System.out.println("------------");

		for (int p = 0; p < estadoInicial.paradas.length; p++) {
			for (int i = 0; i < args.length; i++) {
				System.out.println(estadoInicial.paradas[p].colegiosEnParada[i]);
			}
		}




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
			if (exito) break;
			//Generar sucesores
			actualEstado = abierta.get(0);
			ArrayList<Estado> sucesores = new ArrayList<Estado>();
			//Mover guagua: mover(int source, int target)
			int source = actualEstado.guagua.indexParadaActual;			// Asignar como source la parada actual de la guagua
			for (int target=0; target < Util.NUM_PARADAS; target++) {
				if (Util.costesAdyacentes[source][target] > 0){			// Si las paradas son adyacentes, la guagua se puede mover entre ellas
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
					if (actualEstado.paradas[parada].alumnosPorColegio[colegio]>0) {
						nuevoEstado = new Estado (actualEstado, "entregar", source, colegio);
						if (!Util.isInList(nuevoEstado, abierta)) {
							sucesores.add(nuevoEstado);
						}
					}
				}
			}
			Util.quickSort(sucesores);
			abierta.remove(0);
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


public class Main {



	public static void main(String[] args) {
		// Creamos el estado inicial y le asignamos valores sacados del archivo de entrada 
		Estado estadoInicial = Util.initState("s");

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

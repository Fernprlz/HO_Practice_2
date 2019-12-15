
public class Main {



	public static void main(String[] args) {
		// Creamos el estado inicial y le asignamos valores sacados del archivo de entrada 
		Estado estadoInicial = Util.initState("s");
		
		System.out.println(estadoInicial.toString());

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

package parte_2;
import java.util.*;
public class Estado {

	public Parada[] paradas;
	public Guagua guagua;
	public int h;

	// TODO: Constructor vacio temporal para debugear, no se si lo necesitaremos luego
	public Estado() {

	}

	public Estado(Parada[] paradas, Guagua guagua) {
		this.paradas = paradas;
		this.guagua = guagua;
		// TODO: por arreglar
		this.h = Util.calcHeuristic(paradas, guagua);
	}

	public Estado(Estado estadoPrevio, String accion, int source, int segundoParametro){
		//En caso de que la accion sea mover, el segundo parametro actuara como target
		if (accion.equals("mover")) {
			this.guagua = new Guagua (segundoParametro, estadoPrevio.guagua.alumnosPorColegio);
			this.paradas = estadoPrevio.paradas.clone();
			h = 0;
			h = h + Util.costesAdyacentes[source][segundoParametro];
		}

		//En caso de que la accion sea recoger, el segundo parametro actua como el indice del colegio
		if (accion.equals("recoger")) {
			// Aumentar el numero de alumnos en la guagua del colegio correspondiente
			int[] nuevoAlumnosGuagua = estadoPrevio.guagua.alumnosPorColegio.clone();
			nuevoAlumnosGuagua[segundoParametro]++;
			this.guagua = new Guagua (source, nuevoAlumnosGuagua);

			// Reducir el numero de alumnos en la parada del colegio correspondiente
			Parada[] nuevasParadas = estadoPrevio.paradas.clone();
			nuevasParadas[source].alumnosPorColegio[segundoParametro]--;
			this.paradas = nuevasParadas.clone();
			h = 0;
			//TODO: Heuristica aqui
		}

		//En caso de que la accion sea entregar, el segundo parametro actua como el indice del colegio
		if (accion.equals("entregar")) {
			// Reducir el numero de alumnos en la guagua del colegio correspondiente
			int[] nuevoAlumnosGuagua = estadoPrevio.guagua.alumnosPorColegio.clone();
			nuevoAlumnosGuagua[segundoParametro]--;
			this.guagua = new Guagua (source, nuevoAlumnosGuagua);

			// La parada se queda igual: el alumno "desaparece"
			this.paradas = estadoPrevio.paradas.clone();
			h = 0;
			//TODO: Heuristica aqui
		}

	}

	public Parada getParada(int index) {
		return paradas[index - 1];
	}

	public Guagua getGuagua() {
		return guagua;
	}

	@Override
	public String toString() {
		String result = "DISTRIBUCION DE ALUMNOS\n\t";
		for (int parada = 0; parada <= paradas.length; parada++) {
			for (int colegio = 0; colegio <= paradas[0].alumnosPorColegio.length; colegio++) {
				if (parada == 0 && colegio > 0) {
					result += "C"+colegio+"\t";
				} else if (parada > 0 && colegio == 0) {
					result += "P"+parada+"\t";
				} else if (parada > 0 && colegio > 0) {
					result += paradas[parada - 1].alumnosPorColegio[colegio - 1] + "\t";
				}
			}
			result += "\n";
		}

		result += "\nDATOS DE LA GUAGUA\n";
		result += "Parada Actual: P"+(guagua.indexParadaActual + 1)+"\n";
		result += "\t";
		for (int ii = 0; ii <= 1; ii++) {
			for (int colegio = 0; colegio <= guagua.alumnosPorColegio.length; colegio++) {
				if (ii == 0 && colegio > 0) {
					result += "C"+colegio+"\t";
				} else if (ii > 0 && colegio == 0) {
					result += "G\t";
				} else if (ii > 0 && colegio > 0) {
					result += guagua.alumnosPorColegio[colegio-1] + "\t";
				}
			}
			result += "\n";
		}
		result += "\nDISTRIBUCION DE COLEGIOS\n";
		for (int ii = 0; ii < Util.indexParadaColegio.length; ii++) {
			result += "C" + (ii + 1) + ": P" + Util.indexParadaColegio[ii] + "\n";
		}

		return result;
	}


	public boolean compararEstadoCon(Estado estado2){
		boolean sonIguales = true;
		// Hacer todas las comparaciones
		// Comparar primero el estado de la guagua, que es mas facil
		if (this.guagua.indexParadaActual != estado2.guagua.indexParadaActual) {
			sonIguales = false;
		} else if (Arrays.equals(this.guagua.alumnosPorColegio, estado2.guagua.alumnosPorColegio) == false) {
			sonIguales = false;
		}
		if (sonIguales) {
			// Si sonIguales sigue siendo true, comparar todas las paradas hasta que se encuentre una que no coincide
			for (int ii=0; ii<Util.NUM_PARADAS; ii++){
				if (Arrays.equals(this.paradas[ii].alumnosPorColegio, estado2.paradas[ii].alumnosPorColegio) == false) {
					sonIguales = false;
					break;
				}else if (Arrays.equals(this.paradas[ii].colegiosEnParada, estado2.paradas[ii].colegiosEnParada) == false){ 
					sonIguales = false;
					break;
				}
			}
		}

		return sonIguales;
	}


}

// Definimos el criterio que usará Collections.sort para ordenar la ArrayList de estados.
// Esta función implementa mergeSort.
class ByHeuristics implements Comparator<Estado> {

	/**
	 * Compara dos Estados según su heurística devolviendo la diferencia del primero con el segundo. 
	 */
	public int compare(Estado a, Estado b) {
		return a.h - b.h;
	}
}

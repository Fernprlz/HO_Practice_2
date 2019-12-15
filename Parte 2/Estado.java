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
			this.guagua = new Guagua (segundoParametro, estadoPrevio.guagua.alumnosPorColegio, "mover");
			this.paradas = estadoPrevio.paradas.clone();
			h = 0;
			h = h + Util.costesAdyacentes[source][segundoParametro];
		}
		
		//En caso de que la accion sea recoger, el segundo parametro actua como el ID del colegio
		if (accion.equals("recoger")) {
			this.guagua = new Guagua (segundoParametro, estadoPrevio.guagua.alumnosPorColegio, "recoger");
			this.paradas = estadoPrevio.paradas.clone();
			h = 0;
			h = h + Util.costesAdyacentes[source][segundoParametro];
		}
	}

	public Parada getParada(int index) {
		return paradas[index - 1];
	}

	public Guagua getGuagua() {
		return guagua;
	}

	public boolean compararEstadoCon(Estado estado2){
		boolean sonIguales = true;
		// Hacer todas las comparaciones
		// Comparar primero el estado de la guagua, que es mas facil
		if (this.guagua.indexParadaActual != estado2.guagua.indexParadaActual) {
		sonIguales = false;
		}
		else if (Arrays.equals(this.guagua.alumnosPorColegio, estado2.guagua.alumnosPorColegio) == false) {
		sonIguales = false;
		System.out.println("dos");
		}
		if (sonIguales) {
			System.out.println("tres");
		// Si sonIguales sigue siendo true, comparar todas las paradas hasta que se encuentre una que no coincide
			for (int ii=0; ii<Util.NUM_PARADAS; ii++){
				if (Arrays.equals(this.paradas[ii].alumnosPorColegio, estado2.paradas[ii].alumnosPorColegio) == false)
					sonIguales = false;
				else if (Arrays.equals(this.paradas[ii].colegiosEnParada, estado2.paradas[ii].colegiosEnParada) == false)
					sonIguales = false;
				if (!sonIguales) break;
			}
		}
		
		//System.out.println("Estados iguales: " + sonIguales);
		
		return sonIguales;
	}
}

public class Estado {

	public Parada[] paradas;
	public Guagua guagua;
	private int h;

	// TODO: Constructor vacio temporal para debugear, no se si lo necesitaremos luego
	public Estado() {

	}

	public Estado(Parada[] paradas, Guagua guagua) {
		this.paradas = paradas;
		this.guagua = guagua;
		// TODO: por arreglar
		this.h = Util.calcHeuristic(paradas, guagua);
	}

	public Estado(Estado estadoPrevio, String accion,  int target){
		if (accion.equals("mover")) {
			this.guagua = new Guagua (target, estadoPrevio.guagua.alumnosPorColegio);
			this.paradas = estadoPrevio.paradas.clone();
		}
	}

	public Parada getParada(int index) {
		return paradas[index - 1];
	}

	public Guagua getGuagua() {
		return guagua;
	}

	public boolean compararEstados(Estado estado1, Estado estado2){
		boolean sonIguales = true;
		// Hacer todas las comparaciones
		// Comparar primero el estado de la guagua, que es mas facil
		if (estado1.guagua.indexParadaActual != estado2.guagua.indexParadaActual)
		sonIguales = false;
		else if (estado1.guagua.alumnosPorColegio.equals(estado2.guagua.alumnosPorColegio) == false)
		sonIguales = false;

		// Si sonIguales sigue siendo true, comparar todas las paradas hasta que se encuentre una que no coincide
		for (int ii=0; ii<Util.NUM_PARADAS; ii++){
			if (estado1.paradas[ii].alumnosPorColegio.equals(estado2.paradas[ii].alumnosPorColegio) == false)
			sonIguales = false;
			else if (estado1.paradas[ii].colegiosEnParada.equals(estado2.paradas[ii].colegiosEnParada) == false)
			sonIguales = false;
			if (!sonIguales) break;
		}
		
		return sonIguales;
	}
}

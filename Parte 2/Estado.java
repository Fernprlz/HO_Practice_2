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

	public Parada getParada(int index) {
		return paradas[index - 1];
	}
	
	public Guagua getGuagua() {
		return guagua;
	}
	
	@Override
	public String toString() {
		String result = "DISTRIBUCIÓN DE ALUMNOS\n\t";
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
					result += guagua.alumnosPorColegio[colegio - 1] + "\t";
				}
			}
			result += "\n";
		}
		
		return result;
	}
}
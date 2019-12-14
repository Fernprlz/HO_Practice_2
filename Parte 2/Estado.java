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
}
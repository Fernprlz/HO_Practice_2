public class Estado {

	private Parada[] paradas;
	private Guagua guagua;
	private int indexC1;
	private int indexC2;
	private int indexC3;
	private int h;

	public Estado(Parada[] paradas, Guagua guagua) {
		this.paradas = paradas;
		this.guagua = guagua;
		this.h = Util.calcHeuristic(paradas, guagua);
		initColegios();
	}
	
	public void initColegios() {
		for (int ii = 0; ii < paradas.length; ii++) {
			if (paradas[ii].getColegio1() > 0) {
				indexC1 = ii;
			}
			if (paradas[ii].getColegio2() > 0) {
				indexC2 = ii;
			}
			if (paradas[ii].getColegio3() > 0) {
				indexC3 = ii;
			}
		}
	}

	public Parada getParada(int index) {
		return paradas[index - 1];
	}
	
	public Guagua getGuagua() {
		return guagua;
	}
}
public class Estado {

	Parada[] paradas;
	Guagua guagua;

	public Estado(Parada[] paradas, Guagua guagua) {
		this.paradas = paradas;
		this.guagua = guagua;
	}

	public Parada getParada(int index) {
		return paradas[index - 1];
	}
	
	public Guagua getGuagua() {
		return guagua;
	}
}
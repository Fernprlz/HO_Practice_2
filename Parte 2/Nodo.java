
public class Nodo {
	// Coste para llegar a la parada representada en este nodo
	// a partir de la parada de la que se llama.
	private int coste;
	// Parada que representa este nodo
	Parada parada;

	public Nodo(int coste, Parada parada) {
		this.coste = coste;
		this.parada = parada;
	}
}

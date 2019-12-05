
public class Parada {

	// Podemos hacer de el objeto parada los nodos del grafo
	// añadiendo un campo que sea una lista de paradas adyacentes
	// de alguna forma
	private final int ID;
	private int c1;
	private int c2;
	private int c3;
	private int colegio;
	
	public Parada(int id, int c1, int c2, int c3, int c) {
		this.ID = id;
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
		this.colegio = c;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getC1() {
		return c1;
	}

	public void setC1(int c1) {
		this.c1 = c1;
	}

	public int getC2() {
		return c2;
	}

	public void setC2(int c2) {
		this.c2 = c2;
	}

	public int getC3() {
		return c3;
	}

	public void setC3(int c3) {
		this.c3 = c3;
	}

	public int getColegio() {
		return colegio;
	}

	public void setColegio(int colegio) {
		this.colegio = colegio;
	}
}


public class Parada {

	public int[] alumnosPorColegio;
	public boolean[] colegiosEnParada;

	public Parada(int[] alumnosPorColegio, boolean[] colegiosEnParada) {
		this.alumnosPorColegio = alumnosPorColegio;
		this.colegiosEnParada = colegiosEnParada;
	}

	public int[] getAlumnosPorColegio() {
		return alumnosPorColegio;
	}

	public boolean[] getColegiosEnParada() {
		return colegiosEnParada;
	}

}

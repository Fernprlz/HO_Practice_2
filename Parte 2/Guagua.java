import java.util.stream.*;
public class Guagua {

	// Guardamos la capacidad total para poder sacar la capacidad actual cuando la necesitemos
	// Su valor se asigna desde el método initState() del Main
	public static int CAP;
	
	public int indexParadaActual;
	public int[] alumnosPorColegio;
	
	public Guagua(int indexParadaInicial, int numColegios) {
		this.indexParadaActual = indexParadaInicial;
		this.alumnosPorColegio = new int[numColegios];
	}
	
	public int getCapacidadActual() {
		return CAP - (IntStream.of(alumnosPorColegio).sum());
	}

	public int getParadaActual() {
		return indexParadaActual;
	}

	public void setParadaActual(int indexParadaActual) {
		this.indexParadaActual = indexParadaActual;
	}
	
	public int[] getAlumnosPorColegio() {
		return alumnosPorColegio;
	}

	public int getCAP() {
		return CAP;
	}
}

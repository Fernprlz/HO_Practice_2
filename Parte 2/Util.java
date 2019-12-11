
public class Util {
	public static int calcHeuristic(Estado estado) {
		int costeC1 = estado.getGuagua().getC1() * calcDist();
		int costeC2 = estado.getGuagua().getC1() * calcDist();
		int costeC3 = estado.getGuagua().getC1() * calcDist();
		
		
		
		return  costeC1 + costeC2 + costeC3;
	}
	

}

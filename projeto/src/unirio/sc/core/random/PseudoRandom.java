package unirio.sc.core.random;

/**
 * PseudoRandom.java 
 * Adaptado para este projeto a partir do JMetal (http://jmetal.sourceforge.net/)
 * @author Juan J. Durillo
 */
public class PseudoRandom {

	/**
	 * generator used to obtain the random values
	 */
	private static GeradorNumero random = null;

	/**
	 * other generator used to obtain the random values
	 */
	private static java.util.Random randomJava = null;

	/**
	 * Constructor. Creates a new instance of PseudoRandom.
	 */
	private PseudoRandom() {
		//if (random == null) {
		//	random = new GeradorNumero();
		//	randomJava = new java.util.Random();
		//}
	}
	
	public static void inicia(Double seed) {
		random = new GeradorNumero(seed);
		if (seed!=null)
			randomJava = new java.util.Random(seed.longValue());
		else
			randomJava = new java.util.Random();
	}

	/**
	 * Returns a random int value using the Java random generator.
	 * 
	 * @return A random int value.
	 */
	public static int randInt() {
		//if (random == null) {
		//	new PseudoRandom();
		//}
		return randomJava.nextInt();
	}

	/**
	 * Returns a random double value using the PseudoRandom generator. Returns A
	 * random double value.
	 */
	public static double randDouble() {
		//if (random == null) {
		//	new PseudoRandom();
		//}
		return random.rndreal(0.0, 1.0);
		// return randomJava.nextDouble();
	}

	/**
	 * Returns a random int value between a minimum bound and maximum bound
	 * using the PseudoRandom generator.
	 * 
	 * @param minBound
	 *            The minimum bound.
	 * @param maxBound
	 *            The maximum bound. Return A pseudo random int value between
	 *            minBound and maxBound.
	 */
	public static int randInt(int minBound, int maxBound) {
		//if (random == null) {
		//	new PseudoRandom();
		//}
		return random.rnd(minBound, maxBound);
		// return minBound + randomJava.nextInt(maxBound-minBound+1);
	}

	/**
	 * Returns a random double value between a minimum bound and a maximum bound
	 * using the PseudoRandom generator.
	 * 
	 * @param minBound
	 *            The minimum bound.
	 * @param maxBound
	 *            The maximum bound.
	 * @return A pseudo random double value between minBound and maxBound
	 */
	public static double randDouble(double minBound, double maxBound) {
		//if (random == null) {
		//	new PseudoRandom();
		//}
		return random.rndreal(minBound, maxBound);
		// return minBound + (maxBound - minBound)*randomJava.nextDouble();
	}
}

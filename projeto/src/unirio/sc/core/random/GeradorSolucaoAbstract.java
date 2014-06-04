package unirio.sc.core.random;

public abstract class GeradorSolucaoAbstract {

	//****************************************************************************
	// MÉTODOS GENÉRICOS  
	//****************************************************************************

	// private static GeradorNumero random = null;
	protected static int tamanhoSolucao = 0;
	protected static int limiteInferior = 0;
	protected static int limiteSuperior = 0;

	public static void inicia(
		int tamanhoSolucaoAux, 
		int limiteInferiorAux,
		int limiteSuperiorAux,
		Double seed) 
	{
		// if (random == null)	random = new GeradorNumero();
		tamanhoSolucao = tamanhoSolucaoAux;
		limiteInferior = limiteInferiorAux;
		limiteSuperior = limiteSuperiorAux;
		
		PseudoRandom.inicia(seed);
	}
	
	public static int randInt(int min, int max) {
		return PseudoRandom.randInt(min, max);
	}

	public static int randInt() {
		// return random.rnd(limiteInferior, limiteSuperior);
		return PseudoRandom.randInt(limiteInferior, limiteSuperior);
	}

	public static int getLimiteInferior() 
	{
		return limiteInferior;
	}

	public static int getLimiteSuperior() 
	{
		return limiteSuperior;
	}

	public static int getTamanhoSolucao() 
	{
		return tamanhoSolucao;
	}
	
}

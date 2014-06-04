package unirio.sc.core;

public interface SolucaoAbstract {
	
	public abstract double getFitness();
	
	public abstract String getString();
	
	public abstract int getLocation();
	
	public abstract void setFitness(double fitness);
	
	public abstract void setLocation(int location);

	// representa��o da solu��o onde cada item do vetor corresponde ao m�dulo e o conte�do ao cluster que o modulo faz parte
	public abstract int[] getValores();
	
	public abstract int getTotalItens();

}

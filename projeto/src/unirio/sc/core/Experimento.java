package unirio.sc.core;



public class Experimento {

	protected String nomeExperimento;
	protected AlgoritmoAbstract algoritmo;
	protected double mediaFitness;
	protected double melhorFitness;
	protected long mediaTempoExecucao;
	protected SolucaoAbstract melhorSolucao;
	
	public Experimento(
		String nomeExperimento,
		AlgoritmoAbstract algoritmo) 
	{
		this.nomeExperimento = nomeExperimento;
		this.algoritmo = algoritmo;
		this.mediaTempoExecucao = 0;
		this.mediaFitness = 0.00;
		this.melhorFitness = 0.00;
		this.melhorSolucao = null;
	}

	public void runWithMedia(int cicloInicial, int nCycles, boolean withSeed)
	throws Exception
	{
		this.melhorSolucao = null;
		this.melhorFitness = 0.00;
		this.mediaFitness = 0.00;

		double somaFitness = 0.00;
		long somaTempo = 0;
		int numeroExecucoes = 0;
		
		String[] args = null;
		
		this.algoritmo.getExibicao().printDadosExperimento(this, this.algoritmo);
		
		for (int i = cicloInicial; i < nCycles; i++) {
			this.algoritmo.reset();
			
			// Semente passada por parametro para o algoritmo
			if (withSeed)
				args = new String[]{ String.valueOf(i*0.01) };
			
			this.algoritmo.executa(args);

			SolucaoAbstract solucao = this.algoritmo.getSolucao();
			long tempoExecucao = this.algoritmo.getTempoExecucao();
			
			double fitness = solucao.getFitness();
			
			this.algoritmo.getExibicao().printCiclo(i, solucao, tempoExecucao);

			// Verifica se a solução está correta e normalizada
			Verificador.validaSolucao(this.algoritmo, solucao);

			somaFitness += fitness;
			somaTempo += tempoExecucao;
			numeroExecucoes++;

			if (fitness < melhorFitness) {
				this.melhorFitness = fitness;
				this.melhorSolucao = solucao;
			}
		}

		this.mediaTempoExecucao = (somaTempo / numeroExecucoes);
		this.mediaFitness = (somaFitness / numeroExecucoes);
		
		algoritmo.getExibicao().print(this, algoritmo);
	}
	

	public String getNomeExperimento() 
	{
		return nomeExperimento;
	}

	public double getMediaFitness() 
	{
		return mediaFitness;
	}

	public double getMelhorFitness() 
	{
		return melhorFitness;
	}

	public SolucaoAbstract getMelhorSolucao() 
	{
		return melhorSolucao;
	}

	public long getMediaTempoExecucao() 
	{
		return mediaTempoExecucao;
	}
	
	public AlgoritmoAbstract getAlgoritmo() {
		return algoritmo;
	}
}
package unirio.sc.core;

import unirio.sc.core.Parametro;

/**
 * Principais métodos e parâmetros a serem implementados nos algoritmos
 */
public abstract class AlgoritmoAbstract {

	// Número de gerações de soluções, contado individualmente por cada solução na população
	protected int evaluation = 0;
	// Número máximo de gerações de soluções
	protected int evaluationMax = 0;
	// Iteracao atual do algoritmo, sem contar individuos gerados individualmente
	protected int iteracao = 0;
	// Tempo de execução considerado para execução do algoritmo
	protected long tempoExecucao = 0;
	// Variável auxiliar que retornará a solução gerada pelo algoritmo
	protected SolucaoAbstract solucao = null;
	// Dados de entrada do problema utilizado
	protected Problema problema = null;
	// Classe que efetua o cálculo de fitness das soluções
	protected CalculadorAbstract calculador = null;
	// Publicador
	protected Exibicao exibicao = null;
	// Proporcao maxima de grupos em relacao ao numero total de itens
	protected int maxProporcaoGrupos = 1;

	/**
	 * Número máximo de evaluations a ser executado no algoritmo
	 */
	public void setEvaluationMax(int evaluationMax) 
	{
		this.evaluationMax = evaluationMax;
	}
	
	/**
	 * Número máximo de avaliações a ser executado no algoritmo
	 */
	public void setEvaluation(int evaluation)
	{
		this.evaluation = evaluation;
	}

	/**
	 * Reset nas variáveis do algoritmo
	 */
	public void reset() 
	{
		this.iteracao = 0;
		this.tempoExecucao = 0;
		this.evaluation = 0;
		this.solucao = null;
	}
	
	/**
	 * Informa o número de avaliações de solução
	 */
	public int getEvaluation()
	{
		return this.evaluation;
	}
	
	/**
	 * Seta a posição de iteração atual
	 */
	public void setIteracao(int iteracao) 
	{
		this.iteracao = iteracao;
	}

	/**
	 * Iteracao atual
	 */
	public int getIteracao() 
	{
		return iteracao;
	}

	/**
	 * Informa o tempo de execução da última execução
	 */
	public long getTempoExecucao() 
	{
		return this.tempoExecucao;
	}

	/**
	 * Informa a solução da última execução
	 */
	public SolucaoAbstract getSolucao() 
	{
		return this.solucao;
	}

	/**
	 * Informa o calculator utilizado
	 */
	public CalculadorAbstract getCalculador() 
	{
		return this.calculador;
	}

	/**
	 * Informa o problema utilizado
	 */
	public Problema getProblema() 
	{
		return this.problema;
	}

	/**
	 * Publicador
	 */
	public Exibicao getExibicao() 
	{
		return this.exibicao;
	}
	
	/**
	 * Descricao do algoritmo
	 */
	public abstract String getNomeAlgoritmo(); 
	
	/**
	 * Executa o algoritmo
	 */
	public abstract void executa(String... args);
	
	/**
	 * Informaçãoes dos parametros utilizados
	 */
	public abstract String getInfoParametros();
	
	/**
	 * Configurações do parametro de entrada do algoritmo
	 */
	public abstract void setConfiguracoes(Parametro param) throws Exception;
	
	
}

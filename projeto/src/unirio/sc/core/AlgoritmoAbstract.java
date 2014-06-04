package unirio.sc.core;

import unirio.sc.core.Parametro;

/**
 * Principais m�todos e par�metros a serem implementados nos algoritmos
 */
public abstract class AlgoritmoAbstract {

	// N�mero de gera��es de solu��es, contado individualmente por cada solu��o na popula��o
	protected int evaluation = 0;
	// N�mero m�ximo de gera��es de solu��es
	protected int evaluationMax = 0;
	// Iteracao atual do algoritmo, sem contar individuos gerados individualmente
	protected int iteracao = 0;
	// Tempo de execu��o considerado para execu��o do algoritmo
	protected long tempoExecucao = 0;
	// Vari�vel auxiliar que retornar� a solu��o gerada pelo algoritmo
	protected SolucaoAbstract solucao = null;
	// Dados de entrada do problema utilizado
	protected Problema problema = null;
	// Classe que efetua o c�lculo de fitness das solu��es
	protected CalculadorAbstract calculador = null;
	// Publicador
	protected Exibicao exibicao = null;
	// Proporcao maxima de grupos em relacao ao numero total de itens
	protected int maxProporcaoGrupos = 1;

	/**
	 * N�mero m�ximo de evaluations a ser executado no algoritmo
	 */
	public void setEvaluationMax(int evaluationMax) 
	{
		this.evaluationMax = evaluationMax;
	}
	
	/**
	 * N�mero m�ximo de avalia��es a ser executado no algoritmo
	 */
	public void setEvaluation(int evaluation)
	{
		this.evaluation = evaluation;
	}

	/**
	 * Reset nas vari�veis do algoritmo
	 */
	public void reset() 
	{
		this.iteracao = 0;
		this.tempoExecucao = 0;
		this.evaluation = 0;
		this.solucao = null;
	}
	
	/**
	 * Informa o n�mero de avalia��es de solu��o
	 */
	public int getEvaluation()
	{
		return this.evaluation;
	}
	
	/**
	 * Seta a posi��o de itera��o atual
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
	 * Informa o tempo de execu��o da �ltima execu��o
	 */
	public long getTempoExecucao() 
	{
		return this.tempoExecucao;
	}

	/**
	 * Informa a solu��o da �ltima execu��o
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
	 * Informa��oes dos parametros utilizados
	 */
	public abstract String getInfoParametros();
	
	/**
	 * Configura��es do parametro de entrada do algoritmo
	 */
	public abstract void setConfiguracoes(Parametro param) throws Exception;
	
	
}

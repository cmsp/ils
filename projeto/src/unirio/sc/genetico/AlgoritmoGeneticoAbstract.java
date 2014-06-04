package unirio.sc.genetico;

import unirio.sc.core.AlgoritmoAbstract;
import unirio.sc.core.Calculador;
import unirio.sc.core.Exibicao;
import unirio.sc.core.Parametro;
import unirio.sc.core.Problema;
import unirio.sc.core.SolucaoAbstract;


public abstract class AlgoritmoGeneticoAbstract extends AlgoritmoAbstract {

	// Tamanho da popula��o
	protected int tamanhoPopulacao = 0;
	// Tamanho da elite
	protected int tamanhoElite = 2;
	// Tipo de substitui��o dos piores elementos
	protected TipoDescarte tipoDescarte = null;
	// Tamanho de op��es aleat�rias inseridas na popula��o
	protected int tamanhoDescarte = 0;
	// Tamanho para sele��o das solu��es boas que ser�o modificadas para substituir o descarte
	protected int tamanhoDescarteSelecao = 0;
	// Operador de selecao
	protected Selection operadorSelecao = null;
	// Operador de muta��o
	protected Mutation operadorMutacao = null;
	// Operador de crossover
	protected Crossover operadorCrossover = null;
	// N�mero de gera��es para parada
	protected int tamanhoGeracoesIguaisParada = 0;
	// Par�metro m�ximo de solu��es iguais antes de aplicar muta��o 
	protected int tamanhoSolucoesIguaisMaxima = -1;

	public AlgoritmoGeneticoAbstract( 
			Problema problema, 
			Exibicao exibicao,
			Parametro param) 
	throws Exception
	{
		this.problema = problema;
		this.calculador = new Calculador(problema);
		this.exibicao = exibicao;
		
		setConfiguracoes(param);
	}

	/**
	 * Inicializa a popula��o randomicamente
	 */
	public abstract void iniciaPopulacao(Populacao populacao, int tamPopulacao); 

	/**
	 * Inicializa a popula��o randomicamente
	 */
	/*
	@Deprecated
	protected abstract void iniciaPopulacaoSemOrdenacao(Populacao populacao, int tamPopulacao);
	*/ 

	/**
	 * Seleciona os elementos da popula��o para reprodu��o
	 */
	protected abstract void selecaoElite(Populacao populacao,Populacao populacaoFilha); 

	/**
	 * Substitui os piores elementos da popula��o
	 */
	protected abstract void substituiPioresAleatorio(Populacao populacao, Populacao populacaoFilha); 

	/**
	 * Substitui os piores elementos da popula��o
	 */
	protected abstract void substituiPioresMutacao(Populacao populacao, Populacao populacaoFilha); 

	/**
	 * Substitui os piores elementos da popula��o
	 */
	protected void substituiPiores(Populacao populacao, Populacao populacaoFilha) 
	{
		switch(this.tipoDescarte) {
			case ALEATORIO:
				substituiPioresAleatorio(populacao, populacaoFilha);
				return;
			case MUTACAO_MELHORES:
				substituiPioresMutacao(populacao, populacaoFilha);
				return;
			case NENHUM:
				return;
		}
	}
	
	/**
	 * Executa o crossover
	 */
	protected SolucaoAbstract[] crossover(SolucaoAbstract[] pais) {
		return this.operadorCrossover.executa(pais);
	}

	/**
	 * Otimiza��o
	 */
	protected abstract void otimizacao(SolucaoAbstract[] filhos); 

	/**
	 * Adiciona na nova gera��o
	 */
	protected abstract void novaGeracao(Populacao populacaoFilha, SolucaoAbstract[] filhos);

	/**
	 * Seleciona dois elementos para crossover
	 */
	protected SolucaoAbstract[] selecaoCrossover(Populacao populacao) {
		switch(this.operadorSelecao.getTipoSelecao()) {
			case PAIS_GRUPO_A_E_B:
				return this.operadorSelecao.getPaisAeB(populacao);
			case BINARY_TOURNAMENT:
				return this.operadorSelecao.getVetorBinaryTournament(populacao);
		}
		return null;
	}
	
	/**
	 * Executa a muta��o de acordo com a sele��o
	 */
	protected void mutacao(SolucaoAbstract[] filhos) {
		this.operadorMutacao.executa(filhos, this.evaluation);
	}
	
	/**
	 * Realiza um crossover e muta��o
	 */
	public void reproduzPais(
		Populacao populacao,
		Populacao populacaoFilha) 
	{
		// Selection
		SolucaoAbstract[] pais = selecaoCrossover(populacao);

		// Crossover
		SolucaoAbstract[] filhos = crossover(pais);

		// Mutation
		mutacao(filhos);
		
		/*
		try {
			Verificador.validaSolucao(this, filhos[0],"sem_fitness");
			Verificador.validaSolucao(this, filhos[1],"sem_fitness");
		} catch (Exception e){
			this.exibicao.printlnMsg(e.getMessage());
		}
		*/

		// Otimizacoes
		otimizacao(filhos);

		/*
		try {
			Verificador.validaSolucao(this, filhos[0]);
			Verificador.validaSolucao(this, filhos[1]);
		} catch (Exception e){
			this.exibicao.printlnMsg(e.getMessage());
		}
		*/

		// Otimizacoes
		novaGeracao(populacaoFilha, filhos);
		
		/*
		try {
			Verificador.validaSolucao(this, filhos[0]);
			Verificador.validaSolucao(this, filhos[1]);
		} catch (Exception e){
			this.exibicao.printlnMsg(e.getMessage());
		}
		*/
		
		//this.debug.println(pais);
				
		//System.out.println("i="+i);
		//System.out.println("filhos[0]="+filhos[0].getFitness());
		//System.out.println("filhos[1]="+filhos[1].getFitness());
		//System.out.println("-");
	}	

	/**
	 * Inicializa a popula��o randomicamente
	 */
	public void reproduzPopulacao(
		Populacao populacao,
		Populacao populacaoFilha) 
	{
		int reposicaoElite = this.tamanhoElite / 2;
		int descartePiores = this.tamanhoDescarte / 2;
 		for (int i = 0; i < (populacao.size() / 2 - reposicaoElite - descartePiores); i++) 
		{
 			reproduzPais(populacao, populacaoFilha);
		}
	}
	
	public void iniciaGeradorSolucao(double semente)
	{
		int tamanhoSolucao = this.problema.getTamanho();
		int limiteInferior = 0;
		int limiteSuperior = (tamanhoSolucao/maxProporcaoGrupos) - 1;
		GeradorSolucaoGenetico.inicia(tamanhoSolucao, limiteInferior, limiteSuperior, semente);
	}

	public void executa(String... args) 
	{
		long tempoInicial = System.currentTimeMillis();
		
		Double seed = null;
		if (args!=null && args.length>0)
			seed = Double.valueOf(args[0]);

		// Debug
		// se n�o houver debug, comentar a linha para otimiza��o
		this.exibicao.printDebugGeracaoCabecalho(this,seed);
		// fim Debug

		iniciaGeradorSolucao(seed);
		
		// this.exibicao.printlnMsg("SEMENTE=" + seed);
		
		// DebugUtil debug = new DebugUtil("debug_", this);
		
		
		Populacao populacao = new Populacao();
		iniciaPopulacao(populacao, this.tamanhoPopulacao);
		//iniciaPopulacaoSemOrdenacao(populacao, this.tamanhoPopulacao);

		Populacao populacaoFilha = new Populacao();

		int contadorGeracoesIguais = 0;

		// Comparador comparador = new Comparador( );
		// populacao.sort( comparador );
		while (this.evaluation < this.evaluationMax) {
			
			//debug.printPopulacaoResumo(this.iteracao, populacao);
			//debug.printPopulacao(this.iteracao, populacao);

			// Seleciona as melhores solu��es para manter na nova gera��o
			selecaoElite(populacao, populacaoFilha);

			// Gera��o da nova popula��o
			reproduzPopulacao(populacao, populacaoFilha);

			// Substitui um percentual das piores solu��es
			substituiPiores(populacao, populacaoFilha);

			// Verifica se a popula��o est� homogenea (otimo local?)
			//contadorGeracoesIguais = populacao.compara(populacaoFilha, contadorGeracoesIguais);
			if (this.tamanhoGeracoesIguaisParada>0) {
				contadorGeracoesIguais = populacao.comparaConsirandoInsercaoRandomica(populacaoFilha, contadorGeracoesIguais, this.operadorSelecao.getFimB());
				if (contadorGeracoesIguais>this.tamanhoGeracoesIguaisParada)
					break;
			}
			
			// Debug
			// se n�o houver debug, comentar o bloco para otimiza��o
			this.exibicao.printDebugGeracao(populacaoFilha, this.iteracao, this.evaluation);
			// fim Debug

			populacao.set(populacaoFilha);
			populacaoFilha.clear();
			
			// populacao.sort(comparador);
			this.iteracao++;
		}
		
		// System.out.println("");
		// System.out.println("Total iteracoes=" + this.iteracao);
		// System.out.println("Total iteracoes aproximada=" + this.evaluationMax / ( this.tamanhoPopulacao - this.tamanhoElite + 1));
		
		// debug.printPopulacao(this.iteracao, populacao);

		this.solucao = populacao.getMelhor();

		this.tempoExecucao = System.currentTimeMillis() - tempoInicial;
	}
	
	@Override
	public void setConfiguracoes(Parametro param) throws Exception {
		
		this.evaluationMax = param.getEvaluationMax();
		
		this.tamanhoPopulacao = param.getTamanhoPopulacao();
		
		if (param.getTipoElite() == TipoElite.PERCENTUAL) {
			this.tamanhoElite = Double.valueOf(problema.getTamanho() * param.getValorElite() / 100).intValue();
		} else {
			this.tamanhoElite = Double.valueOf(param.getValorElite()).intValue();
		}
		if (this.tamanhoElite <=1) this.tamanhoElite = 2;
		
		this.tipoDescarte = param.getTipoDescarte();
		this.tamanhoDescarte = Double.valueOf(problema.getTamanho() * param.getPercentualDescarte() / 100).intValue();
		this.tamanhoDescarteSelecao = Double.valueOf(problema.getTamanho() * param.getPercentualDescarteSelecao() / 100).intValue();
		int tamanhoDescarteMovimentacoes = Double.valueOf(problema.getTamanho() * param.getPercentualDescarteMovimentacoes()/100).intValue();
		
		if (this.tipoDescarte == TipoDescarte.NENHUM && this.tamanhoDescarte > 0) {
			throw new Exception("Operador de Descarte configurado como Nenhum tamanho de descarte maior que zero.");
		}
		
		if (this.tipoDescarte != TipoDescarte.NENHUM) {
			if (this.tamanhoDescarte <= 0) {
				throw new Exception("Operador de Descarte configurado mas tamanho de descarte menor ou igual a zero.");	
			}
			if (this.tamanhoDescarteSelecao <= 0) {
				throw new Exception("Operador de Descarte configurado mas tamanho de descarte da sele��o menor ou igual a zero.");	
			}
			if (this.tipoDescarte == TipoDescarte.MUTACAO_MELHORES && tamanhoDescarteMovimentacoes <= 0) {
				throw new Exception("Operador de Descarte configurado mas tamanho de descarte das movimenta��es menor ou igual a zero.");	
			}
		}
		
		this.tamanhoGeracoesIguaisParada = param.getMaxIteracoesSemMelhoria();
		this.evaluationMax = param.getEvaluationMax();
		this.maxProporcaoGrupos = param.getMaxProporcaoGrupos();
		
		// numero de solucoes identicas permitidas na populacao
		this.tamanhoSolucoesIguaisMaxima = Double.valueOf(problema.getTamanho() * param.getPercentualSolucoesIguaisMaxima()/100).intValue();
		if (this.tamanhoSolucoesIguaisMaxima<0)
			this.tamanhoSolucoesIguaisMaxima = 0;
		
		// Se o percentual est� definido estabelece o n�mero de 1 para diversifica��o
		if (param.getPercentualSolucoesIguaisMaxima() != null && param.getPercentualSolucoesIguaisMaxima() > 0.00) {
			if (this.tamanhoSolucoesIguaisMaxima == 0)
				this.tamanhoSolucoesIguaisMaxima = 1;
		}
		
		// movimentacao usada para diversificacao da populacao no AlgoritmoGFComMutacao
		int tamanhoMovimentacoesDiversificacao = Double.valueOf(problema.getTamanho() * param.getPercentualMovimentacoesMutacao()/100).intValue();
		if (tamanhoMovimentacoesDiversificacao<0)
			tamanhoMovimentacoesDiversificacao = 0;

		switch (param.getTipoSelecao()) {
			case BINARY_TOURNAMENT:
				this.operadorSelecao = new Selection(param.getTipoSelecao(), this.tamanhoPopulacao);
				break;
			case PAIS_GRUPO_A_E_B:
				int tamanhoGrupoA = Double.valueOf(problema.getTamanho() * param.getPercentualGrupoA() / 100).intValue();
				int tamanhoGrupoB = Double.valueOf(problema.getTamanho() * param.getPercentualGrupoB() / 100).intValue();
				this.operadorSelecao = new Selection(param.getTipoSelecao(), tamanhoGrupoA, tamanhoGrupoB);
				break;
			default:
				throw new Exception("Operador de Sele��o n�o foi configurado corretamente.");
		}
		
		if (param.getTipoMutacao()==null) throw new Exception("Operador de Muta��o n�o foi configurado corretamente.");
		
		this.operadorMutacao = new Mutation(
			param.getTipoMutacao(), 
			param.getProbabilidadeMutacao(), 
			tamanhoMovimentacoesDiversificacao, 
			tamanhoMovimentacoesDiversificacao,
			tamanhoDescarteMovimentacoes,
			param.getMaxProporcaoGrupos(), 
			param.getEvaluationMax(), 
			problema.getTamanho()
		);
		
		if (param.getTipoCrossover()==null) throw new Exception("Operador de Crossover n�o foi configurado corretamente.");
		
		if (param.getPercentualGruposPorCrossover()==null) throw new Exception("Operador de Crossover, parametro de maximo grupos para o crossover de grupos n�o foi configurado corretamente.");
		
		// em vez de parametro por divisor passa a ser percentual
		//int maxGruposPorCrossover = Double.valueOf(problema.getTamanho() / param.getPercentualGruposPorCrossover()).intValue();
		//if (maxGruposPorCrossover < 3) maxGruposPorCrossover = 3;
		//this.operadorCrossover = new Crossover(param.getTipoCrossover(), param.getProbabilidadeCrossover(), maxGruposPorCrossover);
		int maxGruposPorCrossover = Double.valueOf(problema.getTamanho() * param.getPercentualGruposPorCrossover() / 100).intValue();
		if (maxGruposPorCrossover < 3) maxGruposPorCrossover = 3;
		this.operadorCrossover = new Crossover(param.getTipoCrossover(), param.getProbabilidadeCrossover(), maxGruposPorCrossover);
	}

	@Override
	public String getInfoParametros() {
		return
			"alg=" + this.getNomeAlgoritmo()
			+ ", problema=" + this.problema.getName()
			+ ", tamanho=" + this.problema.getTamanho()
			+ ", elite=" + this.tamanhoElite
			+ ", descarte=" + this.tamanhoDescarte
			+ ", descarte.sel=" + this.tamanhoDescarteSelecao
			+ ", descarte.mov=" + this.operadorMutacao.getTamanhoMovimentacoesDescarte()
			+ ", mutacao=" + this.operadorMutacao.getTipoMutacao().name()
			+ ", probMutacao=" + this.operadorMutacao.getProbabilidadeMutacao()
			+ ", crossover=" + this.operadorCrossover.getTipoCrossover().name()
			+ ", probCrossover=" + this.operadorCrossover.getProbabilidadeCrossover()
			+ ", maxGruposPorCrossover=" + this.operadorCrossover.getMaxGruposPorCrossover()
			+ ", maxProporcaoGruposSolucaoAleatoria=" + this.maxProporcaoGrupos
			+ ", parada=" + this.tamanhoGeracoesIguaisParada
			+ ", iniA=" + this.operadorSelecao.getInicioA()
			+ ", fimA=" + this.operadorSelecao.getFimA()
			+ ", iniB=" + this.operadorSelecao.getInicioB()
			+ ", fimB=" + this.operadorSelecao.getFimB()
			+ ", populacao=" + this.tamanhoPopulacao
			+ ", iguaisMax=" + this.tamanhoSolucoesIguaisMaxima
			+ ", movDiviversif=" + this.operadorMutacao.getTamanhoMovimentacoesDiversificacao() //this.tamanhoMovimentacoesDiversificacao
		;
	}
	
	public Crossover getOperadorCrossover() {
		return operadorCrossover;
	}
	
	public Mutation getOperadorMutacao() {
		return operadorMutacao;
	}
	
	public Selection getOperadorSelecao() {
		return operadorSelecao;
	}
	
}

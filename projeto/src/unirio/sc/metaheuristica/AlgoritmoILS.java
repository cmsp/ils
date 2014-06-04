package unirio.sc.metaheuristica;

import unirio.sc.busca.AlgoritmoHC;
import unirio.sc.busca.SolucaoHC;
import unirio.sc.construtivoLista.AlgoritmoCNMLL;
import unirio.sc.construtivoLista.SolucaoCNMLL;
import unirio.sc.core.AlgoritmoAbstract;
import unirio.sc.core.Calculador;
import unirio.sc.core.Exibicao;
import unirio.sc.core.Parametro;
import unirio.sc.core.Problema;
import unirio.sc.core.TipoAlgoritmo;

public class AlgoritmoILS extends AlgoritmoAbstract 
{
	// se a solução inicial é aleatória ou por algoritmo construtivo
	private Boolean inicialConstrutivo = true;
	
	// algoritmo construtivo para gerar solução inicial
	private AlgoritmoAbstract algoritmoConstrutor;

	// algoritmo de busca local
	private AlgoritmoHC algoritmoBusca;
	
	// quantidade de movimentações para alterar uma solução
	private int quantidadeMovimentacoes = 0;

	// quantidade de swaps para alterar uma solução
	private int quantidadeSwaps = 0;
	
	// quantidade de splits para alterar uma solução
	private int quantidadeSplits = 0;

	// quantidade de joins para alterar uma solução
	private int quantidadeJoins = 0;

	// quantidade de explosion para alterar uma solução
	private int quantidadeExplosion = 0;

	// quantidade máxima de iterações sem melhoria antes de iniciar o restar aleatorio
	private int maxIteracoesSemMelhoria = -1;
	
	// quantidade mínima de movimentações
	private int quantidadeMinimaMovimentacoes = 2;
	
	// quantidade mínima de trocas
	private int quantidadeMinimaSwaps = 2;

	// quantidade mínima de splits
	private int quantidadeMinimaSplits = 1;

	// quantidade mínima de joins
	private int quantidadeMinimaJoins = 1;

	// quantidade mínima de explosion
	private int quantidadeMinimaExplosion = 1;
	
	// se os métodos de perturbação serão simultaneos ou selecionados aleatoriamente
	private boolean metodosPerturbacaoSimultaneos = true;
	
	// vetor auxiliar que informa os métodos a serem selecionados e a quantidade de métodos disponíveis para seleção
	private TipoPerturbacao[] metodosPerturbacaoDisponiveis = null;

	public AlgoritmoILS( 
		Problema problema, 
		Exibicao exibicao, 
		Parametro param) 
	{
		this.problema = problema;
		this.calculador = new Calculador(problema);
		this.exibicao = exibicao;
		setConfiguracoes(param);
		
		Parametro paramCNM = new Parametro(TipoAlgoritmo.GULOSO_HASHMAP,1);
		paramCNM.setEvaluationMax(this.evaluationMax);
		algoritmoConstrutor = new AlgoritmoCNMLL(
			this.problema,
			this.exibicao,
			paramCNM
		);

		Parametro paramHC = new Parametro(TipoAlgoritmo.HILL_CLIMBING,1);
		paramHC.setMaxProporcaoGrupos(this.maxProporcaoGrupos);
		paramHC.setEvaluationMax(this.evaluationMax);
		paramHC.setRestartMultiple(false);
		algoritmoBusca = new AlgoritmoHC(
			this.problema,
			this.exibicao,
			paramHC
		);
		
		//startDebug();
	}

	@Override
	public String getNomeAlgoritmo() 
	{
		return "Algoritmo Iterated Local Search";
	}
	
	/**
	 * Utiliza um algoritmo construtivo rápido para gerar uma solução inicial
	 */
	public SolucaoILS geraSolucaoInicial()
	{
		if (!this.inicialConstrutivo) {
			return GeradorSolucao.getSolucaoILSRandom();
		}
		algoritmoConstrutor.reset();
		algoritmoConstrutor.executa("imprimeCabecalhoDebug=false");
		SolucaoILS solucao = new SolucaoILS((SolucaoCNMLL)algoritmoConstrutor.getSolucao());
		this.evaluation += algoritmoConstrutor.getEvaluation();
		this.iteracao = algoritmoConstrutor.getIteracao();
		return solucao;
	}
	
	/**
	 * Utiliza um algoritmo construtivo rápido para gerar uma solução inicial
	 */
	public SolucaoILS buscaLocal(SolucaoILS solucaoAtual)
	{
		algoritmoBusca.reset();
		algoritmoBusca.setIteracao(this.iteracao);
		algoritmoBusca.setEvaluation(this.evaluation);
		SolucaoILS solucao = new SolucaoILS(algoritmoBusca.executa(new SolucaoHC(solucaoAtual)));
		//this.evaluation += algoritmoHillClimbing.getEvaluation();
		this.evaluation = algoritmoBusca.getEvaluation();
		this.iteracao = algoritmoBusca.getIteracao();
		return solucao;
	}
	
	private void modificaSolucaoPorMovimentacao(SolucaoILS solucaoModificada)
	{
		for (int i = 0; i < this.quantidadeMovimentacoes; i++) {
			int iGrupo = GeradorSolucao.randInt(0, solucaoModificada.getTotalGrupos()-1);
			int iItem = GeradorSolucao.randInt(0, solucaoModificada.getTotalItens()-1);
			
			// atualiza variaveis auxiliares qtdItens e totalGrupos - descartar estas variaveis
			int grupoOld = solucaoModificada.getValores()[iItem];
			int totalGrupos = solucaoModificada.getTotalGrupos();
			solucaoModificada.getQtdItens()[grupoOld]--;
			if (solucaoModificada.getQtdItens()[grupoOld]==0)
				totalGrupos--;
			solucaoModificada.getQtdItens()[iGrupo]++;
			if (solucaoModificada.getQtdItens()[iGrupo]==1)
				totalGrupos++;
			solucaoModificada.setTotalGrupos(totalGrupos);
			
			solucaoModificada.getValores()[iItem] = iGrupo;
		}
	}

	private void modificaSolucaoPorTrocas(SolucaoILS solucaoModificada)
	{
		for (int i = 0; i < this.quantidadeSwaps; i++) {
			int iItem1 = GeradorSolucao.randInt(0, solucaoModificada.getTotalItens()-1);
			int iItem2 = GeradorSolucao.randInt(0, solucaoModificada.getTotalItens()-1);
			
			int iGrupoTemp = solucaoModificada.getValores()[iItem1]; 
			solucaoModificada.getValores()[iItem1] = solucaoModificada.getValores()[iItem2];
			solucaoModificada.getValores()[iItem2] = iGrupoTemp;
		}
	}

	private void modificaSolucaoPorSplits(SolucaoILS solucaoModificada)
	{
		for (int i = 0; i < this.quantidadeSplits; i++) {
			
			int grupo = GeradorSolucao.randInt(0, solucaoModificada.getTotalGrupos()-1);
			
			int[] valores   = solucaoModificada.getValores();
			int[] qtdItens  = solucaoModificada.getQtdItens();
			int totalGrupos = solucaoModificada.getTotalGrupos();
			int totalItens  = solucaoModificada.getTotalItens();
			
			if (totalGrupos>=totalItens/this.maxProporcaoGrupos)
				return;

			int qtdItensGrupo = qtdItens[grupo];
			if (qtdItensGrupo==1) 
				return;
			
			int totalItensGrupoNovo = qtdItensGrupo/2;

			int grupoNovo = totalGrupos;
			totalGrupos++;
			
			qtdItens[grupoNovo] = totalItensGrupoNovo;
			qtdItens[grupo] -= totalItensGrupoNovo;
			
			int contadorGrupoNovo = 0;
			for (int j = totalItens - 1; j >= 0; j--) {
				if (valores[j]==grupo) {
					valores[j] = grupoNovo;
					contadorGrupoNovo++;
				}
				if (contadorGrupoNovo==totalItensGrupoNovo)
					break;
			}

			solucaoModificada.setQtdItens(qtdItens, totalGrupos);
			solucaoModificada.setValores(valores);
		}
	}

	private void modificaSolucaoPorJoins(SolucaoILS solucaoModificada)
	{
		for (int i = 0; i < this.quantidadeJoins; i++) {
			
			int grupo1 = GeradorSolucao.randInt(0, solucaoModificada.getTotalGrupos()-1);
			int grupo2 = GeradorSolucao.randInt(0, solucaoModificada.getTotalGrupos()-1);
			if (grupo1 == grupo2) {
				continue;
			}
			
			int menorGrupo = grupo1;
			int maiorGrupo = grupo2;
			if (grupo1 > grupo2) {
				menorGrupo = grupo2;
				maiorGrupo = grupo1;
			}
			
			int[] valores   = solucaoModificada.getValores();
			int[] qtdItens  = solucaoModificada.getQtdItens();
			int totalGrupos = solucaoModificada.getTotalGrupos();
			int totalItens  = solucaoModificada.getTotalItens();
			
			qtdItens[menorGrupo] += qtdItens[maiorGrupo];
			qtdItens[maiorGrupo] = 0;
			for (int j = 0; j < totalItens; j++) {
				if (valores[j]==maiorGrupo)
					valores[j] = menorGrupo;
			}

			// shift da quantidade de itens dos grupos pois diminuiram em um grupo
			if (maiorGrupo<totalGrupos-1) {
				for (int j = maiorGrupo; j < totalGrupos-1; j++) {
					qtdItens[j] = qtdItens[j+1];
				}
			}
			qtdItens[totalGrupos-1] = 0;
			totalGrupos--;
			
			// shift dos grupos para os valores, pois os códigos vão reduzir em um
			for (int j = 0; j < totalItens; j++) {
				if (valores[j]>=maiorGrupo)
					valores[j]--;
			}
			
			solucaoModificada.setQtdItens(qtdItens, totalGrupos);
			solucaoModificada.setValores(valores);
		}
	}

	private void modificaSolucaoPorExplosion(SolucaoILS solucaoModificada)
	{
		for (int i = 0; i < this.quantidadeExplosion; i++) {
			int grupo = GeradorSolucao.randInt(0, solucaoModificada.getTotalGrupos()-1);
			
			int[] valores   = solucaoModificada.getValores();
			int[] qtdItens  = solucaoModificada.getQtdItens();
			int totalGrupos = solucaoModificada.getTotalGrupos();
			int totalItens  = solucaoModificada.getTotalItens();
			
			if (totalGrupos>=totalItens/this.maxProporcaoGrupos)
				return;

			int qtdItensGrupo = qtdItens[grupo];
			if (qtdItensGrupo==1) 
				return;
			
			int totalItensEmGruposNovos = qtdItensGrupo-1;

			qtdItens[grupo] = 1;
			int contadorGrupoNovo = 0;
			int grupoNovo = totalGrupos-1;
			for (int j = totalItens - 1; j >= 0; j--) {
				if (valores[j]==grupo) {
					grupoNovo++;
					valores[j] = grupoNovo;
					qtdItens[grupoNovo] = 1;
					contadorGrupoNovo++;
				}
				if (contadorGrupoNovo==totalItensEmGruposNovos)
					break;
			}

			solucaoModificada.setQtdItens(qtdItens, totalGrupos+totalItensEmGruposNovos);
			solucaoModificada.setValores(valores);
		}
	}

	public SolucaoILS modificaSolucaoPorMetodosSelecionadosAleatoriamente(SolucaoILS solucaoAtual)
	{
		SolucaoILS solucaoModificada = new SolucaoILS(solucaoAtual);
		
		int perturbacaoSelecionada = GeradorSolucao.randInt(0, this.metodosPerturbacaoDisponiveis.length-1);
		
		TipoPerturbacao tipoPerturbacao = this.metodosPerturbacaoDisponiveis[perturbacaoSelecionada];
		
		switch (tipoPerturbacao) {
			case MOVE:
				modificaSolucaoPorMovimentacao(solucaoModificada);		
				break;
			case SWAP:
				modificaSolucaoPorTrocas(solucaoModificada);
				break;
			case SPLIT:
				modificaSolucaoPorSplits(solucaoModificada);
				break;
			case JOIN:
				modificaSolucaoPorJoins(solucaoModificada);
				break;
			case EXPLOSION:
				modificaSolucaoPorExplosion(solucaoModificada);
				break;
		}
		
		GeradorSolucao.normalizar(solucaoModificada);
		this.evaluation++;
		double fitness = this.calculador.evaluate(solucaoModificada);
		solucaoModificada.setFitness(fitness);
		solucaoModificada.setLocation(this.evaluation);
		
		return solucaoModificada;
	}

	public SolucaoILS modificaSolucaoPorMetodosSimultaneos(SolucaoILS solucaoAtual)
	{
		SolucaoILS solucaoModificada = new SolucaoILS(solucaoAtual);
		
		// após movimentação de módulos podem ficar buracos no vetor qtdItens, influindo nos métodos de split, join, explosion, que sorteiam grupos
		modificaSolucaoPorMovimentacao(solucaoModificada);
		GeradorSolucao.normalizar(solucaoModificada);
		
		modificaSolucaoPorTrocas(solucaoModificada);
		
		modificaSolucaoPorSplits(solucaoModificada);
		
		modificaSolucaoPorJoins(solucaoModificada);
		
		modificaSolucaoPorExplosion(solucaoModificada);

		GeradorSolucao.normalizar(solucaoModificada);
		this.evaluation++;
		double fitness = this.calculador.evaluate(solucaoModificada);
		solucaoModificada.setFitness(fitness);
		solucaoModificada.setLocation(this.evaluation);
		
		return solucaoModificada;
	}

	public void executa(String... args) 
	{
		long tempoInicial = System.currentTimeMillis();

		Double seed = null;
		if (args!=null && args.length>0)
			seed = Double.valueOf(args[0]);
		
		int tamanhoSolucao = this.problema.getTamanho();
		int limiteInferior = 0;
		int limiteSuperior = (tamanhoSolucao/maxProporcaoGrupos) - 1;
		GeradorSolucao.inicia(tamanhoSolucao, limiteInferior, limiteSuperior, seed);
		
		// Debug
		// se não houver debug, comentar o bloco para otimização
		this.exibicao.printDebugGeracaoCabecalho(this,seed);
		// fim Debug
		
		int iteracoesSemMelhoria = 0;

		SolucaoILS solucaoInicial = geraSolucaoInicial();
		
		SolucaoILS solucaoEstrela = buscaLocal(solucaoInicial);
		
		GeradorSolucao.normalizar(solucaoEstrela);
		double fitnessInicial = this.calculador.evaluate(solucaoEstrela);
		this.evaluation++;
		solucaoEstrela.setFitness(fitnessInicial);
		solucaoEstrela.setLocation(this.evaluation);
		
		SolucaoILS solucaoMelhor = new SolucaoILS(solucaoEstrela);
		
		//this.debug.println("solucaoInicial=" + solucaoAtual.getString());
		
		if (this.metodosPerturbacaoSimultaneos) 
		{
			while (this.evaluation < this.evaluationMax) {
				
				// System.out.println(this.iteracao);
				
				SolucaoILS solucaoLinha = modificaSolucaoPorMetodosSimultaneos(solucaoEstrela);

				/*
				try {
					Verificador.validaSolucao(this, solucaoLinha);
				}
				catch (Exception e) {
					System.out.println("ERRO SOLUÇÂO INCONSISTENTE.");
					System.out.println(e.getMessage());
				}
				*/
				
				SolucaoILS solucaoEstrelaLinha = buscaLocal(solucaoLinha);
				
				// Criterio de aceitação
				if (solucaoEstrelaLinha.getFitness()<=solucaoEstrela.getFitness()) {
					solucaoEstrela = solucaoEstrelaLinha;
				}
	
				if (solucaoEstrela.getFitness() < solucaoMelhor.getFitness()) {
					solucaoMelhor = solucaoEstrela;
					iteracoesSemMelhoria = 0;
				}
				else {
					iteracoesSemMelhoria++;
				}
				
				if (this.maxIteracoesSemMelhoria != 0 && iteracoesSemMelhoria >= this.maxIteracoesSemMelhoria) {
					solucaoEstrela = GeradorSolucao.getSolucaoILSRandom();
					double fitness = this.calculador.evaluate(solucaoEstrela);
					solucaoEstrela.setFitness(fitness);
				}
				
				this.iteracao++;			
			}
		}
		else 
		{
			while (this.evaluation < this.evaluationMax) {
				
				// System.out.println(this.iteracao);
								
				SolucaoILS solucaoLinha = modificaSolucaoPorMetodosSelecionadosAleatoriamente(solucaoEstrela);
				
				/*
				try {
					Verificador.validaSolucao(this, solucaoLinha);
				}
				catch (Exception e) {
					System.out.println("ERRO SOLUÇÂO INCONSISTENTE.");
					System.out.println(e.getMessage());
				}
				*/

				SolucaoILS solucaoEstrelaLinha = buscaLocal(solucaoLinha);
				
				// Criterio de aceitação
				if (solucaoEstrelaLinha.getFitness()<=solucaoEstrela.getFitness()) {
					solucaoEstrela = solucaoEstrelaLinha;
				}
	
				if (solucaoEstrela.getFitness() < solucaoMelhor.getFitness()) {
					solucaoMelhor = solucaoEstrela;
					iteracoesSemMelhoria = 0;
				}
				else {
					iteracoesSemMelhoria++;
				}
				
				if (this.maxIteracoesSemMelhoria != 0 && iteracoesSemMelhoria >= this.maxIteracoesSemMelhoria) {
					solucaoEstrela = GeradorSolucao.getSolucaoILSRandom();
					double fitness = this.calculador.evaluate(solucaoEstrela);
					solucaoEstrela.setFitness(fitness);
				}
				
				this.iteracao++;			
			}			
		}
		this.solucao = solucaoMelhor;
		this.tempoExecucao = System.currentTimeMillis() - tempoInicial;
	}
	
	@Override
	public String getInfoParametros() {
		return
			"algoritmo=" + this.getNomeAlgoritmo()
			+ ",problema=" + this.problema.getName()
			+ ",tamanho=" + this.problema.getTamanho()
			+ ",moves=" + this.quantidadeMovimentacoes
			+ ",swaps=" + this.quantidadeSwaps
			+ ",splits=" + this.quantidadeSplits
			+ ",joins=" + this.quantidadeJoins
			+ ",explosion=" + this.quantidadeExplosion
			+ ",iteracoesSemMelhoria=" + this.maxIteracoesSemMelhoria
			+ ",evaluationMax=" + this.evaluationMax
			+ ",maxProporcaoGrupos=" + this.maxProporcaoGrupos
			+ ",maxIteracoesSemMelhoria=" + this.maxIteracoesSemMelhoria
			+ ",inicialConstrutivo=" + this.inicialConstrutivo
			+ ",metodosSimultaneos=" + this.metodosPerturbacaoSimultaneos
			+ ",evaluationMax=" + this.evaluationMax
		;
	}
	
	@Override
	public void setConfiguracoes(Parametro param) {
		this.evaluationMax = param.getEvaluationMax();
		this.maxProporcaoGrupos = param.getMaxProporcaoGrupos();
		
		if (param.isInicialConstrutivo()!=null) {
			this.inicialConstrutivo = param.isInicialConstrutivo();
		}
		
		if (param.getMetodosPerturbacaoSimultaneos()!=null) {
			this.metodosPerturbacaoSimultaneos = param.getMetodosPerturbacaoSimultaneos();
		}

		if (param.getPercentualMoves()!=null) {
			if (param.getPercentualMoves() == 0.00)
				this.quantidadeMovimentacoes = 0;
			else {
				this.quantidadeMovimentacoes = Double.valueOf(problema.getTamanho() * param.getPercentualMoves() / 100).intValue();
				if (this.quantidadeMovimentacoes<=0)
					this.quantidadeMovimentacoes = this.quantidadeMinimaMovimentacoes;
			}
			
		}
		if (param.getPercentualSwaps()!=null) {
			if (param.getPercentualSwaps() == 0.00)
				this.quantidadeSwaps = 0;
			else {
				this.quantidadeSwaps = Double.valueOf(problema.getTamanho() * param.getPercentualSwaps() / 100).intValue();
				if (this.quantidadeSwaps<=0)
					this.quantidadeSwaps = this.quantidadeMinimaSwaps;
			}
		}
		if (param.getPercentualSplits()!=null) {
			if (param.getPercentualSplits() == 0.00)
				this.quantidadeSplits = 0;
			else {
				this.quantidadeSplits = Double.valueOf(problema.getTamanho() * param.getPercentualSplits() / 100).intValue();
				if (this.quantidadeSplits<=0)
					this.quantidadeSplits = this.quantidadeMinimaSplits;
			}
		}
		if (param.getPercentualJoins()!=null) {
			if (param.getPercentualJoins() == 0.00)
				this.quantidadeJoins = 0;
			else {
				this.quantidadeJoins = Double.valueOf(problema.getTamanho() * param.getPercentualJoins() / 100).intValue();
				if (this.quantidadeJoins<=0)
					this.quantidadeJoins = this.quantidadeMinimaJoins;
			}
		}
		if (param.getPercentualExplosion()!=null) {
			if (param.getPercentualExplosion() == 0.00)
				this.quantidadeExplosion = 0;
			else {
				this.quantidadeExplosion = Double.valueOf(problema.getTamanho() * param.getPercentualExplosion() / 100).intValue();
				if (this.quantidadeExplosion<=0)
					this.quantidadeExplosion = this.quantidadeMinimaExplosion;
			}
		}
		
		configuraPerturbacaoDisponiveis();
		
		this.maxIteracoesSemMelhoria = param.getMaxIteracoesSemMelhoria();
	}
	
	private void configuraPerturbacaoDisponiveis() {
		// vetor para seleção dos métodos de perturbacao
		int qtdSelecoes = 0;
		if (this.quantidadeMovimentacoes > 0) 
			qtdSelecoes++;
		if (this.quantidadeSwaps > 0) 
			qtdSelecoes++;
		if (this.quantidadeSplits > 0) 
			qtdSelecoes++;
		if (this.quantidadeJoins > 0) 
			qtdSelecoes++;
		if (this.quantidadeExplosion > 0) 
			qtdSelecoes++;
		this.metodosPerturbacaoDisponiveis = new TipoPerturbacao[qtdSelecoes];
		int i = 0;
		if (this.quantidadeMovimentacoes > 0) 
			this.metodosPerturbacaoDisponiveis[i++] = TipoPerturbacao.MOVE;
		if (this.quantidadeSwaps > 0) 
			this.metodosPerturbacaoDisponiveis[i++] = TipoPerturbacao.SWAP;
		if (this.quantidadeSplits > 0)
			this.metodosPerturbacaoDisponiveis[i++] = TipoPerturbacao.SPLIT;
		if (this.quantidadeJoins > 0)
			this.metodosPerturbacaoDisponiveis[i++] = TipoPerturbacao.JOIN;
		if (this.quantidadeExplosion > 0)
			this.metodosPerturbacaoDisponiveis[i++] = TipoPerturbacao.EXPLOSION;
	}
}


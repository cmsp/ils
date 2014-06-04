package unirio.sc.busca;

import unirio.sc.core.AlgoritmoAbstract;
import unirio.sc.core.Exibicao;
import unirio.sc.core.Parametro;
import unirio.sc.core.Problema;

/**
 * Algoritmo Hill Climbing com multiplos reinicios (Random Restar Hill Climbing)
 */
public class AlgoritmoHC extends AlgoritmoAbstract {
	
	protected boolean restartMultiple = true;
	
	public AlgoritmoHC( 
		Problema problema, 
		Exibicao exibicao, 
		Parametro param) 
	{
		this.problema = problema;
		this.calculador = new CalculadorHC(problema);
		this.exibicao = exibicao;
		setConfiguracoes(param);
	}

	@Override
	public String getNomeAlgoritmo() 
	{
		return "Algoritmo Hill Climbing";
	}
	
	/**
	 * A nova solu��o para o algoritmo randomico � calculada aleatoriamente
	 */
	private SolucaoHC buscaMelhorVizinho(SolucaoHC solucao) {
		
		// cria uma c�pia da solu��o original por seguran�a
		SolucaoHC solucaoMelhor = new SolucaoHC(solucao);
		
		int[] valores = solucaoMelhor.getValores();
		int[] qtdItens = solucaoMelhor.getQtdItens();
		int totalItens  = solucaoMelhor.getTotalItens();
		int totalGrupos = solucaoMelhor.getTotalGrupos();
		
		int melhorItem  = -1;
		int melhorGrupo = -1;
		int evaluationSolucao = 0;
		double melhorFitness = solucaoMelhor.getFitness();

		// percorre cada um dos itens
		for(int i = 0; i < totalItens; i++) {
			int iGrupoAtual = valores[i];
			
			((CalculadorHC)calculador).evaluateEGravaEstado(valores);
			this.evaluation++;
			
			//al�m de todos os grupos permite movimentar para um novo grupo
			for(int j = 0; j < totalGrupos; j++) {
				if(iGrupoAtual!=j) {
					int grupoOrigem = valores[i];
					int grupoDestino = j;
					valores[i] = grupoDestino;
					
					double fitness = ((CalculadorHC)calculador).evaluateMove(valores, i, grupoOrigem, grupoDestino);
					this.evaluation++;
					
					// verifica se este � o melhor vizinho
					if (fitness < melhorFitness) {
						melhorFitness = fitness;
						melhorItem  = i;
						melhorGrupo = j;
						evaluationSolucao = this.evaluation;
					}
				}
			}
			// desfaz a modifica��o do vizinho
			valores[i] = iGrupoAtual;
		}
		
		// sen�o achou melhor retorna a solu��o inicial
		if (melhorItem==-1) {
			return solucaoMelhor;
		}
		
		// atualiza vetor com qtdItens
		qtdItens[valores[melhorItem]]--;
		if (qtdItens[valores[melhorItem]]==0)
			totalGrupos--;
		qtdItens[melhorGrupo]++;
		if (qtdItens[melhorGrupo]==1)
			totalGrupos++;
		
		// atualiza vetor com os valores
		valores[melhorItem] = melhorGrupo;

		//double fitness = this.calculador.evaluate(solucaoMelhor);
		solucaoMelhor.setSolucao(valores, qtdItens, totalGrupos, melhorFitness, evaluationSolucao);
		GeradorSolucao.normalizar(solucaoMelhor);
		
		return solucaoMelhor;
	}	
	
	@Override
	public void executa(String... args) 
	{
		long tempoInicial = System.currentTimeMillis();

		Double seed = null;
		if (args!=null && args.length>0)
			seed = Double.valueOf(args[0]);

		int tamanhoSolucao = this.problema.getTamanho();
		int limiteInferior = 0;
		int limiteSuperior = (tamanhoSolucao/maxProporcaoGrupos) - 1;
		
		// Debug
		// se n�o houver debug, comentar o bloco para otimiza��o
		this.exibicao.printDebugGeracaoCabecalho(this,seed);
		// fim Debug
		
		GeradorSolucao.inicia(tamanhoSolucao, limiteInferior, limiteSuperior, seed);
		
		SolucaoHC solucao = GeradorSolucao.getSolucaoHCRandom();
		double fitness = ((CalculadorHC)this.calculador).evaluateEGravaEstado(solucao.getValores());
		solucao.setFitness(fitness);
		SolucaoHC solucaoMelhor = solucao;
		
		while (this.evaluation < this.evaluationMax) {
			
			SolucaoHC solucaoNova = buscaMelhorVizinho(solucao);
			
			// verifica se a nova solu��o � melhor que a anterior
			if (solucaoNova.getFitness() < solucao.getFitness()) {
				solucao = solucaoNova;
			}
			else
			{
				// verifica se � uma solu��o �tima global
				if (solucao.getFitness() < solucaoMelhor.getFitness()) {
					solucaoMelhor = new SolucaoHC(solucao);
				}

				// se n�o houver restart multiplo sai do loop
				if (!this.restartMultiple)
					break;
				
				// tenta realizar novo hillclimbing em outro ponto
				solucao = GeradorSolucao.getSolucaoHCRandom();
				if (solucao == null) break;
				fitness = this.calculador.evaluate(solucao);
				solucao.setFitness(fitness);		
			}
			
			this.iteracao++;
			
			// Debug
			// se n�o houver debug, comentar o bloco para otimiza��o
			this.exibicao.printDebugGeracao(solucaoNova, this.iteracao, this.evaluation);
			// fim Debug
		}
		
		// pode ser sa�do devido ter atingido o n�mero de avalia��es
		if (solucao.getFitness() < solucaoMelhor.getFitness())
			solucaoMelhor = new SolucaoHC(solucao);
		
		this.solucao = solucaoMelhor;
		this.tempoExecucao = System.currentTimeMillis() - tempoInicial;
	}
	
	/**
	 * Loop utilizado pelo ILS, sem verifica��o de m�ltiplo restart
	 */
	public SolucaoHC executa(SolucaoHC solucaoAtual) 
	{
		SolucaoHC solucaoMelhor = new SolucaoHC(solucaoAtual);
		
		while (this.evaluation < this.evaluationMax) {
			
			SolucaoHC solucaoNova = buscaMelhorVizinho(solucaoMelhor);

			 // verifica se a nova solu��o � melhor que a anterior
			if (solucaoNova == null || solucaoNova.getFitness() >= solucaoMelhor.getFitness()) {
				break;
			}
			
			solucaoMelhor = solucaoNova;
			this.iteracao++;
			
			// Debug
			// se n�o houver debug, comentar o bloco para otimiza��o
			this.exibicao.printDebugGeracao(solucaoMelhor, this.iteracao, this.evaluation);
			// fim Debug
		}
		
		return solucaoMelhor;
	}
	
	@Override
	public String getInfoParametros() {
		return
			"algoritmo=" + this.getNomeAlgoritmo()
			+ ",problema=" + this.problema.getName()
			+ ",tamanho=" + this.problema.getTamanho()
			+ ",restartMultiple=" + this.restartMultiple
			+ ",evaluationMax=" + this.evaluationMax
		;
	}
	
	@Override
	public void setConfiguracoes(Parametro param) {		
		this.evaluationMax = param.getEvaluationMax();
		this.restartMultiple = param.getRestartMultiple();
	}
	
}

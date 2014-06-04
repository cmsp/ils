package unirio.sc.genetico;

import unirio.sc.core.SolucaoAbstract;
import unirio.sc.core.random.PseudoRandom;
import unirio.sc.genetico.classico.SolucaoGeneticoGA;
import unirio.sc.genetico.falkenauer.SolucaoGeneticoGF;

/**
 * Métodos de mutação
 *
 * Métodos: uniformMutation, nonUniformMutation e delta
 * foram adaptados para este projeto a partir do JMetal (http://jmetal.sourceforge.net/)
 * @author Juan J. Durillo
 */
public class Mutation {
	
	private TipoMutacao tipoMutacao = null;
	private double probabilidadeMutacao = 0.00;
	private int tamanhoMovimentacoesMutacao;
	private int tamanhoMovimentacoesDiversificacao;
	private int tamanhoMovimentacoesDescarte;
	private int maxProporcaoGrupos;
	private int maxIteracao;
	private int pertubacao;
	
	public Mutation(TipoMutacao tipoMutacao, 
			double probabilidadeMutacao, 
			int tamanhoMovimentacoesMutacao, 
			int tamanhoMovimentacoesDiversificacao,
			int tamanhoMovimentacoesDescarte,
			int maxProporcaoGrupos,
			int maxIteracao,
			int pertubacao) {
		this.tipoMutacao = tipoMutacao;
		this.probabilidadeMutacao = probabilidadeMutacao;
		this.tamanhoMovimentacoesMutacao = tamanhoMovimentacoesMutacao;
		this.tamanhoMovimentacoesDiversificacao = tamanhoMovimentacoesDiversificacao;
		this.tamanhoMovimentacoesDescarte = tamanhoMovimentacoesDescarte;
		this.maxProporcaoGrupos = maxProporcaoGrupos;
		this.maxIteracao = maxIteracao;
		this.pertubacao = pertubacao;
	}
	
	public TipoMutacao getTipoMutacao() {
		return tipoMutacao;
	}
	
	public void setTamanhoMovimentacoesDiversificacao(
			int tamanhoMovimentacoesDiversificacao) {
		this.tamanhoMovimentacoesDiversificacao = tamanhoMovimentacoesDiversificacao;
	}
	
	public int getTamanhoMovimentacoesDiversificacao() {
		return tamanhoMovimentacoesDiversificacao;
	}
	
	public void setTamanhoMovimentacoesDescarte(int tamanhoMovimentacoesDescarte) {
		this.tamanhoMovimentacoesDescarte = tamanhoMovimentacoesDescarte;
	}
	
	public int getTamanhoMovimentacoesDescarte() {
		return tamanhoMovimentacoesDescarte;
	}
	
	public int getMaxProporcaoGrupos() {
		return maxProporcaoGrupos;
	}
	
	public void setMaxProporcaoGrupos(int maxProporcaoGrupos) {
		this.maxProporcaoGrupos = maxProporcaoGrupos;
	}
	
	public void setProbabilidadeMutacao(double probabilidadeMutacao) {
		this.probabilidadeMutacao = probabilidadeMutacao;
	}
	
	public double getProbabilidadeMutacao() {
		return probabilidadeMutacao;
	}
	
	public int getTamanhoMovimentacoesMutacao() {
		return tamanhoMovimentacoesMutacao;
	}
	
	public void setTamanhoMovimentacoesMutacao(int tamanhoMovimentacoesMutacao) {
		this.tamanhoMovimentacoesMutacao = tamanhoMovimentacoesMutacao;
	}
	
	/**
	 * Realiza uma mutação na solução
	 */
	public void uniformMutation( SolucaoAbstract solucao )
	{
		for ( int i = 0; i < solucao.getTotalItens( ); i++ )
		{
			if ( PseudoRandom.randDouble() < this.probabilidadeMutacao )
			{
				double min = GeradorSolucaoGenetico.getLimiteInferior();
				double max = GeradorSolucaoGenetico.getLimiteSuperior();
				double rand = min + PseudoRandom.randDouble( ) * ( max - min );
				solucao.getValores( )[ i ] = (int)rand;
			}
		}
	}
	
	/**
	 * Delta
	 */
	private double delta(double y, int iteracaoAtual, int pertubacao)
	{
		double rand = PseudoRandom.randDouble();
		return (y * (1.0 - Math.pow(rand, Math.pow((1.0 - iteracaoAtual / (double) this.maxIteracao), pertubacao))));
	}

	/**
	 * Realiza uma mutação na solução
	 */
	public void nonUniformMutation(SolucaoAbstract solucao, int iteracaoAtual)
	{
		for ( int i = 0; i < solucao.getTotalItens( ); i++ )
		{
			if ( PseudoRandom.randDouble() < this.probabilidadeMutacao )
			{
				double rand = PseudoRandom.randDouble();
				int min = GeradorSolucaoGenetico.getLimiteInferior();
				int max = GeradorSolucaoGenetico.getLimiteSuperior();
				int valorAtual = solucao.getValores( )[ i ];
				int novoValor;

				if (rand <= 0.5) {
					novoValor = (int)(delta(max - valorAtual, iteracaoAtual, this.pertubacao));
				} 
				else {
					novoValor = (int)(delta(min - valorAtual, iteracaoAtual, this.pertubacao));
				}
				if (novoValor < min)
					novoValor = min;
				else if (novoValor > max)
					novoValor = max;
				solucao.getValores( )[ i ] += novoValor;
			}
		}
	}

	public void diversificaPorMovimentacoes(SolucaoGeneticoGF solucaoAtual)
	{
		for (int i = 0; i < this.tamanhoMovimentacoesDiversificacao; i++) {
			
			int item = PseudoRandom.randInt(0, solucaoAtual.getTotalItens()-1);
			int grupoDestino = PseudoRandom.randInt(0, solucaoAtual.getTotalGrupos()-1);
			
			move(solucaoAtual, item, grupoDestino);
		}
	}

	public void diversificaPorMovimentacoesNormalizado(SolucaoGeneticoGF solucaoAtual)
	{
		for (int i = 0; i < this.tamanhoMovimentacoesDiversificacao; i++) {
			
			int item = PseudoRandom.randInt(0, solucaoAtual.getTotalItens()-1);
			int grupoDestino = PseudoRandom.randInt(0, solucaoAtual.getTotalGrupos()-1);
			
			moveNormalizado(solucaoAtual, item, grupoDestino);
		}
	}

	public void diversificaPorMovimentacoes(SolucaoGeneticoGA solucaoAtual)
	{
		int maxGrupos = solucaoAtual.getTotalItens()/this.maxProporcaoGrupos;
		
		for (int i = 0; i < this.tamanhoMovimentacoesDiversificacao; i++) {
			
			int item = PseudoRandom.randInt(0, solucaoAtual.getTotalItens()-1);
			int grupoDestino = PseudoRandom.randInt(0, maxGrupos-1);
			
			move(solucaoAtual, item, grupoDestino);
		}
	}

	public void modificaPorMovimentacoes(SolucaoGeneticoGA solucaoAtual)
	{
		int maxGrupos = solucaoAtual.getTotalItens()/this.maxProporcaoGrupos;
		
		for (int i = 0; i < this.tamanhoMovimentacoesMutacao; i++) {
			
			int item = PseudoRandom.randInt(0, solucaoAtual.getTotalItens()-1);
			int grupoDestino = PseudoRandom.randInt(0, maxGrupos-1);
			
			move(solucaoAtual, item, grupoDestino);
		}
	}

	public void modificaPorMovimentacoes(SolucaoGeneticoGF solucaoAtual)
	{
		//SolucaoGenetico solucaoModificada = new SolucaoGenetico(solucaoAtual);
		for (int i = 0; i < this.tamanhoMovimentacoesMutacao; i++) {
			
			int item = PseudoRandom.randInt(0, solucaoAtual.getTotalItens()-1);
			int grupoDestino = PseudoRandom.randInt(0, solucaoAtual.getTotalGrupos()-1);
			
			move(solucaoAtual, item, grupoDestino);
			//moveNormalizado(solucaoAtual, item, grupoDestino);
		}
	}
	
	public void modificaPorMovimentacoesNormalizado(SolucaoGeneticoGF solucaoAtual)
	{
		//SolucaoGenetico solucaoModificada = new SolucaoGenetico(solucaoAtual);
		for (int i = 0; i < this.tamanhoMovimentacoesMutacao; i++) {
			
			int item = PseudoRandom.randInt(0, solucaoAtual.getTotalItens()-1);
			int grupoDestino = PseudoRandom.randInt(0, solucaoAtual.getTotalGrupos()-1);
			
			moveNormalizado(solucaoAtual, item, grupoDestino);
		}
	}

	public void descartePorMovimentacoes(SolucaoGeneticoGF solucaoAtual)
	{
		for (int i = 0; i < this.tamanhoMovimentacoesDescarte; i++) {
			
			int item = PseudoRandom.randInt(0, solucaoAtual.getTotalItens()-1);
			int grupoDestino = PseudoRandom.randInt(0, solucaoAtual.getTotalGrupos()-1);
			
			move(solucaoAtual, item, grupoDestino);
		}
	}

	public void descartePorMovimentacoes(SolucaoGeneticoGA solucaoAtual)
	{
		int maxGrupos = solucaoAtual.getTotalItens()/this.maxProporcaoGrupos;
		
		for (int i = 0; i < this.tamanhoMovimentacoesDescarte; i++) {
			
			int item = PseudoRandom.randInt(0, solucaoAtual.getTotalItens()-1);
			int grupoDestino = PseudoRandom.randInt(0, maxGrupos-1);
			
			move(solucaoAtual, item, grupoDestino);
		}
	}

	/**
	 * Movimenta o item para o respectivo grupo
	 */
	public void move(SolucaoGeneticoGF solucao, int item, int grupoDestino) 
	{
		int[] valores   = solucao.getValores();
		int[] qtdItens  = solucao.getQtdItens();
		int totalGrupos = solucao.getTotalGrupos();

		int grupoOrigem = valores[item];
		qtdItens[grupoOrigem]--;
		qtdItens[grupoDestino]++;
		valores[item] = grupoDestino;
		
		solucao.setQtdItens(qtdItens, totalGrupos);
		solucao.setValores(valores);
		solucao.setFitness(Double.POSITIVE_INFINITY);
		solucao.setLocation(0);
	}
	
	/**
	 * Movimenta o item para o respectivo grupo
	 */
	public void moveNormalizado(SolucaoGeneticoGF solucao, int item, int grupoDestino) 
	{
		int[] valores   = solucao.getValores();
		int[] qtdItens  = solucao.getQtdItens();
		int totalGrupos = solucao.getTotalGrupos();

		int grupoOrigem = valores[item];
		qtdItens[grupoOrigem]--;
		qtdItens[grupoDestino]++;
		valores[item] = grupoDestino;
		
		if (qtdItens[grupoOrigem] == 0) {
			totalGrupos--;
		}
		
		solucao.setQtdItens(qtdItens, totalGrupos);
		solucao.setValores(valores);
		solucao.setFitness(Double.POSITIVE_INFINITY);
		solucao.setLocation(0);
	}
	
	/**
	 * Movimenta o item para o respectivo grupo
	 */
	public void move(SolucaoGeneticoGA solucao, int item, int grupoDestino) 
	{
		int[] valores   = solucao.getValores();
		valores[item] = grupoDestino;
		solucao.setValores(valores);
		solucao.setFitness(Double.POSITIVE_INFINITY);
		solucao.setLocation(0);
	}

	/**
	 * Junta dois grupos
	 */
	public static void join(SolucaoGeneticoGF solucaoAtual, int grupo1, int grupo2) {
		int menorGrupo = grupo1;
		int maiorGrupo = grupo2;
		if (grupo1 > grupo2) {
			menorGrupo = grupo2;
			maiorGrupo = grupo1;
		}
		
		int[] valores   = solucaoAtual.getValores();
		int[] qtdItens  = solucaoAtual.getQtdItens();
		int totalGrupos = solucaoAtual.getTotalGrupos();
		int totalItens  = solucaoAtual.getTotalItens();
		
		qtdItens[menorGrupo] += qtdItens[maiorGrupo];
		qtdItens[maiorGrupo] = 0;
		for (int i = 0; i < totalItens; i++) {
			if (valores[i]==maiorGrupo)
				valores[i] = menorGrupo;
		}

		// shift da quantidade de itens dos grupos pois diminuiram em um grupo
		if (maiorGrupo<totalGrupos-1) {
			for (int i = maiorGrupo; i < totalGrupos-1; i++) {
				qtdItens[i] = qtdItens[i+1];
			}
		}
		qtdItens[totalGrupos-1] = 0;
		totalGrupos--;
		
		// shift dos grupos para os valores, pois os códigos vão reduzir em um
		for (int i = 0; i < totalItens; i++) {
			if (valores[i]>=maiorGrupo)
				valores[i]--;
		}
		
		solucaoAtual.setQtdItens(qtdItens, totalGrupos);
		solucaoAtual.setValores(valores);
	}	
	
	/**
	 * Junta dois grupos
	 */
	public boolean split(SolucaoGeneticoGF solucaoAtual, int grupo) {
		
		int[] valores   = solucaoAtual.getValores();
		int[] qtdItens  = solucaoAtual.getQtdItens();
		int totalGrupos = solucaoAtual.getTotalGrupos();
		int totalItens  = solucaoAtual.getTotalItens();
		
		if (totalGrupos>=totalItens/this.maxProporcaoGrupos)
			return false;

		int qtdItensGrupo = qtdItens[grupo];
		if (qtdItensGrupo==1) 
			return false;
		
		int totalItensGrupoNovo = qtdItensGrupo/2;

		int grupoNovo = totalGrupos;
		totalGrupos++;
		
		qtdItens[grupoNovo] = totalItensGrupoNovo;
		qtdItens[grupo] -= totalItensGrupoNovo;
		
		int contadorGrupoNovo = 0;
		for (int i = totalItens - 1; i >= 0; i--) {
			if (valores[i]==grupo) {
				valores[i] = grupoNovo;
				contadorGrupoNovo++;
			}
			if (contadorGrupoNovo==totalItensGrupoNovo)
				break;
		}

		solucaoAtual.setQtdItens(qtdItens, totalGrupos);
		solucaoAtual.setValores(valores);
		
		return true;
	}	

	public void modificaJuntandoDoisGrupos(SolucaoGeneticoGF solucaoAtual) {
		if ( PseudoRandom.randDouble() < this.probabilidadeMutacao )
		{
			int grupo1 = PseudoRandom.randInt(0, solucaoAtual.getTotalGrupos()-1);
			int grupo2 = PseudoRandom.randInt(0, solucaoAtual.getTotalGrupos()-1);
			if (grupo1!=grupo2) {
				join(solucaoAtual,grupo1,grupo2);
			}
		}
	}
	
	public void modificaJuntandoDoisGruposNormalizado(SolucaoGeneticoGF solucaoAtual) {
		int grupo1 = PseudoRandom.randInt(0, solucaoAtual.getTotalGrupos()-1);
		int grupo2 = PseudoRandom.randInt(0, solucaoAtual.getTotalGrupos()-1);
		if (grupo1!=grupo2) {
			join(solucaoAtual,grupo1,grupo2);
		}
	}

	public void modificaSeparandoGrupoEmDois(SolucaoGeneticoGF solucaoAtual) {
		if ( PseudoRandom.randDouble() < this.probabilidadeMutacao )
		{
			int grupo = PseudoRandom.randInt(0, solucaoAtual.getTotalGrupos()-1);
			split(solucaoAtual, grupo);
		}
	}
	
	public void modificaSeparandoGrupoEmDoisNormalizado(SolucaoGeneticoGF solucaoAtual) {
		int grupo = PseudoRandom.randInt(0, solucaoAtual.getTotalGrupos()-1);
		split(solucaoAtual, grupo);
	}

	public void join(SolucaoGeneticoGF solucaoAtual) {
		if ( PseudoRandom.randDouble() < this.probabilidadeMutacao )
		{
			modificaJuntandoDoisGrupos(solucaoAtual);
		}
	}

	public void moveOuJoinOuSplit(SolucaoGeneticoGF solucaoAtual) {
		if ( PseudoRandom.randDouble() < this.probabilidadeMutacao )
		{
			int operacao = PseudoRandom.randInt(0, 2);
			switch (operacao) {
				case 0: 
					modificaSeparandoGrupoEmDois(solucaoAtual);
					break;
				case 1:
					modificaJuntandoDoisGrupos(solucaoAtual);
					break;
				default:
					modificaPorMovimentacoes(solucaoAtual);
					break;
			}
		}
	}
	
	public void moveOuJoinOuSplitNormalizado(SolucaoGeneticoGF solucaoAtual) {
		if ( PseudoRandom.randDouble() < this.probabilidadeMutacao )
		{
			int operacao = PseudoRandom.randInt(0, 2);
			switch (operacao) {
				case 0: 
					modificaSeparandoGrupoEmDoisNormalizado(solucaoAtual);
					break;
				case 1:
					modificaJuntandoDoisGruposNormalizado(solucaoAtual);
					break;
				default:
					modificaPorMovimentacoesNormalizado(solucaoAtual);
					break;
			}
		}
	}

	public void executa(SolucaoAbstract[] filhos, int iteracaoAtual) {
		if (this.tipoMutacao==null) return;
		switch(this.tipoMutacao) {
			case UNIFORM_MUTATION:
				uniformMutation(filhos[0]);
				uniformMutation(filhos[1]);
				break;
			case NON_UNIFORM_MUTATION:
				nonUniformMutation(filhos[0], iteracaoAtual);
				nonUniformMutation(filhos[1], iteracaoAtual);
				break;
			case MOVE_GNE:
				modificaPorMovimentacoes((SolucaoGeneticoGA)filhos[0]);
				modificaPorMovimentacoes((SolucaoGeneticoGA)filhos[1]);
				break;
			case MOVE_JOIN_SPLIT:
				moveOuJoinOuSplit((SolucaoGeneticoGF)filhos[0]);
				moveOuJoinOuSplit((SolucaoGeneticoGF)filhos[1]);
				break;
			case MOVE_JOIN_SPLIT_V02:
				moveOuJoinOuSplitNormalizado((SolucaoGeneticoGF)filhos[0]);
				moveOuJoinOuSplitNormalizado((SolucaoGeneticoGF)filhos[1]);
				break;
			case MOVE_GGA:
				modificaPorMovimentacoes((SolucaoGeneticoGF)filhos[0]);
				modificaPorMovimentacoes((SolucaoGeneticoGF)filhos[1]);
				break;
			case JOIN:
				join((SolucaoGeneticoGF)filhos[0]);
				join((SolucaoGeneticoGF)filhos[1]);
				break;
			case JOIN_V02:
				modificaJuntandoDoisGrupos((SolucaoGeneticoGF)filhos[0]);
				modificaJuntandoDoisGrupos((SolucaoGeneticoGF)filhos[1]);
				break;
			case NENHUMA:
				return;
		}
	}
	
}
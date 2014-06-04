package unirio.sc.core;

import java.util.HashMap;

import unirio.sc.genetico.TipoCrossover;
import unirio.sc.genetico.TipoDescarte;
import unirio.sc.genetico.TipoElite;
import unirio.sc.genetico.TipoMutacao;
import unirio.sc.genetico.TipoSelection;

/**
 * Parâmetros utilizados em todos os algoritmos, 
 * esta classe é utilizada para facilitar a execução de experimentos 
 * com algoritmos utilizando diferentes parâmetros
 */
public class Parametro {
	
	// separa as informacoes do parametro, pode mudar para ,
	private String sep = " ";
	
	// executa ciclos especificos nas instancias
	private HashMap<String,Integer> ciclosEspecificos = null;
	
	// parametros genericos
	private Integer nCicloInicial = 0;
	private Integer nCiclos = 1;
	private Boolean comSemente = true;
	private TipoAlgoritmo tipoAlgoritmo = null;
	private Integer evaluationMax = null; // quantidade máxima de avaliações
	private Integer multiplicadorEvaluation = 200; // evaluationMax = multiplicador * N * N
	private Integer maxIteracoesSemMelhoria = null;
	
	// parametros do hill climbing
	private Boolean restartMultiple = null;
	
	// parametros do iterated local search
	private Double percentualMoves = null;
	private Double percentualSwaps = null;
	private Double percentualSplits = null;
	private Double percentualJoins = null;
	private Double percentualExplosion = null;
	private Boolean inicialConstrutivo = true;
	private Boolean metodosPerturbacaoSimultaneos = false;

	// parametros do genetico
	private Double probabilidadeMutacao = null;     
	// probabilidade de ocorrer mutação no GA
	private Double probabilidadeCrossover = null;          // probabilidade de ocorrer crossover
	private Integer tamanhoPopulacao = null;               // tamanho da população
	private TipoElite tipoElite = null;                    // se a elite funciona como percentual ou inteiro
	private Double valorElite = null;                 	   // percentual da população elite
	private TipoDescarte tipoDescarte = null;              // tipo de descarte
	private Double percentualDescarte = null;              // percentual da população para descarte
	private Double percentualDescarteSelecao = null;       // percentual da população a ser escolhida para substituir o descarte
	private Double percentualDescarteMovimentacoes = null; // movimentacoes para diviersificar para gerar novas soluções do descarte
	private TipoSelection tipoSelecao = null;              // tipo de seleção de pais utilizados no crossover
	private TipoMutacao tipoMutacao = null;                // tipo de mutação utilizada
	private TipoCrossover tipoCrossover = null;            // tipo de crossover utilizado
	private Double percentualGrupoA = null;                // percentual do grupo A no crossover que utiliza pais de grupos diferentes
	private Double percentualGrupoB = null;                // percentual do grupo B no crossover que utiliza pais de grupos diferentes
	private Integer maxProporcaoGrupos = null;             // número máximo de grupos para geração de soluções aleatórias (número 2 equivale a 50%)
	private Double percentualSolucoesIguaisMaxima = null;  // quantidade máxima de soluções iguais usadas no crossover, se superada aplicada mutação no GF Com Mutação
	private Double percentualMovimentacoesMutacao = null;  // movimentacoes para diversificacao da populacao
	
	//private Integer maxGruposPorCrossover = null;          // número máximo de grupos que podem ser selecionados para o crossover de grupos
	private Double percentualGruposPorCrossover = null;    // percentual de grupos máximo a ser obtido sobre o tamanho total de itens
	
	public Parametro(Parametro param)
	{
		clone(param);
	}
	
	public Parametro(TipoAlgoritmo tipoAlgoritmo, Integer nCiclos) 
	{
		this.tipoAlgoritmo = tipoAlgoritmo;
		this.nCiclos = nCiclos;
	}
	
	public void setHC(Boolean restartMultiple) 
	{
		this.restartMultiple = restartMultiple;
	}
	
	public void setILS(Double percentualMoves, Double percentualSwaps, Double percentualSplits, Double percentualJoins, Double percentualExplosion, Integer maxIteracoesSemMelhoria)
	{
		setILS(percentualMoves, percentualSwaps, percentualSplits, percentualJoins, percentualExplosion, maxIteracoesSemMelhoria, true, false);
	}

	public void setILS(Double percentualMoves, Double percentualSwaps, Integer maxIteracoesSemMelhoria)
	{
		setILS(percentualMoves, percentualSwaps, null, null, null, maxIteracoesSemMelhoria, true, true);
	}

	public void setILS(Double percentualMoves, Double percentualSwaps, Double percentualSplits, Double percentualJoins, Double percentualExplosion, Integer maxIteracoesSemMelhoria, Boolean inicialConstrutivo, Boolean metodosPerturbacaoSimultaneos)
	{
		this.percentualMoves = percentualMoves;
		this.percentualSwaps = percentualSwaps;
		this.percentualSplits = percentualSplits;
		this.percentualJoins = percentualJoins;
		this.percentualExplosion = percentualExplosion;
		this.maxProporcaoGrupos = 2;
		this.maxIteracoesSemMelhoria = maxIteracoesSemMelhoria;
		this.inicialConstrutivo = inicialConstrutivo;
		this.metodosPerturbacaoSimultaneos = metodosPerturbacaoSimultaneos;
	}

	public void setGenetico(
		Integer maxIteracoesSemMelhoria,
		TipoElite tipoElite,
		Double percentualElite,
		TipoDescarte tipoDescarte,
		Double percentualDescarte,
		Double percentualDescarteSelecao,
		Double percentualDescarteMovimentacoes,
		TipoSelection tipoSelecao,
		Double percentualGrupoA,
		Double percentualGrupoB,
		TipoMutacao tipoMutacao,
		TipoCrossover tipoCrossover,
		Integer maxProporcaoGrupos,
		Double percentualGruposPorCrossover,
		Double percentualSolucoesIguaisMaxima,
		Double percentualMovimentacoesMutacao
	)
	{
		this.maxIteracoesSemMelhoria = maxIteracoesSemMelhoria;
		this.tipoElite = tipoElite;
		this.valorElite = percentualElite;
		this.tipoDescarte = tipoDescarte;
		this.percentualDescarte = percentualDescarte;
		this.percentualDescarteSelecao = percentualDescarteSelecao;
		this.percentualDescarteMovimentacoes = percentualDescarteMovimentacoes;
		this.tipoSelecao = tipoSelecao;
		this.percentualGrupoA = percentualGrupoA;
		this.percentualGrupoB = percentualGrupoB;
		this.tipoMutacao = tipoMutacao;
		this.tipoCrossover = tipoCrossover;
		this.maxProporcaoGrupos = maxProporcaoGrupos;
		this.percentualGruposPorCrossover = percentualGruposPorCrossover;
		this.percentualSolucoesIguaisMaxima = percentualSolucoesIguaisMaxima;
		this.percentualMovimentacoesMutacao = percentualMovimentacoesMutacao;
	}
	
	/*
	public void setGeneticoPadrao(Problema problema) 
	{
		int tamanho = problema.getTamanho();
		//ERRO: double probabilidadeCrossover = (tamanho < 100) ? 0.8 : 1.0;
		double probabilidadeCrossover = ((10 * tamanho) < 100) ? 0.8 : 1.0;
		double probabilidadeMutacao = 0.004 * Math.log(tamanho) / Math.log(2);

		int tamanhoPopulacao = tamanho * 10;
		// VALOR PADRAO: 200 * tamanho * tamanhoPopulacao;
		int maxAvaliacoes = 20 * tamanho * tamanhoPopulacao;
		int maxProporcaoGrupos = 2;
		double percentualGruposPorCrossover = 10;
		
		TipoElite tipoElite = TipoElite.PERCENTUAL;
		double tamanhoElite = 30.00;
		TipoDescarte tipoDescarte = TipoDescarte.ALEATORIO;
		double tamanhoDescarte = 0.00;
		double percentualDescarteSelecao = 0.00;
		double percentualDescarteMovimentacoes = 0.00;
		int tamanhoGeracoesIguaisParada = 0;
		TipoSelection tipoSelecao = TipoSelection.BINARY_TOURNAMENT;
		int tamanhoGrupoA = -1;
		int tamanhoGrupoB = -1;
		double percentualSolucoesIguais = -1;
		double percentualMovimentacaoMutacao = -1;

		TipoMutacao tipoMutacao = null;
		if (this.tipoAlgoritmo == TipoAlgoritmo.GENETICO_GNE)
			tipoMutacao = TipoMutacao.UNIFORM_MUTATION;
		else if (this.tipoAlgoritmo == TipoAlgoritmo.GENETICO_FALKENAUER)
			tipoMutacao = TipoMutacao.NENHUMA;
		
		TipoCrossover tipoCrossover = null;
		if (this.tipoAlgoritmo == TipoAlgoritmo.GENETICO_GNE)
			tipoCrossover = TipoCrossover.TWO_POINTS_CROSSOVER;
		else if (this.tipoAlgoritmo == TipoAlgoritmo.GENETICO_FALKENAUER)
			tipoCrossover = TipoCrossover.FALKNAUER_SEM_ELEMENTOS_UNITARIOS;

		setProbabilidadeCrossover(probabilidadeCrossover);
		setProbabilidadeMutacao(probabilidadeMutacao);
		setTamanhoPopulacao(tamanhoPopulacao);
		setEvaluationMax(maxAvaliacoes);
		setGenetico(
			tamanhoGeracoesIguaisParada,
			tipoElite,
			tamanhoElite,
			tipoDescarte,
			tamanhoDescarte, 
			percentualDescarteSelecao,
			percentualDescarteMovimentacoes,
			tipoSelecao, 
			(double)tamanhoGrupoA, 
			(double)tamanhoGrupoB, 
			tipoMutacao,
			tipoCrossover,
			maxProporcaoGrupos,
			percentualGruposPorCrossover,
			percentualSolucoesIguais,
			percentualMovimentacaoMutacao
		);
	}
	*/
	
	public void setnCicloInicial(Integer nCicloInicial) {
		this.nCicloInicial = nCicloInicial;
	}
	
	public Integer getnCicloInicial() {
		return nCicloInicial;
	}

	public Integer getnCiclos() {
		return nCiclos;
	}

	public void setnCiclos(Integer nCiclos) {
		this.nCiclos = nCiclos;
	}

	public TipoAlgoritmo getTipoAlgoritmo() {
		return tipoAlgoritmo;
	}

	public void setTipoAlgoritmo(TipoAlgoritmo tipoAlgoritmo) {
		this.tipoAlgoritmo = tipoAlgoritmo;
	}

	public Integer getEvaluationMax() {
		return evaluationMax;
	}

	public void setEvaluationMax(Integer evaluationMax) {
		this.evaluationMax = evaluationMax;
	}
	
	public Integer getMultiplicadorEvaluation() {
		return multiplicadorEvaluation;
	}

	public void setMultiplicadorEvaluation(Integer multiplicadorEvaluation) {
		this.multiplicadorEvaluation = multiplicadorEvaluation;
	}

	public Integer getMaxProporcaoGrupos() {
		return maxProporcaoGrupos;
	}

	public void setMaxProporcaoGrupos(Integer maxProporcaoGrupos) {
		this.maxProporcaoGrupos = maxProporcaoGrupos;
	}

	public void setPercentualGruposPorCrossover(
			Double percentualGruposPorCrossover) {
		this.percentualGruposPorCrossover = percentualGruposPorCrossover;
	}
	
	public Double getPercentualGruposPorCrossover() {
		return percentualGruposPorCrossover;
	}
	
	public Boolean getRestartMultiple() {
		return restartMultiple;
	}

	public void setRestartMultiple(Boolean restartMultiple) {
		this.restartMultiple = restartMultiple;
	}

	public Double getPercentualMoves() {
		return percentualMoves;
	}

	public void setPercentualMoves(Double percentualMoves) {
		this.percentualMoves = percentualMoves;
	}
	
	public void setPercentualJoins(Double percentualJoins) {
		this.percentualJoins = percentualJoins;
	}
	
	public Double getPercentualJoins() {
		return percentualJoins;
	}
	
	public void setPercentualExplosion(Double percentualExplosion) {
		this.percentualExplosion = percentualExplosion;
	}
	
	public Double getPercentualExplosion() {
		return percentualExplosion;
	}
	
	public void setPercentualSplits(Double percentualSplits) {
		this.percentualSplits = percentualSplits;
	}
	
	public Double getPercentualSplits() {
		return percentualSplits;
	}
	
	public void setInicialConstrutivo(Boolean inicialConstrutivo) {
		this.inicialConstrutivo = inicialConstrutivo;
	}
	
	public Boolean isInicialConstrutivo() {
		return inicialConstrutivo;
	}
	
	public void setMetodosPerturbacaoSimultaneos(
			Boolean metodosPerturbacaoSimultaneos) {
		this.metodosPerturbacaoSimultaneos = metodosPerturbacaoSimultaneos;
	}
	
	public Boolean getMetodosPerturbacaoSimultaneos() {
		return metodosPerturbacaoSimultaneos;
	}

	public Double getPercentualSwaps() {
		return percentualSwaps;
	}

	public void setPercentualSwaps(Double percentualSwaps) {
		this.percentualSwaps = percentualSwaps;
	}

	public Integer getMaxIteracoesSemMelhoria() {
		return maxIteracoesSemMelhoria;
	}

	public void setMaxIteracoesSemMelhoria(Integer maxIteracoesSemMelhoria) {
		this.maxIteracoesSemMelhoria = maxIteracoesSemMelhoria;
	}
	
	public void setComSemente(Boolean comSemente) {
		this.comSemente = comSemente;
	}
	
	public void setProbabilidadeCrossover(Double probabilidadeCrossover) {
		this.probabilidadeCrossover = probabilidadeCrossover;
	}
	
	public void setProbabilidadeMutacao(Double probabilidadeMutacao) {
		this.probabilidadeMutacao = probabilidadeMutacao;
	}
	
	public void setPercentualDescarte(Double percentualDescarte) {
		this.percentualDescarte = percentualDescarte;
	}
	
	public void setPercentualElite(Double percentualElite) {
		this.valorElite = percentualElite;
	}
	
	public void setPercentualGrupoA(Double percentualGrupoA) {
		this.percentualGrupoA = percentualGrupoA;
	}
	
	public void setPercentualGrupoB(Double percentualGrupoB) {
		this.percentualGrupoB = percentualGrupoB;
	}
	
	public void setTamanhoPopulacao(Integer tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
	}
	
	public void setTipoSelecao(TipoSelection tipoSelecao) {
		this.tipoSelecao = tipoSelecao;
	}
	
	public void setTipoMutacao(TipoMutacao tipoMutacao) {
		this.tipoMutacao = tipoMutacao;
	}
	
	public void setTipoCrossover(TipoCrossover tipoCrossover) {
		this.tipoCrossover = tipoCrossover;
	}

	public Boolean getComSemente() {
		return comSemente;
	}
	
	public Double getPercentualDescarte() {
		return percentualDescarte;
	}
	
	public Double getValorElite() {
		return valorElite;
	}
	
	public Double getPercentualGrupoA() {
		return percentualGrupoA;
	}
	
	public Double getPercentualGrupoB() {
		return percentualGrupoB;
	}
	
	public Double getProbabilidadeCrossover() {
		return probabilidadeCrossover;
	}
	
	public Double getProbabilidadeMutacao() {
		return probabilidadeMutacao;
	}
	
	public Integer getTamanhoPopulacao() {
		return tamanhoPopulacao;
	}
	
	public TipoSelection getTipoSelecao() {
		return tipoSelecao;
	}

	public TipoMutacao getTipoMutacao() {
		return tipoMutacao;
	}
	
	public TipoCrossover getTipoCrossover() {
		return tipoCrossover;
	}

	public Double getPercentualSolucoesIguaisMaxima() {
		return percentualSolucoesIguaisMaxima;
	}
	
	public void setPercentualSolucoesIguaisMaxima(Double percentualSolucoesIguaisMaxima) {
		this.percentualSolucoesIguaisMaxima = percentualSolucoesIguaisMaxima;
	}
	
	public Double getPercentualMovimentacoesMutacao() {
		return percentualMovimentacoesMutacao;
	}
	
	public void setPercentualMovimentacoesMutacao(
			Double percentualMovimentacoesMutacao) {
		this.percentualMovimentacoesMutacao = percentualMovimentacoesMutacao;
	}
	
	public void setTipoElite(TipoElite tipoElite) {
		this.tipoElite = tipoElite;
	}
	
	public TipoElite getTipoElite() {
		return tipoElite;
	}
	
	public void setTipoDescarte(TipoDescarte tipoDescarte) {
		this.tipoDescarte = tipoDescarte;
	}
	
	public TipoDescarte getTipoDescarte() {
		return tipoDescarte;
	}
	
	public void setPercentualDescarteMovimentacoes(
			Double percentualDescarteMovimentacoes) {
		this.percentualDescarteMovimentacoes = percentualDescarteMovimentacoes;
	}
	
	public Double getPercentualDescarteMovimentacoes() {
		return percentualDescarteMovimentacoes;
	}
	
	public void setPercentualDescarteSelecao(Double percentualDescarteSelecao) {
		this.percentualDescarteSelecao = percentualDescarteSelecao;
	}
	
	public Double getPercentualDescarteSelecao() {
		return percentualDescarteSelecao;
	}
	
	public String getSep() {
		return sep;
	}

	public String getInfoParametros() {
		if (this.tipoAlgoritmo==null)
			return "ERRO";
		if (this.tipoAlgoritmo.isHillClimbing()) {
			return 
				this.tipoAlgoritmo
				+ sep + " ciclos=" + this.nCiclos
				+ sep + " restart=" + this.restartMultiple
				+ sep + " multiplicadorEval= " + this.multiplicadorEvaluation;
		}
		else if (this.tipoAlgoritmo.isGuloso()) {
			return 
				this.tipoAlgoritmo
				+ sep + " ciclos=" + this.nCiclos
				+ sep + " multiplicadorEval= " + this.multiplicadorEvaluation;
		}
		else if (this.tipoAlgoritmo.isIteratedLocalSearch()) {
			return 
				this.tipoAlgoritmo
				+ sep + " ciclos=" + this.nCiclos
				+ sep + " moves=" + this.percentualMoves
				+ sep + " swaps=" + this.percentualSwaps
				+ sep + " splits=" + this.percentualSplits
				+ sep + " joins=" + this.percentualJoins
				+ sep + " explosion=" + this.percentualExplosion
				+ sep + " iteracoesSemMelhoria=" + this.maxIteracoesSemMelhoria
				+ sep + " inicialConstrutivo=" + this.inicialConstrutivo
				+ sep + " metodosSimultaneos=" + this.metodosPerturbacaoSimultaneos
				+ sep + " multiplicadorEval= " + this.multiplicadorEvaluation;
		}
		else if (this.tipoAlgoritmo.isGenetico()) {
			return 
				this.tipoAlgoritmo
				+ sep + " ciclos=" + this.nCiclos
				+ sep + " iteracoesSemMelhoria=" + this.maxIteracoesSemMelhoria
				+ sep + " tipoElite=" + this.tipoElite
				+ sep + " elite=" + this.valorElite
				+ sep + " selecao=" + this.tipoSelecao.name()
				+ sep + " grupoA=" + this.percentualGrupoA
				+ sep + " grupoB=" + this.percentualGrupoB
				+ sep + " descarte=" + this.percentualDescarte
				+ sep + " descarte.sel=" + this.percentualDescarteSelecao
				+ sep + " descarte.mov=" + this.percentualDescarteMovimentacoes
				+ sep + " mutacao=" + this.tipoMutacao.name()
				+ sep + " crossover=" + this.tipoCrossover.name()
				+ sep + " proporcaoGrupos=" + this.maxProporcaoGrupos
				+ sep + " percGruposPorCrossover=" + this.percentualGruposPorCrossover
				+ sep + " iguaisMax=" + this.percentualSolucoesIguaisMaxima
				+ sep + " movimentacoesMutacao=" + this.percentualMovimentacoesMutacao
				+ sep + " multiplicadorEval= " + this.multiplicadorEvaluation;
		}
		return "ERRO";
	}
	
	public void setCiclosEspecificos(HashMap<String,Integer> ciclosEspecificos) {
		this.ciclosEspecificos = ciclosEspecificos;
	}
	
	public boolean temCiclosEspecificos() {
		return this.ciclosEspecificos != null;
	}
	
	public int getCicloDaInstancia(String nomeInstancia) {
		return this.ciclosEspecificos.get(nomeInstancia);
	}
	
	public void setSeparador(String sep) {
		this.sep = sep;
	}
	
	public void clone(Parametro param){
		
		this.sep = param.sep;
		this.nCiclos = param.nCiclos;
		this.comSemente = param.comSemente;
		this.tipoAlgoritmo = param.tipoAlgoritmo;
		this.evaluationMax = param.evaluationMax;
		this.maxIteracoesSemMelhoria = param.maxIteracoesSemMelhoria;
		this.restartMultiple = param.restartMultiple;
		this.percentualMoves = param.percentualMoves;
		this.percentualSwaps = param.percentualSwaps;
		this.percentualSplits = param.percentualSplits;
		this.percentualJoins = param.percentualJoins;
		this.percentualExplosion = param.percentualExplosion;
		this.inicialConstrutivo = param.inicialConstrutivo;
		this.metodosPerturbacaoSimultaneos = param.metodosPerturbacaoSimultaneos;
		this.probabilidadeMutacao = param.probabilidadeMutacao;     
		this.probabilidadeCrossover = param.probabilidadeCrossover; 
		this.tamanhoPopulacao = param.tamanhoPopulacao;
		this.tipoElite = param.getTipoElite();
		this.valorElite = param.valorElite;
		this.tipoDescarte = param.tipoDescarte;
		this.percentualDescarte = param.percentualDescarte; 
		this.percentualDescarteSelecao = param.percentualDescarteSelecao;
		this.percentualDescarteMovimentacoes = param.percentualDescarteMovimentacoes;
		this.tipoSelecao = param.tipoSelecao;            
		this.tipoMutacao = param.tipoMutacao;            
		this.tipoCrossover = param.tipoCrossover;          
		this.percentualGrupoA = param.percentualGrupoA;       
		this.percentualGrupoB = param.percentualGrupoB;       
		this.maxProporcaoGrupos = param.maxProporcaoGrupos;      
		this.percentualSolucoesIguaisMaxima = param.percentualSolucoesIguaisMaxima;
		this.percentualMovimentacoesMutacao = param.percentualMovimentacoesMutacao;
		this.percentualGruposPorCrossover = param.percentualGruposPorCrossover;
		this.multiplicadorEvaluation = param.multiplicadorEvaluation;
		this.ciclosEspecificos = param.ciclosEspecificos;
	}

}

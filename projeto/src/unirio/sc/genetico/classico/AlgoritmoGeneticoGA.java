package unirio.sc.genetico.classico;

import unirio.sc.core.Exibicao;
import unirio.sc.core.Parametro;
import unirio.sc.core.Problema;
import unirio.sc.core.SolucaoAbstract;
import unirio.sc.genetico.AlgoritmoGeneticoAbstract;
import unirio.sc.genetico.GeradorSolucaoGenetico;
import unirio.sc.genetico.Populacao;


public class AlgoritmoGeneticoGA extends AlgoritmoGeneticoAbstract {
	
	public AlgoritmoGeneticoGA(
		Problema problema, 
		Exibicao exibicao, 
		Parametro param) 
	throws Exception
	{
		super(problema, exibicao, param);
	};

	/**
	 * Inicializa a população randomicamente
	 */
	@Override
	public void iniciaPopulacao(Populacao populacao, int tamPopulacao) 
	{
		for (int i = 0; i < tamPopulacao; i++) {
			SolucaoAbstract novaSolucao = GeradorSolucaoGenetico.getSolucaoGeneticoGARandom();
			double fitness = this.calculador.evaluate(novaSolucao);
			novaSolucao.setLocation(this.evaluation);
			novaSolucao.setFitness(fitness);
			this.evaluation++;
			
			/*
			try {
				Verificador.validaSolucao(this, novaSolucao);
			}catch (Exception e) {
				System.out.println("ERRO na geração da população.");
			}
			*/
			
			
			populacao.add(novaSolucao);
		}
	}

	/**
	 * Seleciona os elementos da população para reprodução
	 */
	@Override
	protected void selecaoElite(Populacao populacao, Populacao populacaoFilha) 
	{
		populacaoFilha.addMelhores(populacao, this.tamanhoElite);
	}
	
	/**
	 * Substitui os piores elementos da população
	 */
	protected void substituiPioresAleatorio(Populacao populacao, Populacao populacaoFilha) 
	{	
		for (int i = 0; i < this.tamanhoDescarte; i++) {
			SolucaoAbstract novaSolucao = GeradorSolucaoGenetico.getSolucaoGeneticoGARandom();
			double fitness = this.calculador.evaluate(novaSolucao);
			novaSolucao.setLocation(this.evaluation);
			novaSolucao.setFitness(fitness);
			this.evaluation++;
			populacaoFilha.add(novaSolucao);
		}
	}
	
	/**
	 * Substitui os piores elementos da população
	 */
	@Override
	protected void substituiPioresMutacao(Populacao populacao, Populacao populacaoFilha) 
	{
		int contador = 0;
		while (contador < this.tamanhoDescarte) {
			int posicao = GeradorSolucaoGenetico.randInt(0, this.tamanhoDescarteSelecao);
			SolucaoGeneticoGA solucao = (SolucaoGeneticoGA)populacao.get(posicao);
			this.operadorMutacao.descartePorMovimentacoes(solucao);
			contador++;
		}
		for (int i = 0; i < this.tamanhoDescarte; i++) {
			SolucaoAbstract novaSolucao = GeradorSolucaoGenetico.getSolucaoGeneticoGARandom();
			double fitness = this.calculador.evaluate(novaSolucao);
			novaSolucao.setLocation(this.evaluation);
			novaSolucao.setFitness(fitness);
			this.evaluation++;
			populacaoFilha.add(novaSolucao);
		}
	}
	
	@Override
	protected void otimizacao(SolucaoAbstract[] filhos) 
	{
		// Atualizacao do fitness
		double fitness0 = this.calculador.evaluate(filhos[0]);
		double fitness1 = this.calculador.evaluate(filhos[1]);
		filhos[0].setFitness(fitness0);
		filhos[0].setLocation(++this.evaluation);
		filhos[1].setFitness(fitness1);
		filhos[1].setLocation(++this.evaluation);
	}
	

	@Override
	public String getNomeAlgoritmo() 
	{
		return "Algoritmo Genético GA";
	}
	
	/**
	 * Executa o cálculo de fitness
	 */
	protected void novaGeracao(Populacao populacaoFilha, SolucaoAbstract[] filhos) 
	{
		int qtdIguaisA = populacaoFilha.addRetornandoFitnessIguais(filhos[0]);
		
		// se existirem uma quantidade excessiva do mesmo fitness introduz diversidade por mutacao
		if (this.tamanhoSolucoesIguaisMaxima>0 && qtdIguaisA>this.tamanhoSolucoesIguaisMaxima) {
			this.operadorMutacao.diversificaPorMovimentacoes((SolucaoGeneticoGA)filhos[0]);
			double fitness0 = this.calculador.evaluate(filhos[0]);
			filhos[0].setFitness(fitness0);
			filhos[0].setLocation(++this.evaluation);
		}
		
		// se existirem uma quantidade excessiva do mesmo fitness introduz diversidade por mutacao
		int qtdIguaisB = populacaoFilha.addRetornandoFitnessIguais(filhos[1]);
		if (this.tamanhoSolucoesIguaisMaxima>0 && qtdIguaisB>this.tamanhoSolucoesIguaisMaxima) {
			this.operadorMutacao.diversificaPorMovimentacoes((SolucaoGeneticoGA)filhos[1]);
			double fitness1 = this.calculador.evaluate(filhos[1]);
			filhos[1].setFitness(fitness1);
			filhos[1].setLocation(++this.evaluation);
		}
	}	
		
}

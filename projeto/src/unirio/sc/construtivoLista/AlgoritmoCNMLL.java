package unirio.sc.construtivoLista;

import java.util.ArrayList;

import unirio.sc.core.AlgoritmoAbstract;
import unirio.sc.core.Exibicao;
import unirio.sc.core.Parametro;
import unirio.sc.core.Problema;

/**
 * Algoritmo guloso para o problema Software Module Clustering
 */
public class AlgoritmoCNMLL extends AlgoritmoAbstract {
	
	public AlgoritmoCNMLL(
		Problema problema, 
		Exibicao exibicao,
		Parametro param) 
	{
		this.problema = problema;
		this.exibicao = exibicao;
		this.calculador = new CalculadorCNM(problema);
		setConfiguracoes(param);
	}
	
	@Override
	public String getNomeAlgoritmo() {
		return "Algoritmo Guloso - HashMap com LinkedList";
	}
	
	public void iniciaSolucao(ArrayList<Integer> clusters, SolucaoCNMLL solucao, int totalItens) {
		for (int i = 0; i < totalItens; i++) {
			solucao.addItem(i, i);
			clusters.add(i);
		}
		double fitness = this.calculador.evaluate(solucao);
		solucao.setFitness(fitness);
	}
	
	@Override
	public void executa(String... args) {
		long tempoInicial = System.currentTimeMillis();
		
		this.solucao = null;
		
		// solução corrente
		SolucaoCNMLL solucaoAtual = new SolucaoCNMLL(this.problema.getTamanho());
		// guarda os clusters ativos
		ArrayList<Integer> clustersAtivos = new ArrayList<Integer>();
		// inicializa a solução com um módulo por cluster
		iniciaSolucao(clustersAtivos, solucaoAtual, this.problema.getTamanho());

		// Debug
		// se não houver debug, pode comentar este bloco para otimização
		boolean imprimeCabecalhoDebug = true;
		if (args.length > 0) {
			if (args[0].indexOf("imprimeCabecalhoDebug=false")>=0) {
				imprimeCabecalhoDebug = false;
			}
		}
		if (imprimeCabecalhoDebug) {
			this.exibicao.printDebugGeracaoCabecalho(this,0.00);
		}
		// fim Debug

		while (this.evaluation < this.evaluationMax) {
			SolucaoCNMLL solucaoJuncao = buscaJuncaoGrupos(clustersAtivos, solucaoAtual);
			if (solucaoJuncao==null)
				break;
			solucaoAtual = new SolucaoCNMLL(solucaoJuncao);
			this.evaluation++;
			this.iteracao++;

			// Debug
			// se não houver debug, comentar a linha para otimização
			this.exibicao.printDebugGeracao(solucaoAtual, this.iteracao, this.evaluation);
			// fim Debug
		};
		this.solucao = solucaoAtual;
		this.tempoExecucao = System.currentTimeMillis() - tempoInicial;
	}
	
	public SolucaoCNMLL buscaJuncaoGrupos(ArrayList<Integer> clustersAtivos, SolucaoCNMLL solucao) {

		boolean encontrouMelhor = false;
		
		int clusterOrigem = -1;
		int clusterDestino = -1;
		int location = -1;

		// salva o status do cálculo para otimizar o cálculo dos diversos joins.
		// double fitnessMelhor = solucao.getFitness();
		double fitnessMelhor = ((CalculadorCNM)this.calculador).calculateMQEgravaEstado(solucao.getValores());

		for (int i = 0; i < clustersAtivos.size()-1; i++) {
			for (int j = i+1; j < clustersAtivos.size(); j++) {
					
				int cluster1 = clustersAtivos.get(i);
				int cluster2 = clustersAtivos.get(j);
				
				this.evaluation++;
				//double fitnessJuncao = ((CalculadorCNM)this.calculador).evaluateJoin(solucao, cluster1, cluster2);
				double fitnessJuncao = ((CalculadorCNM)this.calculador).evaluateJoinOtimizado(solucao, cluster1, cluster2);
				
				if (fitnessJuncao < fitnessMelhor) {
					encontrouMelhor = true;
					fitnessMelhor = fitnessJuncao;
					clusterOrigem = cluster1;
					clusterDestino = cluster2;
					location = this.evaluation;
				}
			}
		}
		
		if (!encontrouMelhor) return null;
		
		SolucaoCNMLL solucaoMelhor = new SolucaoCNMLL(solucao);
		solucaoMelhor.join(clusterOrigem, clusterDestino);
		solucaoMelhor.setFitness(fitnessMelhor);
		solucaoMelhor.setLocation(location);
		
		clustersAtivos.remove((Integer)clusterDestino);
		
		return solucaoMelhor;
	}
	
	@Override
	public String getInfoParametros() {
		return
			"algoritmo=" + this.getNomeAlgoritmo()
			+ ",problema=" + this.problema.getName()
			+ ",tamanho=" + this.problema.getTamanho()
		;
	}
	
	@Override
	public void setConfiguracoes(Parametro param) {
		this.evaluationMax = param.getEvaluationMax();
		return;
	}
}

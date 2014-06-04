package unirio.sc.construtivo;

import java.util.Arrays;

import unirio.sc.construtivoLista.CalculadorCNM;
import unirio.sc.core.AlgoritmoAbstract;
import unirio.sc.core.Exibicao;
import unirio.sc.core.Parametro;
import unirio.sc.core.Problema;


public class AlgoritmoCNM extends AlgoritmoAbstract {
	
	public AlgoritmoCNM(
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
		return "Algoritmo Guloso - Vetor";
	}
	
	@Override
	public void executa(String... args) {
		long tempoInicial = System.currentTimeMillis();
		
		this.solucao = null;
		
		SolucaoCNM solucaoAtual = new SolucaoCNM(this.problema.getTamanho());
		iniciaSolucao(solucaoAtual);
		
		while (this.evaluation < this.evaluationMax) {
			SolucaoCNM solucaoJuncao = buscaJuncaoGrupos(solucaoAtual);
			if (solucaoJuncao==null)
				break;
			solucaoAtual = new SolucaoCNM(solucaoJuncao);
			this.evaluation++;
		};
		this.solucao = solucaoAtual;
		this.tempoExecucao = System.currentTimeMillis() - tempoInicial;
	}
	
	// cada módulo pertencerá a um único cluster
	protected void iniciaSolucao(SolucaoCNM solucao) 
	{
		int totalItens = this.problema.getClassCount();
		int totalGrupos  = totalItens;
		int[] valores = new int[totalItens];
		int[] qtdItens = new int[totalItens];
		
		for (int i = 0; i < totalItens; i++) {
			valores[i] = i;
			qtdItens[i] = 1;
		}
		
		double fitness = this.calculador.evaluate(valores);
		solucao.setSolucao(valores, qtdItens, totalGrupos, fitness, 0);
	}
	
	public SolucaoCNM buscaJuncaoGrupos(SolucaoCNM solucao) {

		int totalGrupos = solucao.getTotalGrupos();
		boolean encontrouMelhor = false;
		
		int clusterOrigem = -1;
		int clusterDestino = -1;
		int location = -1;
		double fitnessMelhor = solucao.getFitness();
		
		for (int i = 0; i < totalGrupos-1; i++) {
			for (int j = i+1; j < totalGrupos; j++) {
				
				int[] valores = Arrays.copyOf(solucao.getValores(), solucao.getValores().length);
				
				// junta os dois clusters e faz o recálculo
				for (int k = 0; k < valores.length; k++) {
					if (valores[k] == j)
						valores[k] = i;
				}
				
				this.evaluation++;
				double fitnessJuncao = this.calculador.evaluate(valores);
				
				if (fitnessJuncao < fitnessMelhor) {
					encontrouMelhor = true;
					fitnessMelhor = fitnessJuncao;
					clusterOrigem = i;
					clusterDestino = j;
					location = this.evaluation;
				}
			}
		}
		if (!encontrouMelhor) return null;
		
		int[] valores = Arrays.copyOf(solucao.getValores(), solucao.getValores().length);
		int qtdMesclados = 0;
		for (int k = 0; k < valores.length; k++) {
			if (valores[k] == clusterDestino) {
				valores[k] = clusterOrigem;
				qtdMesclados++;
			}
		}
			
		int[] qtdItens = Arrays.copyOf(solucao.getQtdItens(), solucao.getQtdItens().length);
		qtdItens[clusterOrigem] += qtdMesclados;
		qtdItens[clusterDestino] = 0;
		
		SolucaoCNM solucaoMelhor = new SolucaoCNM(valores, qtdItens, totalGrupos-1, fitnessMelhor, location);
		GeradorSolucao.normalizar(solucaoMelhor);
		
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

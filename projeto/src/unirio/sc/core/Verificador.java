package unirio.sc.core;

import java.util.TreeMap;
import java.util.TreeSet;

import unirio.sc.busca.SolucaoHC;
import unirio.sc.construtivo.SolucaoCNM;
import unirio.sc.construtivoLista.SolucaoCNMLL;
import unirio.sc.genetico.classico.AlgoritmoGeneticoGA;
import unirio.sc.genetico.classico.SolucaoGeneticoGA;
import unirio.sc.genetico.falkenauer.SolucaoGeneticoGF;
import unirio.sc.metaheuristica.SolucaoILS;

public class Verificador {

	public static boolean validaSolucao(AlgoritmoAbstract algoritmo, SolucaoAbstract solucao, String opcoes)
	throws Exception
	{
		String nomeAlgoritmo = "";
		if (algoritmo != null) {
			nomeAlgoritmo = algoritmo.getNomeAlgoritmo();
			CalculadorAbstract calculador = algoritmo.getCalculador();
			Problema problema = calculador.getProblema();
			
			if (opcoes.indexOf("sem_fitness")<0) {		
				double fitnessOk = calculador.evaluate(solucao.getValores());
				if (Math.abs(solucao.getFitness() - fitnessOk) > 0.0000000001) {
					throw new Exception("ERRO: Fitness da solução está incorreto. Obtido=" + solucao.getFitness() + ". Esperado=" + fitnessOk);
				}
			}
			int totalItensOk = problema.getTamanho();
			if (solucao.getTotalItens() != totalItensOk) {
				throw new Exception("ERRO: Quantidade de módulos da solução está incorreto.");
			}
			if (solucao instanceof SolucaoGeneticoGF) {
				if (((SolucaoGeneticoGF)solucao).getTotalGrupos() > totalItensOk || ((SolucaoGeneticoGF)solucao).getTotalGrupos() < 1) {
					throw new Exception("ERRO: Quantidade de clusters da solução está incorreto.");
				}
			}
			if (solucao instanceof SolucaoHC) {
				if (((SolucaoHC)solucao).getTotalGrupos() > totalItensOk || ((SolucaoHC)solucao).getTotalGrupos() < 1) {
					throw new Exception("ERRO: Quantidade de clusters da solução está incorreto.");
				}
			}
			if (solucao instanceof SolucaoCNM) {
				if (((SolucaoCNM)solucao).getTotalGrupos() > totalItensOk || ((SolucaoCNM)solucao).getTotalGrupos() < 1) {
					throw new Exception("ERRO: Quantidade de clusters da solução está incorreto.");
				}
			}
			if (solucao instanceof SolucaoCNMLL) {
				if (((SolucaoCNMLL)solucao).getTotalGrupos() > totalItensOk || ((SolucaoCNMLL)solucao).getTotalGrupos() < 1) {
					throw new Exception("ERRO: Quantidade de clusters da solução está incorreto.");
				}
			}
			if (solucao instanceof SolucaoILS) {
				if (((SolucaoILS)solucao).getTotalGrupos() > totalItensOk || ((SolucaoILS)solucao).getTotalGrupos() < 1) {
					throw new Exception("ERRO: Quantidade de clusters da solução está incorreto.");
				}
			}
		}
		
		int[] valores = solucao.getValores();
		
		int totalItens = solucao.getTotalItens();
		TreeSet<Integer> clusters = new TreeSet<Integer>();
		TreeMap<Integer,Integer> qtdModulos = new TreeMap<Integer,Integer>();
		for (int i = 0; i < totalItens; i++) {
			int cluster = valores[i];
			clusters.add(cluster);
			Integer qtd = qtdModulos.get(cluster);
			if (qtd==null) qtd = 0;
			qtdModulos.put(cluster, qtd+1);
			if (valores[i] > valores.length) {
				throw new Exception("ERRO: Solução contém cluster maior do que " + valores.length);
			}
		}
		
		if (algoritmo!=null && algoritmo instanceof AlgoritmoGeneticoGA) {
			return true;
		}
		
		int[] qtdItens;
		int totalGrupos;
		if (solucao instanceof SolucaoGeneticoGF) {
			qtdItens = ((SolucaoGeneticoGF)solucao).getQtdItens();
			totalGrupos = ((SolucaoGeneticoGF)solucao).getTotalGrupos();
		}
		else if (solucao instanceof SolucaoHC) {
			qtdItens = ((SolucaoHC)solucao).getQtdItens();
			totalGrupos = ((SolucaoHC)solucao).getTotalGrupos();
		}
		else if (solucao instanceof SolucaoCNM) {
			qtdItens = ((SolucaoCNM)solucao).getQtdItens();
			totalGrupos = ((SolucaoCNM)solucao).getTotalGrupos();
		}
		else if (solucao instanceof SolucaoCNMLL) {
			qtdItens = ((SolucaoCNMLL)solucao).getQtdItens();
			totalGrupos = ((SolucaoCNMLL)solucao).getTotalGrupos();
		}
		else if (solucao instanceof SolucaoILS) {
			qtdItens = ((SolucaoILS)solucao).getQtdItens();
			totalGrupos = ((SolucaoILS)solucao).getTotalGrupos();
		}
		else if (solucao instanceof SolucaoGeneticoGA) {
			qtdItens = null;
			totalGrupos = 0;
		}
		else {
			qtdItens = null;
			totalGrupos = 0;
		}
		if (qtdItens != null) {
			for (Integer cluster : clusters) {
				int qtd = qtdModulos.get(cluster);
				if (qtd != qtdItens[cluster]) {
					throw new Exception("ERRO: Solução contém número de módulos diferente da variável qtdItens[" + cluster + "]=" + qtdItens[cluster] + " <> " + qtd);
				}
			}
		
			if (clusters.size() != totalGrupos) {
				throw new Exception("ERRO: " + nomeAlgoritmo + " - Solução contém número de clusters diferente da variável totalGrupos");
			}
		}
		
		return true;
	}

	public static boolean validaSolucao(AlgoritmoAbstract algoritmo, SolucaoAbstract solucao)
	throws Exception
	{
		return validaSolucao(algoritmo, solucao, "");
	}

}

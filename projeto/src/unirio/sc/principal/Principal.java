package unirio.sc.principal;

import java.util.List;

import unirio.sc.core.Exibicao;
import unirio.sc.core.ExperimentoFactory;
import unirio.sc.core.ExperimentoModel;
import unirio.sc.core.Parametro;
import unirio.sc.core.Problema;
import unirio.sc.core.TipoExibicao;
import unirio.sc.statistics.GeradorCSV;

public class Principal {
	
	public static final void executa(TipoExibicao tipoExibicao, ExperimentoModel experimento) throws Exception 
	{		
		List<Problema> problemas = experimento.getProblemas();
		List<Parametro> params = experimento.getParametros();
		Exibicao[] exibicoes = experimento.iniciaExibicoes();
		
		// Executa o problema para todas as inst�ncias
		for (Problema problema : problemas) {
			int indice = 0;
			for (Parametro param : params) {
				int tamanho = problema.getTamanho();
				
				// Software Module Clustering as MultiObjective Search Problem - Praditwong Harman Yao
				// N�mero de m�dulos = N
				// popula��o = 10*N
				// 0.8 para popula��o menor que 100 e 1.0 para popula��o maior que 100
				// muta��o = 0.004 * log2(n)
				// m�ximo n�mero de gera��es = 200 * N
				// tamanho total dos arquivos igual ao tamanho da popula��o 
				
				// An Analysis of the Effects of Composite Objectives in MultiObjective Software Module Clustering - Marcio Barros
				// crossover = single point 80% menor que 100 m�dulos e 100% maior que 100 m�dulos
				// popula��o = 10*N
				// mutation = 0.04 * log2(n)
				// selecton = binary tournament
				// jmetal - nsgaII
				// evaluations = 200 * N * N
				
				param.setProbabilidadeMutacao( 0.004 * Math.log(tamanho) / Math.log(2) );
				param.setTamanhoPopulacao( tamanho * 10 );

				// param.setProbabilidadeCrossover( (tamanho < 100) ? 0.8 : 1.0 );
				if (param.getTamanhoPopulacao() < 100)
					param.setProbabilidadeCrossover( 0.8 );
				else
					param.setProbabilidadeCrossover( 1.0 );
				
				param.setEvaluationMax( param.getMultiplicadorEvaluation() * tamanho * tamanho );
				// Barros, 2012
				// param.setEvaluationMax( 200 * tamanho * tamanho );
				// Praditwong, 2011
				// param.setEvaluationMax( 2000 * tamanho * tamanho );

				exibicoes[indice].setTipoExibicaoCiclo(tipoExibicao);
				exibicoes[indice].configuraDebug(indice, problema, param);
				
				ExperimentoFactory.executa(problema, exibicoes[indice], param);
				indice++;
			}
		}
		
		// Gera os resultados para uma planilha em formato csv
		GeradorCSV.executa(tipoExibicao, experimento);
		GeradorCSV.executaDebug(tipoExibicao, experimento);
	}

	public static final void main(String[] args) throws Exception 
	{
		//Configura a exibi��o conforme algoritmo e problema
		TipoExibicao tipoExibicao = TipoExibicao.DEFAULT;
		tipoExibicao.setDebug(true);
		
		ExperimentoModel experimento = new ExperimentoFinalParte1();
		executa(tipoExibicao, experimento);
	}
	
}
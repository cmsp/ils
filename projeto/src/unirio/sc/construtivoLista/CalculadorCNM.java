package unirio.sc.construtivoLista;

import java.util.LinkedList;

import unirio.sc.core.Calculador;
import unirio.sc.core.Problema;


/**
 * DEFINICÕES:
 * 
 * - acoplamento = número de dependências que as classes de um pacote possuem
 * com classes de fora do pacote. Deve ser minimizado.
 * 
 * - coesão = número de dependências que as classes de um pacote possuem com
 * outras classes do mesmo pacote. Deve ser maximizado (ou seja, minimizamos seu
 * valor com sinal invertido)
 * 
 * - spread = partindo de zero e percorrendo cada pacote, acumula o quadrado da
 * diferença entre o número de classes do pacote e o número de classes do menor
 * pacote
 * 
 * - diferenca = diferença entre o número máximo de classes em um pacote e o
 * número mínimo de classes em um pacote
 * 
 * @author Marcio Barros
 */
public class CalculadorCNM extends Calculador {

	/**
	 * Inicializa o calculator com os dados do problema
	 */
	public CalculadorCNM(Problema problema) {
		super(problema);
	}	

	/**
	 * Calcula o fitness que resultado do join de dois clusters, sem modificar a solução
	 */
	public double evaluateJoin(SolucaoCNMLL s, int cluster1, int cluster2) {
		LinkedList<Integer> itens2 = s.getMapa().get(cluster2);
		int[] valores = s.getValores();
		for (Integer item : itens2) {
			valores[item] = cluster1;
		}
		double fitness = calculateMQ(valores);
		// desfaz a modificação
		for (Integer item : itens2) {
			valores[item] = cluster2;
		}
		return -fitness;
	}
	
	/**
	 * Calcula o coeficiente de modularidade do projeto
	 */
	public double evaluateJoinOtimizado(SolucaoCNMLL s, int cluster1, int cluster2) {
		
		int[][]listaDependenciasPara = this.problema.getListaDependenciasPara();
		int[] qtdDependenciasPara = this.problema.getQtdDependenciasPara();
		int[][]listaDependenciasDe = this.problema.getListaDependenciasDe();
		int[] qtdDependenciasDe = this.problema.getQtdDependenciasDe();
		
		LinkedList<Integer> itens1 = s.getMapa().get(cluster1);
		LinkedList<Integer> itens2 = s.getMapa().get(cluster2);
		int[] valores = s.getValores();
		
		for (Integer item : itens2) {
			valores[item] = cluster1;
		}
		
		int inboundEdgesJoin = 0;
		int outboundEdgesJoin = 0;
		int intraEdgesJoin = 0;
		
		for (Integer item : itens1) {
			for (int j=0; j<qtdDependenciasPara[item]; j++) {
				int targetPackage = valores[listaDependenciasPara[item][j]];
				if (targetPackage != cluster1) {
					outboundEdgesJoin++;
				} else
					intraEdgesJoin++;
			}
			for (int j=0; j<qtdDependenciasDe[item]; j++) {
				int targetPackage = valores[listaDependenciasDe[item][j]];
				if (targetPackage != cluster1) {
					inboundEdgesJoin++;
				}
			}
		}

		for (Integer item : itens2) {
			for (int j=0; j<qtdDependenciasPara[item]; j++) {
				int targetPackage = valores[listaDependenciasPara[item][j]];
				if (targetPackage != cluster1) {
					outboundEdgesJoin++;
				} else
					intraEdgesJoin++;
			}
			for (int j=0; j<qtdDependenciasDe[item]; j++) {
				int targetPackage = valores[listaDependenciasDe[item][j]];
				if (targetPackage != cluster1) {
					inboundEdgesJoin++;
				}
			}
		}

		// recalculando o MF do join do grupo 1 e grupo 2
		double mq = this.mq;
		mq -= this.mf[cluster1];
		mq -= this.mf[cluster2];
		int interJoin = inboundEdgesJoin + outboundEdgesJoin;
		int intraJoin = intraEdgesJoin;
		double mfJoin = 0.00;
		//if (intraJoin != 0 && interJoin != 0) {
		if (intraJoin != 0) {
			mfJoin = intraJoin / (intraJoin + 0.5 * interJoin);
		}
		mq += mfJoin;

		// desfaz a modificação do join dos clusters
		for (Integer item : itens2) {
			valores[item] = cluster2;
		}
		
		return -mq;
	}

}
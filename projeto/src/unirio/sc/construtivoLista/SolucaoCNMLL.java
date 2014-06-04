package unirio.sc.construtivoLista;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import unirio.sc.core.SolucaoAbstract;

/**
 * Representação de uma solução com inteiros
 */
public class SolucaoCNMLL implements SolucaoAbstract {

	// representa o número da geração em que a solução foi encontrada
	private int location;

	// valor de fitness da solução
	private double fitness = 0.00;

	// parte básica da representação da solução
	private HashMap<Integer,LinkedList<Integer>> mapa;
	
	private int[] valores;
	
	public SolucaoCNMLL(SolucaoCNMLL s) {
		this.mapa = cloneMapa(s.getMapa());
		this.fitness = s.getFitness();
		this.location = s.getLocation();
		this.valores = Arrays.copyOf(s.getValores(), s.getValores().length);
	}
	
	public SolucaoCNMLL(int tamanho) {
		this.mapa = new HashMap<Integer, LinkedList<Integer>>();
		this.fitness = 0.00;
		this.location = 0;
		this.valores = new int[tamanho];
	}
	
	@Override
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	@Override
	public double getFitness() {
		return fitness;
	}

	@Override
	public void setLocation(int location) {
		this.location = location;
	}

	@Override
	public int getLocation() {
		return location;
	}

	@Override
	public int[] getValores() {
		return valores;
	}

	@Override
	public int getTotalItens() {
		return this.valores.length;
	}
	
	public int getTotalGrupos() {
		return this.mapa.size();
	}

	public HashMap<Integer,LinkedList<Integer>> getMapa() {
		return this.mapa;
	}
	
	public void addItem(int cluster, int item) {
		LinkedList<Integer> itens = this.mapa.get(cluster);
		if (itens != null) {
			itens.addLast(item);
		}
		else {
			itens = new LinkedList<Integer>();
			itens.addLast(item);
			this.mapa.put(cluster, itens);
		}
		this.valores[item] = cluster;
	}
	
	public void join(int cluster1, int cluster2) {
		LinkedList<Integer> itens1 = this.mapa.get(cluster1);
		LinkedList<Integer> itens2 = this.mapa.get(cluster2);
		itens1.addAll(itens2);
		this.mapa.remove(cluster2);
		for (Integer item : itens2) {
			valores[item] = cluster1;
		}
	}

	public HashMap<Integer,LinkedList<Integer>> cloneMapa(HashMap<Integer,LinkedList<Integer>> clusters) {
		HashMap<Integer,LinkedList<Integer>> copia = new HashMap<Integer, LinkedList<Integer>>();
		Set<Integer> keys = clusters.keySet();
		for (Integer key :keys) {
			LinkedList<Integer> itens = clusters.get(key);
			copia.put(key, itens);
		}
		return copia;
	}
	
	/**
	 * Utilizado na metahueristica ILS
	 * @return
	 */
	public int[] getQtdItens() {
		int[] qtdItens = new int[this.valores.length];
		for (int i = 0; i < valores.length; i++) {
			qtdItens[valores[i]]++;
		}
		return qtdItens;
	}
	
	/**
	 * Utilizado para exibição dos resultados
	 */
	public String getString() {
		StringBuilder sb = new StringBuilder("");
		if (this.valores.length > 0) {
			sb.append(String.valueOf(this.valores[0]));
		}
		for (int i = 1; i < valores.length; i++) {
			sb.append(",");
			sb.append(String.valueOf(this.valores[i]));
		}
		return sb.toString();
	}
	
	public void setValores(int[] valores, HashMap<Integer, LinkedList<Integer>> mapa) {
		this.valores = valores;
		this.mapa = mapa;
	}

}

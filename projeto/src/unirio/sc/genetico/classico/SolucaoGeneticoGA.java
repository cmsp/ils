package unirio.sc.genetico.classico;

import java.util.Arrays;

import unirio.sc.core.SolucaoAbstract;

/**
 * Representação de uma solução com inteiros
 */
public class SolucaoGeneticoGA implements SolucaoAbstract {

	// representa o número da geração em que a solução foi encontrada
	private int location;

	// valor de fitness da solução
	private double fitness = 0.00;

	// parte básica da representação da solução
	private int[] valores;

	public SolucaoGeneticoGA(SolucaoAbstract s) {
		int[] valores = s.getValores();
		this.valores = Arrays.copyOf(valores, valores.length);
	}

	public SolucaoGeneticoGA(int[] valores) {
		this.valores = valores;
	}

	public SolucaoGeneticoGA(int tamanho) {
		this.valores = new int[tamanho];
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public double getFitness() {
		return fitness;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getLocation() {
		return location;
	}

	public int[] getValores() {
		return this.valores;
	}

	public int getTotalItens() {
		return this.valores.length;
	}

	public void setValores(int[] valores) {
		this.valores = valores;
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
	
	@Override
	public boolean equals(Object obj) {
		return ((SolucaoGeneticoGA)obj).getString().equals(this.getString());
	}
	
}

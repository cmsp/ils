package unirio.sc.genetico.falkenauer;

import java.util.Arrays;

import unirio.sc.core.SolucaoAbstract;

/**
 * Representação de uma solução com inteiros
 */
public class SolucaoGeneticoGF implements SolucaoAbstract {

	// representa o número da geração em que a solução foi encontrada
	private int location;

	// valor de fitness da solução
	private double fitness = 0.00;

	// parte básica da representação da solução
	private int[] valores;

	// quantidade dos grupos
	private int totalGrupos;

	// quantidade de cada item nos grupos
	private int[] qtdItens;
	
	public SolucaoGeneticoGF(SolucaoGeneticoGF s) {
		int[] valores = s.getValores();
		this.valores = Arrays.copyOf(valores, valores.length);

		int[] qtdItens = s.getQtdItens();
		this.qtdItens = Arrays.copyOf(qtdItens, qtdItens.length);
		this.totalGrupos = s.getTotalGrupos();
		this.fitness = s.getFitness();
	}

	public SolucaoGeneticoGF(int[] valores, int[] qtdItens, int totalGrupos ) {
		this.valores = valores;
		this.qtdItens = qtdItens;
		this.totalGrupos = totalGrupos;
	}

	public SolucaoGeneticoGF(int tamanho) {
		this.valores = new int[tamanho];
		this.qtdItens = new int[2 * tamanho];
		this.totalGrupos = 0;
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

	public void setTotalGrupos(int totalGrupos) {
		this.totalGrupos = totalGrupos;
	}

	public int getTotalGrupos() {
		return totalGrupos;
	}

	public void setQtdItens(int[] qtdItens, int totalGrupos) {
		this.totalGrupos = totalGrupos;
		this.qtdItens = qtdItens;
	}

	public int[] getQtdItens() {
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
	
	public int getNumeroDoMaiorGrupo() {
		// pega o número do maior grupo a partir do vetor com os inteiros
		int maiorGrupo = -1;
		for (int i=0; i < this.valores.length; i++) {
			if (valores[i] > maiorGrupo) {
				maiorGrupo = valores[i];
			}
		}
		return maiorGrupo;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((SolucaoGeneticoGF)obj).getString().equals(this.getString());
	}

}

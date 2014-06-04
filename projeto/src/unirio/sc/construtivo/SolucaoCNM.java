package unirio.sc.construtivo;

import java.util.Arrays;

import unirio.sc.core.SolucaoAbstract;

/**
 * Representação de uma solução com inteiros
 */
public class SolucaoCNM implements SolucaoAbstract {

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
	
	public SolucaoCNM(SolucaoCNM s) {
		int[] valores = s.getValores();
		this.valores = Arrays.copyOf(valores, valores.length);

		int[] qtdItens = s.getQtdItens();
		this.qtdItens = Arrays.copyOf(qtdItens, qtdItens.length);
		this.totalGrupos = s.getTotalGrupos();
		this.fitness = s.getFitness();
	}

	public SolucaoCNM(int[] valores, int[] qtdItens, int totalGrupos ) {
		this.valores = valores;
		this.qtdItens = qtdItens;
		this.totalGrupos = totalGrupos;
	}

	public SolucaoCNM(int[] valores, int[] qtdItens, int totalGrupos, double fitness, int location ) {
		this.valores = valores;
		this.qtdItens = qtdItens;
		this.totalGrupos = totalGrupos;
		this.fitness = fitness;
		this.location = location;
	}

	public SolucaoCNM(int tamanho) {
		this.valores = new int[tamanho];
		this.qtdItens = new int[2 * tamanho];
		this.totalGrupos = 0;
	}
	
	public void setSolucao(int[] valores, int[] qtdItens, int totalGrupos, double fitness, int location) {
		this.valores = valores;
		this.qtdItens = qtdItens;
		this.totalGrupos = totalGrupos;
		this.fitness = fitness;
		this.location = location;
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
		StringBuffer sb = new StringBuffer("[");
		sb.append("fitness=[");
		sb.append(this.fitness);
		sb.append("],");
		sb.append("location=[");
		sb.append(this.location);
		sb.append("],");
		sb.append("valores=[");
		if (this.valores.length > 0) {
			sb.append(String.valueOf(this.valores[0]));
		}
		for (int i = 1; i < valores.length; i++) {
			sb.append(",");
			sb.append(String.valueOf(this.valores[i]));
		}
		sb.append("],");
		sb.append("qtdGrupos=[");
		sb.append(this.totalGrupos);
		sb.append("],");
		sb.append("grupos=[");
		if (this.totalGrupos > 0) {
			sb.append(String.valueOf(this.qtdItens[0]));
		}
		for (int i = 1; i < this.qtdItens.length; i++) {
			sb.append(",");
			sb.append(String.valueOf(this.qtdItens[i]));
		}
		sb.append("]]");
		return sb.toString();
	}


}

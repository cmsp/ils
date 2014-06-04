package unirio.sc.genetico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import unirio.sc.core.SolucaoAbstract;

public class Populacao implements Serializable {
	
	private static final long serialVersionUID = -8367740044542461972L;

	protected List<SolucaoAbstract> solucoes;
	
	protected double totalFitness = 0.00;

	public Populacao() {
		solucoes = new ArrayList<SolucaoAbstract>();
	}
	
	public void add(SolucaoAbstract solucao) {
		for (int i = 0; i < solucoes.size(); i++) {
			if (solucao.getFitness()<this.solucoes.get(i).getFitness()) {
				this.solucoes.add(i,solucao);
				this.totalFitness+=solucao.getFitness();
				return;
			}
		}
		solucoes.add(solucao);
		this.totalFitness+=solucao.getFitness();
	}

	public void add(int posicaoInicial, SolucaoAbstract solucao) {
		for (int i = posicaoInicial; i < solucoes.size(); i++) {
			if (solucao.getFitness()<this.solucoes.get(i).getFitness()) {
				this.solucoes.add(i,solucao);
				this.totalFitness+=solucao.getFitness();
				return;
			}
		}
		solucoes.add(solucao);
		this.totalFitness+=solucao.getFitness();
	}

	public int addRetornandoFitnessIguais(SolucaoAbstract solucao) {
		int qtd = 0;
		for (int i = 0; i < solucoes.size(); i++) {
			if (solucao.getFitness()<this.solucoes.get(i).getFitness()) {
				this.solucoes.add(i,solucao);
				this.totalFitness+=solucao.getFitness();
				return qtd;
			}
			else if (solucao.getFitness()==this.solucoes.get(i).getFitness()) {
				qtd++;
			}
		}
		solucoes.add(solucao);
		this.totalFitness+=solucao.getFitness();
		return qtd;
	}

	public int[] addVerificandoFitnessIguais(SolucaoAbstract solucao) {
		int[] qtd = {0, 0};
		for (int i = 0; i < solucoes.size(); i++) {
			if (solucao.getFitness()<this.solucoes.get(i).getFitness()) {
				this.solucoes.add(i,solucao);
				this.totalFitness+=solucao.getFitness();
				qtd[1] = i;
				return qtd;
			}
			else if (solucao.getFitness()==this.solucoes.get(i).getFitness()) {
				qtd[0]++;
				qtd[1] = i;
			}
		}
		solucoes.add(solucao);
		qtd[1] = this.solucoes.size()-1;
		this.totalFitness+=solucao.getFitness();
		return qtd;
	}

	public void reposicionaSolucao(int posicao) {
		SolucaoAbstract solucao = this.solucoes.remove(posicao);
		int posicaoInsercao = 0;
		if (posicao > 0) {
			if (solucao.getFitness()>this.solucoes.get(posicao-1).getFitness()) {
				posicaoInsercao = posicao;
			}
		}
		
		add(posicaoInsercao, solucao);
	}

	public void addUltimaPosicao(SolucaoAbstract solucao) {
		solucoes.add(solucao);
	}

	/*
	public void addUltimaPosicao(SolucaoAbstract[] solucoes) {
		for (int i = 0; i < solucoes.length; i++) {
			this.solucoes.add(solucoes[i]);
		}
	}
	*/

	public SolucaoAbstract get(int i) {
		if (i >= solucoes.size()) {
			throw new IndexOutOfBoundsException("Index out of Bound " + i);
		}
		return solucoes.get(i);
	}

	/*
	public void setDoisMelhores(Solucao[] retorno) {
		if (this.solucoes.size() < 2) {
			retorno[0] = null;
			retorno[1] = null;
			return;
		}

		// Coleta a primeira e segunda solução da população
		Solucao s1 = this.solucoes.get(0);
		Solucao s2 = this.solucoes.get(1);
		if (s2.getFitness() < s1.getFitness()) {
			Solucao aux = s1;
			s1 = s2;
			s2 = aux;
		}

		for (int i = 2; i < solucoes.size(); i++) {
			if (solucoes.get(i).getFitness() < s1.getFitness()) {
				s2 = s1;
				s1 = solucoes.get(i);
			} else if (solucoes.get(i).getFitness() < s2.getFitness()) {
				s2 = solucoes.get(i);
			}
		}

		retorno[0] = s1;
		retorno[1] = s2;
	}
	*/

	public void addMelhores(Populacao populacaoOriginal, int tamanhoElite) {
		for (int i = 0; i < tamanhoElite; i++) {
			this.add(populacaoOriginal.get(i));
		}
	}

	public SolucaoAbstract getMelhor() {
		if (this.solucoes.size() < 1)
			return null;

		// Coleta a primeira e segunda solução da população
		SolucaoAbstract s1 = this.solucoes.get(0);
		for (int i = 1; i < solucoes.size(); i++) {
			if (solucoes.get(i).getFitness() < s1.getFitness()) {
				s1 = solucoes.get(i);
			}
		}

		return s1;
	}

	public int size() {
		return this.solucoes.size();
	}

	public void clear() {
		this.solucoes.clear();
		this.totalFitness = 0.00;
	}

	public void set(Populacao populacao) {
		clear();
		for (int i = 0; i < populacao.getSolucoes().size(); i++) {
			this.solucoes.add(populacao.getSolucoes().get(i));
		}
		this.totalFitness+=populacao.getTotalFitness();
	}

	public List<SolucaoAbstract> getSolucoes() {
		return this.solucoes;
	}

	/*
	@Deprecated
	public void sort(Comparator<SolucaoAbstract> comparador) {
		Collections.sort(this.solucoes, comparador);
	}
	*/

	/**
	 * Coleta as duas primeiras soluções
	 */
	public void setDoisPrimeiros(SolucaoAbstract[] retorno) {
		
		if (this.solucoes.size() < 2) {
			retorno[0] = null;
			retorno[1] = null;
			return;
		}
		retorno[0] = this.solucoes.get(0);
		retorno[1] = this.solucoes.get(1);
	}
	
	/**
	 * Coleta as primeiras soluções
	 */
	public void setSolucoesElite(SolucaoAbstract[] retorno, int quantidade) 
	{
		for (int i=0; i<quantidade; i++)
		{
			if (i>this.solucoes.size())
				retorno[i] = null;
			else
				retorno[i] = this.solucoes.get(i);
		}
	}
	
	public double getMediaFitness() {
		return this.totalFitness / this.solucoes.size();
	}
	
	public double getTotalFitness() {
		return this.totalFitness;
	}
	
	/**
	 * Compara se duas populações tem mesma média e elementos homogeneos
	 */
	public int compara(Populacao populacao, int contadorAtual) {
		double fitnessInicial1 = this.getPrimeiro().getFitness();
		double fitnessFinal1   = this.getUltimo().getFitness();
		
		double fitnessInicial2 = populacao.getPrimeiro().getFitness();
		double fitnessFinal2   = populacao.getUltimo().getFitness();
		
		// População não está totalmente homogênea
		if (fitnessInicial1-fitnessFinal1>0.0001)   return 0;
		// Primeiros elementos diferentes nas populações
		if (fitnessInicial1-fitnessInicial2>0.0001) return 0;
		// Últimos elementos diferentes nas populações
		if (fitnessFinal1-fitnessFinal2>0.0001)     return 0;
		
		return contadorAtual+1;
	}
	
	/**
	 * Compara se os elementos das populações são identicos
	 * Usado para testes
	 */
	public boolean equals(Populacao populacao) 
	{
		List<SolucaoAbstract> solucoes1 = populacao.getSolucoes();
		List<SolucaoAbstract> solucoes2 = this.solucoes;
		if (solucoes1==null || solucoes2==null) return false;
		if (solucoes1.size() != solucoes2.size()) return false;
		for (int i = 0; i < solucoes1.size(); i++) {
			if (!solucoes1.get(i).equals(solucoes2.get(i))) 
				return false;
		}
		return true;
	}
	
	/**
	 * Compara, considerando que um percentual da população será sempre descartado
	 */
	public int comparaConsirandoInsercaoRandomica(Populacao populacao, int contadorAtual, int posicaoLimite) {

		double fitnessAtual   = this.getSolucao(0).getFitness();
		//double mediaAtual     = this.getMediaFitness();
		
		double fitnessProxima = populacao.getSolucao(0).getFitness();
		//double mediaProxima   = populacao.getMediaFitness();
		
		// Não houve variação relevante de fitness
		if (fitnessAtual-fitnessProxima>0.0001)   return 0;
		// Não houve variação da média da população
		//if (Math.abs(mediaAtual-mediaProxima)>0.0001) return 0;
		
		return contadorAtual+1;
	}

	public SolucaoAbstract getSolucao(int i) {
		return this.solucoes.get(i);
	}

	public SolucaoAbstract getPrimeiro() {
		return this.solucoes.get(0);
	}
	
	public SolucaoAbstract getSegundo() {
		return this.solucoes.get(1);
	}

	public SolucaoAbstract getUltimo() {
		return this.solucoes.get(this.solucoes.size()-1);
	}
	
	public String getString() {
		return getString(0,this.solucoes.size());
	}

	public String getString(int inicio, int fim) {
		StringBuilder sb = new StringBuilder();
		sb.append("Populacao.MediaFitness=[");
		sb.append(this.getMediaFitness());
		sb.append("]\n");
		for (int i = inicio; i < fim && i < this.solucoes.size(); i++) {
			sb.append("item[");
			sb.append(i);
			sb.append("]=");
			sb.append(this.solucoes.get(i).getString());
			sb.append("\n");
		}
		return sb.toString();
	}
}

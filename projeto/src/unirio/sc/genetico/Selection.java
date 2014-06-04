package unirio.sc.genetico;

import unirio.sc.core.SolucaoAbstract;
import unirio.sc.core.random.PseudoRandom;

/**
 * Métodos de seleção de indivíduos
 *
 * Método: getBinaryTournament
 * foi adaptado para este projeto a partir do JMetal (http://jmetal.sourceforge.net/)
 * @author Juan J. Durillo
 */
public class Selection {
	
	private int inicioA = 0;
	private int fimA    = 0;
	private int inicioB = 0;
	private int fimB    = 0;
	private TipoSelection tipoSelecao = null;
	
	public Selection(TipoSelection tipoSelecao, int tamanhoGrupoA, int tamanhoGrupoB) {
		this.tipoSelecao = tipoSelecao;
		this.inicioA = 0;
		this.fimA    = tamanhoGrupoA-1;
		this.inicioB = tamanhoGrupoA;
		this.fimB    = tamanhoGrupoA + tamanhoGrupoB - 1;
	}
	
	public Selection(TipoSelection tipoSelecao, int tamanhoPopulacao) {
		this.tipoSelecao = tipoSelecao;
		this.inicioA = 0;
		this.fimA    = tamanhoPopulacao-1; // considera todos os elementos como o primeiro pai (grupo A)
		this.inicioB = 0;
		this.fimB    = tamanhoPopulacao-1; // considera todos os elementos como o segundo pai (grupo B)
	}

	private SolucaoAbstract getBinaryTournament(Populacao populacao, int posicaoInicial, int posicaoFinal) {
		int i1 = PseudoRandom.randInt(posicaoInicial, posicaoFinal);
		int i2 = PseudoRandom.randInt(posicaoInicial, posicaoFinal);

		SolucaoAbstract s1 = populacao.get(i1);
		SolucaoAbstract s2 = populacao.get(i2);

		int flag = 0;
		double value1 = s1.getFitness();
		double value2 = s2.getFitness();
		if (value1 < value2)
			flag = -1;
		else if (value1 > value2)
			flag = 1;

		if (flag == -1)
			return s1;
		else if (flag == 1)
			return s2;
		else if (PseudoRandom.randDouble() < 0.5)
			return s1;
		else
			return s2;
	}

	public SolucaoAbstract[] getVetorBinaryTournament(Populacao populacao) {
		return new SolucaoAbstract[] {
			getBinaryTournament(populacao, 0, populacao.size() - 1),
			getBinaryTournament(populacao, 0, populacao.size() - 1)
		};
	}

	public SolucaoAbstract getBinaryTournament(Populacao populacao) {
		return getBinaryTournament(populacao, 0, populacao.size() - 1);
	}

	private SolucaoAbstract getRandom(Populacao populacao, int posicaoInicial, int posicaoFinal) {
		int i = PseudoRandom.randInt(posicaoInicial, posicaoFinal);
		SolucaoAbstract s = populacao.get(i);
		return s;
	}

	public SolucaoAbstract[] getPaisAeB(Populacao populacao) 
	{
		return new SolucaoAbstract[] {
			getRandom(populacao, this.inicioA, this.fimA),
			getRandom(populacao, this.inicioB, this.fimB)
		};
	}

	public SolucaoAbstract[] getPaisAeBComBinaryTournament(Populacao populacao) 
	{
		return new SolucaoAbstract[] {
			getBinaryTournament(populacao, this.inicioA, this.fimA),
			getBinaryTournament(populacao, this.inicioB, this.fimB)
		};
	}
	
	public TipoSelection getTipoSelecao() {
		return tipoSelecao;
	}
	
	public int getInicioA() {
		return inicioA;
	}
	
	public int getFimA() {
		return fimA;
	}
	
	public int getFimB() {
		return fimB;
	}
	
	public int getInicioB() {
		return inicioB;
	}
	
}

package unirio.sc.genetico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import unirio.sc.core.AlgoritmoAbstract;
import unirio.sc.core.SolucaoAbstract;

public class DebugUtil {
	
	private PrintStream saida = null;
	
	public ArrayList<Long> tempos = new ArrayList<Long>();
	public ArrayList<String> descricoes = new ArrayList<String>();
	
	public int contador = 0;
	public double total = 0.00;
	
	public int contadorX = 0;

	public DebugUtil(String nome, AlgoritmoAbstract algoritmo) {
		String nomeArquivo = "results/" + nome + "_" + algoritmo.getInfoParametros();  
		this.saida = abreArquivo(nomeArquivo);
	}
	
	private PrintStream abreArquivo(String nomeArquivo) {

		File arquivo = new File(nomeArquivo);
		try {
			PrintStream ps = new PrintStream(arquivo);
			return ps;
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não localizado: [" + nomeArquivo);
			System.exit(1);
		}
		return null;
	}
	
	public void printPoint(String descricao) {
		Long tempoAtu = System.currentTimeMillis();
		Long tempoDif = 0L;
		if (tempos.size()>0) {
			tempoDif = tempoAtu-tempos.get(tempos.size()-1);
		}
		tempos.add(tempoAtu);
		descricoes.add(descricao);
		saida.println(">>>" + tempoAtu + " (" + tempoDif + ") " + descricao);
	}
	
	public void println(String descricao) {
		saida.println(descricao);
	}

	public void printPopulacao(int geracao, Populacao populacao) {
		this.saida.println("---------------------------");
		this.saida.println("geracao=" + geracao);
		this.saida.print("fitness=");
		for (SolucaoAbstract solucao : populacao.getSolucoes()) {
			double fitness = solucao.getFitness();
			this.saida.print(fitness);
			this.saida.print(",");
		}
		this.saida.println("");
		this.saida.println("média=" + populacao.getMediaFitness());
		this.saida.println("menorFitness=" + populacao.getPrimeiro().getFitness());
		this.saida.println("maiorFitness=" + populacao.getUltimo().getFitness());
		this.saida.println("\n");
	}
	
	public void printPopulacaoResumo(int geracao, Populacao populacao) {
		this.saida.println("---------------------------");
		this.saida.println("geracao=" + geracao);
		this.saida.print("fitness=");
		
		this.saida.println("");
		this.saida.println("média=" + populacao.getMediaFitness());
		this.saida.println("menorFitness=" + populacao.getPrimeiro().getFitness());
		this.saida.println("menorFitness=" + populacao.getSegundo().getFitness());
		this.saida.println("maiorFitness=" + populacao.getUltimo().getFitness());
		this.saida.println("\n");
	}

	public void resetValor() 
	{
		total = 0.00;
		contador = 0;
	}
	
	public void addValor(double valor)
	{
		total+=valor;
		contador++;
	}
	
	public double getMediaValor()
	{
		return total;
	}
	
	public void resetContadorX()
	{
		contadorX = 0;
	}
	
	public void addContadorX()
	{
		contadorX++;
	}
	
	public void println(SolucaoAbstract[] solucoes) {
		for (SolucaoAbstract solucao : solucoes)
			println(solucao);
	}

	public void println(SolucaoAbstract s) {
		println(s.getString());
	}
	
}
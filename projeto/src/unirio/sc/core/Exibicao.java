package unirio.sc.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;

import unirio.sc.genetico.Populacao;

public class Exibicao {
	
	private TipoExibicao tipoExibicao = TipoExibicao.DEFAULT;

	private PrintStream[] saida = null;
	private String[] nomeSaida = null;
	
	private PrintStream debug = null;
	private int intervaloGeracao = 1;

	/**
	 * Inicializa o publicador vazio
	 */
	public Exibicao() {
	}

	/**
	 * Construtor abrindo uma saída
	 */
	public Exibicao(PrintStream pw, String nomeArquivo) {
		this.saida = new PrintStream[1];
		this.nomeSaida = new String[1];
		this.saida[0] = pw;
		this.nomeSaida[0] = nomeArquivo;
	}

	/**
	 * Inicializa o publicador com um arquivo de saída
	 */
	public Exibicao(String nomeArquivo) {
		saida = new PrintStream[1];
		nomeSaida = new String[1];
		saida[0] = abreArquivo(nomeArquivo);
		nomeSaida[0] = nomeArquivo;
	}

	/**
	 * Inicializa o publicador com vários arquivos de saída
	 */
	public Exibicao(String[] nomeArquivos) {
		for (String nomeArquivo : nomeArquivos) {
			PrintStream ps = abreArquivo(nomeArquivo);
			resizeSaida(ps,nomeArquivo);
		}
	}

	/**
	 * Adiciona um arquivo de saída no publicador
	 */
	public void addListener(PrintStream ps, String nomeArquivo) {
		resizeSaida(ps,nomeArquivo);
	}

	/**
	 * Adiciona um arquivo de saída no publicador
	 */
	public void addListener(String nomeArquivo) {
		resizeSaida(abreArquivo(nomeArquivo), nomeArquivo);
	}

	/**
	 * Acrescenta um elemento na saída
	 */
	private void resizeSaida(PrintStream ps, String nomeArquivo) {
		if (this.saida == null) {
			this.saida = new PrintStream[1];
			this.nomeSaida = new String[1];
			this.saida[0] = ps;
			this.nomeSaida[0] = nomeArquivo;
			return;
		}

		PrintStream[] saidaNova = new PrintStream[this.saida.length + 1];
		String[] nomeSaidaNova = new String[this.nomeSaida.length + 1];
		System.arraycopy(this.saida, 0, saidaNova, 0, this.saida.length);
		System.arraycopy(this.nomeSaida, 0, nomeSaidaNova, 0, this.nomeSaida.length);
		this.saida = saidaNova;
		this.nomeSaida = nomeSaidaNova;
		this.saida[this.saida.length - 1] = ps;
		this.nomeSaida[this.nomeSaida.length - 1] = nomeArquivo;
		saidaNova = null;
		nomeSaidaNova = null;
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

	public void printlnErro(String s) {
		printlnx(s);
	}

	public void printMsg(String s) {
		printx(s);
	}

	public void printlnMsg(String s) {
		printlnx(s);
	}

	public void printlnMsg(String s, int[] valores) {
		printx(s + " [");
		for (int i = 0; i < valores.length; i++) {
			printx(valores[i] + ",");
		}
		printlnx("] ");
	}

	public void close() {
		for (int i = 0; i < saida.length; i++) {
			saida[i].close();
		}
	}

	public void printlnx(String s) {
		for (int i = 0; i < saida.length; i++) {
			saida[i].println(s);
		}
	}

	public void printlnSaida(int numeroSaida, String s) {
		saida[numeroSaida].println(s);
	}

	public void printx(String s) {
		for (int i = 0; i < saida.length; i++) {
			saida[i].print(s);
		}
	}

	/**
	 * Início da execução do experimento
	 */
	public void printDadosExperimento(Experimento experimento, AlgoritmoAbstract algoritmo)
	{
		// se a exibicação está no modo simplificado
		if (this.tipoExibicao.isSimplificado()) {
			printx("\nINSTÂNCIA=" + algoritmo.getProblema().getName());
			printx("; ALGORITMO=" + experimento.getNomeExperimento());
			printlnx("; PARAMETROS=" + algoritmo.getInfoParametros());
			return;
		}
		
		printlnx("INSTÂNCIA=" + algoritmo.getProblema().getName());
		printlnx("ALGORITMO=" + experimento.getNomeExperimento());
		printlnx("PARAMETROS=" + algoritmo.getInfoParametros());
	}

	/**
	 * Adaptado de StreamMonoExperiment
	 */
	public void printCiclo(int cycleNumber, SolucaoAbstract solucao, long executionTime) {
		
		DecimalFormat dc = new DecimalFormat("0.####");
		
		// se a exibicação está no modo simplificado
		if (this.tipoExibicao.isSimplificado()) {
			printx(dc.format(solucao.getFitness())+ "; ");
			printx(formataTempo(executionTime)+ "; ");
			return;
		}
		
		if (solucao == null) {
			printlnx("Cycle #" + cycleNumber + "; solução não encontrada.");
			return;
		}

		printx("Cycle #" + cycleNumber + "; " + dc.format(executionTime));

		// Objetivos
		printx("; " + dc.format(solucao.getFitness()));

		// localização
		printx("; " + solucao.getLocation() + "; ");

		// solução
		printlnx(solucao.getString());
	}
	
	/**
	 * Adaptado de StreamMonoExperiment
	 */
	public void printFitness(SolucaoAbstract solucao) {
		if (solucao == null) {
			printlnx("Solução não encontrada.");
			return;
		}

		DecimalFormat dc = new DecimalFormat("0.####");
		// Objetivos
		printx(dc.format(solucao.getFitness()+ ", "));
	}

	public void println(SolucaoAbstract[] solucoes) {
		for (SolucaoAbstract solucao : solucoes)
			println(solucao);
	}

	public void println(SolucaoAbstract s) {
		printlnx(s.getString());
	}

	public void print(Experimento e, AlgoritmoAbstract a) {
		
		if (this.tipoExibicao.isSimplificado()) {
			DecimalFormat dc = new DecimalFormat("0.####");
			printx("Max=" + dc.format(e.getMelhorFitness()));
			printx(", Media=" + dc.format(e.getMediaFitness()));
			printlnx(", Tempo=" + formataTempo(e.getMediaTempoExecucao()));
			return;
		}
		
		printlnx("Resultado Algoritmo " + a.getNomeAlgoritmo());
		printlnx("Problema " + a.getProblema().getName()); // + " solucaoInicial=" + a.getSolucaoInicial().getString() );
		if (e.getMelhorSolucao() == null)
			printlnx("Solução ---");
		else
			printlnx("Solução " + e.getMelhorSolucao().getString());
		printlnx("Melhor fitness " + e.getMelhorFitness());
		printlnx("Media fitness " + e.getMediaFitness());
		printlnx("Media execucao " + e.getMediaTempoExecucao());
		//printlnx("Media tempo" + formataTempo(e.getMediaTempoExecucao()));
		printlnx("----------------");
	}
	
	public void setTipoExibicaoCiclo(TipoExibicao tipoExibicao) {
		this.tipoExibicao = tipoExibicao;
	}
	
	public void printDebugGeracaoCabecalho(AlgoritmoAbstract algoritmo, double seed) {
		if (this.tipoExibicao.isDebug()) {
			DecimalFormat dcSeed    = new DecimalFormat("0.00");
			debug.println("ALGORITMO=" + algoritmo.getNomeAlgoritmo());
			debug.println("PARAMETROS=" + algoritmo.getInfoParametros());
			debug.println("INSTANCIA=" + algoritmo.getProblema().getName());
			debug.println("SEED="+dcSeed.format(seed));
		}
	}
	
	public void setIntervaloGeracao(int intervaloGeracao) {
		this.intervaloGeracao = intervaloGeracao;
	}
		
	public void configuraDebug(int indice, Problema problema, Parametro param) {
		if (!this.tipoExibicao.isDebug()) {
			return;
		}
		
		this.debug = abreArquivo(Diretorios.diretorioResultados + Diretorios.getNomeArquivoDebug(indice, problema.getName()));
		this.intervaloGeracao = 1;
	}

	public void printDebugGeracao(Populacao p, int geracao, int evaluation) {
		
		if (this.tipoExibicao.isDebug()) {
			if (this.intervaloGeracao <= 0 || geracao % this.intervaloGeracao == 0 ) {
				DecimalFormat dcFitness = new DecimalFormat("0.0000");
				DecimalFormat dcGeracao = new DecimalFormat("000000");
				debug.print  (dcGeracao.format(geracao));
				debug.print  (";" + dcFitness.format(p.getMediaFitness()));
				debug.print  (";" + dcFitness.format(p.getPrimeiro().getFitness()));
				debug.print  (";" + dcFitness.format(p.getUltimo().getFitness()));
				debug.print  (";" + evaluation);
				debug.println("");
			}
		}
	}

	public void printDebugGeracao(SolucaoAbstract solucao, int geracao, int evaluation) {
		
		if (this.tipoExibicao.isDebug()) {
			if (this.intervaloGeracao <= 0 || geracao % this.intervaloGeracao == 0) { 
				DecimalFormat dcFitness = new DecimalFormat("0.0000");
				DecimalFormat dcGeracao = new DecimalFormat("000000");
				debug.print  (dcGeracao.format(geracao));
				debug.print  (";" + dcFitness.format(solucao.getFitness()));
				debug.println(";" + evaluation);
			}
		}
	}
	
	/**
	 * A partir do valor em milisegundos retorna no formato HH:MM:SS
	 * @return
	 */
	public String formataTempo(double milisegundos) {
		return String.valueOf(milisegundos).replace(".",",");
	}

}

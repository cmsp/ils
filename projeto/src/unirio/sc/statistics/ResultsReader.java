package unirio.sc.statistics;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ResultsReader extends Reader {
	
	private ArrayList<DadosAlgoritmo> algoritmos = new ArrayList<DadosAlgoritmo>();
	private ArrayList<DadosInstancia> instancias = new ArrayList<DadosInstancia>();
	
	private void addAlgoritmo(DadosAlgoritmo algoritmo) {
		if (!algoritmos.contains(algoritmo))
			algoritmos.add(algoritmo);
	}
	
	private void addInstancia(DadosInstancia instancia) {
		if (!instancias.contains(instancia))
			instancias.add(instancia);
	}

	public ResultsReader(String diretorioSaida, String nomeArquivoSaida, boolean append) {
		super(diretorioSaida, nomeArquivoSaida, append);
	}
	
	public void geraCabecalhoCSV() {
		try {
			FileWriter saida = abreArquivoSaida(this.diretorioSaida, this.nomeArquivoSaida, true);
			algoritmos.clear();
			instancias.clear();
			saida.write("CONFIGURACAO;ALGORITMO;INSTANCIA;PARAMETROS;CICLO;TEMPO;MQ");
			saida.write("\n");
			saida.close();
		}
		catch (IOException eIO) {
			System.out.println("Erro na leitura dos arquivos. msg=" + eIO.getMessage());
			System.exit(1);
		}
		
	}
	
	private void parseCabecalhoSimplificado(DadosExecucao dadosExecucao, String linha)
	{
		String[] dados = linha.split(";");
		dadosExecucao.iniciaNovaInstancia();
		dadosExecucao.parseInstancia(dados[0]);
		int desloc = 1;
		int pos = dados[1].substring(desloc).indexOf(" ");
		dadosExecucao.parseAlgoritmo(dados[1].substring(1,pos+desloc));
		dadosExecucao.parseParametros(dados[2]);
		addAlgoritmo(dadosExecucao.getAlgoritmo());
		addInstancia(dadosExecucao.getInstancia());
	}
	
	private void parseCiclosSimplificado(int parametro, FileWriter saida, DadosExecucao dadosExecucao, String linha)
	throws IOException
	{
		String[] dados = linha.split(";");
		int cont = 0;
		for (int i = 0; i< dados.length-1; i++){
			if (i % 2 == 0) {
				dadosExecucao.iniciaNovoCiclo();
				dadosExecucao.setCiclo(String.valueOf(cont++));
				dadosExecucao.setFitness(dados[i]);	
			}
			else {
				dadosExecucao.setExecucao(dados[i]);
				saida.write(parametro + ";" + dadosExecucao.getLinhaCSV());
			}
		}
	}
	
	protected void geraCSVSimplificado(String nomeArquivo, int i, BufferedReader entrada, FileWriter saida) throws IOException {
		
		String linha = "";
		DadosExecucao dadosExecucao = new DadosExecucao();
		while ((linha = entrada.readLine()) != null) {
			
			if (linha==null || linha.trim().length()==0)
				continue;

			if (linha.indexOf("INSTÂNCIA")>=0) {
				parseCabecalhoSimplificado(dadosExecucao, linha);
			}
			else {
				parseCiclosSimplificado(i, saida, dadosExecucao, linha);
			}
		}
		System.out.println("fim geracao csv simplificado. Arquivo=" + nomeArquivo);
	}
	
	protected void geraCSV(String nomeArquivo, int i, BufferedReader entrada, FileWriter saida) throws IOException {
		
		String linha = "";
		DadosExecucao dadosExecucao = new DadosExecucao();
		while ((linha = entrada.readLine()) != null) {
			TipoLinhaResultado tipoLinha = TipoLinhaResultado.getTipo(linha);
			if (tipoLinha == null) 
				continue;
			switch (tipoLinha) {
				case BRANCO:
					break;
				case CICLO:
					dadosExecucao.iniciaNovoCiclo();
					dadosExecucao.parseCiclo(linha);
					saida.write(i + ";" + dadosExecucao.getLinhaCSV());
					break;
				case INSTANCIA:
					dadosExecucao.iniciaNovaInstancia();
					dadosExecucao.parseInstancia(linha);
					addInstancia(dadosExecucao.getInstancia());
					break;
				case ALGORITMO:
					dadosExecucao.parseAlgoritmo(linha);
					break;
				case PARAMETROS:
					dadosExecucao.parseParametros(linha);
					addAlgoritmo(dadosExecucao.getAlgoritmo());
					break;
				case MEDIA_EXECUCAO:
					break;
				case MEDIA_FITNESS:
					break;
				case MELHOR_FITNESS:
					break;
				case SOLUCAO:
					break;
				case RESULTADO:
					break;
				case PROBLEMA:
					break;
				case SEPARADOR:
					break;
				case SOLUCAO_MELHOR:
					break;
			}
			
			
		}
		System.out.println("fim gravação no CVS. Arquivo entrada=" + nomeArquivo);
	}
	
	public ArrayList<DadosAlgoritmo> getAlgoritmos() {
		return algoritmos;
	}
	
	public ArrayList<DadosInstancia> getInstancias() {
		return instancias;
	}
	
}

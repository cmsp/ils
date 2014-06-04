package unirio.sc.statistics;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DebugReader extends Reader {
	
	private ArrayList<DadosDebugCabecalho> infoDebugs = new ArrayList<DadosDebugCabecalho>();
	
	private void addDebugCabecalho(DadosDebugCabecalho debug) {
		if (!infoDebugs.contains(debug))
			infoDebugs.add(debug);
	}

	public DebugReader(String diretorioSaida, String nomeArquivoSaida, boolean append) {
		super(diretorioSaida, nomeArquivoSaida, append);
	}
	
	public void geraCabecalhoCSV() {
		try {
			FileWriter saida = abreArquivoSaida(this.diretorioSaida, this.nomeArquivoSaida, true);
			infoDebugs.clear();
			saida.write("CONFIGURACAO");
			//saida.write(";ALGORITMO");
			saida.write(";INSTANCIA");
			//saida.write(";PARAMETROS");
			saida.write(";SEED");
			saida.write(";GERACAO");
			saida.write(";MEDIA");
			saida.write(";MQ1");
			saida.write(";MQN");
			saida.write(";EVALUATION");
			saida.write("\n");
			saida.close();
		}
		catch (IOException eIO) {
			System.out.println("Erro na leitura dos arquivos. msg=" + eIO.getMessage());
			System.exit(1);
		}
		
	}
	
	public void geraCSVSimplificado(String nomeArquivo, int i, BufferedReader entrada, FileWriter saida) throws IOException {
		geraCSV(nomeArquivo, i, entrada, saida);
	}
	
	public void geraCSV(String nomeArquivo, int i, BufferedReader entrada, FileWriter saida) throws IOException {
		
		String linha = "";
		DadosDebug dadosDebug = new DadosDebug();
		while ((linha = entrada.readLine()) != null) {
			TipoLinhaDebug tipoLinha = TipoLinhaDebug.getTipo(linha);
			if (tipoLinha == null) 
				continue;
			switch (tipoLinha) {
				case BRANCO:
					break;
				case ALGORITMO:
					dadosDebug.iniciaNovoAlgoritmoInstanciaSeed(i);
					dadosDebug.parseAlgoritmo(linha);
					break;
				case INSTANCIA:
					dadosDebug.parseInstancia(linha);
					break;
				case PARAMETROS:
					dadosDebug.parseParametros(linha);
					break;
				case SEED:
					dadosDebug.parseSeed(linha);
					addDebugCabecalho(dadosDebug.getDadosDebugCabecalho());
					break;
				case GERACAO:
					dadosDebug.parseGeracao(linha);
					saida.write(i + ";" + dadosDebug.getLinhaCSV());
					break;
			}
			
			
		}
		System.out.println("fim geracao arquivo debug " + nomeArquivo + ".");
	}
	
	public ArrayList<DadosDebugCabecalho> getDadosDebugCabecalhos() {
		return infoDebugs;
	}
	
}

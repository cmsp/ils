package unirio.sc.statistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import unirio.sc.core.TipoExibicao;

public abstract class Reader {
	
	protected String diretorioSaida = null;
	protected String nomeArquivoSaida = null;
	
	public Reader(String diretorioSaida, String nomeArquivoSaida, boolean append) {
		this.diretorioSaida = diretorioSaida;
		this.nomeArquivoSaida = nomeArquivoSaida;
		if (!append)
			limpandoArquivoSaida();
	}

	private void limpandoArquivoSaida() {
		try {
			FileWriter fw = new FileWriter(this.diretorioSaida + this.nomeArquivoSaida);
			//String dataAtual = new Date().toString();
			//fw.write("Resultados gerados em " + dataAtual + ".\r\n");
			fw.close();
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo de saída.");
			System.exit(1);
		}
	}

	protected FileWriter abreArquivoSaida(String diretorioSaida, String nomeArquivoSaida,boolean append) {
		try {
			FileWriter fw = new FileWriter(diretorioSaida + nomeArquivoSaida,append);
			return fw;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo de saída: [" + diretorioSaida + nomeArquivoSaida +"]");
			System.exit(1);
		}
		return null;
	}

	private BufferedReader abreArquivoEntrada(String diretorioEntrada, String nomeArquivoEntrada) {
		try {
			FileReader arquivo = new FileReader(diretorioEntrada + nomeArquivoEntrada);
			BufferedReader bf = new BufferedReader(arquivo);
			return bf;
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não localizado: [" + diretorioEntrada + nomeArquivoEntrada +"]");
			System.exit(1);
		}
		return null;
	}
	
	public void geraCSV(int i, TipoExibicao tipoExibicao, String diretorio, String nomeArquivoEntrada, boolean append) {
		try {
			BufferedReader entrada = abreArquivoEntrada(diretorio, nomeArquivoEntrada);
			FileWriter saida = abreArquivoSaida(this.diretorioSaida, this.nomeArquivoSaida, append);
			switch(tipoExibicao) {
				case DEFAULT:
					geraCSV(nomeArquivoEntrada, i, entrada, saida);
					break;
				case SIMPLIFICADO:
					geraCSVSimplificado(nomeArquivoEntrada, i, entrada, saida);
					break;
			}
			
			entrada.close();
			saida.close();
		}
		catch (IOException eIO) {
			System.out.println("Erro na leitura dos arquivos. Arquivo=" + nomeArquivoEntrada + ". msg=" + eIO.getMessage());
			System.exit(1);
		}
	}
	
	protected abstract void geraCabecalhoCSV() throws IOException;
	protected abstract void geraCSV(String nomeArquivo, int i, BufferedReader entrada, FileWriter saida) throws IOException;
	protected abstract void geraCSVSimplificado(String nomeArquivo, int i, BufferedReader entrada, FileWriter saida) throws IOException;
}

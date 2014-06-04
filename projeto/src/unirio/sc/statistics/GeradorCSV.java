package unirio.sc.statistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import unirio.sc.core.Diretorios;
import unirio.sc.core.ExperimentoModel;
import unirio.sc.core.Parametro;
import unirio.sc.core.Problema;
import unirio.sc.core.TipoExibicao;

public class GeradorCSV {
	
	private static final int INDICE_TEMPO_MS = 4;
	private static final int INDICE_MEDIA = 5;
	private static final int INDICE_DV = 6;
	private static final int INDICE_MIN = 7;
	private static final int INDICE_MAX = 8;
	private static final int INDICE_TEMPO = 9;
	private static final int INDICE_TEMPO_DV = 10;
	private static final int INDICE_MEDIA_4_CASAS = 11;
	private static final int INDICE_DV_4_CASAS = 12;
	private static final int INDICE_MIN_4_CASAS = 13;
	private static final int INDICE_MEDIA_COM_DV = 99;
	private static final int INDICE_MEDIA_COM_DV_4_CASAS = 100;

	// executa o condensador dos resultados em um unico arquivo csv, para todos os parâmetros
	public static void executa(TipoExibicao tipoExibicao, ExperimentoModel experimento) 
	{
		int parametroInicial = 1;
		int parametroFinal = experimento.getParametros().size();
		executa(tipoExibicao, experimento, parametroInicial, parametroFinal, null, true);
	}

	// executa o condensador dos resultados em um unico arquivo csv, para uma faixa de parâmetros
	public static List<DadosInstancia> executa(TipoExibicao tipoExibicao, ExperimentoModel experimento, int parametroInicial, int parametroFinal, String combinacoesWilcox, boolean geraBoxPlot) 
	{
		String diretorioResultados = Diretorios.diretorioResultados;
		String diretorioEstatisticas = Diretorios.diretorioStatistics;
		String nomeArquivoEstatisticas = Diretorios.getNomeArquivoResumoCSV(experimento);
		List<String> arquivos = Diretorios.getNomesArquivosSaida(experimento.getParametros(), parametroInicial, parametroFinal);
		
		boolean append = true;
		ResultsReader rr = new ResultsReader(diretorioEstatisticas, nomeArquivoEstatisticas, false);
		int i = 1;
		rr.geraCabecalhoCSV();
		for (String nomeArquivoResultado : arquivos) {
			rr.geraCSV(i,tipoExibicao, diretorioResultados, nomeArquivoResultado, append);
			i++;
		}
		
		List<Parametro> params = experimento.getParametros();
		
		// Gera um script para leitura no software R
		ArrayList<DadosAlgoritmo> algoritmos = rr.getAlgoritmos();
		ArrayList<DadosInstancia> instancias = rr.getInstancias();
		String nomeArquivoSaidaScriptR = "scriptR_" + nomeArquivoEstatisticas + ".r";
		String nomeArquivoSaidaScriptRWilcox = "scriptR_" + nomeArquivoEstatisticas + "_Wilcox.r";
		String nomeArquivoSaidaScriptDebug = "scriptR_" + nomeArquivoEstatisticas + ".debug.r"; 
		GeradorScriptR.executa(nomeArquivoEstatisticas, params, algoritmos, instancias, diretorioEstatisticas, nomeArquivoSaidaScriptR, nomeArquivoSaidaScriptRWilcox, nomeArquivoSaidaScriptDebug, parametroInicial, parametroFinal, combinacoesWilcox, geraBoxPlot);
		return instancias;
	}
	
	public static void executaDebug(TipoExibicao tipoExibicao, ExperimentoModel experimento) throws IOException
	{
		int parametroInicial = 1;
		int parametroFinal = experimento.getParametros().size();
		executaDebug(tipoExibicao, experimento, parametroInicial, parametroFinal);
	}

	public static void executaDebug(TipoExibicao tipoExibicao, ExperimentoModel experimento, int parametroInicial, int parametroFinal) throws IOException
	{
		if (!tipoExibicao.isDebug())
			return;
		
		String diretorioResultados = Diretorios.diretorioResultados;
		String diretorioEstatisticas = Diretorios.diretorioStatistics;
		String nomeArquivoEstatisticas = Diretorios.getNomeArquivoResumoCSVDebug(experimento);
		
		boolean append = true;
		DebugReader dr = new DebugReader(diretorioEstatisticas, nomeArquivoEstatisticas, false);
		dr.geraCabecalhoCSV();

		List<Problema> problemas = experimento.getProblemas();
		// List<Parametro> params = experimento.getParametros();
		for (Problema problema : problemas) {
			// indice está representando atualmente a execução <Problema,Parametro> e está vinculado ao arquivo de saída
			//for (Parametro param : params) {
			for (int indice = parametroInicial; indice <= parametroFinal; indice++) {
				String nomeArquivoDebug = Diretorios.getNomeArquivoDebug(indice, problema.getName());
				dr.geraCSV(indice, tipoExibicao, diretorioResultados, nomeArquivoDebug, append);
			}
		}
		
		// Gera um script para leitura no software R
		ArrayList<DadosDebugCabecalho> debugs = dr.getDadosDebugCabecalhos();
		
		boolean graficoPorAvaliacao = true; 
		
		// Gera um script para leitura no software R
		GeradorScriptR.geraScriptDebug(tipoExibicao, experimento, debugs, graficoPorAvaliacao, parametroInicial, parametroFinal);
	}
	
	/*
	public static void executa() 
	{
		// Gera os resultados para uma planilha em formato csv
		String nomeArquivoSaida = "resultados.csv";
		
		String diretorioEntrada = Principal.diretorioResultados;
		String diretorioSaida   = Principal.diretorioStatistics;
		
		// Abre os arquivos de resultados para cada algoritmo a ser executado
		Parametro[] param = Principal.getParametros();
		ArrayList<String> arquivos = Principal.getNomesArquivosSaida(param);

		boolean append = true;
		ResultsReader rr = new ResultsReader(diretorioSaida, nomeArquivoSaida);
		int i = 1;
		rr.geraCabecalhoCSV();
		for (String nomeArquivo : arquivos) {
			rr.geraCSV(i,diretorioEntrada, nomeArquivo, append);
			i++;
		}
		
		// Gera um script para leitura no software R
		ArrayList<DadosAlgoritmo> algoritmos = rr.getAlgoritmos();
		ArrayList<DadosInstancia> instancias = rr.getInstancias();
		String nomeArquivoSaidaScriptR = "scriptR_" + nomeArquivoSaida + ".r";
		String nomeArquivoSaidaScriptRWilcox = "scriptR_" + nomeArquivoSaida + "_Wilcox.r";
		String nomeArquivoSaidaScriptDebug = "scriptR_" + nomeArquivoSaida + ".debug.r"; 
		GeradorScriptR.executa(nomeArquivoSaida, param, algoritmos, instancias, diretorioSaida, nomeArquivoSaidaScriptR, nomeArquivoSaidaScriptRWilcox, nomeArquivoSaidaScriptDebug);
	}
	*/

	// executa o condensador dos resultados em um unico arquivo csv, para uma faixa de parâmetros
	public static void executaConversaoCSVEmColunas(ExperimentoModel experimento, int parametroInicial, int parametroFinal, String[] instancias) 
	{
		int indiceColuna;
		String nomeColuna;
		
		indiceColuna = INDICE_TEMPO_MS;
		nomeColuna = "Tempo(ms)";
		executaConversaoCSVEmColunas(experimento, parametroInicial, parametroFinal, instancias, indiceColuna, nomeColuna);

		indiceColuna = INDICE_MEDIA;
		nomeColuna = "Media";
		executaConversaoCSVEmColunas(experimento, parametroInicial, parametroFinal, instancias, indiceColuna, nomeColuna);
		
		indiceColuna = INDICE_DV;
		nomeColuna = "DV";
		executaConversaoCSVEmColunas(experimento, parametroInicial, parametroFinal, instancias, indiceColuna, nomeColuna);

		indiceColuna = INDICE_MIN;
		nomeColuna = "Min";
		executaConversaoCSVEmColunas(experimento, parametroInicial, parametroFinal, instancias, indiceColuna, nomeColuna);
	
		indiceColuna = INDICE_MAX;
		nomeColuna = "Max";
		executaConversaoCSVEmColunas(experimento, parametroInicial, parametroFinal, instancias, indiceColuna, nomeColuna);

		indiceColuna = INDICE_TEMPO;
		nomeColuna = "Tempo";
		executaConversaoCSVEmColunas(experimento, parametroInicial, parametroFinal, instancias, indiceColuna, nomeColuna);
		
		indiceColuna = INDICE_MEDIA_COM_DV;
		nomeColuna = "Media com DV";
		executaConversaoCSVEmColunas(experimento, parametroInicial, parametroFinal, instancias, indiceColuna, nomeColuna);

		indiceColuna = INDICE_TEMPO_DV;
		nomeColuna = "DVTempo";
		executaConversaoCSVEmColunas(experimento, parametroInicial, parametroFinal, instancias, indiceColuna, nomeColuna);
		
		indiceColuna = INDICE_MEDIA_4_CASAS;
		nomeColuna = "Média4Casas";
		executaConversaoCSVEmColunas(experimento, parametroInicial, parametroFinal, instancias, indiceColuna, nomeColuna);
		
		indiceColuna = INDICE_DV_4_CASAS;
		nomeColuna = "DV4Casas";
		executaConversaoCSVEmColunas(experimento, parametroInicial, parametroFinal, instancias, indiceColuna, nomeColuna);

		indiceColuna = INDICE_MIN_4_CASAS;
		nomeColuna = "Minimo4Casas";
		executaConversaoCSVEmColunas(experimento, parametroInicial, parametroFinal, instancias, indiceColuna, nomeColuna);
		
		indiceColuna = INDICE_MEDIA_COM_DV_4_CASAS;
		nomeColuna = "Media com DV (4 casas)";
		executaConversaoCSVEmColunas(experimento, parametroInicial, parametroFinal, instancias, indiceColuna, nomeColuna);
	}

	// executa o condensador dos resultados em um unico arquivo csv, para uma faixa de parâmetros
	private static void executaConversaoCSVEmColunas(ExperimentoModel experimento, int parametroInicial, int parametroFinal, String[] instancias, int indiceColuna, String nomeColuna) 
	{
		String diretorioEstatisticas = Diretorios.diretorioStatistics;
		String nomeArquivoEstatisticas = Diretorios.getNomeArquivoCSVGeradoScriptR();
		String nomeArquivoEstatisticasColuna = Diretorios.getNomeArquivoResumoCSVEmColuna(experimento, nomeColuna);
		boolean append = false;
		
		try {			
			FileWriter saida = new FileWriter(diretorioEstatisticas + nomeArquivoEstatisticasColuna, append);
			
			// Imprime primeira linha com os cabeçalhos para todos os parametros
			for (int i = parametroInicial; i <= parametroFinal; i++) {
				// o arquivo de entrada é gerado a partir do R, executando o scriptR_resultados_NOME_EXPERIMENTO
				saida.write("CONF " + i + ";");
			}
			saida.write("\r\n");
			
			int INDICE_INSTANCIA = 3;
			
			// Cada linha terá todos os resultados das configuracoes para a instancia
			for (String instancia :instancias) {
				FileReader arquivoEntrada = new FileReader(diretorioEstatisticas + nomeArquivoEstatisticas);
				BufferedReader entrada = new BufferedReader(arquivoEntrada);
				
				//String linhaCabecalho = entrada.readLine();
				entrada.readLine();
				
				String linha = null;
				while ((linha = entrada.readLine()) != null) {
					String[] campos = linha.split(";");
					if (!(campos[INDICE_INSTANCIA].indexOf(instancia)>=0)) {
						continue;
					}
					if (indiceColuna == INDICE_MEDIA_COM_DV) {
						saida.write(campos[INDICE_MEDIA]);
						saida.write(" + ");
						saida.write(campos[INDICE_DV]);
						saida.write(";");
					}
					else if (indiceColuna == INDICE_MEDIA_COM_DV_4_CASAS) {
						saida.write(campos[INDICE_MEDIA_4_CASAS]);
						saida.write(" + ");
						saida.write(campos[INDICE_DV_4_CASAS]);
						saida.write(";");
					}
					else {
						String valor = campos[indiceColuna];
						valor = valor.replaceAll("\\.", ",");
						saida.write(valor);
						saida.write(";");
					}
					/*
					saida.write("x");
					saida.write(";");
					saida.write(campos[0]);
					saida.write(";");
					saida.write(campos[4]);
					saida.write(";");
					saida.write(campos[5]);
					saida.write(";");
					saida.write(campos[6]);
					saida.write(";");
					saida.write(campos[7]);
					saida.write(";");
					saida.write(campos[8]);
					saida.write(";");
					saida.write(campos[9]);
					saida.write(";");
					*/
				}
				saida.write("\r\n");
				entrada.close();
			}
			saida.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não localizado: [" + diretorioEstatisticas + nomeArquivoEstatisticas +"]");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo de saída: [" + diretorioEstatisticas + nomeArquivoEstatisticasColuna + "]");
			System.exit(1);
		}
	}
	
	public static void executaConcatenacaoWilcoxEffectSize(ExperimentoModel experimento, String combinacoesWilcox, String[] instancias) {
		int indiceColuna;
		String nomeColuna;

		indiceColuna = 2;
		nomeColuna = "Wilcox";
		executaConcatenacaoWilcoxEffectSize(experimento, combinacoesWilcox, instancias, indiceColuna, nomeColuna);
		
		indiceColuna = 3;
		nomeColuna = "EffectSize";
		executaConcatenacaoWilcoxEffectSize(experimento, combinacoesWilcox, instancias, indiceColuna, nomeColuna);
	}
	
	// executa o condensador dos resultados em um unico arquivo csv, para uma faixa de parâmetros
	private static void executaConcatenacaoWilcoxEffectSize(ExperimentoModel experimento, String combinacoesWilcox, String[] instancias, int INDICE_COLUNA, String nomeColuna) {
		
		String diretorioEstatisticas = Diretorios.diretorioStatistics;
		String nomeArquivoEstatisticas = Diretorios.getNomeArquivoResumoWilcoxCSV(experimento, nomeColuna);
		
		try {			
							
			FileWriter saida = new FileWriter(diretorioEstatisticas + nomeArquivoEstatisticas);
				
			String[] itens = combinacoesWilcox.split(";");
			for (String item : itens) {
				String[] pares = item.split(",");
				String configuracao1 = pares[0];
				String configuracao2 = pares[1];
				
				saida.write("CONF " + configuracao1 + " E " + configuracao2);
				
				if (INDICE_COLUNA == -1) {
					saida.write(";;;;");
				}
				else {
					saida.write(";");					
				}
			}
			saida.write("\r\n");

			// Cada linha terá todos os resultados das configuracoes para a instancia
			for (String instancia :instancias) {
				concatenaArquivosWilcox(saida, instancia, combinacoesWilcox, INDICE_COLUNA);
				saida.write("\r\n");
			}

			saida.write("\r\n");
			saida.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não localizado: [" + diretorioEstatisticas + nomeArquivoEstatisticas +"]");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo de saída: [" + diretorioEstatisticas + nomeArquivoEstatisticas + "]");
			System.exit(1);
		}
	}

	private static void concatenaArquivosWilcox(FileWriter saida, String instancia, String combinacoesWilcox, int INDICE_COLUNA) 
	{
		String[] itens = combinacoesWilcox.split(";");
		for (String item : itens) {
			String[] pares = item.split(",");
			String configuracao1 = pares[0];
			String configuracao2 = pares[1];

			String nomeArquivoWilcox = Diretorios.diretorioStatistics + "COMPARACAO WILCOX e EFFECTSIZE DE  " + configuracao1.trim() + " E " + configuracao2.trim() + " .csv";

			try {
				FileReader arquivoEntrada = new FileReader(nomeArquivoWilcox);
				BufferedReader entrada = new BufferedReader(arquivoEntrada);
					
				String linha = null;
				while ((linha = entrada.readLine()) != null) {
					String[] campos = linha.split(";");
					if (!(campos[1].indexOf(instancia)>=0)) {
						continue;
					}
					
					if (INDICE_COLUNA == -1){
						saida.write(linha);
					}
					else {
						saida.write(campos[INDICE_COLUNA]);
					}
					
					saida.write(";");
					break;
				}
				entrada.close();
			} catch (FileNotFoundException e) {
				System.out.println("Arquivo não localizado: [" + nomeArquivoWilcox +"]");
				System.exit(1);
			} catch (IOException e) {
				System.out.println("Erro ao abrir o arquivo de saída: [" + nomeArquivoWilcox + "]");
				System.exit(1);
			}
		}
	}
	
	/*
	// executa o condensador dos resultados em um unico arquivo csv, para uma faixa de parâmetros
	public static void executaConcatenacaoWilcoxEffectSize(ExperimentoModel experimento, String combinacoesWilcox, String[] instancias) {
		
		String diretorioEstatisticas = Diretorios.diretorioStatistics;
		String nomeArquivoEstatisticas = Diretorios.getNomeArquivoResumoWilcoxCSV(experimento);
		
		try {			
							
			FileWriter saida = new FileWriter(diretorioEstatisticas + nomeArquivoEstatisticas);
				
			String[] itens = combinacoesWilcox.split(";");
			for (String item : itens) {
				String[] pares = item.split(",");
				for (String par1 : pares) {
					for (String par2 : pares) {
						if (!par1.equals(par2)) {
							String configuracao1 = par1;
							String configuracao2 = par2;

							saida.write("CONF " + configuracao1 + " E " + configuracao2);
							saida.write(";;;;");
						}
					}
				}
			}
			saida.write("\r\n");

			// Cada linha terá todos os resultados das configuracoes para a instancia
			for (String instancia :instancias) {
				concatenaArquivosWilcox(saida, instancia, combinacoesWilcox);
				saida.write("\r\n");
			}

			saida.write("\r\n");
			saida.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não localizado: [" + diretorioEstatisticas + nomeArquivoEstatisticas +"]");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo de saída: [" + diretorioEstatisticas + nomeArquivoEstatisticas + "]");
			System.exit(1);
		}
	}

	private static void concatenaArquivosWilcox(FileWriter saida, String instancia, String combinacoesWilcox) 
	{
		String[] itens = combinacoesWilcox.split(";");
		for (String item : itens) {
			String[] pares = item.split(",");
			for (String par1 : pares) {
				for (String par2 : pares) {
					if (!par1.equals(par2)) {
						String configuracao1 = par1;
						String configuracao2 = par2;

						String nomeArquivoWilcox = Diretorios.diretorioStatistics + "COMPARACAO WILCOX e EFFECTSIZE DE  " + configuracao1.trim() + " E " + configuracao2.trim() + " .csv";

						try {
							FileReader arquivoEntrada = new FileReader(nomeArquivoWilcox);
							BufferedReader entrada = new BufferedReader(arquivoEntrada);
								
							String linha = null;
							while ((linha = entrada.readLine()) != null) {
								String[] campos = linha.split(";");
								if (!(campos[1].indexOf(instancia)>=0)) {
									continue;
								}
								
								saida.write(linha);
								saida.write(";");
								break;
							}
							entrada.close();
						} catch (FileNotFoundException e) {
							System.out.println("Arquivo não localizado: [" + nomeArquivoWilcox +"]");
							System.exit(1);
						} catch (IOException e) {
							System.out.println("Erro ao abrir o arquivo de saída: [" + nomeArquivoWilcox + "]");
							System.exit(1);
						}
					}
				}
			}
		}
	}
	*/
}

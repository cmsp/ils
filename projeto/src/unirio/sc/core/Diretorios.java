package unirio.sc.core;

import java.util.ArrayList;
import java.util.List;

public class Diretorios {

	public static String diretorioInstancias = System.getProperty( "user.dir" ) + "\\data\\";
	public static String diretorioResultados = System.getProperty( "user.dir" ) + "\\results\\";
	public static String diretorioStatistics = System.getProperty( "user.dir" ) + "\\statistics\\";
	public static String diretorioInstanciasTXT = System.getProperty( "user.dir" ) + "\\data_txt\\";
	
	// um arquivo por parametro (algoritmo)
	public static String PREFIXO_SAIDA = "saida_";
	// um arquivo por parametro + problema
	public static String PREFIXO_DEBUG = "saida_debug_";
	// resultados do experimento condensados em um arquivo csv
	public static String PREFIXO_SAIDA_CSV = "resultados_";
	// resultados do experimento condensados em um arquivo csv
	public static String PREFIXO_DEBUG_CSV = "resultados_debug_";
	
	// coleta os nomes de arquivos de saída, dentro da faixa de parametros, o primeiro parametro é 1
	public static List<String> getNomesArquivosSaida(List<Parametro> params, int parametroInicial,int parametroFinal)
	{
		List<String> arquivos = new ArrayList<String>();
		for (int i = parametroInicial; i <= parametroFinal; i++ ) {
			String nomeArquivo = Diretorios.PREFIXO_SAIDA +
				+ (i)
				+ ".txt";
			arquivos.add(nomeArquivo);
		}
		return arquivos;
	}
	
	// coleta os nomes de arquivos de saída para todos os parâmetros
	public static List<String> getNomesArquivosSaida(List<Parametro> params)
	{
		return getNomesArquivosSaida(params, 1, params.size());
	}

	public static String getNomeArquivoDebug(int indice, String nomeProblema) {
		return Diretorios.PREFIXO_DEBUG  + indice + "_" + nomeProblema + ".txt";
	}

	@SuppressWarnings("unused")
	public static List<String> getNomesArquivosDebug(ExperimentoModel experimento)
	{
		List<Problema> problemas = experimento.getProblemas();
		List<Parametro> params = experimento.getParametros();
		List<String> arquivos = new ArrayList<String>();
		for (Problema problema : problemas) {
			int indice = 0;
			// loop necessário para pegar a configuração, modificar criando entidade <Problema,Parametro>
			for (Parametro param : params) {
				arquivos.add(getNomeArquivoDebug(indice, problema.getName()));
				indice++;
			}
		}
		return arquivos;
	}
	
	public static String getNomeArquivoSaidaScriptDebug(ExperimentoModel experimento) {
		return "scriptR_debug_" + experimento.getIdExperimento() + ".r";
	}
	
	public static String getDiretorioTrataoParaR(String diretorio) {
		StringBuilder diretorioTratado = new StringBuilder(""); 
		for ( int i = 0; i < diretorio.length(); i++) {
			if (diretorio.charAt(i)=='\\')
				diretorioTratado.append("\\\\");
			else
				diretorioTratado.append(diretorio.charAt(i));
		}
		return diretorioTratado.toString();
	}
	
	public static String getNomeArquivoResumoCSVDebug(ExperimentoModel experimento) {
		return Diretorios.PREFIXO_DEBUG_CSV + experimento.getIdExperimento() + ".csv";
	}
	
	public static String getNomeArquivoResumoCSV(ExperimentoModel experimento) {
		return Diretorios.PREFIXO_SAIDA_CSV + experimento.getIdExperimento() + ".csv";
	}

	public static String getNomeArquivoResumoCSVEmColuna(ExperimentoModel experimento, String nomeColuna ) {
		return "SAIDA RESULTADOS EM COLUNA - " + nomeColuna + ".csv";
	}
	
	public static String getNomeArquivoCSVGeradoScriptR() {
		return "SAIDA RESULTADOS.csv";
	}
	
	public static String getNomeArquivoResumoWilcoxCSV(ExperimentoModel experimento, String nomeColuna ) {
		return "SAIDA RESULTADOS WILCOX - " + experimento.getIdExperimento() + " - " + nomeColuna + ".csv";
	}
}

package unirio.sc.statistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import unirio.sc.core.Diretorios;
import unirio.sc.core.Problema;
import unirio.sc.core.ProblemaFactory;

public class LeitorSolucoes {
	
	/*
	private static String[] arquivosInstancias = {
		"mtunis",
		"ispell",
		"rcs",
		"bison",
		"grappa",
		//"bunch",
		"incl",
	};
	
	private static String[] instancias = {
		"mtunis",
		"ispell",
		"rcs",
		"bison",
		"grappa",
		//"bunch",
		"incl",
	};
	*/

	private static String[] arquivosInstancias = {
		"jstl-1.0.6 18C.odem",
		"jnanoxml 25C.odem",
		"joda-money 26C.odem",
		"jxls-reader 27C.odem",
		"seemp 31C.odem",
		
		"apache_zip 36C.odem", //"notepad-model 46C.odem", // está calculando zero, verificar
		"udt-java 56C.odem",
		"javaocr 59C.odem",
		"servletapi-2.3 63C.odem",
		"pfcda_base 67C.odem",

		"forms-1.3.0 68C.odem",
		"jscatterplot 74C.odem",
		"jfluid-1.7.0 82C.odem",
		"jxls-core 83C.odem",
		"JPassword 96C.odem",
		
		"junit-3.8.1 100C.odem", //
		"xmldom 119C.odem",
		"tinytim 134C.odem",
		"jkaryoscope 136C.odem",
		"gae_plugin_core 140C.odem", //
		
		"javacc 154C.odem",
		"JavaGeom 172C.odem",
		"jdendogram 177C.odem", //
		"xmlapi 184C.odem",
		"JMetal 190C.odem",
		
		"dom4j-1.5.2 195C.odem",
		"pdf_renderer 199C.odem", //
		"Jung-graph 207C.odem",
		"jconsole-1.7.0 220C.odem",
		"Jung-visualization 221C.odem", //
		
		"pfcda_swing 252C.odem", //
		"jpassword 269C.odem",
		"jml 270C.odem", //
		"notepad-full 299C.odem", //
		"poormans_2.3 304C.odem",
		
		"log4j-1.2.16 308C.odem",
		"jtreeview 329C.odem",
		"jace 340C.odem",
		"javaws 378C.odem", //"javaws-7 378C.odem", // repetido
		"res_cobol 483C.odem", //
		
		// "y_base 558C.odem", //
		// "lwjgl 569C.odem",
	};
	
	private static String[] instancias = {
		"jstl",
		"nanoxml",
		"jodamoney",
		"jxlsreader",
		"seemp",
		"apache_zip",
		"udt-java",
		"javaocr",
		"servlet",
		"pfcda_base",
		"forms",
		"jscatterplot",
		"jfluid",
		"jxlscore",
		"jpassword1",
		"junit",
		"xmldom",
		"tinytim",
		"jkaryoscope",
		"gae_plugin_core",
		"javacc",
		"JavaGeom",
		"jdendogram",
		"xmlapi",
		"jmetal",
		"dom4j",
		"pdf_renderer",
		"Jung_graph_model",
		"jconsole",
		"jung_visualization",
		"pfcda_swing",
		"jpassword2",
		"jml-1.0b4",
		"notelab-full",
		"Poormans CMS",
		"log4j",
		"jtreeview",
		"JACE",
		"javaws",
		"res_cobol",
		// "y_base",
		// "lwjgl-2.8.4"
	};
	
	public static void executa(String nomeResultado) 
	{
		String diretorioResultados = Diretorios.diretorioResultados;
		String diretorioEstatisticas = Diretorios.diretorioStatistics;
		String nomeEntrada = diretorioResultados + nomeResultado;
		String nomeSaida1 = diretorioEstatisticas + nomeResultado.replaceAll(".txt", "") + "_CARACTERISTICAS.csv";
		String nomeSaida2 = diretorioEstatisticas + nomeResultado.replaceAll(".txt", "") + "_CARACTERISTICAS_MINIMOS.csv";
		
		try {			
			FileWriter saida1 = new FileWriter(nomeSaida1, false);
			FileWriter saida2 = new FileWriter(nomeSaida2, false);
			
			FileReader arquivoEntrada = new FileReader(nomeEntrada);
			BufferedReader entrada = new BufferedReader(arquivoEntrada);
			
			String instanciaAtual = null;
			Problema problema = null;
			
			saida1.write("instancia;");
			saida1.write("tamanho;");
			saida1.write("qtdClusterOriginal;");
			saida1.write("dependencias;");
			saida1.write("ciclo;");
			saida1.write("mqLido;");
			saida1.write("mqRecalculado;");
			saida1.write("solucao;");
			saida1.write("CFs;");
			saida1.write("in;");
			saida1.write("out;");
			saida1.write("intra;");
			saida1.write("totalClusters;");
			saida1.write("clusterMaior;");
			saida1.write("qtdModulosMaior;");
			saida1.write("clusterMenor;");
			saida1.write("qtdModulosMenor;");
			saida1.write("qtdClustersUnitarios;");
			saida1.write("\n");
					
			String linha = null;
			while ((linha = entrada.readLine()) != null) {
				
				TipoLinhaResultado tipoLinha = TipoLinhaResultado.getTipo(linha);
				if (tipoLinha == null) {
					if (linha.indexOf("Resultado")==0) {
						continue;
					}
					System.out.println("ERRO");
				}
				switch (tipoLinha) {
					case BRANCO:
						break;
					case CICLO:
						coletarDadosCiclo(saida1, problema, linha);
						break;
					case INSTANCIA:
						instanciaAtual = linha.split("=")[1];
						problema = coletarProblema(instanciaAtual);
						if (problema == null) {
							System.out.println("ERRO");
						}
						break;
					case ALGORITMO:
						break;
					case PARAMETROS:
						break;
					case MEDIA_EXECUCAO:
						break;
					case MEDIA_FITNESS:
						break;
					case MELHOR_FITNESS:
						break;
					case SOLUCAO:
						break;
					case SOLUCAO_MELHOR:
						coletarDadosSolucao(saida2, problema, linha);
						break;
					case RESULTADO:
						break;
					case PROBLEMA:
						break;
					case SEPARADOR:
						break;
				}
			}

			saida1.close();
			saida2.close();
			entrada.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não localizado: [" + nomeEntrada +"]");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo de saída: [" + nomeSaida1 + "]");
			System.exit(1);
		}
	}

	private static void coletarDadosCiclo(FileWriter saida, Problema problema, String linha) throws IOException 
	{
		//Cycle #0; 61; -3,3121; 0; 0,7,1,1,2,2,3,3,4,4,3,1,5,6,5,6,6,6
		saida.write(problema.getName());
		saida.write(";");
		saida.write(String.valueOf(problema.getTamanho()));
		saida.write(";");
		saida.write(String.valueOf(problema.getPackageCount()));
		saida.write(";");
		saida.write(String.valueOf(problema.calculaTotalDependencias()));
		saida.write(";");
		
		String[] dados = linha.split(";");
		String ciclo = dados[0].split("#")[1];
		String mqLido = dados[2];
		String valores = dados[4];
		SolucaoInfo info = new SolucaoInfo(problema.getTamanho(), valores, mqLido);
		String[] valoresStr = valores.split(",");
		int n = valoresStr.length;
		int[] valoresInt = new int[n];
		for (int i = 0; i < n; i++) {
			valoresInt[i] = Integer.parseInt(valoresStr[i].trim());
		}
		coletarInfoMQ(problema, valoresInt, info);
		
		saida.write(ciclo);
		saida.write(";");
		saida.write(info.getDadosMQ());
		saida.write(info.getDadosClusters());
		saida.write("\n");
	}
	
	private static void coletarDadosSolucao(FileWriter saida, Problema problema, String linha) throws IOException 
	{
		//Cycle #0; 61; -3,3121; 0; 0,7,1,1,2,2,3,3,4,4,3,1,5,6,5,6,6,6
		saida.write(problema.getName());
		saida.write(";");
		saida.write(String.valueOf(problema.getTamanho()));
		saida.write(";");
		saida.write(String.valueOf(problema.getPackageCount()));
		saida.write(";");
		saida.write(String.valueOf(problema.calculaTotalDependencias()));
		saida.write(";");
		
		String valores = linha.split(" ")[1];
		SolucaoInfo info = new SolucaoInfo(problema.getTamanho(), valores, "");
		String[] valoresStr = valores.split(",");
		int n = valoresStr.length;
		int[] valoresInt = new int[n];
		for (int i = 0; i < n; i++) {
			valoresInt[i] = Integer.parseInt(valoresStr[i].trim());
		}
		coletarInfoMQ(problema, valoresInt, info);
		
		saida.write("");
		saida.write(";");
		saida.write(info.getDadosMQ());
		saida.write(info.getDadosClusters());
		saida.write("\n");
	}

	private static Problema coletarProblema(String nomeInstancia) {
		Problema problema = null;
		for (int i = 0; i < instancias.length; i++) {
			if (instancias[i].equals(nomeInstancia)) {
				problema = ProblemaFactory.getProblema(arquivosInstancias[i]);
			}
		}
		return problema;
	}
	
	/**
	 * Calcula o coeficiente de modularidade do projeto
	 */
	public static void coletarInfoMQ(Problema problema, int[] valores, SolucaoInfo info) {
		
		int[] inboundEdges = new int[problema.getClassCount()];
		int[] outboundEdges = new int[problema.getClassCount()];
		int[] intraEdges = new int[problema.getClassCount()];
		
		int[][]listaDependencias = problema.getListaDependenciasPara();
		int[] qtdDependencias = problema.getQtdDependenciasPara();
		int classCount = problema.getClassCount();
		
		for (int i=0; i<classCount; i++) {
			int sourcePackage = valores[i];
			info.addCluster(sourcePackage, i);
			for (int j=0; j<qtdDependencias[i]; j++) {
				int targetPackage = valores[listaDependencias[i][j]];
				if (targetPackage != sourcePackage) {
					outboundEdges[sourcePackage]++;
					inboundEdges[targetPackage]++;
				} else
					intraEdges[sourcePackage]++;
			}
		}

		double mq = 0.0;

		//for (int i = 0; i < this.problema.getMaxPackages(); i++) {
		for (int i = 0; i < classCount; i++) {
			int inter = inboundEdges[i] + outboundEdges[i];
			int intra = intraEdges[i];

			//if (intra != 0 && inter != 0) {
			if (intra != 0) {
				double mf = intra / (intra + 0.5 * inter);
				mq += mf;
				info.setCluster(i, mf, inboundEdges[i], outboundEdges[i], intraEdges[i] );
			}
			else {
				if (inter != 0) {
					info.setCluster(i, 0.00, inboundEdges[i], outboundEdges[i], intraEdges[i] );
				}
			}
		}
		
		info.mq = mq;
	}
	
}

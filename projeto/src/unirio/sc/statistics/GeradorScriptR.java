package unirio.sc.statistics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import unirio.sc.core.Diretorios;
import unirio.sc.core.ExperimentoModel;
import unirio.sc.core.Parametro;
import unirio.sc.core.TipoExibicao;

public class GeradorScriptR {
	
	private static FileWriter abreArquivoSaida(String diretorioSaida, String nomeArquivoSaida,boolean append) {
		try {
			FileWriter fw = new FileWriter(diretorioSaida + nomeArquivoSaida,append);
			return fw;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo de saída: [" + diretorioSaida + nomeArquivoSaida +"]");
			System.exit(1);
		}
		return null;
	}
	
	private static void geraScriptRMedias(String nomeArquivoEntrada, List<Parametro> params, ArrayList<DadosAlgoritmo> algoritmos, ArrayList<DadosInstancia> instancias, String diretorioSaida, FileWriter saida, int parametroInicial, int parametroFinal) throws IOException
	{
		String dataAtual = new Date().toString();
		saida.write("# Script gerado em " + dataAtual + ".\r\n");
		
		// configura o diretorio de trabalho
		StringBuilder diretorioSaidaTratado = new StringBuilder(""); 
		for ( int i = 0; i < diretorioSaida.length(); i++) {
			if (diretorioSaida.charAt(i)=='\\')
				diretorioSaidaTratado.append("\\\\");
			else
				diretorioSaidaTratado.append(diretorioSaida.charAt(i));
		}
		saida.write("setwd(\"");
		saida.write(diretorioSaidaTratado.toString());
		saida.write("\") \r\n");
		
		saida.write("tabelaMQ<-read.table(\"" + nomeArquivoEntrada + "\",h=T,sep=\";\",dec=\",\") \r\n");
		
		saida.write("################################# \r\n");
		saida.write("# Calcula min, max e desvio padrão para MQ \r\n");
		saida.write("################################# \r\n");
		saida.write("minimos      <- NULL \r\n");
		saida.write("maximos      <- NULL \r\n");
		saida.write("desvioPadrao <- NULL \r\n");
		saida.write("media        <- NULL \r\n");
		saida.write("tempos       <- NULL \r\n");
		saida.write("temposSeg    <- NULL \r\n");
		saida.write("desvioPadraoTempo <- NULL \r\n");
		saida.write("media4casas  <- NULL \r\n");
		saida.write("desvioPadrao4casas <- NULL \r\n");
		saida.write("minimos4casas<- NULL \r\n");
		saida.write("\r\n");
		saida.write("i<-1 \r\n");
		
		saida.write("aplicacoesMQ<-c(");
		StringBuilder sbInstancias = new StringBuilder("");
		for (DadosInstancia instancia : instancias) {
			sbInstancias.append("'");
			sbInstancias.append(instancia.getNome());
			sbInstancias.append("'");
			sbInstancias.append(",");
		}
		sbInstancias.deleteCharAt(sbInstancias.length()-1);
		saida.write(sbInstancias.toString());
		saida.write(") \r\n");
		
		saida.write("configuracoes<-c(");
		for ( int i = parametroInicial; i < parametroFinal; i++) {
			saida.write(String.valueOf(i));
			saida.write(",");
		}
		saida.write(String.valueOf(parametroFinal));
		saida.write(")\r\n");
		
		saida.write("algoritmo  <- NULL \r\n");
		saida.write("parametros <- NULL \r\n");
		
		saida.write("cabecalho<-array(dim=c(1,14)) \r\n");
		saida.write("cabecalho[1,1]<-'conf' \r\n");
		saida.write("cabecalho[1,2]<-'algoritmo' \r\n");
		saida.write("cabecalho[1,3]<-'parametros' \r\n");
		saida.write("cabecalho[1,4]<-'instancia' \r\n");
		saida.write("cabecalho[1,5]<-'tempos' \r\n");
		saida.write("cabecalho[1,6]<-'media' \r\n");
		saida.write("cabecalho[1,7]<-'desvioPadrao' \r\n");
		saida.write("cabecalho[1,8]<-'minimos' \r\n");
		saida.write("cabecalho[1,9]<-'maximos' \r\n");
		saida.write("cabecalho[1,10]<-'tempos (seg)' \r\n");
		saida.write("cabecalho[1,11]<-'desvioPadraoTempo (seg)' \r\n");
		saida.write("cabecalho[1,12]<-'media4casas' \r\n");
		saida.write("cabecalho[1,13]<-'desvioPadrao4casas)' \r\n");
		saida.write("cabecalho[1,14]<-'minimo4casas' \r\n");
		saida.write("write.table(cabecalho,file=\"" + Diretorios.getNomeArquivoCSVGeradoScriptR() + "\",sep=\";\",eol=\"\\n\",row.names=FALSE,col.names=FALSE,append=FALSE) \r\n");
		
		saida.write("for ( conf in configuracoes ) { \r\n");
		saida.write("	for ( proj in aplicacoesMQ ) { \r\n"); 
		saida.write("		tabelaAux<-subset(tabelaMQ,tabelaMQ$INSTANCIA==proj & tabelaMQ$CONFIGURACAO==conf) \r\n");
				
		saida.write("		algoritmo <- tabelaAux[1,2] \r\n");
		//Não está atulizando as informações sobre os parametros corretamente para cada projeto
		saida.write("		parametros<- tabelaAux[1,4] \r\n");

		saida.write("		tamanho<-nrow(tabelaAux) \r\n");

		saida.write("		minimos[proj]<-round(min(tabelaAux$MQ),2) \r\n");
		saida.write("		maximos[proj]<-round(max(tabelaAux$MQ),2) \r\n");
		saida.write("		media[proj]<-round(mean(tabelaAux$MQ),2) \r\n");
		saida.write("		desvioPadrao[proj]<-round(sd(tabelaAux$MQ),2) \r\n");
		saida.write("		tempos[proj]<-round(mean(tabelaAux$TEMPO),2) \r\n");
		saida.write("		temposSeg[proj]<-round(mean(tabelaAux$TEMPO)/1000,2) \r\n");
		saida.write("		desvioPadraoTempo[proj]<-round(sd(tabelaAux$TEMPO/1000),2) \r\n");
		saida.write("		media4casas[proj]<-round(mean(tabelaAux$MQ),4) \r\n");
		saida.write("		desvioPadrao4casas[proj]<-round(sd(tabelaAux$MQ),4) \r\n");
		saida.write("		minimos4casas[proj]<-round(min(tabelaAux$MQ),4) \r\n");
		saida.write("		i<-i+1 \r\n");
		saida.write("	} \r\n");
		saida.write("	resultadosMQ<-data.frame(conf,algoritmo,parametros,aplicacoesMQ,tempos,media,desvioPadrao,minimos,maximos,temposSeg,desvioPadraoTempo,media4casas,desvioPadrao4casas,minimos4casas) \r\n");
		saida.write("	nomeArquivo <- paste(\"SAIDA\",conf,\".csv\") \r\n");
		//saida.write("	write.csv2(resultadosMQ,file=nomeArquivo) \r\n");
		saida.write("	write.table(resultadosMQ,file=\"SAIDA RESULTADOS.csv\",sep=\";\",eol=\"\\n\",row.names=FALSE,col.names=FALSE,append=TRUE) \r\n");
		saida.write("} \r\n");
		
		//saida.write("arquivos<-NULL \r\n");
		//for (int i = 1; i <= algoritmos.size(); i++) {
		//for (int i = 1; i <= param.length; i++) {
		//	saida.write("arquivos[ " + i + "]<-'SAIDA " + i + ".csv' \r\n");
		//}
	}
	
	private static void geraScriptRWilcoxEffectSize(String configuracao1, String configuracao2, String nomeArquivoEntrada, List<Parametro> params, ArrayList<DadosAlgoritmo> algoritmos, ArrayList<DadosInstancia> instancias, String diretorioSaida, FileWriter saida, int parametroInicial, int parametroFinal, boolean geraBoxPlot) throws IOException
	{
		String dataAtual = new Date().toString();
		saida.write("# Script gerado em " + dataAtual + ".\r\n");
		
		// configura o diretorio de trabalho
		StringBuilder diretorioSaidaTratado = new StringBuilder(""); 
		for ( int i = 0; i < diretorioSaida.length(); i++) {
			if (diretorioSaida.charAt(i)=='\\')
				diretorioSaidaTratado.append("\\\\");
			else
				diretorioSaidaTratado.append(diretorioSaida.charAt(i));
		}
		saida.write("setwd(\"");
		saida.write(diretorioSaidaTratado.toString());
		saida.write("\") \r\n");
		
		saida.write("tabelaMQ<-read.table(\"" + nomeArquivoEntrada + "\",h=T,sep=\";\",dec=\",\") \r\n");
		
		saida.write("nomeArquivo<-NULL \r\n");
		saida.write("wilcoxTest<-NULL \r\n");
		saida.write("effectSize<-NULL \r\n");
		saida.write("resultado<-NULL \r\n");
		
		saida.write("instancias<-c(");
		StringBuilder sbInstancias = new StringBuilder("");
		for (DadosInstancia instancia : instancias) {
			sbInstancias.append("'");
			sbInstancias.append(instancia.getNome());
			sbInstancias.append("'");
			sbInstancias.append(",");
		}
		sbInstancias.deleteCharAt(sbInstancias.length()-1);
		saida.write(sbInstancias.toString());
		saida.write(") \r\n");

		saida.write("confA<-" + configuracao2 + " \r\n");
		saida.write("confB<-" + configuracao1 + " \r\n");
		saida.write("i<-1 \r\n");
		saida.write("for ( instancia in instancias ) { \r\n");

		saida.write("	tabelaA<-subset(tabelaMQ,tabelaMQ$INSTANCIA==instancia & tabelaMQ$CONFIGURACAO==confA) \r\n");
		saida.write("	tamanhoA <- nrow(tabelaA) \r\n");

		saida.write("	tabelaB<-subset(tabelaMQ,tabelaMQ$INSTANCIA==instancia & tabelaMQ$CONFIGURACAO==confB) \r\n");
		saida.write("	tamanhoB <- nrow(tabelaB) \r\n");

		saida.write("	rx <- sum(rank(c(tabelaA$MQ, tabelaB$MQ))[seq_along(tabelaA$MQ)]) \r\n");
		saida.write("	effectSize[i]<-round(((rx / tamanhoA - (tamanhoA + 1) / 2) / tamanhoB),2) \r\n");

		saida.write("	wilcoxTest[i]<-round(wilcox.test(tabelaA$MQ,tabelaB$MQ)$p.value,4) \r\n");

		saida.write("	i<-i+1 \r\n");
		saida.write("} \r\n");
		saida.write("resultado<-data.frame(instancias,wilcoxTest,effectSize) \r\n");
		saida.write("nomeArquivo <- paste(\"COMPARACAO WILCOX e EFFECTSIZE DE \",confA,\"E\",confB,\".csv\") \r\n");
		saida.write("write.csv2(resultado,file=nomeArquivo) \r\n");

		if (geraBoxPlot) {
			saida.write("######################## \r\n");
			saida.write("# VERIFICANDO BOXPLOTS \r\n");
			saida.write("######################## \r\n");
	
			saida.write("for ( instancia in instancias ) { \r\n");
			saida.write("	tabelaA<-subset(tabelaMQ$MQ,tabelaMQ$INSTANCIA==instancia & tabelaMQ$CONFIGURACAO==confA) \r\n");
			saida.write("	tabelaB<-subset(tabelaMQ$MQ,tabelaMQ$INSTANCIA==instancia & tabelaMQ$CONFIGURACAO==confB) \r\n");
			saida.write("	bmp(filename=paste(\"BOXPLOT1 instancia=\",instancia,\"conf=\",confA,\".bmp\")) \r\n");
			saida.write("	boxplot(tabelaA) \r\n");
			saida.write("	dev.off() \r\n");
			saida.write("	bmp(filename=paste(\"BOXPLOT1 instancia=\",instancia,\"conf=\",confB,\".bmp\")) \r\n");
			saida.write("	boxplot(tabelaB) \r\n");
			saida.write("	dev.off() \r\n");
			saida.write("} \r\n");
		}
	}
	
	private static boolean contemMultiplasSolucoesPorIteracao(DadosDebugCabecalho debug) {
		if (debug.getNomeAlgoritmo().indexOf("Genetico")>=0 || debug.getNomeAlgoritmo().indexOf("Genético")>=0) {
			return true;
		}
		return false;
	}
	
	/*
	private static void geraScriptGraficoIteracoes(ExperimentoModel experimento, FileWriter saida, List<DadosDebugCabecalho> debugs) throws IOException
	{
		List<Parametro> params = experimento.getParametros();
		
		String DECIMAL = "."; // como o decimal foi gravado no arquivo de entrada
		
		String dataAtual = new Date().toString();
		saida.write("# Script gerado em " + dataAtual + ".\r\n");
		
		// configura o diretorio de trabalho
		String diretorioSaidaTratado = Diretorios.getDiretorioTrataoParaR(Diretorios.diretorioStatistics);
		saida.write("setwd(\"");
		saida.write(diretorioSaidaTratado);
		saida.write("\") \r\n");
		
		saida.write("configuracoes<-c(");
		for ( int i = 1; i < params.size(); i++) {
			saida.write(String.valueOf(i));
			saida.write(",");
		}
		saida.write(String.valueOf(params.size()));
		saida.write(")\r\n");
		
		String nomeArquivoDebug = Diretorios.getNomeArquivoResumoCSVDebug(experimento);

		for (DadosDebugCabecalho debug : debugs) {
			
			boolean incluiMinMax = contemMultiplasSolucoesPorIteracao(debug);
			
			//if (!debug.getSeed().equals("0.00")) {
			//	continue;
			//}
		
			saida.write("#################################\r\n");
			saida.write("# Gráficos\r\n");
			saida.write("#################################\r\n");
			saida.write("tabelaEvolutionMQ<-read.table(\"" + nomeArquivoDebug + "\",h=T,sep=\";\",dec=\"" + DECIMAL + "\") \r\n");
			saida.write("conf<-" + debug.getConfiguracao() + " \r\n");
			saida.write("proj<-\"" + debug.getNomeInstancia() + "\" \r\n");
			saida.write("seed<-" + debug.getSeed().replace(",",".") + " \r\n");
			saida.write("\r\n");
			saida.write("dadosDebug  <- subset(tabelaEvolutionMQ,tabelaEvolutionMQ$SEED==seed & tabelaEvolutionMQ$CONFIGURACAO==conf & tabelaEvolutionMQ$INSTANCIA==proj)\r\n");
			saida.write("geracoes    <- as.numeric(names(table(subset(tabelaEvolutionMQ$GERACAO,tabelaEvolutionMQ$SEED==seed & tabelaEvolutionMQ$CONFIGURACAO==conf & tabelaEvolutionMQ$INSTANCIA==proj))))\r\n");
			saida.write("valoresMed <- NULL\r\n");
			saida.write("iteracoes <- NULL\r\n");
			if (incluiMinMax) {
				saida.write("valoresMin <- NULL\r\n");
				saida.write("valoresMax <- NULL\r\n");
			}
			saida.write("j <- 1\r\n");
			saida.write("for ( ger in geracoes ) {\r\n");
			//min,max,mean -> comandos para calcular a partir do subset 
			saida.write("	valoresMed[j] <- subset(dadosDebug$MEDIA,dadosDebug$GERACAO==ger)\r\n");
			if (incluiMinMax) {
				saida.write("	valoresMin[j] <- subset(dadosDebug$MQN  ,dadosDebug$GERACAO==ger)\r\n");
				saida.write("	valoresMax[j] <- subset(dadosDebug$MQ1  ,dadosDebug$GERACAO==ger)\r\n");
			}
			saida.write("	iteracoes[j] <- j\r\n");
			saida.write("	j <- j + 1\r\n");
			saida.write("}\r\n");
			//saida.write("valoresDebug <- dadosDebug$VALOR\r\n");
			saida.write("xrange<-range(0,j)\r\n");
			if (incluiMinMax) {
				saida.write("yrange<-range(0,max(valoresMax))\r\n");
			}
			else {
				saida.write("yrange<-range(0,max(valoresMed))\r\n");
			}
			saida.write("\r\n");
			saida.write("nome<-paste(proj,conf,seed) \r\n");
			saida.write("bmp(filename=paste(nome,\".bmp\"))\r\n");
			saida.write("plot(xrange, yrange, type=\"n\", xlab=\"Iteracoes\", ylab=\"MQ\")\r\n");
			saida.write("title(proj)\r\n");
			saida.write("\r\n");
			saida.write("lines(iteracoes, valoresMed  , type=\"l\", lwd=1.5, lty=1, col=\"black\", pch=18)\r\n");
			if (incluiMinMax) {
				saida.write("lines(iteracoes, valoresMin  , type=\"l\", lwd=1.5, lty=2, col=\"black\", pch=18)\r\n");
				saida.write("lines(iteracoes, valoresMax  , type=\"l\", lwd=1.5, lty=4, col=\"black\", pch=18)\r\n");
			}
			//saida.write("lines(geracoes, valoresDebug, type=\"l\", lwd=2.0, lty=1, col=\"black\", pch=18)\r\n");
			saida.write("dev.off()\r\n");
		}
		
		saida.close();
		
	}
	*/

	private static void geraScriptGrafico(ExperimentoModel experimento, FileWriter saida, List<DadosDebugCabecalho> debugs, boolean eixoXPorAvaliacao, int parametroInicial, int parametroFinal) throws IOException
	{
		// List<Parametro> params = experimento.getParametros();
		
		String DECIMAL = "."; // como o decimal foi gravado no arquivo de entrada
		
		String dataAtual = new Date().toString();
		saida.write("# Script gerado em " + dataAtual + ".\r\n");
		
		// configura o diretorio de trabalho
		String diretorioSaidaTratado = Diretorios.getDiretorioTrataoParaR(Diretorios.diretorioStatistics);
		saida.write("setwd(\"");
		saida.write(diretorioSaidaTratado);
		saida.write("\") \r\n");
		
		saida.write("configuracoes<-c(");
		for ( int i = parametroInicial; i < parametroFinal; i++) {
			saida.write(String.valueOf(i));
			saida.write(",");
		}
		saida.write(String.valueOf(parametroFinal));
		saida.write(")\r\n");
		
		String nomeArquivoDebug = Diretorios.getNomeArquivoResumoCSVDebug(experimento);
		
		String campoEixoX = "GERACAO";
		String nomeEixoX = "Iterações";
		if (eixoXPorAvaliacao) {
			campoEixoX = "EVALUATION";
			nomeEixoX = "Avaliações";
		}

		for (DadosDebugCabecalho debug : debugs) {
			
			boolean incluiMinMax = contemMultiplasSolucoesPorIteracao(debug);
			
			//if (!debug.getSeed().equals("0.00")) {
			//	continue;
			//}
		
			saida.write("#################################\r\n");
			saida.write("# Gráficos\r\n");
			saida.write("#################################\r\n");
			saida.write("tabelaEvolutionMQ<-read.table(\"" + nomeArquivoDebug + "\",h=T,sep=\";\",dec=\"" + DECIMAL + "\") \r\n");
			saida.write("conf<-" + debug.getConfiguracao() + " \r\n");
			saida.write("proj<-\"" + debug.getNomeInstancia() + "\" \r\n");
			saida.write("seed<-" + debug.getSeed().replace(",",".") + " \r\n");
			saida.write("\r\n");
			saida.write("dadosDebug  <- subset(tabelaEvolutionMQ,tabelaEvolutionMQ$SEED==seed & tabelaEvolutionMQ$CONFIGURACAO==conf & tabelaEvolutionMQ$INSTANCIA==proj)\r\n");
			saida.write("geracoes <- as.numeric(names(table(subset(tabelaEvolutionMQ$" + campoEixoX + ",tabelaEvolutionMQ$SEED==seed & tabelaEvolutionMQ$CONFIGURACAO==conf & tabelaEvolutionMQ$INSTANCIA==proj))))\r\n");
			saida.write("valoresMed <- NULL\r\n");
			saida.write("iteracoes <- NULL\r\n");
			if (incluiMinMax) {
				saida.write("valoresMin <- NULL\r\n");
				saida.write("valoresMax <- NULL\r\n");
			}
			saida.write("j <- 1\r\n");
			saida.write("for ( ger in geracoes ) {\r\n");
			//min,max,mean -> comandos para calcular a partir do subset 
			saida.write("	valoresMed[j] <- subset(dadosDebug$MEDIA,dadosDebug$" + campoEixoX + "==ger)\r\n");
			if (incluiMinMax) {
				saida.write("	valoresMin[j] <- subset(dadosDebug$MQN  ,dadosDebug$" + campoEixoX + "==ger)\r\n");
				saida.write("	valoresMax[j] <- subset(dadosDebug$MQ1  ,dadosDebug$" + campoEixoX + "==ger)\r\n");
			}
			//saida.write("	iteracoes[j] <- j\r\n");
			saida.write("	iteracoes[j] <- ger\r\n");
			saida.write("	j <- j + 1\r\n");
			saida.write("}\r\n");
			//saida.write("valoresDebug <- dadosDebug$VALOR\r\n");
			saida.write("xrange<-range(0,max(iteracoes))\r\n");
			if (incluiMinMax) {
				saida.write("yrange<-range(0,max(valoresMax))\r\n");
			}
			else {
				saida.write("yrange<-range(0,max(valoresMed))\r\n");
			}
			saida.write("\r\n");
			saida.write("nome<-paste(proj,conf,seed) \r\n");
			saida.write("bmp(filename=paste(nome,\".bmp\"))\r\n");
			saida.write("plot(xrange, yrange, type=\"n\", xlab=\"" + nomeEixoX + "\", ylab=\"MQ\")\r\n");
			saida.write("title(proj)\r\n");
			saida.write("\r\n");
			saida.write("lines(iteracoes, valoresMed  , type=\"l\", lwd=1.5, lty=1, col=\"black\", pch=18)\r\n");
			if (incluiMinMax) {
				saida.write("lines(iteracoes, valoresMin  , type=\"l\", lwd=1.5, lty=2, col=\"black\", pch=18)\r\n");
				saida.write("lines(iteracoes, valoresMax  , type=\"l\", lwd=1.5, lty=4, col=\"black\", pch=18)\r\n");
			}
			//saida.write("lines(geracoes, valoresDebug, type=\"l\", lwd=2.0, lty=1, col=\"black\", pch=18)\r\n");
			saida.write("dev.off()\r\n");
		}
		
		saida.close();
		
	}

	public static void executa(String nomeArquivoEntrada, List<Parametro> params, ArrayList<DadosAlgoritmo> algoritmos, ArrayList<DadosInstancia> instancias, String diretorioSaida,  String nomeArquivoSaida, String nomeArquivoSaidaWilcox, String nomeArquivoSaidaDebug, int parametroInicial, int parametroFinal, String combinacoesWilcox, boolean geraBoxPlot) 
	{
		try {
			FileWriter saida = abreArquivoSaida(diretorioSaida, nomeArquivoSaida, false);
			geraScriptRMedias(nomeArquivoEntrada, params, algoritmos, instancias, diretorioSaida, saida, parametroInicial, parametroFinal);
			saida.close();
			
			if (nomeArquivoSaidaWilcox!=null) {
				FileWriter saidaWilcox = abreArquivoSaida(diretorioSaida, nomeArquivoSaidaWilcox, false);
				if (combinacoesWilcox != null) {
					
					boolean combinaTodas = combinacoesWilcox.indexOf(";") < 0;
					if (combinaTodas) {
						String[] pares = combinacoesWilcox.split(",");
						for (String par1 : pares) {
							for (String par2 : pares) {
								if (!par1.equals(par2)) {
									String configuracao1 = par1;
									String configuracao2 = par2;
									geraScriptRWilcoxEffectSize(configuracao1, configuracao2, nomeArquivoEntrada, params, algoritmos, instancias, diretorioSaida, saidaWilcox, parametroInicial, parametroFinal, geraBoxPlot);
								}
							}
						}
					}
					else {
						String[] itens = combinacoesWilcox.split(";");
						for (String item : itens) {
							String[] pares = item.split(",");
							for (String par1 : pares) {
								for (String par2 : pares) {
									if (!par1.equals(par2)) {
										String configuracao1 = par1;
										String configuracao2 = par2;
										geraScriptRWilcoxEffectSize(configuracao1, configuracao2, nomeArquivoEntrada, params, algoritmos, instancias, diretorioSaida, saidaWilcox, parametroInicial, parametroFinal, geraBoxPlot);
									}
								}
							}
						}
					}
				}
				else {
					String configuracao1 = "2";
					String configuracao2 = "1";
					geraScriptRWilcoxEffectSize(configuracao1, configuracao2, nomeArquivoEntrada, params, algoritmos, instancias, diretorioSaida, saidaWilcox, parametroInicial, parametroFinal, geraBoxPlot);					
				}
				saidaWilcox.close();
			}

		}
		catch (IOException eIO) {
			System.out.println("Erro na leitura dos arquivos. msg=" + eIO.getMessage());
			System.exit(1);
		}
	}

	public static void geraScriptDebug(TipoExibicao tipoExibicao, ExperimentoModel experimento, List<DadosDebugCabecalho> debugs, boolean graficoPorAvaliacao, int parametroInicial, int parametroFinal) 
	{
		if (!tipoExibicao.isDebug()) {
			return;
		}
		
		String nomeArquivoSaidaDebug = Diretorios.getNomeArquivoSaidaScriptDebug(experimento);
		try {
			FileWriter saidaDebug = abreArquivoSaida(Diretorios.diretorioStatistics, nomeArquivoSaidaDebug, false);
			geraScriptGrafico(experimento, saidaDebug, debugs, graficoPorAvaliacao, parametroInicial, parametroFinal);
			saidaDebug.close();
		}
		catch (IOException eIO) {
			System.out.println("Erro na leitura dos arquivos. msg=" + eIO.getMessage());
			System.exit(1);
		}
	}

}

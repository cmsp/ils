package unirio.sc.statistics;

import java.io.IOException;

public class DadosDebug 
{
	private final static String SEP = ";";
	
	private DadosDebugCabecalho cabecalhoDebug = null;
	private String geracao = null;
	private String mqMedia = null;
	private String mq1 = null;
	private String mqN = null;
	private String evaluation = null;
	
	public void iniciaNovoAlgoritmoInstanciaSeed(int i) {
		this.cabecalhoDebug = new DadosDebugCabecalho(i);
		this.geracao = null;
		this.mqMedia = null;
		this.mq1 = null;
		this.mqN = null;
		this.evaluation = null;
	}

	public void parseInstancia(String linha)
	{
		String[] dados = linha.split("=");
		this.cabecalhoDebug.setNomeInstancia(dados[1]);
	}
	
	public void parseParametros(String linha)
	{
		int i = linha.indexOf(" ");
		this.cabecalhoDebug.setParametros(linha.substring(i));
	}
	
	public void parseAlgoritmo(String linha)
	{
		String[] dados = linha.split("=");
		this.cabecalhoDebug.setNomeAlgoritmo(dados[1]);
	}
	
	public void parseSeed(String linha)
	{
		String[] dados = linha.split("=");
		this.cabecalhoDebug.setSeed(trataValor(dados[1]));
	}
	
	public String trataValor(String valor)
	{
		return valor.replace(",",".").replace("-", "");
	}

	public void parseGeracao(String linha)
	{
		String[] dados = linha.split(";");
		int i = 0;
		this.geracao = dados[i++];
		this.mqMedia = trataValor(dados[i++]);
		if (dados.length>3){
			this.mq1 = trataValor(dados[i++]);
			this.mqN = trataValor(dados[i++]);
		}
		this.evaluation = trataValor(dados[i++]);
	}

	public String getLinhaCSV() throws IOException {
		StringBuffer sb = new StringBuffer();
		if (this.cabecalhoDebug == null)
		{
			throw new IOException("Cabeçalho do Debug não foi impresso");
			// return ";;;;;;;;";
		}
		//sb.append(this.cabecalhoDebug.getNomeAlgoritmo());
		//sb.append(SEP);
		sb.append(this.cabecalhoDebug.getNomeInstancia());
		sb.append(SEP);
		//sb.append(this.cabecalhoDebug.getParametros());
		//sb.append(SEP);
		sb.append(this.cabecalhoDebug.getSeed());
		sb.append(SEP);
		sb.append(this.geracao);
		sb.append(SEP);
		sb.append(this.mqMedia);
		if (this.mq1 != null) {
			sb.append(SEP);
			sb.append(this.mq1);
			sb.append(SEP);
			sb.append(this.mqN);
		}
		else {
			sb.append(SEP);
			sb.append(SEP);
		}
		sb.append(SEP);
		sb.append(this.evaluation);
		sb.append("\r\n");	
		return sb.toString();
	}
	
	public DadosDebugCabecalho getDadosDebugCabecalho() {
		return cabecalhoDebug;
	}
	
}



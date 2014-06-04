package unirio.sc.statistics;

public enum TipoLinhaDebug {
	
	BRANCO("",0),
	ALGORITMO("ALGORITMO",9),
	INSTANCIA("INSTANCIA",9),
	PARAMETROS("PARAMETROS",10),
	SEED("SEED",4),
	GERACAO("GERACAO",7);
	
	private String prefixo;
	private int tamanho;
	
	private TipoLinhaDebug(String prefixo, int tamanho) {
		this.prefixo = prefixo;
		this.tamanho = tamanho;
	}
	
	public String getPrefixo() {
		return prefixo;
	}
	
	public int getTamanho() {
		return tamanho;
	}
	
	public static boolean testaTipo(String linha, TipoLinhaDebug tipoLinha) {
		int tamanho = tipoLinha.tamanho;
		String prefixo = tipoLinha.prefixo;
		if (linha.length()<tamanho) return false;
		if (linha.substring(0,tamanho).equals(prefixo)) 
			return true;
		return false;
	}
	
	public static TipoLinhaDebug getTipo(String linha) {
		if (linha==null) return TipoLinhaDebug.BRANCO;
		if (linha.trim().length()==0) return TipoLinhaDebug.BRANCO;
		if (testaTipo(linha,TipoLinhaDebug.ALGORITMO)) return TipoLinhaDebug.ALGORITMO;
		if (testaTipo(linha,TipoLinhaDebug.INSTANCIA)) return TipoLinhaDebug.INSTANCIA;
		if (testaTipo(linha,TipoLinhaDebug.PARAMETROS)) return TipoLinhaDebug.PARAMETROS;
		if (testaTipo(linha,TipoLinhaDebug.SEED)) return TipoLinhaDebug.SEED;
		return TipoLinhaDebug.GERACAO;
	}
	
}

package unirio.sc.statistics;

public enum TipoLinhaResultado {
	
	SEPARADOR("----------------",16),
	CICLO("Cycle",5),
	BRANCO("",0),
	SOLUCAO("Solução fitness",15),
	MEDIA_FITNESS("Media fitness",13),
	MEDIA_EXECUCAO("Media execucao",14),
	MELHOR_FITNESS("Melhor fitness",14),
	INSTANCIA("INSTÂNCIA",9),
	ALGORITMO("ALGORITMO",9),
	PARAMETROS("PARAMETROS",10),
	PROBLEMA("Problema",8),
	RESULTADO("Resultado",9),
	SOLUCAO_MELHOR("Solução",7);
	
	private String prefixo;
	private int tamanho;
	
	private TipoLinhaResultado(String prefixo, int tamanho) {
		this.prefixo = prefixo;
		this.tamanho = tamanho;
	}
	
	public String getPrefixo() {
		return prefixo;
	}
	
	public int getTamanho() {
		return tamanho;
	}
	
	public static boolean testaTipo(String linha, TipoLinhaResultado tipoLinha) {
		int tamanho = tipoLinha.tamanho;
		String prefixo = tipoLinha.prefixo;
		if (linha.length()<tamanho) return false;
		if (linha.substring(0,tamanho).equals(prefixo)) 
			return true;
		return false;
	}
	
	public static TipoLinhaResultado getTipo(String linha) {
		if (linha==null) return TipoLinhaResultado.BRANCO;
		if (linha.trim().length()==0) return TipoLinhaResultado.BRANCO;
		if (testaTipo(linha,TipoLinhaResultado.CICLO)) return TipoLinhaResultado.CICLO;
		if (testaTipo(linha,TipoLinhaResultado.SEPARADOR)) return TipoLinhaResultado.SEPARADOR;
		if (testaTipo(linha,TipoLinhaResultado.INSTANCIA)) return TipoLinhaResultado.INSTANCIA;
		if (testaTipo(linha,TipoLinhaResultado.MEDIA_FITNESS)) return TipoLinhaResultado.MEDIA_FITNESS;
		if (testaTipo(linha,TipoLinhaResultado.MEDIA_EXECUCAO)) return TipoLinhaResultado.MEDIA_EXECUCAO;
		if (testaTipo(linha,TipoLinhaResultado.MELHOR_FITNESS)) return TipoLinhaResultado.MELHOR_FITNESS;
		if (testaTipo(linha,TipoLinhaResultado.ALGORITMO)) return TipoLinhaResultado.ALGORITMO;
		if (testaTipo(linha,TipoLinhaResultado.PARAMETROS)) return TipoLinhaResultado.PARAMETROS;
		if (testaTipo(linha,TipoLinhaResultado.RESULTADO)) return TipoLinhaResultado.RESULTADO;
		if (testaTipo(linha,TipoLinhaResultado.PROBLEMA)) return TipoLinhaResultado.PROBLEMA;
		if (testaTipo(linha,TipoLinhaResultado.SOLUCAO_MELHOR)) return TipoLinhaResultado.SOLUCAO_MELHOR;
		return null;
	}
	
}

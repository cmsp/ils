package unirio.sc.genetico;


public class Descarte {
	
	private TipoDescarte tipoDescarte;
	private int tamanhoDescarte;
	private int tamanhoSelecao;
	private int tamanhoMovimentacoes;
	
	public Descarte(TipoDescarte tipoDescarte, int tamanhoDescarte, int tamanhoSelecao, int tamanhoMovimentacoes) {
		this.tipoDescarte = tipoDescarte;
		this.tamanhoDescarte = tamanhoDescarte;
		this.tamanhoSelecao = tamanhoSelecao;
		this.tamanhoMovimentacoes = tamanhoMovimentacoes;
	}
	
	public TipoDescarte getTipoDescarte() {
		return tipoDescarte;
	}
	
	public int getTamanhoDescarte() {
		return tamanhoDescarte;
	}
	
	public int getTamanhoMovimentacoes() {
		return tamanhoMovimentacoes;
	}
	
	public int getTamanhoSelecao() {
		return tamanhoSelecao;
	}
		
}
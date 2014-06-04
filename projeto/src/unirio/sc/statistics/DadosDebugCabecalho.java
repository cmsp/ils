package unirio.sc.statistics;

public class DadosDebugCabecalho {
	
	private int    configuracao = -1;
	private String parametros = null;
	private String nomeAlgoritmo = null;
	private String nomeInstancia = null;
	private String seed = null;
	
	public DadosDebugCabecalho(int configuracao) {
		this.configuracao = configuracao;
	}
	
	public String getParametros() {
		return parametros;
	}
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
	public void setNomeAlgoritmo(String nomeAlgoritmo) {
		this.nomeAlgoritmo = nomeAlgoritmo;
	}
	public String getNomeAlgoritmo() {
		return nomeAlgoritmo;
	}
	public void setNomeInstancia(String nomeInstancia) {
		this.nomeInstancia = nomeInstancia;
	}
	public String getNomeInstancia() {
		return nomeInstancia;
	}
	public void setSeed(String seed) {
		this.seed = seed;
	}
	public String getSeed() {
		return seed;
	}
	public int getConfiguracao() {
		return configuracao;
	}
	
	public String getId() {
		return 
				this.configuracao
		+ "," + this.parametros
		+ "," + this.nomeAlgoritmo
		+ "," + this.nomeInstancia
		+ "," + this.seed;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (((DadosDebugCabecalho)obj).getId().equalsIgnoreCase(this.getId()))
			return true;
		return false;
	}
	
}
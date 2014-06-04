package unirio.sc.statistics;

public class DadosAlgoritmo {
	
	private String parametros = null;
	private String nome = null;
	
	public String getParametros() {
		return parametros;
	}
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (((DadosAlgoritmo)obj).getNome().equalsIgnoreCase(this.nome))
			return true;
		return false;
	}
	
}

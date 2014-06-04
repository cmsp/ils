package unirio.sc.statistics;

public class DadosInstancia {
	
	private String nome;
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

	@Override
	public boolean equals(Object obj) {
		if (((DadosInstancia)obj).getNome().equalsIgnoreCase(this.nome))
			return true;
		return false;
	}
}

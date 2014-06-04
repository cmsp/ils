package unirio.sc.core;

public enum TipoExibicao {
	DEFAULT,
	SIMPLIFICADO;
	
	private boolean debug = false;
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public boolean isDebug() {
		return this.debug;
	}
	
	public boolean isSimplificado() {
		if (this == SIMPLIFICADO) return true;
		return false;
	}
}

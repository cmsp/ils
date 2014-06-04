package unirio.sc.statistics;

public class DadosExecucao 
{
	private final static String SEP = ";";
	
	private DadosAlgoritmo algoritmo = null;
	private DadosInstancia instancia = null;
	private String ciclo = null;
	private String execucao = null;
	private String fitness = null;
	
	public void iniciaNovaInstancia() {
		this.algoritmo = new DadosAlgoritmo();
		this.instancia = new DadosInstancia();
		this.ciclo = null;
		this.execucao = null;
		this.fitness = null;
	}
	
	public void iniciaNovoCiclo() {
		this.ciclo = null;
		this.execucao = null;
		this.fitness = null;
	}

	public void parseInstancia(String linha)
	{
		String[] dados = linha.split("=");
		this.instancia.setNome(dados[1]);
	}
	
	public void parseParametros(String linha)
	{
		int i = linha.indexOf(" ");
		this.algoritmo.setParametros(linha.substring(i));
	}
	
	public void parseAlgoritmo(String linha)
	{
		String[] dados = linha.split("=");
		this.algoritmo.setNome(dados[1]);
	}
	
	public void parseCiclo(String linha)
	{
		String[] dados = linha.split(";");
		this.ciclo = dados[0].split("#")[1];
		this.execucao = dados[1];
		this.fitness = dados[2];
	}
	
	
	public String getLinhaCSV() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.algoritmo.getNome());
		sb.append(SEP);
		sb.append(this.instancia.getNome());
		sb.append(SEP);
		sb.append(this.algoritmo.getParametros());
		sb.append(SEP);
		sb.append(this.ciclo);
		sb.append(SEP);
		sb.append(this.execucao);
		sb.append(SEP);
		sb.append(this.fitness);
		sb.append("\r\n");	
		return sb.toString();
	}
	
	public DadosAlgoritmo getAlgoritmo() {
		return algoritmo;
	}
	
	public DadosInstancia getInstancia() {
		return instancia;
	}
	
	public String getParametros() {
		return algoritmo.getParametros();
	}
	
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	
	public void setExecucao(String execucao) {
		this.execucao = execucao;
	}
	
	public void setFitness(String fitness) {
		this.fitness = fitness;
	}
	
	public void setAlgoritmo(DadosAlgoritmo algoritmo) {
		this.algoritmo = algoritmo;
	}
	
	public void setInstancia(DadosInstancia instancia) {
		this.instancia = instancia;
	}
	
}
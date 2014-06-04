package unirio.sc.core;

import java.util.ArrayList;
import java.util.List;

public abstract class ExperimentoModel {
	
	public ArrayList<Problema> getProblemas() {
		try {
		return LeitorProblema.loadInstances(getInstancias());
		}
		catch (Exception e) {
			System.out.println("ERRO NA LEITURA DAS INSTANCIAS. msg=" + e.getLocalizedMessage() + "]");
			return null;
		}
	}

	public abstract List<Parametro> getParametros();
	
	public abstract String[] getInstancias();
	
	public abstract String getIdExperimento();
	
	// abre os arquivos de saída para todos os parâmetros do experimento
	public Exibicao[] iniciaExibicoes()
	{
		List<String> arquivos = Diretorios.getNomesArquivosSaida(getParametros());
		Exibicao[] exibicoes = new Exibicao[arquivos.size()];
		for (int i = 0; i < arquivos.size(); i++) {
			String nomeArquivo = arquivos.get(i);
			exibicoes[i] = new Exibicao(Diretorios.diretorioResultados + nomeArquivo);
			exibicoes[i].addListener(System.out, "system.out");
		}
		return exibicoes;
	}
	
	public void setMultiplicadorMaxEvaluation(List<Parametro> params, int multiplicadorEvaluation) {
		for (Parametro param : params) {
			param.setMultiplicadorEvaluation(multiplicadorEvaluation);
		}
	}
}

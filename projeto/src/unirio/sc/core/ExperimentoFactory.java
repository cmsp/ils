package unirio.sc.core;

import unirio.sc.busca.AlgoritmoHC;
import unirio.sc.construtivo.AlgoritmoCNM;
import unirio.sc.construtivoLista.AlgoritmoCNMLL;
import unirio.sc.genetico.classico.AlgoritmoGeneticoGA;
import unirio.sc.genetico.falkenauer.AlgoritmoGeneticoGF;
import unirio.sc.genetico.falkenauer.AlgoritmoGeneticoGFNormalizado;
import unirio.sc.metaheuristica.AlgoritmoILS;


public class ExperimentoFactory {
	
	private static AlgoritmoAbstract getAlgoritmo(
		Problema problema, 
		Exibicao exibicao,
		Parametro param) 
	throws Exception
	{
		int tamanho = problema.getTamanho();
		
		if (param.getEvaluationMax()==null)
			throw new Exception("Quantidade de avaliações não definida.");
				
		TipoAlgoritmo tipoAlgoritmo = param.getTipoAlgoritmo();

		AlgoritmoAbstract algoritmo = null;
		switch (tipoAlgoritmo) {
			case GENETICO_GNE:
			case GENETICO_FALKENAUER_NORMALIZADO:
			case GENETICO_FALKENAUER:
				if (param.getMaxProporcaoGrupos()==null)
					// deprecated: param.setMaxProporcaoGrupos( 2 );
					throw new Exception("Quantidade de proporção de grupos não definida.");
				
				boolean bCrossoverPadrao = false;
				if (param.getProbabilidadeCrossover() == null){
					// ERRO: param.setProbabilidadeCrossover( (tamanho < 100) ? 0.8 : 1.0 );
					param.setProbabilidadeCrossover( ( ( 10 * tamanho ) < 100) ? 0.8 : 1.0 );
					bCrossoverPadrao = true;
				}
				boolean bMutacaoPadrao = false;
				if (param.getProbabilidadeMutacao() == null) {
					param.setProbabilidadeMutacao( 0.004 * Math.log(tamanho) / Math.log(2) );
					bMutacaoPadrao = true;
				}
				boolean bPopulacaoPadrao = false;
				if (param.getTamanhoPopulacao() == null){
					param.setTamanhoPopulacao( tamanho * 10 );
					bPopulacaoPadrao = true;
				}
				
				if (param.getTipoCrossover().isFalkenauer()) {
					if (param.getTipoAlgoritmo() == null | param.getTipoAlgoritmo() == TipoAlgoritmo.GENETICO_FALKENAUER) {
						algoritmo = new AlgoritmoGeneticoGF(
							problema,
							exibicao,
							param
						);
					} else {
						algoritmo = new AlgoritmoGeneticoGFNormalizado(
							problema,
							exibicao,
							param
						);
					}
				}
				else {
					algoritmo = new AlgoritmoGeneticoGA(
						problema,
						exibicao,
						param
					);
				}
				if (bCrossoverPadrao)
					param.setProbabilidadeCrossover(null);
				if (bMutacaoPadrao)
					param.setProbabilidadeMutacao(null);
				if (bPopulacaoPadrao)
					param.setTamanhoPopulacao(null);
				break;
			case GULOSO_VETOR:
				algoritmo = new AlgoritmoCNM(
						problema,
						exibicao,
						param);
				break;
			case GULOSO_HASHMAP:
				algoritmo = new AlgoritmoCNMLL(
						problema,
						exibicao,
						param);
				break;
			case HILL_CLIMBING:
				algoritmo = new AlgoritmoHC(
						problema,
						exibicao,
						param
					);
				break;
			case ITERATED_LOCAL_SEARCH:
				algoritmo = new AlgoritmoILS(
						problema,
						exibicao,
						param
					);
				break;
			default:
				break;
			
		}
		if(algoritmo==null) {
			System.out.println("Algoritmo não encontrado=" + tipoAlgoritmo);
			return null;
		}
		
		return algoritmo;
	}
	
	public static Experimento executa(
		Problema problema,
		Exibicao exibicao,
		Parametro param
	) 
	throws Exception
	{		
		AlgoritmoAbstract algoritmo = getAlgoritmo(problema, exibicao, param);
		if (algoritmo == null) 
			throw new Exception("Algoritmo não executado:" + param.getTipoAlgoritmo());
		Experimento experimento = new Experimento(param.getInfoParametros(), algoritmo);
		
		int nCicloInicial = param.getnCicloInicial();
		int nCiclos = param.getnCiclos();
		if (param.temCiclosEspecificos()) {
			String nomeInstancia = problema.getFileName();
			int i = nomeInstancia.indexOf("\\");
			while (i>=0) {
				nomeInstancia = nomeInstancia.substring(i+1);
				i = nomeInstancia.indexOf("\\");
			}
			
			nCicloInicial = param.getCicloDaInstancia(nomeInstancia);
			nCiclos = nCicloInicial + 1;
		}
		
		experimento.runWithMedia(nCicloInicial, nCiclos, param.getComSemente());
		return experimento;
	}
	
}

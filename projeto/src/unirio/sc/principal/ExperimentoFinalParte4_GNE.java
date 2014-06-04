package unirio.sc.principal;

import java.util.List;

import unirio.sc.core.ExperimentoModel;
import unirio.sc.core.Parametro;
import unirio.sc.core.TipoExibicao;

public class ExperimentoFinalParte4_GNE extends ExperimentoFinalParte3_GNE {
	
	@Override
	public String getIdExperimento() {
		return "Final_Parte4_GNE";
	}
	
	public String[] getInstancias()
	{
		return new String[] {
			"mtunis",
			"ispell",
			"rcs",
			"bison",
			"grappa",
			//"bunch",
			"incl",

			/*
			"acqCIGNA",
			"bison",
			"boxer",
			"bunch",
			"bunch2",
			"cia",
			"cia++",
			"ciald",
			"compiler",
			"graph10up49",
			"graph10up193",
			"grappa",
			"hw",
			"incl",
			"ispell",
			"lab4",
			"islayout",
			"lucent",
			"Modulizer",
			"mtunis",
			"nos",
			"rcs",
			"small",
			"spdb",
			"star",
			"swing",
			*/
		};
	}
	
	public List<Parametro> getParametros() 
	{
		List<Parametro> params = super.getParametros();
		
		// PRADITWONG usa 2000 n2 avaliacoes ou 200N geracoes com 10*N populacao
		setMultiplicadorMaxEvaluation(params, 2000);
		
		return params;
	}

	public static void main(String[] args) throws Exception {
		int i = 0;
		List<Parametro> params = new ExperimentoFinalParte4_GNE().getParametros();
		System.out.println("Total parametros=" + params.size());
		for (Parametro param: params) {
			System.out.println(++i + "=" + param.getInfoParametros());	
		}
		
		//Configura a exibição conforme algoritmo e problema
		TipoExibicao tipoExibicao = TipoExibicao.DEFAULT;
		tipoExibicao.setDebug(false);
		
		ExperimentoModel experimento = new ExperimentoFinalParte4_GNE();
		Principal.executa(tipoExibicao, experimento);
	}
}

package unirio.sc.principal;

import java.util.List;

import unirio.sc.core.ExperimentoModel;
import unirio.sc.core.Parametro;
import unirio.sc.core.TipoExibicao;

public class ExperimentoFinalParte4_ILS extends ExperimentoFinalParte3_ILS {
	
	@Override
	public String getIdExperimento() {
		return "Final_Parte4_ILS";
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
		
		// inicialConstrutivo = true
		// memoria = 0
		Parametro paramILS = params.get(0);
		
		// inicialConstrutivo = false
		// memoria = 0
		Parametro paramILSVar1 = new Parametro(paramILS);
		paramILSVar1.setInicialConstrutivo(false);
		
		// inicialConstrutivo = true
		// memoria = 5
		Parametro paramILSVar2 = new Parametro(paramILS);
		paramILSVar2.setMaxIteracoesSemMelhoria(5);
		
		// inicialConstrutivo = false
		// memoria = 5
		Parametro paramILSVar3 = new Parametro(paramILS);
		paramILSVar3.setInicialConstrutivo(false);
		paramILSVar3.setMaxIteracoesSemMelhoria(5);
		
		params.add(paramILSVar1);
		params.add(paramILSVar2);
		params.add(paramILSVar3);
		
		// PRADITWONG usa 2000 n2 avaliacoes ou 200N geracoes com 10*N populacao
		setMultiplicadorMaxEvaluation(params, 2000);

		return params;
	}

	public static void main(String[] args) throws Exception {
		int i = 0;
		List<Parametro> params = new ExperimentoFinalParte4_ILS().getParametros();
		System.out.println("Total parametros=" + params.size());
		for (Parametro param: params) {
			System.out.println(++i + "=" + param.getInfoParametros());	
		}
		
		//Configura a exibição conforme algoritmo e problema
		TipoExibicao tipoExibicao = TipoExibicao.DEFAULT;
		tipoExibicao.setDebug(false);
		
		ExperimentoModel experimento = new ExperimentoFinalParte4_ILS();
		Principal.executa(tipoExibicao, experimento);
	}
}

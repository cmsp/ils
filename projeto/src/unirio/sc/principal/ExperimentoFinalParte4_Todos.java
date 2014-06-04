package unirio.sc.principal;

import java.util.ArrayList;
import java.util.List;

import unirio.sc.core.ExperimentoModel;
import unirio.sc.core.Parametro;
import unirio.sc.core.TipoExibicao;

public class ExperimentoFinalParte4_Todos extends ExperimentoModel {
	
	@Override
	public String getIdExperimento() {
		return "Final_Parte4_Todos";
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
		List<Parametro> params = new ArrayList<Parametro>();
		
		params.addAll(new ExperimentoFinalParte4_CNM().getParametros());
		params.addAll(new ExperimentoFinalParte4_HC().getParametros());
		params.addAll(new ExperimentoFinalParte4_ILS().getParametros());
		params.addAll(new ExperimentoFinalParte4_GNE().getParametros());
		params.addAll(new ExperimentoFinalParte4_GGA().getParametros());

		return params;
	}

	public static void main(String[] args) throws Exception {
		int i = 0;
		List<Parametro> params = new ExperimentoFinalParte4_Todos().getParametros();
		System.out.println("Total parametros=" + params.size());
		for (Parametro param: params) {
			System.out.println(++i + "=" + param.getInfoParametros());	
		}
		
		//Configura a exibição conforme algoritmo e problema
		TipoExibicao tipoExibicao = TipoExibicao.DEFAULT;
		tipoExibicao.setDebug(false);
		
		ExperimentoModel experimento = new ExperimentoFinalParte4_Todos();
		Principal.executa(tipoExibicao, experimento);
	}
}

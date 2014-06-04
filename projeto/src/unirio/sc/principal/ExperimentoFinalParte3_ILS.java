package unirio.sc.principal;

import java.util.ArrayList;
import java.util.List;

import unirio.sc.core.ExperimentoModel;
import unirio.sc.core.Parametro;
import unirio.sc.core.TipoAlgoritmo;
import unirio.sc.core.TipoExibicao;

public class ExperimentoFinalParte3_ILS extends ExperimentoModel {
	
	@Override
	public String getIdExperimento() {
		return "Final_Parte3_ILS";
	}
	
	public String[] getInstancias()
	{
		return new String[] {
			"jstl-1.0.6 18C.odem",
			"jnanoxml 25C.odem",
			"joda-money 26C.odem",
			"jxls-reader 27C.odem",
			"seemp 31C.odem",
			
			"apache_zip 36C.odem",
			"udt-java 56C.odem",
			"javaocr 59C.odem",
			"servletapi-2.3 63C.odem",
			"pfcda_base 67C.odem",

			"forms-1.3.0 68C.odem",
			"jscatterplot 74C.odem",
			"jfluid-1.7.0 82C.odem",
			"jxls-core 83C.odem",
			"JPassword 96C.odem",
			
			"junit-3.8.1 100C.odem",
			"xmldom 119C.odem",
			"tinytim 134C.odem",
			"jkaryoscope 136C.odem",
			"gae_plugin_core 140C.odem",
			
			"javacc 154C.odem",
			"JavaGeom 172C.odem",
			"jdendogram 177C.odem",
			"xmlapi 184C.odem",
			"JMetal 190C.odem",
			
			"dom4j-1.5.2 195C.odem",
			"pdf_renderer 199C.odem",
			"Jung-graph 207C.odem",
			"jconsole-1.7.0 220C.odem",
			"Jung-visualization 221C.odem",
			
			"pfcda_swing 252C.odem",
			"jpassword 269C.odem",
			"jml 270C.odem",
			"notepad-full 299C.odem",
			"poormans_2.3 304C.odem",
			
			"log4j-1.2.16 308C.odem",
			"jtreeview 329C.odem",
			"jace 340C.odem",
			"javaws 378C.odem",
			"res_cobol 483C.odem",
		};
	}

	public void addParametros(List<Parametro> params, int nCiclos) 
	{	
		//Solução Inicial Gulosa	Memória	Sequencial	Movimentação	Trocas	Divisão	União	Explosão	Vitórias
		//ILS1		true			Não			Sim			Sim			-		-		-		-		7 -- igual ao 63
		//ILS63		true			Não			Não			Sim			-		-		-		-		7 -- igual ao 1
		//ILS3		true			Não			Sim			Sim			Sim		Sim		-		-		6
		//ILS16		true			Não			Sim			-			Sim		-		-		-		6 -- igual ao 78
		//ILS64		true			Não			Não			Sim			Sim		-		-		-		6
		//ILS78		true			Não			Não			-			Sim		-		-		-		6 -- igual ao 16
		//ILS2		true			Não			Sim			Sim			Sim		-		-		-		5
		//ILS65		true			Não			Não			Sim			Sim		Sim		-		-		5
		//ILS8		true			Não			Sim			Sim			Sim		-		-		Sim		4
		//ILS79		true			Não			Não			-			Sim		Sim		-		-		4	
		//ILS82		true			Não			Não			-			Sim		Sim		-		Sim		4
		
		
		boolean inicialConstrutivo = true;
		int maxIteracoesSemMelhoria = 0;
		Double[] percentuais = { null, null, null, null, null};
		boolean metodosPerturbacaoSimultaneos = true;
		Parametro paramILSPadrao = new Parametro(TipoAlgoritmo.ITERATED_LOCAL_SEARCH, nCiclos);
		paramILSPadrao.setILS(percentuais[0], percentuais[1], percentuais[2], percentuais[3], percentuais[4], maxIteracoesSemMelhoria, inicialConstrutivo, metodosPerturbacaoSimultaneos);

		Parametro paramILS1 = new Parametro(paramILSPadrao);
		paramILS1.setMetodosPerturbacaoSimultaneos(true);
		paramILS1.setPercentualMoves(10.0);
		params.add(paramILS1);

		/*
		Parametro paramILS3 = new Parametro(paramILSPadrao);
		paramILS3.setMetodosPerturbacaoSimultaneos(true);
		paramILS3.setPercentualMoves(10.0);
		paramILS3.setPercentualSwaps(10.0);
		paramILS3.setPercentualSplits(10.0);
		params.add(paramILS3);

		Parametro paramILS16 = new Parametro(paramILSPadrao);
		paramILS16.setMetodosPerturbacaoSimultaneos(true);
		paramILS16.setPercentualSwaps(10.0);
		params.add(paramILS16);
		
		Parametro paramILS64 = new Parametro(paramILSPadrao);
		paramILS64.setMetodosPerturbacaoSimultaneos(false);
		paramILS64.setPercentualMoves(10.0);
		paramILS64.setPercentualSwaps(10.0);
		params.add(paramILS64);
		
		Parametro paramILS2 = new Parametro(paramILSPadrao);
		paramILS2.setMetodosPerturbacaoSimultaneos(true);
		paramILS2.setPercentualMoves(10.0);
		paramILS2.setPercentualSwaps(10.0);
		params.add(paramILS2);
		
		Parametro paramILS65 = new Parametro(paramILSPadrao);
		paramILS65.setMetodosPerturbacaoSimultaneos(false);
		paramILS65.setPercentualMoves(10.0);
		paramILS65.setPercentualSwaps(10.0);
		paramILS65.setPercentualSplits(10.0);
		params.add(paramILS65);
		
		Parametro paramILS8 = new Parametro(paramILSPadrao);
		paramILS8.setMetodosPerturbacaoSimultaneos(true);
		paramILS8.setPercentualMoves(10.0);
		paramILS8.setPercentualSwaps(10.0);
		paramILS8.setPercentualExplosion(10.0);
		params.add(paramILS8);
		
		Parametro paramILS79 = new Parametro(paramILSPadrao);
		paramILS79.setMetodosPerturbacaoSimultaneos(false);
		paramILS79.setPercentualSwaps(10.0);
		paramILS79.setPercentualSplits(10.0);
		params.add(paramILS79);
		
		Parametro paramILS82 = new Parametro(paramILSPadrao);
		paramILS82.setMetodosPerturbacaoSimultaneos(false);
		paramILS82.setPercentualSwaps(10.0);
		paramILS82.setPercentualSplits(10.0);
		paramILS82.setPercentualExplosion(10.0);
		params.add(paramILS82);
		*/
	}

	public List<Parametro> getParametros() 
	{		
		List<Parametro> params = new ArrayList<Parametro>();
		
		int nCiclos = 30;
		
		addParametros(params, nCiclos);

		return params;
	}
	
	public static void main(String[] args) throws Exception {
		int i = 0;
		List<Parametro> params = new ExperimentoFinalParte3_ILS().getParametros();
		System.out.println("Total parametros=" + params.size());
		for (Parametro param: params) {
			System.out.println(++i + "=" + param.getInfoParametros());	
		}
		
		//Configura a exibição conforme algoritmo e problema
		TipoExibicao tipoExibicao = TipoExibicao.DEFAULT;
		tipoExibicao.setDebug(false);
		
		ExperimentoModel experimento = new ExperimentoFinalParte3_ILS();
		Principal.executa(tipoExibicao, experimento);
	}
}

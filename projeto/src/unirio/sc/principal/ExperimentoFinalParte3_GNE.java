package unirio.sc.principal;

import java.util.ArrayList;
import java.util.List;

import unirio.sc.core.ExperimentoModel;
import unirio.sc.core.Parametro;
import unirio.sc.core.TipoAlgoritmo;
import unirio.sc.core.TipoExibicao;
import unirio.sc.genetico.TipoCrossover;
import unirio.sc.genetico.TipoDescarte;
import unirio.sc.genetico.TipoElite;
import unirio.sc.genetico.TipoMutacao;
import unirio.sc.genetico.TipoSelection;

public class ExperimentoFinalParte3_GNE extends ExperimentoModel  {

	@Override
	public String getIdExperimento() {
		return "Final_Parte3_GNE";
	}

	public String[] getInstancias() {
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

	public List<Parametro> getParametros() 
	{		
		List<Parametro> params = new ArrayList<Parametro>();
		
		// parametros fixos
		int nCiclos = 30;
		Integer maxIteracoesSemMelhoria = 0;
		int maxProporcaoGrupos = 2;
		double percentualGruposPorCrossover = 10.00;

		TipoElite tipoElite = TipoElite.PERCENTUAL;
		TipoDescarte tipoDescarte = TipoDescarte.NENHUM;

		// Teste com introdução de diversificação da população
		double percentualSolucoesIguaisMaximo = -1.00;
		double percentualMovimentacoesMutacao = -1.00;
		double elite = 2.00;
		double percentualDescarte = 0.00;
		
		Parametro paramPadrao = new Parametro(TipoAlgoritmo.GENETICO_GNE, nCiclos);
		paramPadrao.setGenetico(
			maxIteracoesSemMelhoria,
			tipoElite,
			elite,
			tipoDescarte,
			percentualDescarte,
			percentualDescarte,
			percentualDescarte,
			TipoSelection.BINARY_TOURNAMENT, 
			0.00, 
			0.00, 
			TipoMutacao.UNIFORM_MUTATION,
			TipoCrossover.TWO_POINTS_CROSSOVER,
			maxProporcaoGrupos,
			percentualGruposPorCrossover,
			percentualSolucoesIguaisMaximo,
			percentualMovimentacoesMutacao);
		
		Parametro param13 = new Parametro(paramPadrao);
		param13.setTipoSelecao(TipoSelection.BINARY_TOURNAMENT);
		param13.setTipoElite(TipoElite.PERCENTUAL);
		param13.setPercentualElite(300.00); // para calcular sobre a população multiplicou por 10  
		param13.setTipoCrossover(TipoCrossover.TWO_POINTS_CROSSOVER);
		params.add(param13);

		return params;
	}

	
	public static void main(String[] args) throws Exception {
		int i = 0;
		List<Parametro> params = new ExperimentoFinalParte3_GNE().getParametros();
		System.out.println("Total parametros=" + params.size());
		for (Parametro param: params) {
			System.out.println(++i + "=" + param.getInfoParametros());	
		}
		
		//Configura a exibição conforme algoritmo e problema
		TipoExibicao tipoExibicao = TipoExibicao.DEFAULT;
		tipoExibicao.setDebug(false);
		
		ExperimentoModel experimento = new ExperimentoFinalParte3_GNE();
		Principal.executa(tipoExibicao, experimento);
	}
}

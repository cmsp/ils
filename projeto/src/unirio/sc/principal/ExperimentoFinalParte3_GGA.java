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

@Deprecated
public class ExperimentoFinalParte3_GGA extends ExperimentoModel  {

	@Override
	public String getIdExperimento() {
		return "Final_Parte3_GGA";
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
		
		int nCiclos = 30;
		
		Integer maxIteracoesSemMelhoria = 0;
		TipoElite tipoElite = TipoElite.PERCENTUAL;
		Double percentualElite = 30.00;
		TipoSelection tipoSelecao = TipoSelection.BINARY_TOURNAMENT;
		Double percentualGrupoA = 0.00;
		Double percentualGrupoB = 0.00;
		TipoDescarte tipoDescarte = TipoDescarte.NENHUM;
		Double percentualDescarteZero = 0.00;
		Double percentualDescarteSelecao = 0.00;
		Double percentualDescarteMovimentacoes = 0.00;
		int maxProporcaoGrupos = 2;
		double percentualGruposPorCrossover = 10.00;
		double percentualSolucoesIguaisMaximo = 1.00;
		double percentualMovimentacoesMutacao = 5.00;
		
		Parametro paramGFPadrao = new Parametro(TipoAlgoritmo.GENETICO_FALKENAUER,nCiclos);
		paramGFPadrao.setGenetico(
				maxIteracoesSemMelhoria,
				tipoElite,
				percentualElite, 
				tipoDescarte,
				percentualDescarteZero,
				percentualDescarteSelecao,
				percentualDescarteMovimentacoes,
				tipoSelecao, 
				percentualGrupoA, 
				percentualGrupoB, 
				TipoMutacao.JOIN,
				TipoCrossover.FALKNAUER_SEM_ELEMENTOS_UNITARIOS,
				maxProporcaoGrupos,
				percentualGruposPorCrossover,
				percentualSolucoesIguaisMaximo,
				percentualMovimentacoesMutacao);

		Parametro paramGF = new Parametro(paramGFPadrao);
		paramGF.setPercentualGruposPorCrossover(10.00);
		paramGF.setTipoMutacao(TipoMutacao.JOIN);

		params.add(paramGF);
		
		return params;
	}

	
	public static void main(String[] args) throws Exception {
		int i = 0;
		List<Parametro> params = new ExperimentoFinalParte3_GGA().getParametros();
		System.out.println("Total parametros=" + params.size());
		for (Parametro param: params) {
			System.out.println(++i + "=" + param.getInfoParametros());	
		}
		
		//Configura a exibição conforme algoritmo e problema
		TipoExibicao tipoExibicao = TipoExibicao.DEFAULT;
		tipoExibicao.setDebug(false);
		
		ExperimentoModel experimento = new ExperimentoFinalParte3_GGA();
		Principal.executa(tipoExibicao, experimento);
	}
}

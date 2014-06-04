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

public class ExperimentoFinalParte2A extends ExperimentoModel  {

	@Override
	public String getIdExperimento() {
		return "Final_Parte2A_GNE";
	}

	public String[] getInstancias() {
		return new String[] {
			"jnanoxml 25C.odem",
			"apache_zip 36C.odem",
			"jscatterplot 74C.odem",
			"junit-3.8.1 100C.odem",
			"tinytim 134C.odem",
			"gae_plugin_core 140C.odem",
			"jdendogram 177C.odem",
			"pdf_renderer 199C.odem",
			"Jung-visualization 221C.odem",
			"pfcda_swing 252C.odem",
			"jml 270C.odem",
			"notepad-full 299C.odem"
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
		
		TipoCrossover[] crossovers = { TipoCrossover.ONE_POINT_CROSSOVER, TipoCrossover.TWO_POINTS_CROSSOVER }; 
		for (TipoCrossover crossover : crossovers) {
		
			Parametro param1 = new Parametro(paramPadrao);
			param1.setTipoSelecao(TipoSelection.BINARY_TOURNAMENT);
			param1.setTipoElite(TipoElite.INTEIRO);
			param1.setPercentualElite(2.00);
			param1.setTipoCrossover(crossover);
			params.add(param1);
			
			Parametro param2 = new Parametro(paramPadrao);
			param2.setTipoSelecao(TipoSelection.BINARY_TOURNAMENT);
			param2.setTipoElite(TipoElite.PERCENTUAL);
			param2.setPercentualElite(100.00);
			param2.setTipoCrossover(crossover);
			params.add(param2);
			
			Parametro param3 = new Parametro(paramPadrao);
			param3.setTipoSelecao(TipoSelection.PAIS_GRUPO_A_E_B);
			param3.setTipoElite(TipoElite.PERCENTUAL);
			param3.setPercentualElite(100.00);
			param3.setPercentualGrupoA(100.00);
			param3.setPercentualGrupoB(850.00);
			param3.setTipoCrossover(crossover);
			params.add(param3);
			
			Parametro param4 = new Parametro(paramPadrao);
			param4.setTipoSelecao(TipoSelection.BINARY_TOURNAMENT);
			param4.setTipoElite(TipoElite.PERCENTUAL);
			param4.setPercentualElite(200.00);
			param4.setTipoCrossover(crossover);
			params.add(param4);

			Parametro param5 = new Parametro(paramPadrao);
			param5.setTipoSelecao(TipoSelection.PAIS_GRUPO_A_E_B);
			param5.setTipoElite(TipoElite.PERCENTUAL);
			param5.setPercentualElite(200.00);
			param5.setPercentualGrupoA(200.00);
			param5.setPercentualGrupoB(750.00);
			param5.setTipoCrossover(crossover);
			params.add(param5);

			Parametro param6 = new Parametro(paramPadrao);
			param6.setTipoSelecao(TipoSelection.BINARY_TOURNAMENT);
			param6.setTipoElite(TipoElite.PERCENTUAL);
			param6.setPercentualElite(300.00);
			param6.setTipoCrossover(crossover);
			params.add(param6);

			Parametro param7 = new Parametro(paramPadrao);
			param7.setTipoSelecao(TipoSelection.PAIS_GRUPO_A_E_B);
			param7.setTipoElite(TipoElite.PERCENTUAL);
			param7.setPercentualElite(300.00);
			param7.setPercentualGrupoA(300.00);
			param7.setPercentualGrupoB(650.00);
			param7.setTipoCrossover(crossover);
			params.add(param7);
		}
		
		return params;
	}

	
	public static void main(String[] args) throws Exception {
		int i = 0;
		List<Parametro> params = new ExperimentoFinalParte2A().getParametros();
		System.out.println("Total parametros=" + params.size());
		for (Parametro param: params) {
			System.out.println(++i + "=" + param.getInfoParametros());	
		}
		
		//Configura a exibição conforme algoritmo e problema
		TipoExibicao tipoExibicao = TipoExibicao.DEFAULT;
		tipoExibicao.setDebug(false);
		
		ExperimentoModel experimento = new ExperimentoFinalParte2A();
		Principal.executa(tipoExibicao, experimento);
	}
}

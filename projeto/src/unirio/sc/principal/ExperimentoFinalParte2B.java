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
public class ExperimentoFinalParte2B extends ExperimentoModel {
	
	@Override
	public String getIdExperimento() {
		return "Final_Parte2B_GGA";
	}
	
	public String[] getInstancias()
	{
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
			"notepad-full 299C.odem",
		};
	}

	public static Parametro getVariacaoGeneticoGF(Parametro paramGF, double percentualGruposPorCrossover, TipoMutacao tipoMutacao) 
	{
		Parametro paramGFNovo = new Parametro(paramGF);
		paramGFNovo.setPercentualGruposPorCrossover(percentualGruposPorCrossover);
		paramGFNovo.setTipoMutacao(tipoMutacao);
		return paramGFNovo;
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
		
		Parametro paramGFPadrao1 = new Parametro(TipoAlgoritmo.GENETICO_FALKENAUER,nCiclos);
		paramGFPadrao1.setGenetico(
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

		Parametro paramGF1 = getVariacaoGeneticoGF(paramGFPadrao1, 100.00, TipoMutacao.NENHUMA);
		Parametro paramGF2 = getVariacaoGeneticoGF(paramGFPadrao1, 100.00, TipoMutacao.JOIN);
		Parametro paramGF3 = getVariacaoGeneticoGF(paramGFPadrao1, 100.00, TipoMutacao.MOVE_JOIN_SPLIT);
		Parametro paramGF4 = getVariacaoGeneticoGF(paramGFPadrao1,  10.00, TipoMutacao.NENHUMA);
		Parametro paramGF5 = getVariacaoGeneticoGF(paramGFPadrao1,  10.00, TipoMutacao.JOIN);
		Parametro paramGF6 = getVariacaoGeneticoGF(paramGFPadrao1,  10.00, TipoMutacao.MOVE_JOIN_SPLIT);

		params.add(paramGF1);
		params.add(paramGF2);
		params.add(paramGF3);
		params.add(paramGF4);
		params.add(paramGF5);
		params.add(paramGF6);

		return params;
	}
	
	public static void main(String[] args) throws Exception {
		List<Parametro> params = new ExperimentoFinalParte2B().getParametros();
		for (Parametro param: params) {
			System.out.println(param.getInfoParametros());	
		}
		
		//Configura a exibição conforme algoritmo e problema
		TipoExibicao tipoExibicao = TipoExibicao.DEFAULT;
		tipoExibicao.setDebug(false);
		
		ExperimentoModel experimento = new ExperimentoFinalParte2B();
		Principal.executa(tipoExibicao, experimento);
	}
}

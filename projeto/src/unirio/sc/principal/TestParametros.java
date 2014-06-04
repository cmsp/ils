package unirio.sc.principal;

import java.util.List;

import unirio.sc.core.Parametro;

public class TestParametros {

	public static void main(String[] args) {
		List<Parametro> parametros = new ExperimentoFinalParte4_ILS().getParametros();
		for (int i = 0; i < parametros.size(); i++) {
			parametros.get(i).setSeparador("\n");
			System.out.println(
				"\n\nparametros[" + i + "]=" + parametros.get(i).getInfoParametros()
			);
		}

	}

}

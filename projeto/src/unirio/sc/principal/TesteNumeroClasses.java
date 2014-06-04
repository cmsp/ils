package unirio.sc.principal;

import java.util.ArrayList;

import unirio.sc.core.Problema;

public class TesteNumeroClasses {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		ArrayList<Problema> instances = new ExperimentoFinalParte4_ILS().getProblemas();
		try {
			for (Problema problema : instances) {
				System.out.println(problema.getFileName() + "," + problema.getName() + ",tamanho=," + problema.getTamanho() + ",dependencias=," + problema.calculaTotalDependencias());
			}
		}
		catch (Exception e) {
			System.out.println( "Erro e=" + e.getMessage());
		}
	}

}

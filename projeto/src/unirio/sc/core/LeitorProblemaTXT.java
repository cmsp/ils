package unirio.sc.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeitorProblemaTXT 
{
	/**
	 * A partir do arquivo da instância, carrega um objeto Problema
	 */
	public static Problema loadTXT(String filename)
	{
		try {
			HashMap<String,Integer> modulos = new HashMap<String, Integer>();
			HashMap<Integer,List<Integer>> dependenciasDe = new HashMap<Integer, List<Integer>>();
			HashMap<Integer,List<Integer>> dependenciasPara = new HashMap<Integer, List<Integer>>();
			
			leituraModulos(filename, modulos, dependenciasDe, dependenciasPara);
			
			int classCount = modulos.size();
			int packageCount = 0; // sem informação da quantidade de pacotes original
			
			/// System.out.println("qtd_modulos=[" + modulos.size() + "]");
			
			int[][] listaDependenciasPara = new int[classCount][classCount];
			int[] qtdDependenciasPara = new int[classCount];
			int[][] listaDependenciasDe = new int[classCount][classCount];
			int[] qtdDependenciasDe = new int[classCount];
	
			int[] originalPackage = new int[classCount];
			int[] originalClasses = new int[classCount];
			
			for (Integer moduloOrigem: dependenciasPara.keySet()) {
				List<Integer> depPARA = dependenciasPara.get(moduloOrigem);
				qtdDependenciasPara[ moduloOrigem ] = depPARA.size();
				for (int i = 0; i < depPARA.size(); i++) {
					/// System.out.println("PARA[" + moduloOrigem + "][" + i + "]=" + depPARA.get(i));
					listaDependenciasPara[ moduloOrigem ][ i ] = depPARA.get( i );
				}
			}
	
			for (Integer moduloDestino: dependenciasDe.keySet()) {
				List<Integer> depDE = dependenciasDe.get(moduloDestino);
				qtdDependenciasDe[ moduloDestino ] = depDE.size();
				for (int i = 0; i < depDE.size(); i++) {
					/// System.out.println("DE[" + moduloDestino + "][" + i + "]=" + depDE.get(i));
					listaDependenciasDe[ moduloDestino ][ i ] = depDE.get( i );
				}
			}
			String barra = "\\";
			int pos = filename.lastIndexOf(barra);
			String name = filename.substring(pos+barra.length());
			
			Problema problema = new Problema(
				filename,
				name,
				classCount, 
				packageCount,
				originalClasses, 
				originalPackage, 
				listaDependenciasPara, 
				qtdDependenciasPara,
				listaDependenciasDe,
				qtdDependenciasDe);
			
			return problema;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void leituraModulos(String filename, HashMap<String, Integer> modulos, HashMap<Integer, List<Integer>> dependenciasDe, HashMap<Integer, List<Integer>> dependenciasPara) {
		
		try {
			FileReader arquivoEntrada = new FileReader(filename);
			BufferedReader entrada = new BufferedReader(arquivoEntrada);
			
			String linha = null;
			int qtdModulos = 0;
			while ((linha = entrada.readLine()) != null) {
				String[] dados = linha.split(" ");
				String nomeModulo1 = dados[0];
				String nomeModulo2 = dados[1];
				Integer indiceModulo1 = modulos.get(nomeModulo1);
				Integer indiceModulo2 = modulos.get(nomeModulo2);
				if (indiceModulo1 == null) {
					indiceModulo1 = qtdModulos;
					modulos.put(nomeModulo1, qtdModulos++);
				}
				if (indiceModulo2 == null && indiceModulo2 != indiceModulo1) {
					indiceModulo2 = qtdModulos;
					modulos.put(nomeModulo2, qtdModulos++);
				}
				List<Integer> depDE   = dependenciasDe  .get(indiceModulo2);
				List<Integer> depPARA = dependenciasPara.get(indiceModulo1);
				if (depDE == null) {
					depDE = new ArrayList<Integer>();
					dependenciasDe.put(indiceModulo2, depDE);
				}
				if (depPARA == null) {
					depPARA = new ArrayList<Integer>();
					dependenciasPara.put(indiceModulo1, depPARA);
				}
				depDE.add(indiceModulo1);
				depPARA.add(indiceModulo2);
			}

			entrada.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Arquivo não localizado: [" + filename +"]");
			System.exit(1);
		} catch (IOException e2) {
			System.out.println("Erro ao abrir o arquivo de saída: [" + filename + "]");
			System.exit(1);
		}
	}
}

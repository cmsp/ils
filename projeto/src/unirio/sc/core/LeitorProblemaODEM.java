package unirio.sc.core;

import java.util.ArrayList;

import unirio.sc.reader.Reader;
import unirio.sc.reader.model.Project;
import unirio.sc.reader.model.ProjectClass;

public class LeitorProblemaODEM 
{
	
	public static ArrayList<Problema> loadInstancesODEM(String diretorio, String[] filenames) throws Exception {
		ArrayList<Problema> instances = new ArrayList<Problema>();
		
		for (String filename : filenames)
			if (filename.length() > 0)  {
				filename = diretorio + filename;
				Problema problema = loadODEM(filename);
				instances.add(problema);
			}

		return instances;
	}

	/**
	 * A partir do objeto modelo Project, carrega os dados do problema
	 * Adaptado do projeto de Marcio Barros
	 */
	public static Problema loadODEM(String filename) throws Exception 
	{
		Project modelo = Reader.load(filename);
		Problema problema = loadProject(modelo);
		return problema;
	}

	/**
	 * A partir do objeto modelo Project, carrega os dados do problema
	 * Adaptado do projeto de Marcio Barros
	 */
	private static Problema loadProject(Project modelo) throws Exception 
	{
		int classCount = modelo.getClassCount();
		int packageCount = modelo.getPackageCount();
		int[][] listaDependenciasPara = new int[classCount][classCount];
		int[] qtdDependenciasPara = new int[classCount];
		int[][] listaDependenciasDe = new int[classCount][classCount];
		int[] qtdDependenciasDe = new int[classCount];

		int[] originalPackage = new int[classCount];
		int[] originalClasses = new int[classCount];

		for (int i = 0; i < classCount; i++) {
			ProjectClass _class = modelo.getClassIndex(i);
			int sourcePackageIndex = modelo.getIndexForPackage(_class.getPackage());

			originalPackage[i] = sourcePackageIndex;
			originalClasses[sourcePackageIndex]++;

			for (int j = 0; j < _class.getDependencyCount(); j++) {
				String targetName = _class.getDependencyIndex(j).getElementName();
				int classIndex = modelo.getClassIndex(targetName);

				if (classIndex == -1) {
					throw new Exception("Class not registered in project: "	+ targetName);
				}

				listaDependenciasPara[i][j] = classIndex;
				qtdDependenciasPara[i]++;
				
				listaDependenciasDe[classIndex][qtdDependenciasDe[classIndex]++] = i;
			}
		}
		
		String name = modelo.getName();
		String filename = modelo.getFilename();
		
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

}

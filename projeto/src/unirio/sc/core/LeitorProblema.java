package unirio.sc.core;

import java.util.ArrayList;

public class LeitorProblema 
{
	
	public static ArrayList<Problema> loadInstances(String[] filenames) throws Exception {
		ArrayList<Problema> instances = new ArrayList<Problema>();
		
		for (String filename : filenames)
			if (filename.length() > 0)  {
				if (filename.toLowerCase().indexOf(".odem")>=0) {			
					filename = Diretorios.diretorioInstancias + filename;
					Problema problema = LeitorProblemaODEM.loadODEM(filename);
					instances.add(problema);
				}
				else {			
					filename = Diretorios.diretorioInstanciasTXT + filename;
					Problema problema = LeitorProblemaTXT.loadTXT(filename);
					instances.add(problema);
				}
			}

		return instances;
	}

	@Deprecated
	public static ArrayList<Problema> loadInstancesODEM(String diretorio, String[] filenames) throws Exception {
		return loadInstances(filenames);
	}

	public static Problema loadODEM(String filename) throws Exception {
		return LeitorProblemaODEM.loadODEM(filename);
	}

	public static Problema loadTXT(String filename) throws Exception {
		return LeitorProblemaTXT.loadTXT(filename);
	}

}

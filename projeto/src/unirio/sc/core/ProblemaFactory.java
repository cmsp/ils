package unirio.sc.core;


public class ProblemaFactory {

	public static Problema getProblema1( )
	{
		int numClasses = 8;
		int numPackages = 8;
		int[] originalPackages = {0,1,2,3,4,5,6,7};
		int[] originalClasses  = {1,1,1,1,1,1,1,1};

		int[][] listaDependencias = 
			{
				{1, 2, 3, 0, 0, 0, 0, 0}, 
				{0, 2, 3, 0, 0, 0, 0, 0}, 
				{0, 1, 3, 0, 0, 0, 0, 0}, 
				{0, 1, 2, 4, 0, 0, 0, 0}, 
				{3, 5, 6, 7, 0, 0, 0, 0}, 
				{4, 6, 7, 0, 0, 0, 0, 0}, 
				{4, 5, 7, 0, 0, 0, 0, 0}, 
				{4, 5, 6, 0, 0, 0, 0, 0}
			};
		int[] qtdDependencias = 
			{
				3, 3, 3, 4, 4, 3, 3, 3
			};

		Problema problema = new Problema(
			"teste",
			"",
			numClasses,
			numPackages,
			originalClasses,
			originalPackages,
			listaDependencias,
			qtdDependencias,
			null,
			null
		);
		
		return problema;
	}
	
	public static Problema getProblema6Classes( )
	{
		int numClasses = 6;
		int numPackages = 3;
		int[] originalPackages = {0,0,1,1,2,2};
		int[] originalClasses  = {2,2,2,0,0,0};

		int[][] listaDependenciasPara = 
			{
				{1, 0, 0, 0, 0, 0}, 
				{2, 3, 4, 0, 0, 0}, 
				{0, 1, 3, 0, 0, 0}, 
				{4, 0, 0, 0, 0, 0}, 
				{5, 0, 0, 0, 0, 0}, 
				{0, 0, 0, 0, 0, 0}
			};
		int[] qtdDependenciasPara = 
			{
				1, 3, 3, 1, 1, 0
			};

		int[][] listaDependenciasDe = 
			{
				{2, 0, 0, 0, 0, 0}, 
				{0, 2, 0, 0, 0, 0}, 
				{1, 0, 0, 0, 0, 0}, 
				{1, 2, 0, 0, 0, 0}, 
				{1, 3, 0, 0, 0, 0}, 
				{4, 0, 0, 0, 0, 0}
			};
		int[] qtdDependenciasDe = 
			{
				1, 2, 1, 2, 2, 1
			};

		Problema problema = new Problema(
			"teste6",
			"",
			numClasses,
			numPackages,
			originalClasses,
			originalPackages,
			listaDependenciasPara,
			qtdDependenciasPara,
			listaDependenciasDe,
			qtdDependenciasDe
		);
		
		return problema;
	}

	public static Problema getProblema(String nome) {
		try {
			Problema problema;
			if (nome.toLowerCase().indexOf(".odem")>=0) {
				String filename = Diretorios.diretorioInstancias + nome;
				problema = LeitorProblema.loadODEM(filename);
			} else {
				String filename = Diretorios.diretorioInstanciasTXT + nome;
				problema = LeitorProblema.loadTXT(filename);
			}
			return problema;
		}
		catch (Exception io) {
			return null;
		}
	}

	public static Problema getJstl18Classes() {
		return getProblema("jstl-1.0.6 18C.odem");
	}

	public static Problema getJnanoxml25Classes() {
		return getProblema("jnanoxml 25C.odem");
	}

	public static Problema getApache36Classes() {
		return getProblema("apache_zip 36C.odem");
	}
	
	public static Problema getJavaOCR59Classes() {
		return getProblema("javaocr 59C.odem");
	}

	public static Problema getServlet63Classes() {
		return getProblema("servletapi-2.3 63C.odem");
	}
	
	public static Problema getGaePlugin140Classes() {
		return getProblema("gae_plugin_core 140C.odem");
	}

	public static Problema getJscatterplot74Classes() {
		return getProblema("jscatterplot 74C.odem");
	}
	
	public static Problema getJunit100Classes() {
		return getProblema("junit-3.8.1 100C.odem");
	}

	public static Problema getPDFRenderer199Classes() {
		return getProblema("pdf_renderer 199C.odem");
	}
	
	public static Problema getTinytim134Classes() {
		return getProblema("tinytim 134C.odem");
	}

	public static Problema getPDFSwing252Classes() {
		return getProblema("pfcda_swing 252C.odem");
	}
	
	public static Problema getNotepad299Classes() {
		return getProblema("notepad-full 299C.odem");
	}
	
	public static Problema getJml270Classes() {
		return getProblema("jml 270C.odem");
	}
	
	public static Problema getYComplete2898Classes() {
		return getProblema("ycomplete 2898C.odem");
	}
	

}
package unirio.sc.core;

/**
 * DEFINICÕES:
 * 
 * - acoplamento = número de dependências que as classes de um pacote possuem
 * com classes de fora do pacote. Deve ser minimizado.
 * 
 * - coesão = número de dependências que as classes de um pacote possuem com
 * outras classes do mesmo pacote. Deve ser maximizado (ou seja, minimizamos seu
 * valor com sinal invertido)
 * 
 * - spread = partindo de zero e percorrendo cada pacote, acumula o quadrado da
 * diferença entre o número de classes do pacote e o número de classes do menor
 * pacote
 * 
 * - diferenca = diferença entre o número máximo de classes em um pacote e o
 * número mínimo de classes em um pacote
 * 
 * @author Marcio Barros
 */
public class Calculador extends CalculadorAbstract {

	//Limite do vetor é o limite superior informado para o problema
	protected int[] inboundEdges;
	protected int[] outboundEdges;
	protected int[] intraEdges;
	protected double[] mf;
	protected double mq;

	/**
	 * Inicializa o calculator com os dados do problema
	 */
	public Calculador(Problema problema) {
		super(problema);
	}
	
	/**
	 * Calcula o coeficiente de modularidade do projeto
	 */
	public double calculateMQEgravaEstado(int[] valores) {
		
		this.inboundEdges = new int[this.problema.getClassCount()];
		this.outboundEdges = new int[this.problema.getClassCount()];
		this.intraEdges = new int[this.problema.getClassCount()];
		this.mf = new double[this.problema.getClassCount()];
		
		int[][]listaDependencias = this.problema.getListaDependenciasPara();
		int[] qtdDependencias = this.problema.getQtdDependenciasPara();
		int classCount = this.problema.getClassCount();
		
		for (int i=0; i<classCount; i++) {
			int sourcePackage = valores[i];
			for (int j=0; j<qtdDependencias[i]; j++) {
				int targetPackage = valores[listaDependencias[i][j]];
				if (targetPackage != sourcePackage) {
					outboundEdges[sourcePackage]++;
					inboundEdges[targetPackage]++;
				} else
					intraEdges[sourcePackage]++;
			}
		}

		double mq = 0.0;

		//for (int i = 0; i < this.problema.getMaxPackages(); i++) {
		for (int i = 0; i < classCount; i++) {
			int inter = inboundEdges[i] + outboundEdges[i];
			int intra = intraEdges[i];

			//if (intra != 0 && inter != 0) {
			if (intra != 0) {
				this.mf[i] = intra / (intra + 0.5 * inter);
				mq += this.mf[i];
			}
		}
		
		this.mq = mq;
		
		return -mq;
	}

	/**
	 * Calcula o coeficiente de modularidade do projeto
	 */
	public double calculateMQ(int[] valores) {
		
		int[] inboundEdges = new int[this.problema.getClassCount()];
		int[] outboundEdges = new int[this.problema.getClassCount()];
		int[] intraEdges = new int[this.problema.getClassCount()];
		
		int[][]listaDependencias = this.problema.getListaDependenciasPara();
		int[] qtdDependencias = this.problema.getQtdDependenciasPara();
		int classCount = this.problema.getClassCount();
		
		for (int i=0; i<classCount; i++) {
			int sourcePackage = valores[i];
			for (int j=0; j<qtdDependencias[i]; j++) {
				int targetPackage = valores[listaDependencias[i][j]];
				if (targetPackage != sourcePackage) {
					outboundEdges[sourcePackage]++;
					inboundEdges[targetPackage]++;
				} else
					intraEdges[sourcePackage]++;
			}
		}

		double mq = 0.0;

		//for (int i = 0; i < this.problema.getMaxPackages(); i++) {
		for (int i = 0; i < classCount; i++) {
			int inter = inboundEdges[i] + outboundEdges[i];
			int intra = intraEdges[i];

			//if (intra != 0 && inter != 0) {
			if (intra != 0) {
				double mf = intra / (intra + 0.5 * inter);
				mq += mf;
			}
		}
		
		return mq;
	}

	/**
	 * Calcula o fitness
	 */
	public double evaluate(SolucaoAbstract s) {
		return evaluate(s.getValores());
	}
	
	/**
	 * Avalia a solução
	 */
	public double evaluate(int[] valores) {
		return -calculateMQ(valores);
	}

	/**
	 * Avalia a solução
	 */
	public double evaluateEGravaEstado(int[] valores) {
		return calculateMQEgravaEstado(valores);
	}

	/**
	 * Calcula o coeficiente de modularidade do projeto
	 * Considera somente a diferença nos vetores intraEdges, inboundEdges, outboundEdges calculado na última iteração
	 */
	public double evaluateMove(int[] valores, int item, int grupo1, int grupo2) {
		
		int[][]listaDependenciasPara = this.problema.getListaDependenciasPara();
		int[] qtdDependenciasPara = this.problema.getQtdDependenciasPara();
		int[][]listaDependenciasDe = this.problema.getListaDependenciasDe();
		int[] qtdDependenciasDe = this.problema.getQtdDependenciasDe();
		
		for (int i=0; i < qtdDependenciasPara[item]; i++) {
			int filho = listaDependenciasPara[item][i];
			// se o item era do grupo1 reduz as arestas intra de suas dependencias
			if (valores[filho] == grupo1) {
				this.intraEdges[grupo1]--;
				this.inboundEdges[grupo1]++;
				this.outboundEdges[grupo2]++;
			}
			// se o item passa para o grupo2 aumenta as arestas intra de suas dependencias
			else if (valores[filho] == grupo2) {
				this.inboundEdges[grupo2]--;
				this.outboundEdges[grupo1]--;
				this.intraEdges[grupo2]++;
			}
			// se o dependente não for nenhum dos dois grupos
			else {
				this.outboundEdges[grupo1]--;
				this.outboundEdges[grupo2]++;
			}
		}

		for (int i=0; i < qtdDependenciasDe[item]; i++) {
			int pai = listaDependenciasDe[item][i];
			// se o item era do grupo1 reduz as arestas intra de suas dependencias
			if (valores[pai] == grupo1) {
				this.intraEdges[grupo1]--;
				this.outboundEdges[grupo1]++;
				this.inboundEdges[grupo2]++;
			}
			// se o item passa para o grupo2 aumenta as arestas intra de suas dependencias
			else if (valores[pai] == grupo2) {
				this.inboundEdges[grupo1]--;
				this.outboundEdges[grupo2]--;
				this.intraEdges[grupo2]++;
			}
			// se o dependente não for nenhum dos dois grupos
			else {
				this.inboundEdges[grupo1]--;
				this.inboundEdges[grupo2]++;
			}
		}
		
		// recalculando o MF do grupo 1 e grupo 2
		this.mq -= this.mf[grupo1];
		this.mq -= this.mf[grupo2];
		
		this.mf[grupo1] = 0.0;
		int interG1 = inboundEdges[grupo1] + outboundEdges[grupo1];
		int intraG1 = intraEdges[grupo1];
		//if (intraG1 != 0 && interG1 != 0) {
		if (intraG1 != 0) {
			this.mf[grupo1] = intraG1 / (intraG1 + 0.5 * interG1);
		}

		this.mf[grupo2] = 0.0;
		int interG2 = inboundEdges[grupo2] + outboundEdges[grupo2];
		int intraG2 = intraEdges[grupo2];
		//if (intraG2 != 0 && interG2 != 0) {
		if (intraG2 != 0) {
			this.mf[grupo2] = intraG2 / (intraG2 + 0.5 * interG2);
		}
		
		this.mq += this.mf[grupo1];
		this.mq += this.mf[grupo2];
		 
		return -this.mq;
	}
	
}
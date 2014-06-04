package unirio.sc.genetico;

import unirio.sc.core.SolucaoAbstract;
import unirio.sc.core.random.PseudoRandom;
import unirio.sc.genetico.classico.SolucaoGeneticoGA;
import unirio.sc.genetico.falkenauer.SolucaoGeneticoGF;

public class Crossover 
{	
	private TipoCrossover tipoCrossover = null;
	private int maxGruposPorCrossover = 3;
	private double probabilidadeCrossover = 0.00;
	
	public Crossover(TipoCrossover tipoCrossover, double probabilidadeCrossover, int maxGruposPorCrossover) {
		this.tipoCrossover = tipoCrossover;
		this.probabilidadeCrossover = probabilidadeCrossover;
		this.maxGruposPorCrossover = maxGruposPorCrossover;
	}
	
	public void setTipoCrossover(TipoCrossover tipoCrossover) {
		this.tipoCrossover = tipoCrossover;
	}
	
	public TipoCrossover getTipoCrossover() {
		return tipoCrossover;
	}
	
	public double getProbabilidadeCrossover() {
		return probabilidadeCrossover;
	}
	
	public void setProbabilidadeCrossover(double probabilidadeCrossover) {
		this.probabilidadeCrossover = probabilidadeCrossover;
	}
	
	public int getMaxGruposPorCrossover() {
		return maxGruposPorCrossover;
	}
	
	public void setMaxGruposPorCrossover(int maxGruposPorCrossover) {
		this.maxGruposPorCrossover = maxGruposPorCrossover;
	}

	public SolucaoGeneticoGF[] falkenauerRetirandoElementosUnitarios(SolucaoAbstract[] pais) 
	{
		SolucaoGeneticoGF[] solucoes;

		if (PseudoRandom.randDouble() < this.probabilidadeCrossover) {
			
			int i1 = PseudoRandom.randInt(0, ((SolucaoGeneticoGF)pais[0]).getTotalGrupos());
			int i2 = i1 + PseudoRandom.randInt(0, this.maxGruposPorCrossover);
			int j1 = PseudoRandom.randInt(0, ((SolucaoGeneticoGF)pais[1]).getTotalGrupos());
			int j2 = j1 + PseudoRandom.randInt(0, this.maxGruposPorCrossover);

			//System.out.println("i1="+i1+",i2="+i2+",j1="+j1+",j2="+j2);
			solucoes = executaFalkenauer(pais[0], pais[1], i1, i2, j1, j2);
			otimizarRetirandoElementosUnitarios(solucoes);
		}
		else {
			// filhos são iguais aos pais caso não tenha havido a probabilidade de crossover
			solucoes = new SolucaoGeneticoGF[] { 
				new SolucaoGeneticoGF((SolucaoGeneticoGF)pais[0]), 
				new SolucaoGeneticoGF((SolucaoGeneticoGF)pais[1]) 
			};
		}
		
		return solucoes;
	}
	
	public SolucaoGeneticoGF[] falkenauerNormalizando(SolucaoAbstract[] pais) 
	{
		SolucaoGeneticoGF[] solucoes;

		if (PseudoRandom.randDouble() < this.probabilidadeCrossover) {
			
			// seleciona um ponto entre 0 e totalGrupos (sem seleção)
			int i1 = PseudoRandom.randInt(0, ((SolucaoGeneticoGF)pais[0]).getTotalGrupos());
			int i2 = i1 + PseudoRandom.randInt(0, this.maxGruposPorCrossover);
			if (i2 > ((SolucaoGeneticoGF)pais[0]).getTotalGrupos()) {
				i2 = ((SolucaoGeneticoGF)pais[0]).getTotalGrupos();
			}
			
			// seleciona um ponto entre 0 e totalGrupos (sem seleção)
			int j1 = PseudoRandom.randInt(0, ((SolucaoGeneticoGF)pais[1]).getTotalGrupos());
			int j2 = j1 + PseudoRandom.randInt(0, this.maxGruposPorCrossover);
			if (j2 > ((SolucaoGeneticoGF)pais[1]).getTotalGrupos()) {
				j2 = ((SolucaoGeneticoGF)pais[1]).getTotalGrupos();
			}

			// coleta os clusters na faixa [i1, i2-1] e [j2, j2-1]
			// System.out.println("i1="+i1+",i2="+i2+",j1="+j1+",j2="+j2);
			solucoes = executaFalkenauer(pais[0], pais[1], i1, i2, j1, j2);
			otimizarRetirandoElementosUnitarios(solucoes);
		}
		else {
			// filhos são iguais aos pais caso não tenha havido a probabilidade de crossover
			solucoes = new SolucaoGeneticoGF[] { 
				new SolucaoGeneticoGF((SolucaoGeneticoGF)pais[0]), 
				new SolucaoGeneticoGF((SolucaoGeneticoGF)pais[1]) 
			};
		}
		
		return solucoes;
	}

	/**
	 * Normaliza os grupos, não deixando elementos unitários
	 */
	public static void otimizarRetirandoElementosUnitarios(SolucaoGeneticoGF[] solucoes) 
	{
		// Retira elementos unitários nos grupos
		GeradorSolucaoGenetico.otimizarRetirandoElementosUnitarios(solucoes[0]);
		GeradorSolucaoGenetico.otimizarRetirandoElementosUnitarios(solucoes[1]);		

		// Normaliza a representação para que os números dos grupos sejam sequenciais
		GeradorSolucaoGenetico.normalizar(solucoes[0]);
		GeradorSolucaoGenetico.normalizar(solucoes[1]);
		
		// TestUtil.testeGrupos( "dentro two points", solucaoA );
		// TestUtil.testeGrupos( "dentro two points", solucaoB );
	}

	/**
	 * Realiza um crossover em dois pontos 
	 * Complexidade 4*n
	 */
	public static SolucaoGeneticoGF[] executaFalkenauer(
			SolucaoAbstract solucaoOriginalA,
			SolucaoAbstract solucaoOriginalB,
			int aIni, 
			int aFim, 
			int bIni, 
			int bFim) 
		{
			int[] valoresOriginalA  = solucaoOriginalA.getValores(); // Arrays.copyOf(valoresA, valoresA.length);
			int[] valoresOriginalB  = solucaoOriginalB.getValores(); // Arrays.copyOf(valoresB, valoresB.length);
			int[] valoresA = new int[valoresOriginalA.length]; //solucaoA.getValores();
			int[] valoresB = new int[valoresOriginalB.length]; //solucaoB.getValores();

			boolean[] marcadosA = new boolean[2 * valoresA.length];
			boolean[] marcadosB = new boolean[2 * valoresB.length];
			int[] qtdItensA = new int[2 * valoresA.length];
			int[] qtdItensB = new int[2 * valoresB.length];
			//int maiorGrupoA = 0;
			//int maiorGrupoB = 0;
			int totalGruposA = 0;
			int totalGruposB = 0;

			int shiftEsqA = aIni;
			int shiftDirA = aFim - aIni;
			int shiftEsqB = bIni;
			int shiftDirB = bFim - bIni;

			// Copia os elementos selecionados do elementoA para o elementoB
			for (int j = 0; j < valoresOriginalA.length; j++) {
				if (valoresOriginalA[j] >= aIni && valoresOriginalA[j] < aFim) {
					valoresB[j] = valoresOriginalA[j] - shiftEsqA;

					// vai marcando os elementos que foram setados
					marcadosB[j] = true;

					// contabiliza a quantidade de grupos
					if (qtdItensB[valoresB[j]] == 0) {
						totalGruposB++;
					}

					// contabiliza a quantidade de elementos de cada grupo
					qtdItensB[valoresB[j]]++;

					// guarda o maior grupo utilizado pois no crossover pode haver
					// buracos
					// if (valoresB[j] > maiorGrupoB) maiorGrupoB = valoresB[j];
				}
			}

			// Copia os elementos de B que não foram marcados, realizando o
			// deslocamento
			for (int j = 0; j < valoresOriginalB.length; j++) {
				if (!marcadosB[j]) {
					valoresB[j] = valoresOriginalB[j] + shiftDirA;

					// vai marcando os elementos que foram setados
					marcadosB[j] = true;

					// contabiliza a quantidade de grupos
					if (qtdItensB[valoresB[j]] == 0) {
						totalGruposB++;
					}

					// contabiliza a quantidade de elementos de cada grupo
					qtdItensB[valoresB[j]]++;

					// guarda o maior grupo utilizado pois no crossover pode haver
					// buracos
					// if (valoresB[j] > maiorGrupoB) maiorGrupoB = valoresB[j];
				}
			}

			// Copia os elementos selecionados do elementoB para o elementoA
			for (int j = 0; j < valoresOriginalB.length; j++) {
				if (valoresOriginalB[j] >= bIni && valoresOriginalB[j] < bFim) {
					valoresA[j] = valoresOriginalB[j] - shiftEsqB;

					// vai marcando os elementos que foram setados
					marcadosA[j] = true;

					// contabiliza a quantidade de grupos
					if (qtdItensA[valoresA[j]] == 0) {
						totalGruposA++;
					}

					// contabiliza a quantidade de elementos de cada grupo
					qtdItensA[valoresA[j]]++;

					// guarda o maior grupo utilizado pois no crossover pode haver
					// buracos
					// if (valoresA[j] > maiorGrupoA) maiorGrupoA = valoresA[j];
				}
			}

			// Copia os elementos de A que não foram marcados, realizando o
			// deslocamento
			for (int j = 0; j < valoresOriginalA.length; j++) {
				if (!marcadosA[j]) {
					valoresA[j] = valoresOriginalA[j] + shiftDirB;

					// vai marcando os elementos que foram setados
					marcadosA[j] = true;

					// contabiliza a quantidade de grupos
					if (qtdItensA[valoresA[j]] == 0) {
						totalGruposA++;
					}

					// contabiliza a quantidade de elementos de cada grupo
					qtdItensA[valoresA[j]]++;

					// guarda o maior grupo utilizado pois no crossover pode haver
					// buracos
					// if (valoresA[j] > maiorGrupoA) maiorGrupoA = valoresA[j];
				}
			}

			SolucaoGeneticoGF sA = new SolucaoGeneticoGF(valoresA, qtdItensA, totalGruposA);
			SolucaoGeneticoGF sB = new SolucaoGeneticoGF(valoresB, qtdItensB, totalGruposB);
			
			return new SolucaoGeneticoGF[]{sA,sB};
		}
	
	/**
	 * Realiza um crossover simples somente sobre a representacao em forma de inteiros
	 * @param probabilidade
	 * @param sA solução 1 de entrada
	 * @param sB solução 2 de entrada
	 * @return um vetor com as soluções geradas após o crossover
	 */
	public SolucaoAbstract[] singlePointCrossover(SolucaoAbstract[] pais)
	{
		SolucaoAbstract[] solucoes = new SolucaoGeneticoGA[]{ new SolucaoGeneticoGA( pais[0] ), new SolucaoGeneticoGA( pais[1] ) };
		if ( PseudoRandom.randDouble( ) < this.probabilidadeCrossover )
		{
			int crossoverPoint = PseudoRandom.randInt( 0, pais[0].getTotalItens( ) - 1 );
			for ( int i = crossoverPoint; i < pais[0].getTotalItens( ); i++ )
			{
				solucoes[ 0 ].getValores( )[ i ] = pais[ 1 ].getValores( )[ i ];
				solucoes[ 1 ].getValores( )[ i ] = pais[ 0 ].getValores( )[ i ];
			}
		}
		return solucoes;
	}
	
	/**
	 * Realiza um crossover simples somente sobre a representacao em forma de inteiros
	 * @param probabilidade
	 * @param sA solução 1 de entrada
	 * @param sB solução 2 de entrada
	 * @return um vetor com as soluções geradas após o crossover
	 */
	public SolucaoAbstract[] twoPointsCrossover(SolucaoAbstract[] pais)
	{
		SolucaoAbstract[] solucoes = new SolucaoGeneticoGA[]{ new SolucaoGeneticoGA( pais[0] ), new SolucaoGeneticoGA( pais[1] ) };
		if ( PseudoRandom.randDouble( ) < this.probabilidadeCrossover )
		{
			int crossoverPoint1 = PseudoRandom.randInt(0, pais[0].getTotalItens()-1);
			int crossoverPoint2 = PseudoRandom.randInt(0, pais[0].getTotalItens()-1);

			while (crossoverPoint1 == crossoverPoint2)
				crossoverPoint2 = PseudoRandom.randInt(0, pais[0].getTotalItens()-1);

			if (crossoverPoint1 > crossoverPoint2) {
				int  temp       = crossoverPoint1;
				crossoverPoint1 = crossoverPoint2;
				crossoverPoint2 = temp;
			}

			for (int i = crossoverPoint1; i <= crossoverPoint2; i++)
			{
				solucoes[ 0 ].getValores( )[ i ] = pais[ 1 ].getValores( )[ i ];
				solucoes[ 1 ].getValores( )[ i ] = pais[ 0 ].getValores( )[ i ];
			}
		}
		
		return solucoes;
	}

	/**
	 * Executa o crossover
	 */
	public SolucaoAbstract[] executa(SolucaoAbstract[] pais) {
		if (this.tipoCrossover==null) return pais;
		switch(this.tipoCrossover) {
			case ONE_POINT_CROSSOVER:
				return singlePointCrossover(pais);
			case TWO_POINTS_CROSSOVER:
				return twoPointsCrossover(pais);
			case FALKNAUER_SEM_ELEMENTOS_UNITARIOS:
				return falkenauerRetirandoElementosUnitarios(pais); 
			case FALKNAUER_NORMALIZADO:
				return falkenauerNormalizando(pais); 
			case NENHUMA:
				return pais;
		}
		return pais;
	}
	
}
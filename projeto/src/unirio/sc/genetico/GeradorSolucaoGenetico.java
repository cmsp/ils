package unirio.sc.genetico;

import java.util.Arrays;

import unirio.sc.core.random.GeradorSolucaoAbstract;
import unirio.sc.genetico.classico.SolucaoGeneticoGA;
import unirio.sc.genetico.falkenauer.SolucaoGeneticoGF;

public class GeradorSolucaoGenetico extends GeradorSolucaoAbstract {
	
	/**
	 * @Deprecated
	 * Faz com que os grupos tenham numera��o sequencial
	 * Complexidade n*n
	 */
	public static void normalizar(SolucaoGeneticoGF s) 
	{
		int[] valores = s.getValores();
		int[] qtdItens = s.getQtdItens();
		// int qtdGrupos = s.getTotalGrupos();

		// c�pia do vetor que ser� normalizada
		int[] valoresOriginal = Arrays.copyOf(valores, valores.length);
		int[] qtdItensOriginal = Arrays.copyOf(qtdItens, qtdItens.length);

		// percorre o vetor com os grupos marcados como setados no vetor de valores
		for (int i = 0; i < qtdItensOriginal.length; i++) {
		// for (int i = 0; i < qtdGrupos; i++) {
			// se n�o existir ocorrencia do grupo i na express�o
			if (qtdItensOriginal[i] == 0) {
				// percorre os valores diminuindo em 1 os grupos para preencher a aus�ncia do grupo
				for (int j = 0; j < valores.length; j++) {
					if (valoresOriginal[j] > i) {
						qtdItens[valores[j]]--;
						valores[j]--;
						qtdItens[valores[j]]++;
					}
				}
			}
		}

		// libera a memoria
		valoresOriginal = null;
		qtdItensOriginal = null;
	}
	
	/**
	 * @Deprecated
	 * Faz com que os grupos tenham numera��o sequencial
	 * Complexidade n*n
	 */
	public static void normalizar(SolucaoGeneticoGA s) 
	{
		int[] valores = s.getValores();

		// c�pia do vetor que ser� normalizada
		int[] valoresOriginal = Arrays.copyOf(valores, valores.length);
		
		// identifica os grupos com m�dulos
		boolean[] grupos = new boolean[valoresOriginal.length];
		for (int i = 0; i < valores.length; i++) {
			grupos[valoresOriginal[i]] = true;
		}

		// percorre o vetor com os grupos marcados como setados no vetor de valores
		for (int i = 0; i < grupos.length; i++) {
			// se n�o existir ocorrencia do grupo i na express�o
			if (!grupos[i]) {
				// percorre os valores diminuindo em 1 os grupos para preencher a aus�ncia do grupo
				for (int j = 0; j < valores.length; j++) {
					if (valoresOriginal[j] > i) {
						valores[j]--;
					}
				}
			}
		}

		// libera a memoria
		valoresOriginal = null;
	}

	/**
	 * Encontra o pr�ximo �ndice do grupo com elementos
	 * Se n�o encontrar retorna o �nicio
	 */
	private static int proximoGrupoComItens(int[] qtdItens, int inicio, int fim) 
	{
		//for (int i=inicio+1; i<fim; i++) {
		for (int i=inicio+1; i<=fim; i++) {
			if (qtdItens[i]>0)
				return i;
		}
		return inicio;
	}	

	/**
	 * Encontra o pr�ximo �ndice do grupo com elementos
	 * Se n�o encontrar retorna o �nicio
	 */
	private static int anteriorGrupoComItens(int[] grupos, int fim) 
	{
		for (int i=fim-1; i>=0; i--) {
			if (grupos[i]>0)
				return i;
		}
		return fim;
	}
	
	/**
	 * N�o deixa elementos dos grupos sozinhos
	 * Complexidade n*n
	 */
	public static void otimizarRetirandoElementosUnitarios(SolucaoGeneticoGF s) 
	{
		int[] valores = s.getValores();
		int[] qtdItens = s.getQtdItens();
		// int qtdGrupos = s.getQtdGrupos();
		int maiorGrupo = s.getNumeroDoMaiorGrupo();

		for (int i = 0; i < valores.length; i++) {
			// se a quantidade de elementos for igual a 1 passa o item para o pr�ximo grupo
			if (qtdItens[valores[i]] == 1) {
				// se for o maior grupo passa para o grupo anterior
				if (valores[i]==maiorGrupo) {
					// if (i>0) { // verifica se existe um �nico grupo com um elemento
						// passa o item para o grupo anterior v�lido
						int iGrupo = anteriorGrupoComItens(qtdItens, maiorGrupo);
						qtdItens[valores[i]]--;
						valores[i]=iGrupo;
						qtdItens[iGrupo]++;
					// }
				}
				else {
					// passa o item para o pr�ximo grupo v�lido
					int iGrupo = proximoGrupoComItens(qtdItens, valores[i], maiorGrupo);
					qtdItens[valores[i]]--;
					valores[i]=iGrupo;
					qtdItens[iGrupo]++;
				}
			}
		}
		
		// talvez possa recalcular no passo anterior
		// como houve normalizacao o maior grupo setado � o �ltimo grupo com elementos
		// int maiorGrupoAux = 0;
		int totalGruposAux = 0;
		for (int i=0; i<qtdItens.length; i++) {
			if (qtdItens[i]>0) {
				// if (i>maiorGrupoAux) maiorGrupoAux = i;
				totalGruposAux++;
			}
		}
		s.setQtdItens(qtdItens, totalGruposAux);
		//s.setMaiorGrupo(maiorGrupoAux);
	}
	
	public static SolucaoGeneticoGF getSolucaoGeneticoGFRandom() 
	{
		int[] valores = new int[tamanhoSolucao];
		int[] qtdItens = new int[2 * tamanhoSolucao];
		int totalGrupos = 0;
		
		for (int i = 0; i < tamanhoSolucao; i++) {
			valores[i] = randInt();
			if (qtdItens[valores[i]] == 0) {
				totalGrupos++;
			}
			qtdItens[valores[i]]++;
		}

		SolucaoGeneticoGF solucao = new SolucaoGeneticoGF(valores, qtdItens, totalGrupos);

		normalizar(solucao);

		// TestUtil.testeGrupos( "solucao random", solucao );

		return solucao;
	}

	public static SolucaoGeneticoGA getSolucaoGeneticoGARandom() 
	{
		int[] valores = new int[tamanhoSolucao];
		for (int i = 0; i < tamanhoSolucao; i++) {
			valores[i] = randInt();
		}

		SolucaoGeneticoGA solucao = new SolucaoGeneticoGA(valores);

		normalizar(solucao);

		return solucao;
	}
	
}

package unirio.sc.core.random;

public class StringUtil {

	public static final String SEPARADOR_ITENS = ",";
	public static final String SEPARADOR_GRUPOS = ";";
	public static final String SEPARADOR_REGISTROS = "#";

	public static String intVectorToString(String separador, int[] vetor) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < vetor.length; i++) {
			sb.append(" ");
			sb.append(vetor[i]);
			sb.append(separador);
		}
		if (sb.length() > 0)
			sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	/**
	 * Concatena os elementos passados por parâmetros
	 */
	static String concatena(String separador, String... itens) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < itens.length; i++) {
			sb.append(itens[i]);
			sb.append(separador);
		}
		return sb.toString();
	}

	/**
	 * Concatena os elementos passados por parâmetros
	 */
	public static String concatenaLabelsEItens(String separador,
			String... labelEItens) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < labelEItens.length; i += 2) {
			sb.append(labelEItens[i]);
			sb.append("=");
			sb.append(labelEItens[i + 1]);
			sb.append(separador);
		}
		return sb.toString();
	}

	public static String exibeGrupos(int[] valores) {
		int tam = valores.length;
		int[][] hashmap = new int[2 * tam][2 * tam];
		int[] pos = new int[tam + 1];

		// for ( int i = 0; i < valores.length; i++ )
		for (int i = 0; i < tam; i++) {
			int grupo = valores[i];
			// System.out.println( "i=" + i + ",grupo=" + grupo + ",pos[grupo]="
			// + pos[ grupo ] );
			hashmap[grupo][pos[grupo]] = i;
			pos[grupo]++;
		}

		int nGrupos = 0;
		StringBuffer expressao = new StringBuffer("");
		for (int i = 0; i < tam; i++) {
			if (pos[i] > 0) {
				expressao.append(nGrupos + "={ " + hashmap[i][0]);
				for (int j = 1; j < pos[i]; j++) {
					expressao.append(", ");
					expressao.append(hashmap[i][j]);
				}
				expressao.append(" }, ");
				nGrupos++;
			}
		}
		if (nGrupos > 0) {
			expressao.delete(expressao.length() - 2, expressao.length());
		}

		return expressao.toString();
	}

	/**
	 * @param vetor
	 * @return
	 */
	public static String exibeValores(int[] vetor) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < vetor.length; i++) {
			sb.append(i + "=[");
			sb.append(vetor[i]);
			sb.append("],");
		}
		if (sb.length() > 0)
			sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

}
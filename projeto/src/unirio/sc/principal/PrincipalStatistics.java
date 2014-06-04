package unirio.sc.principal;

import java.io.IOException;

import unirio.sc.core.ExperimentoModel;
import unirio.sc.core.TipoExibicao;
import unirio.sc.statistics.GeradorCSV;

public class PrincipalStatistics {

	private int parametroInicial;
	private int parametroFinal;
	private ExperimentoModel experimento;
	private String combinacoesWilcox;
	private boolean geraBoxPlot;
	private String[] instancias;

	/* ILS */
	/*
	private static int parametroInicial = 1;
	private static int parametroFinal = 228;
	private static ExperimentoModel experimento = new ExperimentoFinalParte1_2();
	//private static String combinacoesWilcox = "1,115;2,116;3,117;4,118;5,119;6,120;7,121;8,122;9,123;10,124;11,125;12,126;13,127;14,128;15,129;16,130;17,131;18,132;19,133;20,134;21,135;22,136;23,137;24,138;25,139;26,140;27,141;28,142;29,143;30,144;31,145;32,146;33,147;34,148;35,149;36,150;37,151;38,152;39,153;40,154;41,155;42,156;43,157;44,158;45,159;46,160;47,161;48,162;49,163;50,164;51,165;52,166;53,167;54,168;55,169;56,170;57,171;58,172;59,173;60,174;61,175;62,176;63,177;64,178;65,179;66,180;67,181;68,182;69,183;70,184;71,185;72,186;73,187;74,188;75,189;76,190;77,191;78,192;79,193;80,194;81,195;82,196;83,197;84,198;85,199;86,200;87,201;88,202;89,203;90,204;91,205;92,206;93,207;94,208;95,209;96,210;97,211;98,212;99,213;100,214;101,215;102,216;103,217;104,218;105,219;106,220;107,221;108,222;109,223;110,224;111,225;112,226;113,227;114,228";
	private static String combinacoesWilcox = "1,32;2,33;3,34;4,35;5,36;6,37;7,38;8,39;9,40;10,41;11,42;12,43;13,44;14,45;15,46;16,47;17,48;18,49;19,50;20,51;21,52;22,53;23,54;24,55;25,56;26,57;27,58;28,59;29,60;30,61;31,62;63,89;64,90;65,91;66,92;67,93;68,94;69,95;70,96;71,97;72,98;73,99;74,100;75,101;76,102;77,103;78,104;79,105;80,106;81,107;82,108;83,109;84,110;85,111;86,112;87,113;88,114;115,146;116,147;117,148;118,149;119,150;120,151;121,152;122,153;123,154;124,155;125,156;126,157;127,158;128,159;129,160;130,161;131,162;132,163;133,164;134,165;135,166;136,167;137,168;138,169;139,170;140,171;141,172;142,173;143,174;144,175;145,176;177,203;178,204;179,205;180,206;181,207;182,208;183,209;184,210;185,211;186,212;187,213;188,214;189,215;190,216;191,217;192,218;193,219;194,220;195,221;196,222;197,223;198,224;199,225;200,226;201,227;202,228";
	//private static String combinacoesWilcox9 = "1,3,16,64;1,2;1,65;1,8;1,79;1,82";
	private static boolean geraBoxPlot = false;
	*/

	/* ILS 9 MELHORES */
	/*
	private static int parametroInicial = 1;
	private static int parametroFinal = 9;
	private static ExperimentoModel experimento = new ExperimentoFinalParte3_ILS();
	private static String combinacoesWilcox = "1,2,3,4,5,6,7,8,9";
	*/
	
	/* TODOS - PARTE 3 */
	/*
	private static int parametroInicial = 1;
	private static int parametroFinal = 5;
	private static ExperimentoModel experimento = new ExperimentoFinalParte3_Todos();
	private static String combinacoesWilcox = "2,3,4,5";
	private static boolean geraBoxPlot = false;
	*/
	
	/* TODOS - PARTE 4 */
	/*
	private static int parametroInicial = 1;
	private static int parametroFinal = 5;
	private static ExperimentoModel experimento = new ExperimentoFinalParte4_Todos();
	private static String combinacoesWilcox = "1, 2,3,4,5";
	private static boolean geraBoxPlot = false;
	*/

	/* TODOS - GGA */
	/*
	private static int parametroInicial = 1;
	private static int parametroFinal = 1;
	private static ExperimentoModel experimento = new ExperimentoFinalParte3_GGA();
	private static String combinacoesWilcox = "1";
	private static boolean geraBoxPlot = false;
	*/

	/* GNE - Parte 2 */
	/*
	private static int parametroInicial = 1;
	private static int parametroFinal = 14;
	private static ExperimentoModel experimento = new ExperimentoFinalParte2A_2();
	private static String combinacoesWilcox = "1,2,3,4,5,6,7,8,9,10,11,12,13,14";
	*/

	/* GGA */
	/*
	private static int parametroInicial = 1;
	private static int parametroFinal = 6;
	private static ExperimentoModel experimento = new ExperimentoFinalParte2B_v02();
	//private static String combinacoesWilcox = "1,6;2,6;3,6;4,6;5,6";
	private static String combinacoesWilcox = "1,5;2,5;3,5;4,5;6,5";
	private static boolean geraBoxPlot = false;
	*/

	/* GULOSO */
	/*
	private static int parametroInicial = 1;
	private static int parametroFinal = 1;
	private static ExperimentoModel experimento = new ExperimentoFinalParte3_CNM();
	private static String combinacoesWilcox = "1";
	*/

	/*
	private static String[] instancias = {
			"nanoxml",
			"apache_zip",
			"jscatterplot",
			"junit",
			"tinytim",
			"gae_plugin_core",
			"jdendogram",
			"pdf_renderer",
			"jung_visualization",
			"pfcda_swing",
			"jml-1.0b4",
			"notelab-full"
		};
	*/

	/*
	private static String[] instancias = {
			"jstl",
			"nanoxml",
			"jodamoney",
			"jxlsreader",
			"seemp",
			"apache_zip",
			"udt-java",
			"javaocr",
			"servlet",
			"pfcda_base",
			"forms",
			"jscatterplot",
			"jfluid",
			"jxlscore",
			"jpassword1",
			"junit",
			"xmldom",
			"tinytim",
			"jkaryoscope",
			"gae_plugin_core",
			"javacc",
			"JavaGeom",
			"jdendogram",
			"xmlapi",
			"jmetal",
			"dom4j",
			"pdf_renderer",
			"Jung_graph_model",
			"jconsole",
			"jung_visualization",
			"pfcda_swing",
			"jpassword2",
			"jml-1.0b4",
			"notelab-full",
			"Poormans CMS",
			"log4j",
			"jtreeview",
			"JACE",
			"javaws",
			"res_cobol",
			//"y_base",
			//"lwjgl-2.8.4",
		};
	*/
		
	public final void executa() throws IOException
	{
		TipoExibicao tipoExibicao = TipoExibicao.DEFAULT;
		tipoExibicao.setDebug(false);
		//TipoExibicao tipoExibicao = TipoExibicao.SIMPLIFICADO;
		//tipoExibicao.setDebug(false);
		
		GeradorCSV.executa(tipoExibicao, experimento, parametroInicial, parametroFinal, combinacoesWilcox, geraBoxPlot);
		GeradorCSV.executaDebug(tipoExibicao, experimento, parametroInicial, parametroFinal);
	}
	
	public void executaConversaoCSVEmColunas() throws IOException
	{
		GeradorCSV.executaConversaoCSVEmColunas(experimento, parametroInicial, parametroFinal, instancias);
	}
	
	public void executaConcatenacaoWilcox(String combinacoesWilcox)
	{
		GeradorCSV.executaConcatenacaoWilcoxEffectSize(experimento, combinacoesWilcox, instancias);
	}
	
	public void geraScriptExperimentoParte4B() throws IOException {
		
		this.parametroInicial = 1;
		this.parametroFinal = 8;
		this.experimento = new ExperimentoFinalParte4B_Todos();
		this.combinacoesWilcox = "1,2,3,4,5,6,7,8";
		this.geraBoxPlot = false;
		this.instancias = new String[]{
			"mtunis",
			"ispell",
			"rcs",
			"bison",
			"grappa",
			//"bunch",
			"incl",
		};
		// 1. gerar script
		// executa();
		// 2. rodar os script no R
		// 3. condensar em colunas as medias, etc
		// executaConversaoCSVEmColunas();
		// 4. condensar em colunas os wilcox e effect-size 
		//String combinacoesWilcox = "1,2;3,2;4,2;5,2;6,2;7,2;8,2;2,3;4,3;5,3;6,3;7,3;8,3;7,8";
		String combinacoesWilcox = "1,2;3,2;4,2;5,2;6,2;7,2;8,2;2,3;4,3;5,3;6,3;7,3;8,3;7,8;1,5;2,5;3,5;4,5;6,5;7,5;8,5";
		executaConcatenacaoWilcox(combinacoesWilcox);
	}
	
	/*
	public void geraScriptExperimentoParte3() throws IOException {
		
		this.parametroInicial = 1;
		this.parametroFinal = 4;
		this.experimento = new ExperimentoFinalParte3_Todos();
		this.combinacoesWilcox = "1,2,3,4";
		this.geraBoxPlot = false;
		this.instancias = new String[]{
			"jstl",
			"nanoxml",
			"jodamoney",
			"jxlsreader",
			"seemp",
			"apache_zip",
			"udt-java",
			"javaocr",
			"servlet",
			"pfcda_base",
			"forms",
			"jscatterplot",
			"jfluid",
			"jxlscore",
			"jpassword1",
			"junit",
			"xmldom",
			"tinytim",
			"jkaryoscope",
			"gae_plugin_core",
			"javacc",
			"JavaGeom",
			"jdendogram",
			"xmlapi",
			"jmetal",
			"dom4j",
			"pdf_renderer",
			"Jung_graph_model",
			"jconsole",
			"jung_visualization",
			"pfcda_swing",
			"jpassword2",
			"jml-1.0b4",
			"notelab-full",
			"Poormans CMS",
			"log4j",
			"jtreeview",
			"JACE",
			//"javaws",
			//"res_cobol",
		};
		// 1. gerar script
		// executa();
		// 2. rodar os script no R
		// 3. condensar em colunas as medias, etc
		// executaConversaoCSVEmColunas();
		// 4. condensar em colunas os wilcox e effect-size 
		// String combinacoesWilcox = "1,2;3,2;4,2;5,2;1,3;2,3;4,3;5,3;4,5";
		//String combinacoesWilcox = "1,2;3,2;4,2;1,3;2,3;4,3";
		//executaConcatenacaoWilcox(combinacoesWilcox);
	}
	*/
	
	public static final void main(String[] args) throws Exception 
	{
		//new PrincipalStatistics().geraScriptExperimentoParte3();
		new PrincipalStatistics().geraScriptExperimentoParte4B();
		
		// 1. Com os arquivos de saída no diretorio results roda a primeira parte (executa)
		// Vai gerar na pasta statistics os scripts e arquivos resumidos csv
		// executa();
		
		// 2. Entrar no R e executar os scripts da pasta statistics, gerando os arquivos SAIDA_RESULTADOS.csv
		// executar script gerado no passo 1 no R
		// primeiro o scriptR_resultados_[EXPERIMENTO].csv.r
		// depois o scriptR_resultados_[EXPERIMENTO]_Wilcox.csv.r
		
		// 3. Se quiser compactar os resultados para colunas com o comando abaixo
		// executaConversaoCSVEmColunas();
		
		// 4. O comando abaixo concatena os wilcox e effectsize em colunas
		// deve ser invertido as configurações pois os números estão negativos
		// String combinacoesWilcox = "3,2;4,2;5,2;4,3;5,3;4,5";
		// executaConcatenacaoWilcox(combinacoesWilcox);
	}

}


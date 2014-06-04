package unirio.sc.statistics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SolucaoInfo {
	
	public String mqLido;
	public double mq;
	public String valores;
	
	public HashMap<Integer,List<Integer>> clusters = new HashMap<Integer, List<Integer>>();
	public HashMap<Integer,Double> cfs = new HashMap<Integer, Double>();
	public HashMap<Integer,Integer> inbounds = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> outbounds = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> intraEdge = new HashMap<Integer, Integer>();
	public List<Integer> ids = new ArrayList<Integer>();
	
	public SolucaoInfo(int n, String valores, String mqLido) {
		this.valores = valores;
		this.mqLido = mqLido;
	}
	
	public void setCluster(int id, double cf, int in, int out, int intra) {
		this.ids.add(id);
		this.cfs.put(id, cf);
		this.inbounds.put(id, in);
		this.outbounds.put(id, out);
		this.intraEdge.put(id, intra);
	}
	
	public void addCluster(int id, int modulo) {
		List<Integer> modulos = this.clusters.get(id);
		if (modulos == null) {
			modulos = new ArrayList<Integer>();
			modulos.add(modulo);
			this.clusters.put(id, modulos);
		}
		else {
			modulos.add(modulo);
		}
	}

	public String getDadosMQ() {
		DecimalFormat dc = new DecimalFormat("0.####");
		String dados = "";
		dados += String.valueOf(dc.format(mq));
		dados += ";";
		dados += mqLido;
		dados += ";";
		dados += String.valueOf(this.valores);
		dados += ";";
		int totalIntra = 0;
		int totalIn = 0;
		int totalOut = 0;
		// int qtde = 0;
		for (Integer cluster : this.clusters.keySet()) {
		// for (Integer cluster : this.ids) {
			if (!this.ids.contains(cluster)) {
				dados += "<CF=0>";
			}
			else {
				dados += "<CF=";
				dados += String.valueOf(this.cfs.get(cluster));
				dados += ",IN=";
				dados += String.valueOf(this.inbounds.get(cluster));
				dados += ",OUT=";
				dados += String.valueOf(this.outbounds.get(cluster));
				dados += ",INTRA=";
				dados += String.valueOf(this.intraEdge.get(cluster));
				dados += ">";
				totalIn += this.inbounds.get(cluster);
				totalOut += this.outbounds.get(cluster);
				totalIntra += this.intraEdge.get(cluster);
			}
		}
		dados += ";";
		dados += totalIn;
		dados += ";";
		dados += totalOut;
		dados += ";";
		dados += totalIntra;
		dados += ";";
		return dados;
	}
	
	public String getDadosClusters() {
		int clusterMenor = -1;
		int clusterMaior = -1;
		int qtdMaiorModulos = Integer.MIN_VALUE;
		int qtdMenorModulos = Integer.MAX_VALUE;
		int qtdClustersUnitarios = 0;
		int qtdTotalClusters = this.clusters.keySet().size();
		for (Integer cluster : this.clusters.keySet()) {
			List<Integer> modulos = this.clusters.get(cluster);
			if (modulos.size() < qtdMenorModulos) {
				clusterMenor = cluster;
				qtdMenorModulos = modulos.size();
			}
			if (modulos.size() > qtdMaiorModulos) {
				clusterMaior = cluster;
				qtdMaiorModulos = modulos.size();
			}
			if (modulos.size() == 1) {
				qtdClustersUnitarios++;
			}
			
		}
		
		String dados = "";
		dados += qtdTotalClusters; 
		dados += ";";
		dados += clusterMaior; 
		dados += ";";
		dados += qtdMaiorModulos; 
		dados += ";";
		dados += clusterMenor; 
		dados += ";";
		dados += qtdMenorModulos; 
		dados += ";";
		dados += qtdClustersUnitarios; 
		dados += ";";
		return dados;
		
	}

	/*
	private String vetorParaString(int[] v) {
		StringBuffer sb = new StringBuffer("");
		for (int n : v) {
			sb.append(n);
			sb.append(",");
		}
		if (sb.length()>0)
			sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}
		
	private String vetorParaString(double[] v) {
		StringBuffer sb = new StringBuffer("");
		for (double n : v) {
			sb.append(n);
			sb.append(",");
		}
		if (sb.length()>0)
			sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}
	*/

}

package unirio.sc.core;

public enum TipoAlgoritmo {
	GULOSO_VETOR,
	GULOSO_UF,
	GULOSO_HASHMAP,
	HILL_CLIMBING,
	GENETICO_GNE, // Grouping Number Encoding
	GENETICO_FALKENAUER, // Grouping Genetic Algorithm
	GENETICO_FALKENAUER_NORMALIZADO, // Grouping Genetic Algorithm v02
	ITERATED_LOCAL_SEARCH;
	
	public boolean isGuloso() {
		return this == GULOSO_VETOR || this == GULOSO_UF || this == GULOSO_HASHMAP;  
	}
	
	public boolean isGenetico() {
		return this == GENETICO_GNE || this == GENETICO_FALKENAUER || this == GENETICO_FALKENAUER_NORMALIZADO;  
	}
	
	public boolean isHillClimbing() {
		return this == HILL_CLIMBING;  
	}
	
	public boolean isIteratedLocalSearch() {
		return this == ITERATED_LOCAL_SEARCH;  
	}
}

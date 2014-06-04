package unirio.sc.genetico;

public enum TipoCrossover {
	NENHUMA,
	ONE_POINT_CROSSOVER,
	TWO_POINTS_CROSSOVER,
	FALKNAUER_SEM_ELEMENTOS_UNITARIOS,
	FALKNAUER_NORMALIZADO;
	
	public boolean isFalkenauer() {
		return this == FALKNAUER_SEM_ELEMENTOS_UNITARIOS || this == FALKNAUER_NORMALIZADO;
	}

}

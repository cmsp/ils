package unirio.sc.busca;

import unirio.sc.core.Calculador;
import unirio.sc.core.Problema;


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
 */
public class CalculadorHC extends Calculador {
	
	public CalculadorHC(Problema problema) {
		super(problema);
	}
	
}
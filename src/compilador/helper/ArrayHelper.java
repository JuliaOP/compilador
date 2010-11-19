package compilador.helper;

/**
 * Classe com m�todos de aux�lio para trabalhar com vetores. 
 * 
 * @author rafaelivan
 *
 */
public class ArrayHelper {
	
	/**
	 * Aloca um vetor de inteiros com o tamanho m�nimo necess�rio, de acordo com o vetor recebido.
	 * O final do vetor recebido � composto por uma s�rie de valores -1, que s�o desconsiderados.
	 * 
	 * @param vetor
	 * @return um vetor alocado na mem�ria com tamanho m�nimo.
	 */
	public static int[] alocarVetor(int[] vetor) {
		int tamanho = 0;
		
		for(int i = 0; i < vetor.length; i++) {
			if(vetor[i] == -1) {
				tamanho = i;
				break;
			}
		}
		
		int[] novoVetor = new int[tamanho];
		
		for(int i = 0; i < novoVetor.length; i++)
			novoVetor[i] = vetor[i];
		
		return novoVetor;
	}
	
	/**
	 * Compara dois vetores de inteiros.
	 * 
	 * @param vetor1
	 * @param vetor2
	 * @return <code>true</code>, caso os vetores sejam iguais. <code>false</code>, caso contr�rio.
	 */
	public static boolean compararVetoresInt(int[] vetor1, int[] vetor2) {		
		// Compara os tamanhos.
		if(vetor1.length != vetor2.length)
			return false;
		
		// Compara cada valor.
		for(int i = 0; i < vetor1.length; i++)
			if(vetor1[i] != vetor2[i])
				return false;
		
		return true;
	}
}
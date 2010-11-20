package compilador.analisador.sintatico;

public class Submaquina {
	
	/**
	 * O estado inicial da subm�quina.
	 */
	private int estadoInicial;
	
	/**
	 * Array com os estados iniciais da subm�quina.
	 */
	private int[] estadosFinais;
	
	/**
	 * Estado atual da subm�quina.
	 */
	private int estadoAtual;
	
	/**
	 * N�mero de estados da subm�quina.
	 */
	private int numeroEstados;
	
	/**
	 * Tabela de transi��o de estados da subm�quina.
	 */
	private int[][][] tabelaTransicao;
	
	/**
	 * Tabela com a rela��o de quais subm�quinas podem ser chamadas em cada estado.
	 */
	private int[][] tabelaChamadaSubmaquinas;
	
	public Submaquina(int estadoInicial, int[] estadosFinais, int[][][] tabelaTransicao, int[][] tabelaChamadaSubmaquinas) {
		this.estadoInicial = this.estadoAtual = estadoInicial;
		this.estadosFinais = estadosFinais;
		this.tabelaTransicao = tabelaTransicao;
		this.numeroEstados = this.tabelaTransicao.length;
		this.tabelaChamadaSubmaquinas = tabelaChamadaSubmaquinas;
	}
	
}
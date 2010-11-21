package compilador.analisador.sintatico;

import compilador.analisador.lexico.Token;
import compilador.estruturas.ListaLigada;
import compilador.estruturas.String;

public class Submaquina {
	
	/**
	 * Indica um estado inv�lido.
	 */
	public static final int ESTADO_INVALIDO = -1;
	
	/**
	 * Indica que a transi��o ocorreu normalmente.
	 */
	public static final int TRANSICAO_OK = 10;
	
	/**
	 * Indica que a transi��o � de chamada de subm�quina.
	 */
	public static final int TRANSICAO_CHAMADA_SUBMAQUINA = 11;
	
	/**
	 * O nome da subm�quina.
	 */
	private String nome;
	
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
	
	public Submaquina(String nome, int estadoInicial, int[] estadosFinais, int[][][] tabelaTransicao, int[][] tabelaChamadaSubmaquinas) {
		this.nome = nome;
		this.estadoInicial = this.estadoAtual = estadoInicial;
		this.estadosFinais = estadosFinais;
		this.tabelaTransicao = tabelaTransicao;
		this.numeroEstados = this.tabelaTransicao.length;
		this.tabelaChamadaSubmaquinas = tabelaChamadaSubmaquinas;
	}
	
	/**
	 * @return o nome da subm�quina.
	 */
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * @return o estado inicial da subm�quina.
	 */
	public int getEstadoInicial() {
		return this.estadoInicial;
	}
	
	/**
	 * @return o estado atual da subm�quina.
	 */
	public int getEstadoAtual() {
		return this.estadoAtual;
	}
	
	/**
	 * Seta o estado atual da sum�quina.
	 * 
	 * @param estadoAutal
	 */
	public void setEstadoAtual(int estadoAutal) {
		this.estadoAtual = estadoAutal;
	}
	
	/**
	 * Executa uma transi��o na subm�quina.
	 * 
	 * @param token
	 * @return
	 */
	public int transicao(Token token) {
		int proximoEstado = this.tabelaTransicao[this.estadoAtual][token.getClasse()][token.getID()];
		
		System.out.println("Prox estado : " + proximoEstado);
		System.out.println();
		
		if(proximoEstado == ESTADO_INVALIDO) {
			// Deve tentar chamar uma subm�quina.
			return TRANSICAO_CHAMADA_SUBMAQUINA;
		} else {
			// H� transi��o dispon�vel.
			this.estadoAtual = proximoEstado;
			return TRANSICAO_OK;
		}
	}
	
	/**
	 * Identifica qual subm�quina deve ser chamada.
	 * 
	 * @param token
	 * @return o identificador da subm�quina a ser chamada.
	 */
	public ListaLigada<Integer> possiveisSubmaquinas(Token token) {
		ListaLigada<Integer> possiveisSubmaquinas = new ListaLigada<Integer>();
		
		// Identificar as subm�quinas que podem ser chamadas neste estado.
		for(int i = 0; i < this.tabelaChamadaSubmaquinas[this.estadoAtual].length; i++)
			if(this.tabelaChamadaSubmaquinas[this.estadoAtual][i] != -1)
				possiveisSubmaquinas.put(i);
		
		return possiveisSubmaquinas;
	}
	
	/**
	 * Verifica se a subm�quina possui transi��o para o token dado.
	 * 
	 * @param token 
	 * @return <code>true</code>, caso exista a transi��o. <code>false</code>, caso n�o exista.
	 */
	public boolean haTransicao(Token token) {
		int proximoEstado = this.tabelaTransicao[this.estadoAtual][token.getClasse()][token.getID()];
		
		if(proximoEstado == ESTADO_INVALIDO)
			return false;
		return true;
	}
	
	/**
	 * Executa uma transi��o de estado baseada em chamada de subm�quina.
	 * N�o chama efetivamente outra subm�quina, apenas verifica a possibilidade e muda para o estado de retorno.
	 * 
	 * @param idSubmaquina
	 * @return 
	 */
	public boolean chamarSubmaquina(int idSubmaquina) {
		int proximoEstado = this.tabelaChamadaSubmaquinas[this.estadoAtual][idSubmaquina];
		
		if(proximoEstado == ESTADO_INVALIDO)
			return false;
		
		this.estadoAtual = proximoEstado;
		return true;
	}
}
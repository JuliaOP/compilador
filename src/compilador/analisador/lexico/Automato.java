package compilador.analisador.lexico;

public class Automato {
	
	/**
	 * Indica um estado inv�lido.
	 */
	public static final int ESTADO_INVALIDO = -1;
	
	/**
	 * Indica o estado inicial de qualquer aut�mato.
	 */
	public static final int ESTADO_INICIAL = 0;
	
	/**
	 * N�mero de estados do aut�mato.
	 */
	private int estados;
	
	/**
	 * Indica o estado atual no qual o aut�mato se encontra.
	 */
	private int estadoAtual;
	
	/**
	 * Tabela com as transi��es do aut�mato.
	 */
	private int[][] tabelaTransicao;
	
	/**
	 * Tabela contendo as classes de tokens para cada estado do aut�mato.
	 * Se o estado n�o for final, a classe adotada ser� a <code>Token.CLASSE_TOKEN_INVALIDO</code>.
	 */
	private int[] tabelaClasses;
	
	public Automato() {
		this.carregaTabelaTransicao();
		this.carregaTabelaClasses();
		this.estadoAtual = ESTADO_INICIAL;
	}
	
	/**
	 * Carrega as tabela de transi��o de estados.
	 */
	private void carregaTabelaTransicao() {
		this.estados = 6;
		this.tabelaTransicao = new int[this.estados][256];
		
		for(int i = 0; i < this.estados; i++) {
			for(int j = 0; j < 256; j++) {
				this.tabelaTransicao[i][j] = -1;
			}
		}
		
		/* Transi��es do estado 0. */
		for(int i = (int)'A'; i <= (int)'Z'; i++) {
			this.tabelaTransicao[0][i] = 1;
		}
		
		for(int i = (int)'0'; i <= (int)'9'; i++) {
			this.tabelaTransicao[0][i] = 2;
		}
		
		this.tabelaTransicao[0][(int)' '] = 0;
		this.tabelaTransicao[0][(int)'\n'] = 0;
		this.tabelaTransicao[0][(int)'\t'] = 0;
		
		this.tabelaTransicao[0][(int)';'] = 3;
		this.tabelaTransicao[0][(int)':'] = 3;
		this.tabelaTransicao[0][(int)'+'] = 3;
		this.tabelaTransicao[0][(int)'-'] = 3;
		this.tabelaTransicao[0][(int)'*'] = 3;
		this.tabelaTransicao[0][(int)'/'] = 3;
		this.tabelaTransicao[0][(int)'('] = 3;
		this.tabelaTransicao[0][(int)')'] = 3;
		this.tabelaTransicao[0][(int)'.'] = 3;
		this.tabelaTransicao[0][(int)'>'] = 3;
		this.tabelaTransicao[0][(int)'='] = 3;
		this.tabelaTransicao[0][(int)'<'] = 3;
		
		this.tabelaTransicao[0][(int)'%'] = 4;
		
		for(int i = 0; i < 256; i++) {
			if(this.tabelaTransicao[0][i] == -1)
				this.tabelaTransicao[0][i] = 5;
		}
		
		/* Transi��es do estado 1. */
		for(int i = (int)'A'; i <= (int)'Z'; i++) {
			this.tabelaTransicao[1][i] = 1;
		}
		
		for(int i = (int)'0'; i <= (int)'9'; i++) {
			this.tabelaTransicao[1][i] = 1;
		}
		
		/* Transi��es do estado 2 */
		for(int i = (int)'0'; i <= (int)'9'; i++) {
			this.tabelaTransicao[2][i] = 2;
		}
		
		/* Transi��es do estado 3. */
		
		/* Transi��es do estado 4. */
		for(int i = 0; i < 256; i++) {
			this.tabelaTransicao[4][i] = 4;
		}
		this.tabelaTransicao[4][(int)'\n'] = 0;
		
		/* Transi��es do estado 5. */
	}
	
	/**
	 * Carrega a tabela de classes dos estados finais do aut�mato.
	 */
	private void carregaTabelaClasses() {
		this.tabelaClasses = new int[this.estados];
		for(int i = 0; i < this.tabelaClasses.length; i++) {
			this.tabelaClasses[i] = Token.CLASSE_TOKEN_INVALIDO;
		}
		
		/* Registra os estados finais. */
		this.tabelaClasses[1] = Token.CLASSE_IDENTIFICADOR;
		this.tabelaClasses[2] = Token.CLASSE_NUMERO_INTEIRO;
		this.tabelaClasses[3] = Token.CLASSE_CARACTER_ESPECIAL;
	}
	
	/**
	 * Carrega a descri��o do aut�mato presente no arquivo de entrada.
	 * @param arquivo o caminho do arquivo.
	 */
	/*
	private void carregaTabelaTransicao(String arquivo) throws IOException {
		FileInputStream fileInputStream = null;
		
		try {
			fileInputStream = new FileInputStream(new File(arquivo));
			
			int ch;
			
			// Carrega o n�mero de estados.
			ch = fileInputStream.read();
			this.estados = Integer.parseInt(String.valueOf(ch));
			
			// Inicializa a tabela de transi��o.
			this.tabelaTransicao = new int[this.estados][256];
			
			// Carrega a tabela de transi��o.
			for(int i = 0; i < this.estados; i++)
				for(int j = 0; j < 256; j++)
					this.tabelaTransicao[i][j] = -1;
			
			int estadoAtual = -1; // Indica a linha da tabela de transi��o.
			int proxEstado; // Indica a coluna da tabela de transi��o.
			int entrada; // Entrada do aut�mato.
			
			while((ch = fileInputStream.read()) != -1) {	
				if((char)ch == '\n') {
					estadoAtual++;
				} else if((char)ch != ' '){
					entrada = ch;
					fileInputStream.read(); // L� o sinal de igual.
					proxEstado = fileInputStream.read();
					
					this.tabelaTransicao[estadoAtual][entrada] = proxEstado;
				}
			}
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} finally {			
			if(fileInputStream != null)
				fileInputStream.close();
		}
	}
	*/
	
	/**
	 * Recebe uma entrada e executa uma transi��o no aut�mato.
	 * 
	 * @param entrada c�digo ASCII decimal do caracter de entrada.
	 * @return a classe do token reconhecido at� o momento.
	 */
	public int transicao(int entrada) {
		int proximoEstado; 
		int saida;
		
		if(entrada < 0){
			proximoEstado = ESTADO_INVALIDO;
		} else {
			proximoEstado = this.tabelaTransicao[this.estadoAtual][entrada];
		}
		
		if(proximoEstado == ESTADO_INVALIDO) {
			// Terminou o reconhecimento do token, pois n�o achou transi��o.
			saida = this.tabelaClasses[this.estadoAtual];
			
			// Faz transi��o com cadeia vazia para o estado inicial.
			this.estadoAtual = ESTADO_INICIAL;
		} else {
			// Ainda n�o chegou ao final do reconhecimento.
			saida = Token.CLASSE_TOKEN_NAO_FINALIZADO;
			this.estadoAtual = proximoEstado;
		}
		
		return saida;
	}
	
	/**
	 * Verfica qual a classe dos tokens que s�o reconhecidos pelo atual estado do aut�mato
	 * @param estado
	 * @return a classe do token. Se o estado n�o for final, retorna -1.
	 */
	public int classeEstadoAtual() {
		return this.tabelaClasses[this.estadoAtual];
	}
}
package compilador.analisador.lexico;

public class Token {
	
	/* Poss�veis classes para os tokens. */
	public static final int CLASSE_TOKEN_INVALIDO = -1;
	public static final int CLASSE_TOKEN_NAO_FINALIZADO = 0;
	public static final int CLASSE_PALAVRA_RESERVADA = 1;
	public static final int CLASSE_IDENTIFICADOR = 2;
	public static final int CLASSE_CARACTER_ESPECIAL = 3;
	public static final int CLASSE_NUMERO_INTEIRO = 4;
	public static final int CLASSE_OUTROS_CARACTERES = 5;
	public static final int CLASSE_STRING = 6;
	
	/**
	 * A classe do Token.
	 */
	private int classe;
	
	/**
	 * Identificador do Token na tabela correspondende � sua classe.
	 * Para algumas classes, como a <code>Token.CLASSE_NUMERO_INTEIRO</code>, 
	 * esse campo cont�m a pr�pria representa��o do Token.
	 */
	private int id;
	
	/**
	 * A linha onde o Token estava no c�digo-fonte.
	 */
	private int linha;
	
	/**
	 * A coluna onde o Token estava no c�digo-fonte.
	 */
	private int coluna;
	
	public Token(int classe, int id) {
		this.classe = classe;
		this.id = id;
	}

	public int getClasse() {
		return this.classe;
	}

	public int getID() {
		return this.id;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
}
package compilador.analisador.lexico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import compilador.analisador.semantico.TabelaPalavrasReservadas;
import compilador.analisador.semantico.TabelaSimbolos;
import compilador.helper.ArrayHelper;

public class AnalisadorLexico {
	
	/**
	 * Aut�mato que reconhece os tokens da linguagem.
	 */
	private Transdutor transdutor;
	
	/**
	 * Aponta para o caracter atualmente a ser processo.
	 */
	private int ch;
	
	/**
	 * Aponta para o caracter seguinte ao atualmente em processamento.
	 */
	private int lookahead;
	
	/**
	 * Buffer para armazenar a representa��o do token.
	 */
	private int[] buffer;
	
	/**
	 * Indica se o armazenamento de caracteres no buffer est� ativado.
	 */
	private boolean bufferAtivo;
	
	/**
	 * Indica se os Tokens do arquivo j� se esgotaram.
	 */
	private boolean haMaisTokens; 
	
	/**
	 * FileInputStream que aponta para o arquivo do c�digo-fonte.
	 */
	private FileInputStream arquivoCondigoFonte;
	
	/**
	 * Indica se o final do arquivo foi atingido.
	 */
	private boolean finalDoArquivo;
	
	public AnalisadorLexico() {
		this.buffer = new int[100];
		this.transdutor = new Transdutor();
		this.haMaisTokens = true;
		this.finalDoArquivo = false;
		this.bufferAtivo = true;
		this.ch = -1;
		this.lookahead = -1;
	}
	
	/**
	 * Ativa ou desativa o armazenamento de caracteres em buffer, dependendo da situa��o do compilador.
	 */
	private void gerenciarAtividadeBuffer() {
		if(this.transdutor.estaNoEstadoComentario() 
				|| this.ch == (int)' ' 
				|| this.ch == (int)'%' 
				|| this.ch == (int)'\n' 
				|| this.ch == (int)'\t') {
			this.bufferAtivo = false;
		} else {
			this.bufferAtivo = true;
		}
	}
	
	/**
	 * Abre o arquivo que cont�m o c�digo-fonte.
	 */
	public void carregarCodigoFonte(String arquivo) {
		try {
			this.arquivoCondigoFonte = new FileInputStream(new File(arquivo));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * L� o c�digo-fonte buscando o pr�ximo Token.
	 * 
	 * @return o pr�ximo Token encontrado.
	 */
	public Token proximoToken() throws IOException {
		// inicializa o buffer.
		for(int i = 0; i < 100; i++)
			this.buffer[i] = -1;
		
		int classe; // Armazenar� a sa�da do aut�mato para uma dada transi��o.
		int chave; // Chave do Token na tabela correspondente � sua classe.
		int indice = 0; // Indexa o buffer.
		Token token; // Armazenar� o token reconhecido.
		int[] bufferMinimo;
		
		// Coloca o primeiro carater no lookahead, pois ainda n�o iniciou o processamento.
		// Executa somente na primeira vez.
		if(this.lookahead == -1) {
			this.lookahead = arquivoCondigoFonte.read();
			
			if(this.lookahead == -1) {
				// Atingiu o final do arquivo.
				this.finalDoArquivo = true;
			}
		}
		
		while(true) {
			if(this.finalDoArquivo && this.ch == -1) {
				// Atingiu o final do arquivo e terminou de processar os caracteres.
				this.haMaisTokens = false;
				//break;
			}
			
			if(this.ch == -1) {
				this.ch = this.lookahead;
				this.lookahead = arquivoCondigoFonte.read();
				
				if(this.lookahead == -1)
					this.finalDoArquivo = true;
			}
			
			// Executa uma transi��o no aut�mato.
			classe = transdutor.transicao(this.ch);
			
			// Verifica se � necess�rio ativar ou desativar o armazenamento em buffer.
			this.gerenciarAtividadeBuffer();
			
			switch(classe) {
				case Token.CLASSE_TOKEN_NAO_FINALIZADO:
					// L� mais caracteres do c�digo-fonte.
					if(this.bufferAtivo) {
						// Se o armazenamento estiver ativo, guarda o caracter no buffer.
						this.buffer[indice] = this.ch;
						indice++;
					}
					this.ch = -1; // Sinaliza que j� processou o caracter.
					break;
				case Token.CLASSE_PALAVRA_RESERVADA:
					bufferMinimo = ArrayHelper.alocarVetor(this.buffer);
					
					chave = TabelaPalavrasReservadas.recuperarChave(bufferMinimo);
					token = new Token(Token.CLASSE_PALAVRA_RESERVADA, chave);
					return token;
				case Token.CLASSE_IDENTIFICADOR:
					bufferMinimo = ArrayHelper.alocarVetor(this.buffer);
					
					chave = TabelaPalavrasReservadas.recuperarChave(bufferMinimo);
					
					if(chave == -1) {
						// N�o est� na tabela de palavras reservadas.
						
						chave = TabelaSimbolos.recuperarChave(bufferMinimo);
						if(chave == -1) {
							// N�o est� na tabela de s�mbolos.
							chave = TabelaSimbolos.inserirSimbolo(bufferMinimo);
						}
						
						token = new Token(Token.CLASSE_IDENTIFICADOR, chave);
					} else {
						// � uma palavra reservada.
						token = new Token(Token.CLASSE_PALAVRA_RESERVADA, chave);
					}
					
					return token;
				case Token.CLASSE_CARACTER_ESPECIAL:
					token = new Token(Token.CLASSE_CARACTER_ESPECIAL, this.buffer[0]);
					return token;
				case Token.CLASSE_NUMERO_INTEIRO:
					int numero = 0;
					
					for(int i = 0; i < this.buffer.length && this.buffer[i] != -1; i++) {
						numero = 10*numero + Integer.valueOf(String.valueOf(this.buffer[i]));
					}
					
					token = new Token(Token.CLASSE_NUMERO_INTEIRO, numero);
					return token;
			}
			
			if(!this.haMaisTokens)
				break;
		}
		
		return new Token(Token.CLASSE_TOKEN_INVALIDO, -1);
	}
	
	/**
	 * Verifica se h� mais tokens no c�digo-fonte.
	 * @return <code>true</code>, caso haja mais Tokens. <code>false</code>, caso contr�rio.
	 */
	public boolean haMaisTokens() {
		return this.haMaisTokens;
	}
	
	public static void main(String[] args) {
		AnalisadorLexico lexico = new AnalisadorLexico();
		lexico.carregarCodigoFonte("src/compilador/testes/source.ling");
		
		try{
			Token token;
			
			while(lexico.haMaisTokens()) {
				token = lexico.proximoToken();
				System.out.println("Token.classe: " + token.getClasse());
				System.out.println("Token.representacao: " + token.getID() + "\n");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
package compilador.analisador.sintatico;

import java.io.IOException;

import compilador.analisador.lexico.AnalisadorLexico;
import compilador.analisador.lexico.Token;

public class AnalisadorSintatico {
	
	/**
	 * O aut�mato de pilha estruturado do analisador sint�tico.
	 */
	private AutomatoPilhaEstruturado ape;
	
	/**
	 * O analisador l�xico do compilador.
	 */
	private AnalisadorLexico lexico;
	
	public AnalisadorSintatico() {
		// Inicializa o analisador l�xico.
		lexico = new AnalisadorLexico();
		lexico.carregarCodigoFonte("src/compilador/testes/source.ling");
		
		// Inicializa o APE.
		ape = new AutomatoPilhaEstruturado();
	}
	
	/**
	 * Processa o c�digo fonte.
	 * 
	 * @return <code>true</code> caso o c�digo-fonte esteja sintaticamente correto. <code>false</code> caso contr�rio.
	 */
	public boolean processarCodigoFonte() { 
		try{
			Token token;
			
			while(lexico.haMaisTokens()) {
				token = lexico.proximoToken();
				this.ape.consumirToken(token);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return this.ape.estaNoEstadoAceitacao();
	}
	
	public static void main(String[] args) {
		AnalisadorSintatico sintatico = new AnalisadorSintatico();
		boolean resultado = sintatico.processarCodigoFonte();
		
		if(resultado)
			System.out.println("RESULTADO: O programa esta sintaticamente correto.");
		else
			System.out.println("RESULTADO: O programa nao esta sintaticamente correto.");
	}
}
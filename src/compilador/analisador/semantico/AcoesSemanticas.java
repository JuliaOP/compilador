package compilador.analisador.semantico;

import compilador.analisador.lexico.ParametrosAcoesSemanticas;


public class AcoesSemanticas {
	
	/* ************************ */
	/* A��ES SEM�NTICAS GLOBAIS */
	/* ************************ */
	
	public static void acaoPadrao() {
		// System.out.println("ACAO SEMANTICA PADRAO");
	}
	
	/**
	 * Cria uma nova tabela de s�mbolos no contexto de escopos.
	 */
	public static void criarEscopo() {
		System.out.println("ACAO SEMANTICA: criarEscopo");
		
		TabelaSimbolos ts = new TabelaSimbolos();
		Escopos.putTabelaSimbolos(ts);
	}
	
	/**
	 * Deleta uma tabela de s�mbolos do contexto de escopos.
	 */
	public static void deletarEscopo() {
		System.out.println("ACAO SEMANTICA: deletarEscopo");
		
		Escopos.popTabelaSimbolos();
	}
	
	/* **************************** */
	/* A��ES SEM�NTICAS ESPEC�FICAS */
	/* **************************** */
	
	// Subm�quina PROGRAMA
	
	public static void programa_0_1_3_1() {
		AcoesSemanticas.criarEscopo();
		ParametrosAcoesSemanticas.TIPO = ParametrosAcoesSemanticas.TIPO_SIMBOLO_INT;
		ParametrosAcoesSemanticas.CATEGORIA = ParametrosAcoesSemanticas.CATEGORIA_SIMBOLO_FUNCAO;
		ParametrosAcoesSemanticas.DECLARADO = true;
	}
	
	public static void programa_1_2_0_4() {
		Escopos.setSimboloTipo(ParametrosAcoesSemanticas.TOKEN.getID(), ParametrosAcoesSemanticas.TIPO);
		Escopos.setSimboloCategoria(ParametrosAcoesSemanticas.TOKEN.getID(), ParametrosAcoesSemanticas.CATEGORIA);
		Escopos.setSimboloDeclarado(ParametrosAcoesSemanticas.TOKEN.getID(), ParametrosAcoesSemanticas.DECLARADO);
		
		ParametrosAcoesSemanticas.limparParametros();
	}
	
	public static void programa_4_3_40_6() {
		// Leu "("
	}
	
	public void programa_8_3_41_9() {
		// Leu ")"
	}
	
	public void programa_9_3_123_10() {
		// Leu "{"
	}
	
	// Subm�quina PAR�METROS
	
	public static void parametros_0_1_3_1() {
		ParametrosAcoesSemanticas.TIPO = ParametrosAcoesSemanticas.TIPO_SIMBOLO_INT;
		ParametrosAcoesSemanticas.CATEGORIA = ParametrosAcoesSemanticas.CATEGORIA_SIMBOLO_VARIAVEL;
		ParametrosAcoesSemanticas.DECLARADO = true;
	}
	
	public static void parametros_1_2_0_3() {
		Escopos.setSimboloTipo(ParametrosAcoesSemanticas.TOKEN.getID(), ParametrosAcoesSemanticas.TIPO);
		Escopos.setSimboloCategoria(ParametrosAcoesSemanticas.TOKEN.getID(), ParametrosAcoesSemanticas.CATEGORIA);
		Escopos.setSimboloDeclarado(ParametrosAcoesSemanticas.TOKEN.getID(), ParametrosAcoesSemanticas.DECLARADO);
	}
	
	// Subm�quina COMANDOS
	
	public static void comandos_0_1_0_2() {
		// TODO: Leitura do if - CONTAIF...
	}
}
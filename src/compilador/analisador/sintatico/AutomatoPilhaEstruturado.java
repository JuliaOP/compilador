package compilador.analisador.sintatico;

import compilador.analisador.lexico.Token;
import compilador.estruturas.ListaLigada;
import compilador.estruturas.Mapa;
import compilador.estruturas.Pilha;
import compilador.estruturas.String;

public class AutomatoPilhaEstruturado {
	
	/**
	 * O nome da subm�quina inicial.
	 */
	public static final String SUBMAQUINA_INICIAL = new String("programa".toCharArray());
	
	/**
	 * Array com todas as subm�quinas do APE.
	 */
	private Submaquina[] submaquinas;
	
	/**
	 * Utilizado pelo <code>GeradorSubmaquinas</code>. Relaciona o nome da subm�quinha com seu identificador.
	 */
	private Mapa<String, Integer> mapa;
	
	/**
	 * A pilha que gerenciar� as transi��es entre subm�quinas.
	 */
	private Pilha<Par> pilha;
	
	/**
	 * A subm�quina que est� atualmente em execu��o.
	 */
	private Submaquina submaquinaAtual;
	
	/**
	 * O estado atual da subm�quina atualmente em execu��o.
	 */
	private int estadoAtual;
	
	public AutomatoPilhaEstruturado() {
		this.inicializaSubmaquinas();
		
		this.submaquinaAtual = this.encontrarSubmaquina(AutomatoPilhaEstruturado.SUBMAQUINA_INICIAL);
		this.estadoAtual = this.submaquinaAtual.getEstadoInicial();
		
		this.pilha = new Pilha<Par>();
	}
	
	/**
	 * Consume um token executando as transi��es necess�rias nas subm�quinas.
	 * 
	 * @param token o token a ser consumido.
	 */
	public void consumirToken(Token token) {
		System.out.print("Submaquina: ");
		this.submaquinaAtual.getNome().imprimirln();
		System.out.println("Estado Atual: " + this.estadoAtual);
		System.out.println("Token.classe: " + token.getClasse());
		System.out.println("Token.id: " + token.getID());
		
		int statusTransicao = this.submaquinaAtual.transicao(token);
		
		if(statusTransicao == Submaquina.TRANSICAO_OK) {
			
			this.estadoAtual = this.submaquinaAtual.getEstadoAtual();
			
		} else if(statusTransicao == Submaquina.TRANSICAO_CHAMADA_SUBMAQUINA) {
			
			ListaLigada<Integer> possiveisSubmaquinas = this.submaquinaAtual.possiveisSubmaquinas(token);
			
			// Verfica as transi��es das subm�quinas com base no token.
			ListaLigada<Integer> submaquinasComTransicao = new ListaLigada<Integer>();
			for(int i = 0; i < possiveisSubmaquinas.tamanho(); i++)
				if(this.submaquinas[possiveisSubmaquinas.get(i)].haTransicao(token))
					submaquinasComTransicao.put(possiveisSubmaquinas.get(i));
			
			// TODO: chamar a subm�quina correta. Por enquanto enquanto a primeira � sempre chamada.
			Integer idSubmaquina = submaquinasComTransicao.get(0);
			if(idSubmaquina != null && this.submaquinaAtual.chamarSubmaquina(idSubmaquina)){
				// Empilha a situa��o atual.
				this.estadoAtual = this.submaquinaAtual.getEstadoAtual();
				Par par = new Par(this.submaquinaAtual, this.estadoAtual);
				this.pilha.push(par);
				
				// Chama a outra subm�quina.
				this.submaquinaAtual = this.submaquinas[idSubmaquina];
				this.submaquinaAtual.setEstadoAtual(this.submaquinaAtual.getEstadoInicial());
				this.estadoAtual = this.submaquinaAtual.getEstadoAtual();
				
				// Chamada recursiva.
				this.consumirToken(token);
			}
		}
		
		
		/*
		switch(token.getClasse()) {
			case Token.CLASSE_PALAVRA_RESERVADA:
				statusTransicao = this.submaquinaAtual.transicao(token.getClasse(), token.getID());
				this.estadoAtual = this.submaquinaAtual.getEstadoAtual();
				
				break;
			case Token.CLASSE_IDENTIFICADOR:
				statusTransicao = this.submaquinaAtual.transicao(token.getClasse(), token.getID());
				this.estadoAtual = this.submaquinaAtual.getEstadoAtual();
				
				break;
			case Token.CLASSE_CARACTER_ESPECIAL:
				statusTransicao = this.submaquinaAtual.transicao(token.getClasse(), token.getID());
				this.estadoAtual = this.submaquinaAtual.getEstadoAtual();
				
				break;
			case Token.CLASSE_NUMERO_INTEIRO:
				statusTransicao = this.submaquinaAtual.transicao(token.getClasse(), token.getID());
				this.estadoAtual = this.submaquinaAtual.getEstadoAtual();
				
				break;
			case Token.CLASSE_STRING:
				// TODO
				break;
			default:
				// TODO: tratar situa��o de erro.
				break;
		
		}
		*/
		
	}
	
	/**
	 * Localiza uma subm�quina a partir do seu nome.
	 * 
	 * @param nome
	 * @return
	 */
	private Submaquina encontrarSubmaquina(String nome) {
		for(int i = 0; i < this.submaquinas.length; i++) {
			if(this.submaquinas[i] != null && this.submaquinas[i].getNome().equals(nome))
				return this.submaquinas[i];
		}
		
		return null;
	}
	
	
	/**
	 * Inicializa as subm�quinas do APE.
	 */
	private void inicializaSubmaquinas() {
		this.mapa = new Mapa<String, Integer>();
		this.submaquinas = new Submaquina[10];
		int indice = 0;
		
		GeradorSubmaquinas geradorSubmaquinas = new GeradorSubmaquinas(this.mapa);
		Submaquina submaquina;
		
		geradorSubmaquinas.parseXML("src/compilador/config/programa.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		geradorSubmaquinas.parseXML("src/compilador/config/parametros.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		geradorSubmaquinas.parseXML("src/compilador/config/declaracoes.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		geradorSubmaquinas.parseXML("src/compilador/config/comandos.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		geradorSubmaquinas.parseXML("src/compilador/config/expressaoBooleana.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		geradorSubmaquinas.parseXML("src/compilador/config/expressao.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		geradorSubmaquinas.parseXML("src/compilador/config/texto.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		// Ordena o array de subm�quinas para poder ser indexador pelos valores do mapa de subm�quinas.;
		Submaquina[] temporario = new Submaquina[10]; 
		
		ListaLigada<String> chaves = this.mapa.chaves();
		for(int i = 0; i < chaves.tamanho(); i++) {
			String chave = chaves.get(i);
			
			for(int j = 0; j < this.submaquinas.length; j++)
				if(this.submaquinas[j] != null && this.submaquinas[j].getNome().equals(chave))
					temporario[mapa.get(chave).intValue()] = this.submaquinas[j];
		}
		
		this.submaquinas = temporario;
	}
	
}
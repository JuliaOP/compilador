package compilador.analisador.semantico;

import compilador.estruturas.String;

public class TSLinha {	
	private int[] nome;							// nome do simbolo
	private int tipo;							// tipo do simbolo: int, char, string, boolean, "struct"
	private int categoria;						// categoria do simbolo: VARIAVEL, FUNCAO, VETOR, MATRIZ, PARAMETRO
	private String endereco;					// pode ser HexaDecimal
	private int tamanho;						// numero de bytes do simbolo
	private boolean declarado;					// Se ele j� foi declarado antes de ser usado, � setado true se est� numa declara��o
	private int[] parametros = new int[100];	// fun��o com parametros, os tipos dos parametros ficam alistados na ordem neste vetor 
	
	public TSLinha(int[] nome)
	{
		this.nome = nome;
		for(int i = 0; i < this.parametros.length; i++)
		{
			this.parametros[i] = -1;
		}
	}
	
	public int[] getNome() {
		return nome;
	}

	public void setNome(int[] nome) {
		this.nome = nome;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public boolean isDeclarado() {
		return declarado;
	}

	public void setDeclarado(boolean declarado) {
		this.declarado = declarado;
	}

	public int[] getParametros() {
		return parametros;
	}

	public void setParametros(int[] parametros) {
		this.parametros = parametros;
	}
	
	public void addToParametros(int parametro) {
		for(int i = 0; i < this.parametros.length; i++)
		{
			if(this.parametros[i] == -1){
				this.parametros[i] = parametro;
			}
		}
	}
	
	public int numberOfParameters()
	{
		for(int i = 0; i < this.parametros.length; i++)
		{
			if(this.parametros[i] == -1){
				return i;
			}
		}
		return 0;
	}
}

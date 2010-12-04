package compilador.gerador.codigo;

import compilador.estruturas.String;
import compilador.helper.ArrayHelper;

public class GeradorCodigo {
	
	/**
	 * Buffer com as informa��es a serem inseridas na �rea de dados do c�digo-objeto.
	 */
	private BufferDados<String> bufferDados;
	
	/**
	 * Buffer com as informa��es a serem inseridas na �rea de c�digo do c�digo-objeto.
	 */
	private BufferCodigo<String> bufferCodigo;
	
	/**
	 * Endere�o (hexadecimal) do in�cio da �rea de c�digo.
	 */
	private static final int ENDERECO_AREA_CODIGO = 0;
	
	/**
	 * Endere�o (hexadecimal) do in�cio da �rea de dados.
	 */
	private static final int ENDERECO_AREA_DADOS = 200;
	
	public GeradorCodigo() {
		this.bufferCodigo = new BufferCodigo<String>();
		this.bufferDados = new BufferDados<String>();
	}
	
	/**
	 * Adiciona uma entrada na �rea de dados.
	 * @param str
	 */
	public void addAreaDados(String str) {
		this.bufferDados.add(str);
	}
	
	/**
	 * Adiciona uma entrada na �rea de c�digo.
	 * @param str
	 */
	public void addAreaCodigo(String str) {
		this.bufferCodigo.add(str);
	}
	
	/**
	 * Gera o c�digo-objeto completo.
	 * @return
	 */
	public String gerarCodigo() {
		String codigo = new String(("@ /"+GeradorCodigo.ENDERECO_AREA_CODIGO+"\n").toCharArray());
		
		while(!this.bufferCodigo.estaVazio())
			codigo = new String(ArrayHelper.concatenarVetoresChar(codigo.toCharArray(), this.bufferCodigo.proximo().toCharArray()));
		
		codigo = new String(ArrayHelper.concatenarVetoresChar(codigo.toCharArray(), ("@ /"+GeradorCodigo.ENDERECO_AREA_DADOS+"\n").toCharArray()));
		
		while(!this.bufferDados.estaVazio())
			codigo = new String(ArrayHelper.concatenarVetoresChar(codigo.toCharArray(), this.bufferDados.proximo().toCharArray()));
		
		return codigo;
	}
}
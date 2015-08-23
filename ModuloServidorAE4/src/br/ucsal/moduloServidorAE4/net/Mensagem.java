/**
 * 
 */
package br.ucsal.moduloServidorAE4.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author fredson
 *
 */
public class Mensagem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  ArrayList<String> userDest;
	private String userOrigem;
	private String textoMensagem;
	private InputStream streamEntrada;
	private OutputStream streamSaida;

	public Mensagem(ArrayList<String> userDest, String userOrigem,
			String textoMensagem) {
		this.userDest = userDest;
		this.userOrigem = userOrigem;
		this.textoMensagem = textoMensagem;
	}
	
	
	/**
	 * @return the userDest
	 */
	public ArrayList<String> getUserDest() {
		return userDest;
	}
	/**
	 * @param userDest the userDest to set
	 */
	public void setUserDest(ArrayList<String> userDest) {
		this.userDest = userDest;
	}
	/**
	 * @return the userOrigem
	 */
	public String getUserOrigem() {
		return userOrigem;
	}
	/**
	 * @param userOrigem the userOrigem to set
	 */
	public void setUserOrigem(String userOrigem) {
		this.userOrigem = userOrigem;
	}
	/**
	 * @return the textoMensagem
	 */
	public String getTextoMensagem() {
		return textoMensagem;
	}
	/**
	 * @param textoMensagem the textoMensagem to set
	 */
	public void setTextoMensagem(String textoMensagem) {
		this.textoMensagem = textoMensagem;
	}
	/**
	 * @return the streamEntrada
	 */
	public InputStream getStreamEntrada() {
		return streamEntrada;
	}
	/**
	 * @param streamEntrada the streamEntrada to set
	 */
	public void setStreamEntrada(InputStream streamEntrada) {
		this.streamEntrada = streamEntrada;
	}
	/**
	 * @return the streamSaida
	 */
	public OutputStream getStreamSaida() {
		return streamSaida;
	}
	/**
	 * @param streamSaida the streamSaida to set
	 */
	public void setStreamSaida(OutputStream streamSaida) {
		this.streamSaida = streamSaida;
	}
	
	
}

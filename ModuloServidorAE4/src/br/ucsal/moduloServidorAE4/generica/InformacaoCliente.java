package br.ucsal.moduloServidorAE4.generica;

import java.io.OutputStream;

public class InformacaoCliente {
	private OutputStream streamCliente;
	private String nomeCliente;
	private Thread threadCliente;
	private String ipCliente;
	/**
	 * @return the streamCliente
	 */
	public OutputStream getStreamCliente() {
		return streamCliente;
	}
	/**
	 * @param streamCliente the streamCliente to set
	 */
	public void setStreamCliente(OutputStream streamCliente) {
		this.streamCliente = streamCliente;
	}
	/**
	 * @return the nomeCliente
	 */
	public String getNomeCliente() {
		return nomeCliente;
	}
	/**
	 * @param nomeCliente the nomeCliente to set
	 */
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	/**
	 * @return the threadCliente
	 */
	public Thread getThreadCliente() {
		return threadCliente;
	}
	/**
	 * @param threadCliente the threadCliente to set
	 */
	public void setThreadCliente(Thread threadCliente) {
		this.threadCliente = threadCliente;
	}
	/**
	 * @return the ipCliente
	 */
	public String getIpCliente() {
		return ipCliente;
	}
	/**
	 * @param ipCliente the ipCliente to set
	 */
	public void setIpCliente(String ipCliente) {
		this.ipCliente = ipCliente;
	}
	
	
	
}

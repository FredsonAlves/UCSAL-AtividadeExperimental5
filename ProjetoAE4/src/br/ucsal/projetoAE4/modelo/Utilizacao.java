/**
 * 
 */
package br.ucsal.projetoAE4.modelo;

import java.util.Calendar;

/**
 * @author fredson
 *
 */
public class Utilizacao {

	private int codUtilizacao;
	private String matriculaUtilizador;
	private String nomeUtilizador;
	private Computador computador;
	private String enderecoIPV4;
	private Calendar dataHoraLogon;
	private Calendar dataHoraLogoff;

	
	/**
	 * @return the codUtilizacao
	 */
	public int getCodUtilizacao() {
		return codUtilizacao;
	}
	/**
	 * @param codUtilizacao the codUtilizacao to set
	 */
	public void setCodUtilizacao(int codUtilizacao) {
		this.codUtilizacao = codUtilizacao;
	}
	/**
	 * @return the matriculaUtilizador
	 */
	public String getMatriculaUtilizador() {
		return matriculaUtilizador;
	}
	/**
	 * @param matriculaUtilizador the matriculaUtilizador to set
	 */
	public void setMatriculaUtilizador(String matriculaUtilizador) {
		this.matriculaUtilizador = matriculaUtilizador;
	}
	/**
	 * @return the nomeUtilizador
	 */
	public String getNomeUtilizador() {
		return nomeUtilizador;
	}
	/**
	 * @param nomeUtilizador the nomeUtilizador to set
	 */
	public void setNomeUtilizador(String nomeUtilizador) {
		this.nomeUtilizador = nomeUtilizador;
	}
	/**
	 * @return the enderecoIPV4
	 */
	public String getEnderecoIPV4() {
		return enderecoIPV4;
	}
	/**
	 * @param enderecoIPV4 the enderecoIPV4 to set
	 */
	public void setEnderecoIPV4(String enderecoIPV4) {
		this.enderecoIPV4 = enderecoIPV4;
	}
	/**
	 * @return the dataHoraLogon
	 */
	public Calendar getDataHoraLogon() {
		return dataHoraLogon;
	}
	/**
	 * @param dataHoraLogon the dataHoraLogon to set
	 */
	public void setDataHoraLogon(Calendar dataHoraLogon) {
		this.dataHoraLogon = dataHoraLogon;
	}
	/**
	 * @return the dataHoraLogoff
	 */
	public Calendar getDataHoraLogoff() {
		return dataHoraLogoff;
	}
	/**
	 * @param dataHoraLogoff the dataHoraLogoff to set
	 */
	public void setDataHoraLogoff(Calendar dataHoraLogoff) {
		this.dataHoraLogoff = dataHoraLogoff;
	}
	/**
	 * @return the computador
	 */
	public Computador getComputador() {
		return computador;
	}
	/**
	 * @param computador the computador to set
	 */
	public void setComputador(Computador computador) {
		this.computador = computador;
	}
	
}

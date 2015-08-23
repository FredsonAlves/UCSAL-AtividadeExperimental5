/**
 * 
 */
package br.ucsal.projetoAE4.modelo;

import java.util.Calendar;

/**
 * @author fredson
 *
 */
public class Computador {

	private int idComputador;
	private String nomeComputador;
	private Laboratorio laboratorio;
	private int posicaoHorizontal;
	private int posicaoVertical;
	private Calendar ultimaComunicacao;
	/**
	 * @return the idComputador
	 */
	public int getIdComputador() {
		return idComputador;
	}
	/**
	 * @param idComputador the idComputador to set
	 */
	public void setIdComputador(int idComputador) {
		this.idComputador = idComputador;
	}
	/**
	 * @return the nomeComputador
	 */
	public String getNomeComputador() {
		return nomeComputador;
	}
	/**
	 * @param nomeComputador the nomeComputador to set
	 */
	public void setNomeComputador(String nomeComputador) {
		this.nomeComputador = nomeComputador;
	}
	/**
	 * @return the posicaoHorizontal
	 */
	public int getPosicaoHorizontal() {
		return posicaoHorizontal;
	}
	/**
	 * @param posicaoHorizontal the posicaoHorizontal to set
	 */
	public void setPosicaoHorizontal(int posicaoHorizontal) {
		this.posicaoHorizontal = posicaoHorizontal;
	}
	/**
	 * @return the posicaoVertical
	 */
	public int getPosicaoVertical() {
		return posicaoVertical;
	}
	/**
	 * @param posicaoVertical the posicaoVertical to set
	 */
	public void setPosicaoVertical(int posicaoVertical) {
		this.posicaoVertical = posicaoVertical;
	}
	/**
	 * @return the ultimaComunicacao
	 */
	public Calendar getUltimaComunicacao() {
		return ultimaComunicacao;
	}
	/**
	 * @param ultimaComunicacao the ultimaComunicacao to set
	 */
	public void setUltimaComunicacao(Calendar ultimaComunicacao) {
		this.ultimaComunicacao = ultimaComunicacao;
	}
	/**
	 * @return the laboratorio
	 */
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}
	/**
	 * @param laboratorio the laboratorio to set
	 */
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}
	
	
	
}

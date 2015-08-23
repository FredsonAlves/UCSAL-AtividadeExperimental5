/**
 * 
 */
package br.ucsal.projetoAE4.modelo;

/**
 * @author fredson
 *
 */
public class Laboratorio implements Comparable<Laboratorio>{
	private int idLaboratorio;
	private Campus campus;
	private String nomeLaboratorio;
	private int qteComputadoresVertical;
	private int qteComputadoresHorizontal;
	private String faixaRedeIPV4;
	/**
	 * @return the idLaboratorio
	 */
	public int getIdLaboratorio() {
		return idLaboratorio;
	}
	/**
	 * @param idLaboratorio the idLaboratorio to set
	 */
	public void setIdLaboratorio(int idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	/**
	 * @return the nomeLaboratorio
	 */
	public String getNomeLaboratorio() {
		return nomeLaboratorio;
	}
	/**
	 * @param nomeLaboratorio the nomeLaboratorio to set
	 */
	public void setNomeLaboratorio(String nomeLaboratorio) {
		this.nomeLaboratorio = nomeLaboratorio;
	}
	/**
	 * @return the qteComputadoresVertical
	 */
	public int getQteComputadoresVertical() {
		return qteComputadoresVertical;
	}
	/**
	 * @param qteComputadoresVertical the qteComputadoresVertical to set
	 */
	public void setQteComputadoresVertical(int qteComputadoresVertical) {
		this.qteComputadoresVertical = qteComputadoresVertical;
	}
	/**
	 * @return the qteComputadoresHorizontal
	 */
	public int getQteComputadoresHorizontal() {
		return qteComputadoresHorizontal;
	}
	/**
	 * @param qteComputadoresHorizontal the qteComputadoresHorizontal to set
	 */
	public void setQteComputadoresHorizontal(int qteComputadoresHorizontal) {
		this.qteComputadoresHorizontal = qteComputadoresHorizontal;
	}
	/**
	 * @return the faixaRedeIPV4
	 */
	public String getFaixaRedeIPV4() {
		return faixaRedeIPV4;
	}
	/**
	 * @param faixaRedeIPV4 the faixaRedeIPV4 to set
	 */
	public void setFaixaRedeIPV4(String faixaRedeIPV4) {
		this.faixaRedeIPV4 = faixaRedeIPV4;
	}
	/**
	 * @return the campus
	 */
	public Campus getCampus() {
		return campus;
	}
	/**
	 * @param campus the campus to set
	 */
	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Laboratorio o) {
		// TODO Auto-generated method stub
		return this.nomeLaboratorio.compareTo(o.getNomeLaboratorio());
	}

	public String toString() {
		return this.nomeLaboratorio;
	}
	
	
}

package br.ucsal.projetoAE4.modelo;

public class Campus implements Comparable<Campus> {
	private Long idCampus;
	private String nomeCampus;
	private String logradouro;
	private String complemento;
	private String numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;

	public Long getIdCampus() {
		return idCampus;
	}

	public void setIdCampus(Long idCampus) {
		this.idCampus = idCampus;
	}

	public String getNomeCampus() {
		return nomeCampus;
	}

	public void setNomeCampus(String nomeCampus) {
		this.nomeCampus = nomeCampus;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String l) {
		this.numero = l;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Campus o) {
		// TODO Auto-generated method stub
		return this.nomeCampus.compareTo(o.getNomeCampus());
	}

	public String toString() {
		return this.nomeCampus;
	}

	
}

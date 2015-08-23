package br.ucsal.projetoAE4.modelo;

public class Estado implements Comparable<Estado> {
	private String sigla;
	private String nomeEstado;

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	@Override
	public int compareTo(Estado o) {
		// TODO Auto-generated method stub
		return sigla.compareTo(o.getSigla());
	}
	
	public String toString() {
		return nomeEstado;
	}

}

package br.ucsal.projetoAE4.dao;

import java.util.List;

public interface iDAO<T> {
	   public void adiciona(T obj);
	   public List<T> getLista();
	   public List<T> getListaFiltrada(T obj);
	   T find(long id);
	   public void altera(T cobj);
	   public void remove(T obj);
	   public void closeConnection();
}

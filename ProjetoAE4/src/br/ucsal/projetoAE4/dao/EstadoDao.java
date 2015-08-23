package br.ucsal.projetoAE4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ucsal.projetoAE4.jdbc.ConnectionFactory;
import br.ucsal.projetoAE4.modelo.Campus;
import br.ucsal.projetoAE4.modelo.Estado;

public class EstadoDao  {

	// a conexão com o banco de dados
	private Connection connection;

	public EstadoDao() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void adiciona(Campus campus) {
		System.out.println("Não implementado...");	
	}

	public List<Estado> getLista() {
		try {
			List<Estado> estados = new ArrayList<Estado>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from estado");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Contato
				Estado estado = new Estado();
				estado.setSigla(rs.getString("sigla"));
				estado.setNomeEstado(rs.getString("nomeEstado"));

				// adicionando o objeto à lista
				estados.add(estado);
			}
			rs.close();
			stmt.close();
			return estados;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void altera(Campus campus) {
		System.out.println("Não implementada...");	
	}

	public void remove(Campus campus) {
		System.out.println("Não implementada...");
	}

	public Estado find(String sigla) {

		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from estado where sigla=?");
			stmt.setString(1, sigla);

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Estado estado = new Estado();
				estado.setSigla(rs.getString("sigla"));
				estado.setNomeEstado(rs.getString("nomeEstado"));
				return estado;
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public void closeConnection() {
		// TODO Auto-generated method stub
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

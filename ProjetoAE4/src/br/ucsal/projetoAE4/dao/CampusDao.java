package br.ucsal.projetoAE4.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.ucsal.projetoAE4.jdbc.ConnectionFactory;
import br.ucsal.projetoAE4.modelo.Campus;

public class CampusDao implements iDAO<Campus> {

	// a conexão com o banco de dados
	private Connection connection;

	public CampusDao() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void adiciona(Campus campus) {

		String sql = "insert into campus "
				+ "(nomeCampus,logradouro,complemento,numero,bairro, cidade, estado, cep)"
				+ " values (?,?,?,?,?,?,?,?)";
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);
			// seta os valores
			stmt.setString(1, campus.getNomeCampus());
			stmt.setString(2, campus.getLogradouro());
			stmt.setString(3, campus.getComplemento());
			stmt.setString(4, campus.getNumero());
			stmt.setString(5, campus.getBairro());
			stmt.setString(6, campus.getCidade());
			stmt.setString(7, campus.getEstado());
			stmt.setString(8, campus.getCep());
			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Campus> getLista() {
		try {
			List<Campus> listCampus = new ArrayList<Campus>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from campus");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Contato
				Campus campus = new Campus();
				campus.setIdCampus(rs.getLong("idCampus"));
				campus.setNomeCampus(rs.getString("nomeCampus"));
				campus.setLogradouro(rs.getString("logradouro"));
				campus.setComplemento(rs.getString("complemento"));
				campus.setNumero(rs.getString("numero"));
				campus.setBairro(rs.getString("bairro"));
				campus.setCidade(rs.getString("cidade"));
				campus.setEstado(rs.getString("estado"));
				campus.setCep(rs.getString("cep"));

				// adicionando o objeto à lista
				listCampus.add(campus);
			}
			rs.close();
			stmt.close();
			return listCampus;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void altera(Campus campus) {

		String sql = "update campus set nomeCampus=?, logradouro=?, complemento=?,"
				+ "numero=?, bairro=?, cidade=?, estado=?, cep=? where idCampus=?";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, campus.getNomeCampus());
			stmt.setString(2, campus.getLogradouro());
			stmt.setString(3, campus.getComplemento());
			stmt.setString(4, campus.getNumero());
			stmt.setString(5, campus.getBairro());
			stmt.setString(6, campus.getCidade());
			stmt.setString(7, campus.getEstado());
			stmt.setString(8, campus.getCep());
			stmt.setLong(9, campus.getIdCampus());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remove(Campus campus) {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("delete from campus where idCampus=?");
			stmt.setLong(1, campus.getIdCampus());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Campus find(long id) {

		try {
			PreparedStatement stmt = this.connection
			.prepareStatement("select * from campus where idCampus=?");
			stmt.setLong(1, Long.valueOf(id));

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Campus campus = new Campus();
				campus.setIdCampus(rs.getLong("idCampus"));
				campus.setNomeCampus(rs.getString("nomeCampus"));
				campus.setLogradouro(rs.getString("logradouro"));
				campus.setComplemento(rs.getString("complemento"));
				campus.setNumero(rs.getString("numero"));
				campus.setBairro(rs.getString("bairro"));
				campus.setCidade(rs.getString("cidade"));
				campus.setEstado(rs.getString("estado"));				
				campus.setCep(rs.getString("cep"));
				rs.close();
				stmt.close();
				return campus;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	public Campus findByName(String nameCampus) {

		try {
			PreparedStatement stmt = this.connection
			.prepareStatement("select * from campus where nomeCampus Like ?");
			stmt.setString(1, String.valueOf(nameCampus));

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Campus campus = new Campus();
				campus.setIdCampus(rs.getLong("idCampus"));
				campus.setNomeCampus(rs.getString("nomeCampus"));
				campus.setLogradouro(rs.getString("logradouro"));
				campus.setComplemento(rs.getString("complemento"));
				campus.setNumero(rs.getString("numero"));
				campus.setBairro(rs.getString("bairro"));
				campus.setCidade(rs.getString("cidade"));
				campus.setEstado(rs.getString("estado"));				
				campus.setCep(rs.getString("cep"));
				rs.close();
				stmt.close();
				return campus;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Campus UltimoRegistroInserido() {
		try {
		PreparedStatement stmt = this.connection
		//.prepareStatement("select * from campus WHERE idCampus=(select LAST_INSERT_ID(idCampus)) from campus)");
		.prepareStatement("select * from campus ORDER BY idCampus Desc limit 1");
		
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Campus campus = new Campus();
			campus.setIdCampus(rs.getLong("idCampus"));
			campus.setNomeCampus(rs.getString("nomeCampus"));
			campus.setLogradouro(rs.getString("logradouro"));
			campus.setComplemento(rs.getString("complemento"));
			campus.setNumero(rs.getString("numero"));
			campus.setBairro(rs.getString("bairro"));
			campus.setCidade(rs.getString("cidade"));
			campus.setEstado(rs.getString("estado"));				
			campus.setCep(rs.getString("cep"));
			rs.close();
			stmt.close();
			return campus;
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Campus> getListaFiltrada(Campus c) {
		try {
			List<Campus> listCampus = new ArrayList<Campus>();
			String filtro= "";

			//checa os filtros passados (se não for vazio vai adicionando à string Filtro
			if (!c.getNomeCampus().isEmpty()) {
				filtro += " AND nomeCampus Like '%" + c.getNomeCampus() +"%'"; 
			}
			if (!c.getBairro().isEmpty()) {
				filtro += " AND bairro  = '" + c.getBairro() + "'"; 
			}
			if (!c.getCidade().isEmpty()) {
				filtro += " AND cidade  Like '%" + c.getCidade() + "%'"; 
			}
			
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from campus Where 1 " + filtro);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Contato
				Campus campus = new Campus();
				campus.setIdCampus(rs.getLong("idCampus"));
				campus.setNomeCampus(rs.getString("nomeCampus"));
				campus.setLogradouro(rs.getString("logradouro"));
				campus.setComplemento(rs.getString("complemento"));
				campus.setNumero(rs.getString("numero"));
				campus.setBairro(rs.getString("bairro"));
				campus.setCidade(rs.getString("cidade"));
				campus.setEstado(rs.getString("estado"));
				campus.setCep(rs.getString("cep"));

				// adicionando o objeto à lista
				listCampus.add(campus);
			}
			rs.close();
			stmt.close();
			return listCampus;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	/* (non-Javadoc)
	 * @see br.ucsal.projetoAE4.dao.iDAO#closeConnection(java.sql.Connection)
	 */
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

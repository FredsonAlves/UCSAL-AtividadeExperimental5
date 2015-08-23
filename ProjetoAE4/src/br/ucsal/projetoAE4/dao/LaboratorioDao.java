package br.ucsal.projetoAE4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ucsal.projetoAE4.jdbc.ConnectionFactory;
import br.ucsal.projetoAE4.modelo.Laboratorio;

public class LaboratorioDao implements iDAO<Laboratorio> {

	// a conexão com o banco de dados
	private Connection connection;

	public LaboratorioDao() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void adiciona(Laboratorio laboratorio) {
		String sql = "insert into laboratorio "
				+ "(nomeLaboratorio,idCampus,qteComputadoresVertical,qteComputadoresHorizontal,faixaRedeIPV4)"
				+ " values (?,?,?,?,?)";
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);
			// seta os valores
			stmt.setString(1, laboratorio.getNomeLaboratorio());
			stmt.setLong(2, laboratorio.getCampus().getIdCampus());
			stmt.setInt(3, laboratorio.getQteComputadoresVertical());
			stmt.setInt(4, laboratorio.getQteComputadoresHorizontal());
			stmt.setString(5, laboratorio.getFaixaRedeIPV4());
			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Laboratorio> getLista() {
		try {
			List<Laboratorio> listLaboratorio = new ArrayList<Laboratorio>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from laboratorio");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Contato
				Laboratorio laboratorio = new Laboratorio();
				laboratorio.setIdLaboratorio(rs.getInt("idLaboratorio"));
				laboratorio.setNomeLaboratorio(rs.getString("nomeLaboratorio"));
				CampusDao campdao = new CampusDao();
				laboratorio
						.setCampus(campdao.find(rs.getInt("idCampus")));
				
				laboratorio.setQteComputadoresVertical(rs.getInt("qteComputadoresVertical"));
				laboratorio.setQteComputadoresHorizontal(rs.getInt("qteComputadoresHorizontal"));
				laboratorio.setFaixaRedeIPV4(rs.getString("faixaRedeIPV4"));
				listLaboratorio.add(laboratorio);
			}
			rs.close();
			stmt.close();
			return listLaboratorio;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void altera(Laboratorio laboratorio) {
		String sql = "update laboratorio set idCampus=?, nomeLaboratorio=?, qteComputadoresVertical=?,"
				+ "qteComputadoresHorizontal=?, faixaRedeIPV4=? where idLaboratorio=?";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setLong(1, laboratorio.getCampus().getIdCampus());
			stmt.setString(2, laboratorio.getNomeLaboratorio());
			stmt.setInt(3, laboratorio.getQteComputadoresVertical());
			stmt.setInt(4, laboratorio.getQteComputadoresHorizontal());
			stmt.setString(5, laboratorio.getFaixaRedeIPV4());
			stmt.setInt(6, laboratorio.getIdLaboratorio());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remove(Laboratorio laboratorio) {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("delete from laboratorio where idLaboratorio=?");
			stmt.setInt(1, laboratorio.getIdLaboratorio());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Laboratorio find(long id) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from laboratorio where idLaboratorio=?");
			stmt.setLong(1, Long.valueOf(id));

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Laboratorio laboratorio = new Laboratorio();
				laboratorio.setIdLaboratorio(rs.getInt("idLaboratorio"));
				laboratorio.setNomeLaboratorio(rs.getString("nomeLaboratorio"));
				CampusDao campdao = new CampusDao();
				laboratorio
						.setCampus(campdao.find(rs.getInt("idCampus")));
				laboratorio.setQteComputadoresVertical(rs.getInt("qteComputadoresVertical"));
				laboratorio.setQteComputadoresHorizontal(rs.getInt("qteComputadoresHorizontal"));
				laboratorio.setFaixaRedeIPV4(rs.getString("faixaRedeIPV4"));
				rs.close();
				stmt.close();
				return laboratorio;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Laboratorio UltimoRegistroInserido() {
		try {
			PreparedStatement stmt = this.connection
			// .prepareStatement("select * from laboratorio WHERE idLaboratorio=(select LAST_INSERT_ID(idLaboratorio)) from laboratorio)");
					.prepareStatement("select * from laboratorio ORDER BY idLaboratorio Desc limit 1");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Laboratorio laboratorio = new Laboratorio();
				laboratorio.setIdLaboratorio(rs.getInt("idLaboratorio"));
				laboratorio.setNomeLaboratorio(rs.getString("nomeLaboratorio"));
				CampusDao campdao = new CampusDao();
				laboratorio
						.setCampus(campdao.find(rs.getInt("idCampus")));
				laboratorio.setQteComputadoresVertical(rs.getInt("qteComputadoresVertical"));
				laboratorio.setQteComputadoresHorizontal(rs.getInt("qteComputadoresHorizontal"));
				laboratorio.setFaixaRedeIPV4(rs.getString("faixaRedeIPV4"));
				rs.close();
				stmt.close();
				return laboratorio;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Laboratorio> getListaFiltrada(Laboratorio c) {
		try {
			List<Laboratorio> listLaboratorio = new ArrayList<Laboratorio>();
			String filtro = "";

			// checa os filtros passados (se não for vazio vai adicionando à
			// string Filtro
			if (!c.getNomeLaboratorio().isEmpty()) {
				filtro += " AND nomeLaboratorio Like '%" + c.getNomeLaboratorio()
						+ "%'";
			}
			if (c.getCampus() != null) {
				filtro += " AND idCampus  Like '%" + c.getCampus().getIdCampus() + "'";
			}

			PreparedStatement stmt = this.connection
					.prepareStatement("select * from laboratorio Where 1 "
							+ filtro);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Contato
				Laboratorio laboratorio = new Laboratorio();
				laboratorio.setIdLaboratorio(rs.getInt("idLaboratorio"));
				laboratorio.setNomeLaboratorio(rs.getString("nomeLaboratorio"));
				CampusDao campdao = new CampusDao();
				laboratorio
						.setCampus(campdao.find(rs.getInt("idCampus")));
				laboratorio.setQteComputadoresVertical(rs.getInt("qteComputadoresVertical"));
				laboratorio.setQteComputadoresHorizontal(rs.getInt("qteComputadoresHorizontal"));
				laboratorio.setFaixaRedeIPV4(rs.getString("faixaRedeIPV4"));
				listLaboratorio.add(laboratorio);
			}
			rs.close();
			stmt.close();
			return listLaboratorio;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
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

	/**
	 * @author fredson
	 *
	 * @param text
	 * @return
	 */
	public Laboratorio findByName(String text) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from laboratorio where nomeLaboratorio=?");
			stmt.setString(1, String.valueOf(text));

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Laboratorio laboratorio = new Laboratorio();
				laboratorio.setIdLaboratorio(rs.getInt("idLaboratorio"));
				laboratorio.setNomeLaboratorio(rs.getString("nomeLaboratorio"));
				CampusDao campdao = new CampusDao();
				laboratorio
						.setCampus(campdao.find(rs.getInt("idCampus")));
				laboratorio.setQteComputadoresVertical(rs.getInt("qteComputadoresVertical"));
				laboratorio.setQteComputadoresHorizontal(rs.getInt("qteComputadoresHorizontal"));
				laboratorio.setFaixaRedeIPV4(rs.getString("faixaRedeIPV4"));
				rs.close();
				stmt.close();
				return laboratorio;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		

	}

}

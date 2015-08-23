package br.ucsal.projetoAE4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ucsal.projetoAE4.jdbc.ConnectionFactory;
import br.ucsal.projetoAE4.modelo.Computador;

public class ComputadorDao implements iDAO<Computador> {

	// a conexão com o banco de dados
	private Connection connection;

	public ComputadorDao() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void adiciona(Computador computador) {

		String sql = "insert into computador "
				+ "(nomeComputador,idLaboratorio,posicaoVertical,posicaoHorizontal,ultimaComunicacao)"
				+ " values (?,?,?,?,?)";
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);
			// seta os valores
			stmt.setString(1, computador.getNomeComputador());
			stmt.setInt(2, computador.getLaboratorio().getIdLaboratorio());
			stmt.setInt(3, computador.getPosicaoVertical());
			stmt.setInt(4, computador.getPosicaoHorizontal());
			if (computador.getUltimaComunicacao() == null) {
				stmt.setTimestamp(5, null);
			} else {
				stmt.setTimestamp(5, new java.sql.Timestamp(computador.getUltimaComunicacao().getTimeInMillis()));
			}
			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Computador> getLista() {
		try {
			List<Computador> listComputador = new ArrayList<Computador>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from computador");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Contato
				Computador computador = new Computador();
				computador.setIdComputador(rs.getInt("idComputador"));
				computador.setNomeComputador(rs.getString("nomeComputador"));

				computador.setPosicaoHorizontal(rs.getInt("posicaoHorizontal"));
				computador.setPosicaoVertical(rs.getInt("posicaoVertical"));

				if (rs.getTimestamp("ultimaComunicacao") != null) {
					// montando a data através do Calendar
					Calendar data = Calendar.getInstance();

					data.setTime(rs.getTimestamp("ultimaComunicacao"));
					computador.setUltimaComunicacao(data);
				}

				// adicionando objeto laboratorio ao objeto computador com base
				// no id
				LaboratorioDao labdao = new LaboratorioDao();
				computador.setLaboratorio(labdao.find(rs
						.getInt("idLaboratorio")));

				// adicionando o objeto à lista
				listComputador.add(computador);
			}
			rs.close();
			stmt.close();
			return listComputador;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void altera(Computador computador) {
		String sql = "update computador set nomeComputador=?, idLaboratorio=?, posicaoVertical=?,"
				+ "posicaoHorizontal=?, ultimaComunicacao=? where idComputador=?";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, computador.getNomeComputador());
			stmt.setInt(2, computador.getLaboratorio().getIdLaboratorio());
			stmt.setInt(3, computador.getPosicaoVertical());
			stmt.setInt(4, computador.getPosicaoHorizontal());
			if (computador.getUltimaComunicacao() != null) {
				stmt.setTimestamp(5, new java.sql.Timestamp(computador.getUltimaComunicacao().getTimeInMillis()));
			} else {
				stmt.setTimestamp(5, null);
			}
			stmt.setInt(6, computador.getIdComputador());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remove(Computador computador) {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("delete from computador where idComputador=?");
			stmt.setInt(1, computador.getIdComputador());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Computador find(long id) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from computador where idComputador=?");
			stmt.setLong(1, Long.valueOf(id));

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Computador computador = new Computador();
				computador.setIdComputador(rs.getInt("idComputador"));
				computador.setNomeComputador(rs.getString("nomeComputador"));

				computador.setPosicaoHorizontal(rs.getInt("posicaoHorizontal"));
				computador.setPosicaoVertical(rs.getInt("posicaoVertical"));

				if (rs.getTimestamp("ultimaComunicacao") != null) {
					// montando a data através do Calendar
					Calendar data = Calendar.getInstance();
					data.setTime(rs.getTimestamp("ultimaComunicacao"));
					computador.setUltimaComunicacao(data);
				}
				// adicionando objeto laboratorio ao objeto computador com base
				// no id
				LaboratorioDao labdao = new LaboratorioDao();
				computador.setLaboratorio(labdao.find(rs
						.getInt("idLaboratorio")));
				rs.close();
				stmt.close();
				return computador;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Computador UltimoRegistroInserido() {
		try {
			PreparedStatement stmt = this.connection
			// .prepareStatement("select * from computador WHERE idComputador=(select LAST_INSERT_ID(idComputador)) from computador)");
					.prepareStatement("select * from computador ORDER BY idComputador Desc limit 1");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Computador computador = new Computador();
				computador.setIdComputador(rs.getInt("idComputador"));
				computador.setNomeComputador(rs.getString("nomeComputador"));

				computador.setPosicaoHorizontal(rs.getInt("posicaoHorizontal"));
				computador.setPosicaoVertical(rs.getInt("posicaoVertical"));

				if (rs.getTimestamp("ultimaComunicacao") != null) {
					// montando a data através do Calendar
					Calendar data = Calendar.getInstance();
					data.setTime(rs.getTimestamp("ultimaComunicacao"));
					computador.setUltimaComunicacao(data);
				}
				// adicionando objeto laboratorio ao objeto computador com base
				// no id
				LaboratorioDao labdao = new LaboratorioDao();
				computador.setLaboratorio(labdao.find(rs
						.getInt("idLaboratorio")));
				rs.close();
				stmt.close();
				return computador;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Computador> getListaFiltrada(Computador c) {
		try {
			List<Computador> listComputador = new ArrayList<Computador>();
			String filtro = "";

			// checa os filtros passados (se não for vazio vai adicionando à
			// string Filtro
			if (!c.getNomeComputador().isEmpty()) {
				filtro += " AND nomeComputador Like '%" + c.getNomeComputador()
						+ "%'";
			}
			if (c.getLaboratorio() != null) {
				filtro += " AND idLaboratorio  = '"
						+ c.getLaboratorio().getIdLaboratorio() + "'";
			}

			PreparedStatement stmt = this.connection
					.prepareStatement("select * from computador Where 1 "
							+ filtro);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Contato
				Computador computador = new Computador();
				computador.setIdComputador(rs.getInt("idComputador"));
				computador.setNomeComputador(rs.getString("nomeComputador"));

				computador.setPosicaoHorizontal(rs.getInt("posicaoHorizontal"));
				computador.setPosicaoVertical(rs.getInt("posicaoVertical"));

				if (rs.getTimestamp("ultimaComunicacao") != null) {
					// montando a data através do Calendar
					Calendar data = Calendar.getInstance();
					data.setTime(rs.getTimestamp("ultimaComunicacao"));
					computador.setUltimaComunicacao(data);
				}
				// adicionando objeto laboratorio ao objeto computador com base
				// no id
				LaboratorioDao labdao = new LaboratorioDao();
				computador.setLaboratorio(labdao.find(rs
						.getInt("idLaboratorio")));
				listComputador.add(computador);
			}
			rs.close();
			stmt.close();
			return listComputador;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Computador findByName(String nome) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from computador where nomeComputador=?");
			stmt.setString(1, String.valueOf(nome));

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Computador computador = new Computador();
				computador.setIdComputador(rs.getInt("idComputador"));
				computador.setNomeComputador(rs.getString("nomeComputador"));

				computador.setPosicaoHorizontal(rs.getInt("posicaoHorizontal"));
				computador.setPosicaoVertical(rs.getInt("posicaoVertical"));

				if (rs.getTimestamp("ultimaComunicacao") != null) {
					// montando a data através do Calendar
					Calendar data = Calendar.getInstance();
					data.setTime(rs.getTimestamp("ultimaComunicacao"));
					computador.setUltimaComunicacao(data);
				}
				// adicionando objeto laboratorio ao objeto computador com base
				// no id
				LaboratorioDao labdao = new LaboratorioDao();
				computador.setLaboratorio(labdao.find(rs
						.getInt("idLaboratorio")));
				rs.close();
				stmt.close();
				return computador;
			}

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

package br.ucsal.projetoAE4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ucsal.projetoAE4.jdbc.ConnectionFactory;
import br.ucsal.projetoAE4.modelo.Utilizacao;

public class UtilizacaoDao implements iDAO<Utilizacao> {

	// a conexão com o banco de dados
	private Connection connection;

	public UtilizacaoDao() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void adiciona(Utilizacao utilizacao) {
		String sql = "insert into utilizacao "
				+ "(matriculaUtilizador,nomeUtilizador,idComputador,enderecoIPV4,dataHoraLogon, dataHoraLogoff)"
				+ " values (?,?,?,?,?,?)";
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);
			// seta os valores
			stmt.setString(1, utilizacao.getMatriculaUtilizador());
			stmt.setString(2, utilizacao.getNomeUtilizador());
			stmt.setInt(3, utilizacao.getComputador().getIdComputador());
			stmt.setString(4, utilizacao.getEnderecoIPV4());
			if (utilizacao.getDataHoraLogon() == null) {
				stmt.setTimestamp(5, null);
			} else {
				stmt.setTimestamp(5, new Timestamp(utilizacao
						.getDataHoraLogon().getTimeInMillis()));
			}
			if (utilizacao.getDataHoraLogoff() == null) {
				stmt.setDate(6, null);
			} else {
				stmt.setTimestamp(6, new Timestamp(utilizacao
						.getDataHoraLogoff().getTimeInMillis()));
			}

			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Utilizacao> getLista() {
		try {
			List<Utilizacao> listUtilizacao = new ArrayList<Utilizacao>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from utilizacao");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Contato
				Utilizacao utilizacao = new Utilizacao();
				utilizacao.setCodUtilizacao(rs.getInt("codUtilizacao"));
				utilizacao.setMatriculaUtilizador(rs
						.getString("matriculaUtilizador"));
				utilizacao.setNomeUtilizador(rs.getString("nomeUtilizador"));
				ComputadorDao labdao = new ComputadorDao();
				utilizacao
						.setComputador(labdao.find(rs.getInt("idComputador")));
				utilizacao.setEnderecoIPV4(rs.getString("enderecoIPV4"));

				// montando a data através do Calendar
				if (rs.getTimestamp("dataHoraLogon") != null) {
					Calendar data = Calendar.getInstance();
					data.setTime(rs.getTimestamp("dataHoraLogon"));
					utilizacao.setDataHoraLogon(data);
				}

				// montando a data através do Calendar
				if (rs.getTimestamp("dataHoraLogoff") != null) {
					Calendar data2 = Calendar.getInstance();
					data2.setTime(rs.getTimestamp("dataHoraLogoff"));
					utilizacao.setDataHoraLogon(data2);
				}

				// adicionando o objeto à lista
				listUtilizacao.add(utilizacao);
			}
			rs.close();
			stmt.close();
			return listUtilizacao;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void altera(Utilizacao utilizacao) {
		String sql = "update utilizacao set matriculaUtilizador=?, nomeUtilizador=?, idComputador=?,"
				+ "enderecoIPV4=?, dataHoraLogon=?, dataHoraLogoff=? where codUtilizacao=?";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, utilizacao.getMatriculaUtilizador());
			stmt.setString(2, utilizacao.getNomeUtilizador());
			stmt.setInt(3, utilizacao.getComputador().getIdComputador());
			stmt.setString(4, utilizacao.getEnderecoIPV4());
			if (utilizacao.getDataHoraLogon() == null) {
				stmt.setTimestamp(5, null);
			} else {
				stmt.setTimestamp(5, new Timestamp(utilizacao
						.getDataHoraLogon().getTimeInMillis()));
			}
			if (utilizacao.getDataHoraLogoff() == null) {
				stmt.setDate(6, null);
			} else {
				stmt.setTimestamp(6, new Timestamp(utilizacao
						.getDataHoraLogoff().getTimeInMillis()));
			}
			stmt.setInt(7, utilizacao.getCodUtilizacao());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remove(Utilizacao utilizacao) {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("delete from utilizacao where codUtilizacao=?");
			stmt.setInt(1, utilizacao.getCodUtilizacao());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Utilizacao find(long id) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from utilizacao where codUtilizacao=?");
			stmt.setLong(1, Long.valueOf(id));

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Utilizacao utilizacao = new Utilizacao();
				utilizacao.setCodUtilizacao(rs.getInt("codUtilizacao"));
				utilizacao.setMatriculaUtilizador(rs
						.getString("matriculaUtilizador"));
				utilizacao.setNomeUtilizador(rs.getString("nomeUtilizador"));
				ComputadorDao labdao = new ComputadorDao();
				utilizacao
						.setComputador(labdao.find(rs.getInt("idComputador")));
				utilizacao.setEnderecoIPV4(rs.getString("enderecoIPV4"));

				// montando a data através do Calendar
				if (rs.getTimestamp("dataHoraLogon") != null) {
					Calendar data = Calendar.getInstance();
					data.setTime(rs.getTimestamp("dataHoraLogon"));
					utilizacao.setDataHoraLogon(data);
				}

				// montando a data através do Calendar
				if (rs.getTimestamp("dataHoraLogoff") != null) {
					Calendar data2 = Calendar.getInstance();
					data2.setTime(rs.getTimestamp("dataHoraLogoff"));
					utilizacao.setDataHoraLogon(data2);
				}

				rs.close();
				stmt.close();
				return utilizacao;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Utilizacao UltimoRegistroInserido() {
		try {
			PreparedStatement stmt = this.connection
			// .prepareStatement("select * from utilizacao WHERE idUtilizacao=(select LAST_INSERT_ID(idUtilizacao)) from utilizacao)");
					.prepareStatement("select * from utilizacao ORDER BY codUtilizacao Desc limit 1");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Utilizacao utilizacao = new Utilizacao();
				utilizacao.setCodUtilizacao(rs.getInt("codUtilizacao"));
				utilizacao.setMatriculaUtilizador(rs
						.getString("matriculaUtilizador"));
				utilizacao.setNomeUtilizador(rs.getString("nomeUtilizador"));
				ComputadorDao labdao = new ComputadorDao();
				utilizacao
						.setComputador(labdao.find(rs.getInt("idComputador")));
				utilizacao.setEnderecoIPV4(rs.getString("enderecoIPV4"));

				// montando a data através do Calendar
				if (rs.getTimestamp("dataHoraLogon") != null) {
					Calendar data = Calendar.getInstance();
					data.setTime(rs.getTimestamp("dataHoraLogon"));
					utilizacao.setDataHoraLogon(data);
				}

				// montando a data através do Calendar
				if (rs.getTimestamp("dataHoraLogoff") != null) {
					Calendar data2 = Calendar.getInstance();
					data2.setTime(rs.getTimestamp("dataHoraLogoff"));
					utilizacao.setDataHoraLogon(data2);
				}

				rs.close();
				stmt.close();
				return utilizacao;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Utilizacao> getListaFiltrada(Utilizacao c) {
		try {
			List<Utilizacao> listUtilizacao = new ArrayList<Utilizacao>();
			String filtro = "";

			// checa os filtros passados (se não for vazio vai adicionando à
			// string Filtro
			if (!c.getMatriculaUtilizador().isEmpty()) {
				filtro += " AND matriculaUtilizador Like '%"
						+ c.getMatriculaUtilizador() + "%'";
			}
			/*
			 * SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			 * Date data = formatador.parse();
			 * 
			 * Calendar dataCalendar = Calendar.getInstance();
			 * dataCalendar.setTime(data);
			 */
			if (c.getDataHoraLogon() != null) {
				SimpleDateFormat formatador = new SimpleDateFormat("yyyy/mm/dd");
				filtro += " AND dataHoraLogon  = '"
						+ formatador.format(c.getDataHoraLogon()) + "'";
			}

			if (c.getComputador() != null) {
				filtro += " AND idComputador  = '"
						+ c.getComputador().getIdComputador() + "'";
			}

			PreparedStatement stmt = this.connection
					.prepareStatement("select * from utilizacao Where 1 "
							+ filtro);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Contato
				Utilizacao utilizacao = new Utilizacao();
				utilizacao.setCodUtilizacao(rs.getInt("codUtilizacao"));
				utilizacao.setMatriculaUtilizador(rs
						.getString("matriculaUtilizador"));
				utilizacao.setNomeUtilizador(rs.getString("nomeUtilizador"));
				ComputadorDao labdao = new ComputadorDao();
				utilizacao
						.setComputador(labdao.find(rs.getInt("idComputador")));
				utilizacao.setEnderecoIPV4(rs.getString("enderecoIPV4"));

				// montando a data através do Calendar
				if (rs.getTimestamp("dataHoraLogon") != null) {
					Calendar data = Calendar.getInstance();
					data.setTime(rs.getTimestamp("dataHoraLogon"));
					utilizacao.setDataHoraLogon(data);
				}

				// montando a data através do Calendar
				if (rs.getTimestamp("dataHoraLogoff") != null) {
					Calendar data2 = Calendar.getInstance();
					data2.setTime(rs.getTimestamp("dataHoraLogoff"));
					utilizacao.setDataHoraLogon(data2);
				}
				// adiciona objeto atual à lista
				listUtilizacao.add(utilizacao);
			}
			rs.close();
			stmt.close();
			return listUtilizacao;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Utilizacao> getByComputador(Utilizacao c) {
		try {
			List<Utilizacao> listUtilizacao = new ArrayList<Utilizacao>();
			String filtro = "";

			if (c.getDataHoraLogoff() == null) {
				filtro += " AND dataHoraLogoff IS NULL";
			}

			if (c.getMatriculaUtilizador() != null) {
				filtro += " AND matriculaUtilizador ='"+c.getMatriculaUtilizador()+"'";
			}
			
			
			if (c.getComputador() != null) {
				filtro += " AND idComputador  = '"
						+ c.getComputador().getIdComputador() + "'";
			}

			PreparedStatement stmt = this.connection
					.prepareStatement("select * from utilizacao Where 1 "
							+ filtro);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Contato
				Utilizacao utilizacao = new Utilizacao();
				utilizacao.setCodUtilizacao(rs.getInt("codUtilizacao"));
				utilizacao.setMatriculaUtilizador(rs
						.getString("matriculaUtilizador"));
				utilizacao.setNomeUtilizador(rs.getString("nomeUtilizador"));
				ComputadorDao labdao = new ComputadorDao();
				utilizacao
						.setComputador(labdao.find(rs.getInt("idComputador")));
				utilizacao.setEnderecoIPV4(rs.getString("enderecoIPV4"));

				// montando a data através do Calendar
				if (rs.getTimestamp("dataHoraLogon") != null) {
					Calendar data = Calendar.getInstance();
					data.setTime(rs.getTimestamp("dataHoraLogon"));
					utilizacao.setDataHoraLogon(data);
				}

				// montando a data através do Calendar
				if (rs.getTimestamp("dataHoraLogoff") != null) {
					Calendar data2 = Calendar.getInstance();
					data2.setTime(rs.getTimestamp("dataHoraLogoff"));
					utilizacao.setDataHoraLogon(data2);
				}
				// adiciona objeto atual à lista
				listUtilizacao.add(utilizacao);
			}
			rs.close();
			stmt.close();
			return listUtilizacao;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public int UltimoID() {
		int ultimo=0;
		try {
		PreparedStatement stmt = this.connection
		//.prepareStatement("select * from campus WHERE idCampus=(select LAST_INSERT_ID(idCampus)) from campus)");
		.prepareStatement("select * from utilizacao ORDER BY codUtilizacao Desc limit 1");
		
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			ultimo = rs.getInt("codUtilizacao");
			rs.close();
			stmt.close();
			return ultimo;
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ultimo;
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

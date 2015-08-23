package br.ucsal.projetoAE4.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.ucsal.projetoAE4.uteis.LeitorXML;

public class ConnectionFactory {
	private static Connection conn;
	LeitorXML lt;
	String enderecoConfigurado=null;
	//porta padrão 12345
	String usuarioConfigurado=null;			
	String senhaConfigurada=null;
	String bdConfigurado=null;
	
	//throws IOExcepetio par ao leitor XML
	public Connection getConnection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Acesso Casa
			
/*			if (conn.isValid(0)) {
				conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/projetoAE4", "ucsal", "ucsal");
			}*/
			//buscando dados de configuração no arquivo XML
			lt = new LeitorXML("c:\\ModuloClienteAE4\\configModuloCliente.xml");
			// Acesso BB
			// return DriverManager.getConnection("jdbc:mysql://lab01.intrabb.bb.com.br/projetoAE4", "f3530309", "272602");
		} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			//não tem módulocliente, tenta módulo servidor
			try {
				lt = new LeitorXML("c:\\ModuloServidorAE4\\configModuloServidor.xml");
			} catch (IOException | ParserConfigurationException | SAXException e1) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		enderecoConfigurado = lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoBancodeDados"), "nomeIPServidor", 1);
		usuarioConfigurado = lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoBancodeDados"), "usuario", 1);
		senhaConfigurada = lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoBancodeDados"), "senha", 1);
		bdConfigurado = lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoBancodeDados"), "bancodeDados", 1);
		
		try {
			if(conn == null || conn.isClosed()){  
				conn =  DriverManager.getConnection(
						"jdbc:mysql://"+enderecoConfigurado+"/"+bdConfigurado, usuarioConfigurado, senhaConfigurada);
						//"jdbc:mysql://192.168.1.200/projetoAE4", "ucsal", "ucsal");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  		
		 
		return conn;
	}

}

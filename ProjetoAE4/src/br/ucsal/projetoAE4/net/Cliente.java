package br.ucsal.projetoAE4.net;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.ucsal.projetoAE4.uteis.LeitorXML;

public class Cliente {
	
	private OutputStream saida;
	Socket cliente;

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		/*sequência para criar o serviço de mensagens propriamente dito */
		//abre conexão com o servidor de mensagens Porta 12345
		//con = new Cliente("192.168.1.4", 12345);
		LeitorXML lt;
		String enderecoConfigurado = null;
		int portaConfigurada = 12346; //porta padrão...
		try {
			lt = new LeitorXML("c:\\ModuloClienteAE4\\configModuloCliente.xml");
			enderecoConfigurado = lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoServidorChat"), "enderecoIPChat", 1);
			portaConfigurada = Integer.parseInt(lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoServidorChat"), "portaServidorChat", 1));
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		// dispara cliente
		new Cliente(enderecoConfigurado, portaConfigurada).executa();
	}

	private String host;
	private int porta;

	public Cliente(String host, int porta) {
		this.host = host;
		this.porta = porta;
	}

	public void executa() throws UnknownHostException, IOException,ConnectException  {
		cliente = new Socket(this.host, this.porta);
		System.out.println("O cliente se conectou ao servidor!");

		// lê msgs do teclado e manda pro servidor
		//Scanner teclado = new Scanner(System.in);
		saida = cliente.getOutputStream();

		//Scanner teclado = new Scanner(System.in);
		
		//PrintStream ps = new PrintStream(saida);
		//ps.println(User.getUserLogado());
		//ps.close();
		
		/*
		while (teclado.hasNextLine()) {
			saida.println(teclado.nextLine());
		}*/

		//saida.close();
		//teclado.close();
		//cliente.close();

		/*
		while (teclado.hasNextLine()) {
			saida.println(teclado.nextLine());
		}*/

		//saida.close();
		//teclado.close();
		//cliente.close();
	}

	/**Fecha a conexão socket aberta para o objeto
	 * @author fredson
	 * @return void
	 */

	public void closeConexaoSocket() {
		try {
			this.cliente.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the saida
	 */
	public OutputStream getSaida() {
		return saida;
	}
	

	/**
	 * @param saida the saida to set
	 */
	public void setSaida(OutputStream saida) {
		this.saida = saida;
	}
	
	public Recebedor criaRecebedor(JTextArea j) throws IOException {
		// thread para receber mensagens do servidor
		Recebedor r = new Recebedor(cliente.getInputStream(), j);
		return r;
	}
	
	public void executaRecebedor(Recebedor r) {
		new Thread(r).start();
	}
}
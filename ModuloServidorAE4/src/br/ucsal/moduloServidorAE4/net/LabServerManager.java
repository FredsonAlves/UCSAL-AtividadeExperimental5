package br.ucsal.moduloServidorAE4.net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import br.ucsal.moduloServidorAE4.generica.InformacaoCliente;
import br.ucsal.projetoAE4.dao.ComputadorDao;
import br.ucsal.projetoAE4.modelo.Computador;
import br.ucsal.projetoAE4.uteis.Uteis;

public class LabServerManager {
	private int porta;
	@SuppressWarnings("unused")
	private List<PrintStream> clientes;
	private ServerSocket servidor;
	private Scanner s;
	HashMap<String, Integer> recusas = new HashMap<String, Integer>();
	// private HashMap<String, InformacaoCliente> MapaClientes;

	public LabServerManager(int porta) {
		this.porta = porta;
		this.clientes = new ArrayList<PrintStream>();
	}

	public void executa() throws IOException {
		//desconecta todos os usuário que estejam conectados no momento da inicialização do servidor
		ConexaoCliente.desconectaUtilizador(null);
	
		
		servidor = new ServerSocket(this.porta);
		System.out.println(Uteis.obterDataHoraAtual()+": Conexão servidor aberta na porta 12345!");

		while (true) {
			// aceita um cliente
			Socket cliente = servidor.accept();
			System.out.println(cliente.getInetAddress().getHostAddress());
			PrintStream ps = new PrintStream(cliente.getOutputStream());
			if (recusas.containsKey(cliente.getInetAddress().getHostAddress())) {
				if (recusas.get(cliente.getInetAddress().getHostAddress())
						.intValue() <= 4) {
					// if (cliente.getInetAddress().getHostAddress()
					// .equals("192.168.0.6")) {
					// Pergunta o nome do host ao cliente
					System.out.println(Uteis.obterDataAtual()+": Desconectando cliente - IP: " + cliente.getInetAddress().getHostAddress());
					ps.println("AcessoNegado");
					System.out.println("Contagem Atual: "
							+ recusas.get(
									cliente.getInetAddress().getHostAddress())
									.intValue());
					//recusas.replace(cliente.getInetAddress().getHostAddress(),(recusas.get(cliente.getInetAddress().getHostAddress()).intValue() + 1));
					recusas.put(cliente.getInetAddress().getHostAddress(),(recusas.get(cliente.getInetAddress().getHostAddress()).intValue() + 1));
					cliente.close();
				} else {
					recusas.remove(cliente.getInetAddress().getHostAddress());
					System.out.println(Uteis.obterDataHoraAtual()+": Excluiu o cliente do mapa.");
					cliente.close();
//					continue;
				}
			} else if (cliente.isConnected()) {
				System.out.println(Uteis.obterDataHoraAtual()+": Nova conexão com o cliente "
						+ cliente.getInetAddress().getHostAddress());

				// this.clientes.add(ps);

				InformacaoCliente infCliente = new InformacaoCliente();
				infCliente.setIpCliente(cliente.getInetAddress()
						.getHostAddress());

//				PrintStream ps = new PrintStream(cliente.getOutputStream());
				// Pergunta o nome do host ao cliente
				System.out
						.println(Uteis.obterDataHoraAtual()+": Enviando pergunta para o cliente - NomeHost?  ");
				ps.println("NomeHost?");

				s = new Scanner(cliente.getInputStream());
				// while (s.hasNextLine()) {
				String Resposta = s.nextLine();
				infCliente.setNomeCliente(Resposta);
				// mostrando o nome do cliente - para depuração...
				System.out.println("Nome do Cliente respondido: " + infCliente.getNomeCliente());

				// checando se o computador ora conectado está cadastrado...
				ComputadorDao compdao = new ComputadorDao();
				Computador comp = compdao.findByName(infCliente
						.getNomeCliente());
				if (comp != null) {
					// cria tratador de cliente numa nova thread
					ConexaoCliente tc = new ConexaoCliente(
							cliente.getInputStream(), ps, infCliente);
					Thread th = new Thread(tc, "ThreadCliente_"
							+ infCliente.getNomeCliente());
					th.start();
				} else {
					ps.println(Uteis.obterDataHoraAtual()+": O computador " + infCliente.getNomeCliente() +" não está cadastrado...Desconectando...");
					recusas.put(infCliente.getIpCliente(), 1);
					cliente.close();
				}
				//fecha a conexao
				//compdao.closeConnection();
			}

		}

	}

}
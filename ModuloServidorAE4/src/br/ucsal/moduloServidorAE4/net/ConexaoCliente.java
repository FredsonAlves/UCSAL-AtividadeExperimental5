package br.ucsal.moduloServidorAE4.net;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import br.ucsal.moduloServidorAE4.generica.InformacaoCliente;
import br.ucsal.projetoAE4.dao.ComputadorDao;
import br.ucsal.projetoAE4.dao.UtilizacaoDao;
import br.ucsal.projetoAE4.modelo.Computador;
import br.ucsal.projetoAE4.modelo.Utilizacao;
import br.ucsal.projetoAE4.uteis.Uteis;

public class ConexaoCliente implements Runnable {

	private InputStream clienteIn;
	private PrintStream clienteOut;
	private InformacaoCliente infCliente;
	private Scanner s;

	public ConexaoCliente(InputStream clienteIn, PrintStream clienteOut,
			InformacaoCliente infCliente) {
		this.clienteIn = clienteIn;
		this.clienteOut = clienteOut;
		this.infCliente = infCliente;
	}

	public void run() {

		s = new Scanner(this.clienteIn);
		ComputadorDao compdao = new ComputadorDao();
		//inicia o loop para aceitar conexões dos clientes...
		while (true) {
			// pergunta se o cliente ainda está online
			System.out.println("Enviando pergunta para o cliente " + infCliente.getNomeCliente() +" - Online?");
			clienteOut.println("Online?");
			
			try {
				if (s.nextLine().equals("OK")) {
					System.out.println("Cliente Respondeu OK");
					try {
						Computador comp = compdao.findByName(this.infCliente.getNomeCliente());
						if (comp != null) {
							Calendar dataAtual = Calendar.getInstance();
							dataAtual.setTime(new Date());
							comp.setUltimaComunicacao(dataAtual);
							
							compdao.altera(comp);

							
						} else {
							System.out.println(Uteis.obterDataHoraAtual()+": Computador " + infCliente.getNomeCliente() + " não cadastrado... Encerrando conexão.");
							//saindo do loop e encerrando a thread consequentemente...
							break;
						}
						Thread.sleep(120000);
					} catch (InterruptedException e) {
						//derrubando registros de usuários conectados
						Computador comp = compdao.findByName(this.infCliente.getNomeCliente());
						if (comp != null) {
							desconectaUtilizador(comp);
						}
						//Thread.sleep(120000);
						//System.out.println("Exceção na pausa");
						e.printStackTrace();
						break;
					}
					
					System.out.println(Uteis.obterDataHoraAtual()+": Cliente "+infCliente.getNomeCliente() + "  ainda online.");
				} else {
					System.out.println(Uteis.obterDataHoraAtual()+": A resposta do cliente "+infCliente.getNomeCliente() + " veio diferente de OK, testando novamente.");
					clienteOut.println("Online?");
				}
			} catch (NoSuchElementException e) {

				System.out.println(Uteis.obterDataHoraAtual()+": Provavelmente o cliente "+infCliente.getNomeCliente()+" caiu. Data/Hora:");
				//derrubando registros de usuários conectados
				Computador comp = compdao.findByName(this.infCliente.getNomeCliente());
				if (comp != null) {
					desconectaUtilizador(comp);
				}
				break;
			}
			
				
			//}
			
		}
		//fecha conexão na saída
		//compdao.closeConnection();
	}
	
	public static void desconectaUtilizador(Computador computador) {
		
			UtilizacaoDao utilizDao = new UtilizacaoDao();
			Utilizacao utiliz = new Utilizacao();
			
			if (computador != null) {
				utiliz.setComputador(computador);
			}
			
			ArrayList<Utilizacao> listaObtida = (ArrayList<Utilizacao>) utilizDao.getByComputador(utiliz);
			
			if (listaObtida.size() >= 1) {
				for (Utilizacao utilizacao : listaObtida) {
					Calendar dataAtual = Calendar.getInstance();
					dataAtual.setTime(new Date());
					utilizacao.setDataHoraLogoff(dataAtual);
					utilizDao.altera(utilizacao);
				}
				
			}
			//fecha conexão 
			//utilizDao.closeConnection();
	}	
}
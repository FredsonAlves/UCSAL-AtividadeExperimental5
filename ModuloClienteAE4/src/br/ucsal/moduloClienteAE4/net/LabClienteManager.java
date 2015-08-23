package br.ucsal.moduloClienteAE4.net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class LabClienteManager {
	private String host;
	private int porta;

	public LabClienteManager(String host, int porta) {
		this.host = host;
		this.porta = porta;
	}

	public void executa() throws UnknownHostException, IOException {
		Socket cliente = new Socket(this.host, this.porta);
		Calendar dt = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String DataFormatada = df.format(dt.getTimeInMillis());
		
		System.out.println("O cliente se conectou ao servidor em: "+DataFormatada);
		
		// thread para receber mensagens do servidor
		// ClienteResponser r = new ClienteResponser(cliente.getInputStream());
		// new Thread(r).start();

		// lê msgs do teclado e manda pro servidor
		@SuppressWarnings("resource")
		Scanner teclado = new Scanner(cliente.getInputStream());
		PrintStream saida = new PrintStream(cliente.getOutputStream());

		while (teclado.hasNextLine()) {
			String Pergunta = teclado.nextLine();
			if (Pergunta.equals("NomeHost?")) {
				System.out.println("Indagado nome do host pelo servidor");
				saida.println(System.getenv("COMPUTERNAME"));
				System.out.println("Enviado nome:"
						+ System.getenv("COMPUTERNAME"));
				// }
			} else if (Pergunta.equals("Online?")) {
				dt = Calendar.getInstance();
				df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				DataFormatada = df.format(dt.getTimeInMillis());
				System.out.println("Servidor perguntado se estou ON Line em: "+DataFormatada);
				saida.println("OK");
				System.out.println("Enviado resposta: OK");
			} else if (Pergunta.equals("AcessoNegado")) {
				System.out.println("Servidor negou o acesso...");
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("Veio pergunta diferente...");

			}
		}
		saida.close();
		// teclado.close();
		cliente.close();

	}
	
}
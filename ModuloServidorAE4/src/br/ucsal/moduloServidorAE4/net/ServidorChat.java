package br.ucsal.moduloServidorAE4.net;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.ucsal.moduloServidorAE4.generica.LeitorXML;
import br.ucsal.projetoAE4.net.Mensagem;
import br.ucsal.projetoAE4.uteis.Uteis;


public class ServidorChat implements Runnable  {
	static int numero=1;
	private int porta;
	private List<OutputStream> clientes;
	private ServerSocket servidor;
	Map<Thread, OutputStream> associacaoThreadOutput = new HashMap<Thread, OutputStream>();
	Map<String, OutputStream> associacaoUsuarioOutput = new HashMap<String, OutputStream>();
/*
	public static void main(String[] args) throws IOException {
		// inicia o servidor
		new ServidorChat(12346).executa();
	}*/
	
	@Override
	public void run()  {	
		// inicia o servidor
		LeitorXML lt;
		//porta padrão 12345
		int portaConfigurada=12346;
		try {
			lt = new LeitorXML("c:\\ModuloServidorAE4\\configModuloServidor.xml");
			portaConfigurada = Integer.parseInt(lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoServidor"), "portaServidorChat", 1));
			this.porta  =portaConfigurada; 
			//new ServidorChat().executa();
			executa();
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
	}


	
	public ServidorChat() {
		this.clientes = new ArrayList<OutputStream>();
	}

	public void executa() throws IOException {
		servidor = new ServerSocket(this.porta);
		System.out.println("Porta 12346 aberta!");

		while (true) {
			// aceita um cliente
			Socket cliente = servidor.accept();

			System.out.println(Uteis.obterDataHoraAtual()+": Servidor de Chat - Nova conexão com o cliente "
					+ cliente.getInetAddress().getHostAddress());

			OutputStream  ostream = cliente.getOutputStream();
			
			//DESATIVADO POR FRDSON - Scanner s = new Scanner(cliente.getInputStream());

			//while(s.hasNextLine()) {
				// DESATIVADO POR FRDSON - associacaoUsuarioOutput.put(s.nextLine(), ostream);
				//s.close();
			//}
			
			
			// adiciona saida do cliente à lista
			
			this.clientes.add(ostream);

			// cria tratador de cliente numa nova thread
			TrataClienteChat tc = new TrataClienteChat(cliente.getInputStream(), this);
			
			Thread th = new Thread(tc,"cliente" + numero);
			th.start();
			associacaoThreadOutput.put(th, ostream);
			
			numero++;
		
		}

	}

	public void excluidaLista() {
		
	}
	
	/**
	 * @author fredson
	 *
	 * @param msg
	 * @throws IOException 
	 */
	public void distribuiMensagem(Mensagem msg) throws IOException {
		// TODO Auto-generated method stub
		// envia msg para todo mundo

		for (Thread mapa : associacaoThreadOutput.keySet()) {
			if (!mapa.isAlive()) {
				this.clientes.remove(associacaoThreadOutput.get(mapa));
				//associacaoThreadOutput.remove(mapa); -->gera exceção
			}
		}

		
		for (OutputStream cliente : this.clientes) {
			ObjectOutputStream obj;
			try {
				obj = new ObjectOutputStream(cliente);
				obj.writeObject(msg);
				obj.flush();
							
			} catch (SocketException e) {
				System.out.println(Uteis.obterDataHoraAtual()+": Cliente com socket fechado...");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//cliente.println(obj);
			
			
			//cliente.print(msg);
		}
		
	}

}
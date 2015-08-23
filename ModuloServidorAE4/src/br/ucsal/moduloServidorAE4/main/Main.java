package br.ucsal.moduloServidorAE4.main;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.ucsal.moduloServidorAE4.generica.LeitorXML;
import br.ucsal.moduloServidorAE4.net.LabServerManager;
import br.ucsal.moduloServidorAE4.net.ServidorChat;

public class Main {

	public static void main(String[] args) throws IOException {

		ServidorChat runChat = new ServidorChat();
		Thread thChat = new Thread(runChat, "Carregador do Servidor de Chat");
		thChat.start();

		/*iniciando Servidor de controle de uso dos micros do laboratório */

		LeitorXML lt;
		//porta padrão 12345
		int portaConfigurada=12345;
		try {
			lt = new LeitorXML("c:\\ModuloServidorAE4\\configModuloServidor.xml");
			portaConfigurada = Integer.parseInt(lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoServidor"), "portaServidorControle", 1));
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// inicia o servidor na porta 12345
		new LabServerManager(portaConfigurada).executa();
	}
	
	/**Método de saída - Usado no momento do encerramento do serviço**/
	public static void exit(String args[])  {
		System.exit(0);
	}
}

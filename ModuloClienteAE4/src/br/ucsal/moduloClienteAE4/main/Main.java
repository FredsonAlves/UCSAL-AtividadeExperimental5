package br.ucsal.moduloClienteAE4.main;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.ucsal.moduloClienteAE4.net.LabClienteManager;
import br.ucsal.moduloClienteAE4.uteis.LeitorXML;

public class Main {

	public static void main(String[] args) throws UnknownHostException,
	IOException {

// new LabClienteManager("127.0.0.1", 12345).executa();
	LeitorXML lt;
	String enderecoConfigurado=null;
	//porta padrão 12345
	int portaConfigurada=12345;
	try {
		lt = new LeitorXML("c:\\ModuloClienteAE4\\configModuloCliente.xml");
		enderecoConfigurado = lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoServidor"), "enderecoIP", 1);
		portaConfigurada = Integer.parseInt(lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoServidor"), "portaServidor", 1));
	} catch (ParserConfigurationException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SAXException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
		
while (true) {
	try {
		System.out.println("Tentando conexão...");
		
		new LabClienteManager(enderecoConfigurado, portaConfigurada).executa();
		// new LabClienteManager("127.0.0.1", 12345).executa();
	} catch (Exception e) {
		e.printStackTrace();
		Calendar dt = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String DataFormatada = df.format(dt.getTimeInMillis());
		
		System.out.println("Lançou exceção e continou em: "+DataFormatada);
		continue;
	}
}
}
	
	
	static void exit(String args[]) {
		System.exit(0);
	}
	

}

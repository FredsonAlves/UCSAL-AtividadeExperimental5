package br.ucsal.projetoAE4.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.SocketException;

import javax.swing.JTextArea;

public class Recebedor implements Runnable {

	private InputStream servidor;
	JTextArea AreaMsg;
	Mensagem msgRec;

	public Recebedor(InputStream servidor, JTextArea j) {
		this.servidor = servidor;
		this.AreaMsg = j;
	}

	public void run() {
		// s = new Scanner(this.servidor);
		
		while (true) {
			
			try {
				ObjectInputStream  obj = new ObjectInputStream(this.servidor);
				Mensagem message = (Mensagem) obj.readObject();

				// System.out.println(s.nextLine());
				AreaMsg.append("\n" + message.getUserOrigem() + ": " + message.getTextoMensagem());
				AreaMsg.setCaretPosition(AreaMsg.getText().length());

			} catch (SocketException e) {
				//sai do loop, fecha a thread
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
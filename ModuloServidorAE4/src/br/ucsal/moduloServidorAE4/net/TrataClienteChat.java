package br.ucsal.moduloServidorAE4.net;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import br.ucsal.projetoAE4.net.Mensagem;

 public class TrataClienteChat implements Runnable {
 
   private InputStream cliente;
   private ServidorChat servidor;
 
   public TrataClienteChat(InputStream cliente, ServidorChat servidor) {
     this.cliente = cliente;
     this.servidor = servidor;
   }
   
   public void run() {
	   while(true) {
		   ObjectInputStream obj = null;
		try {
			obj = new ObjectInputStream(this.cliente);			
			Mensagem msg = (Mensagem) obj.readObject();
			servidor.distribuiMensagem(msg);
		} catch (EOFException e) {
			// TODO Auto-generated catch block
			try {
				this.cliente.close();
				break;
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			break; //ultima alteração em 03/06 - verificar estabilidade
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			break; //ultima alteração em 03/06 - verificar estabilidade
		}
     }
	 
	 //obj.close();
   }
 }
package br.ucsal.projetoAE4.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import br.ucsal.projetoAE4.uteis.LinhadeComando;
import br.ucsal.projetoAE4.uteis.Uteis;


public class Rede {
		private String ip;
		private String nomeHost;

		public Rede() {
			LinhadeComando cmd = new LinhadeComando();
			cmd.execute("route print");
		    StringBuffer resultadoComando = new StringBuffer(cmd.getStdinData());
		    resultadoComando.replace(0, resultadoComando.indexOf("Rotas ativas:"), "");
		    resultadoComando.replace(0 , resultadoComando.indexOf("Endere"), "");
		    int PosicaoInterface =   resultadoComando.indexOf(" Interface  ");
		    resultadoComando.replace(0, resultadoComando.indexOf("          0.0.0.0          0.0.0.0"), "");
		    resultadoComando.replace(0, PosicaoInterface, "");
		    this.ip = (resultadoComando.substring(0, 15).trim());
		    this.nomeHost = System.getenv("COMPUTERNAME");
		}
		
		
		public Rede(String EndRede) {
			if (Uteis.isIpValido(EndRede, 4)) {
			
				this.nomeHost = System.getenv("COMPUTERNAME");
				try {
					String ip = null;
					//String nomeMaquina = null;
					
					//InetAddress addr = InetAddress.getLocalHost();
	
					InetAddress arrAddr[] = InetAddress.getAllByName(this.nomeHost);
				
					for (InetAddress addr : arrAddr) {
						
						//O usuário tem que passar o endereço de rede  
						if (addr.getHostAddress().contains(EndRede.substring(0,EndRede.lastIndexOf(".")+1))) {  
							ip=addr.getHostAddress();
			                  	//nomeMaquina = addr.getHostName();
			              }
					}
					this.ip = ip;
					//this.nomeHost = nomeMaquina;
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				this.ip = null;
			}
		}
		
/**
		 * @return Retorna o IPv4 da máquina
		 */
		public String getIp() {
			return ip;
		}



		/**
		 * @return Retorna o nome da máquina
		 */
		public String getNomeHost() {
			return nomeHost;
		}

		public static void main(String[] args) {
			//Rede rede = new Rede("192.168.1.0");
			Rede rede = new Rede();
			System.out.println(rede.getNomeHost());
			System.out.println(rede.getIp());
			
	        
	
		}

}

package br.ucsal.projetoAE4.net;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.ucsal.projetoAE4.uteis.LeitorXML;

public class LoginLDAP {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Boolean login(String usuario, String senha) {
		
		//LENDO CONFIGURAÇÕES DO LDAP DO ARQUIVO XML DE CONFIGURAÇÃO
		LeitorXML lt;
		String enderecoConfigurado=null;
		String dominioConfigurado=null;
		//porta padrão 389
		int portaConfigurada=389;
		try {
			lt = new LeitorXML("c:\\ModuloClienteAE4\\configModuloCliente.xml");
			enderecoConfigurado = lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoLDAP"), "enderecoIP", 1);
			portaConfigurada = Integer.parseInt(lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoLDAP"), "portaServidorLDAP", 1));
			dominioConfigurado = lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoLDAP"), "dominioLDAP", 1);
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

		
		Hashtable authEnv = new Hashtable(11);
		authEnv.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		authEnv.put(Context.PROVIDER_URL, "ldap://"+enderecoConfigurado+":"+portaConfigurada);
		//Acesso BB
		//authEnv.put(Context.PROVIDER_URL, "ldap://10.60.251.34:3268");
		authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
		authEnv.put(Context.SECURITY_PRINCIPAL, usuario + "@"+dominioConfigurado);
		//Acesso BB
		//authEnv.put(Context.SECURITY_PRINCIPAL, usuario + "@intrabb.bb.com.br");
		authEnv.put(Context.SECURITY_CREDENTIALS, senha);

		try {
			@SuppressWarnings("unused")
			DirContext authContext = new InitialDirContext(authEnv);
			System.out.println("Autenticado!");
			return true;

		} catch (AuthenticationException authEx) {
			//authEx.getCause().printStackTrace();
			authEx.printStackTrace();
			return false;
		} catch (NamingException namEx) {
			System.out.println("Problemas na conexão! ");
			namEx.printStackTrace();
			//namEx.getCause().printStackTrace();
			return false;
			
		}

	}

}
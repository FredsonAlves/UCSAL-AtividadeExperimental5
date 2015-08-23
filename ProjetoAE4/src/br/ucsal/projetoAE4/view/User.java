/**
 * 
 */
package br.ucsal.projetoAE4.view;

/**
 * @author fredson
 *
 */
public class User {
	
	//construtor privado - Não permite que a classe seja instanciada...
	private User() {};
	
	/**
	 * @return O nome do usuário logado
	 */
	public static String getUserLogado() {
		return System.getProperty("user.name");
	}


	/**
	 * @return O diretório home do usuário atual
	 */
	public static String getHomeDir() {
		return System.getProperty("user.home");
	}	
}

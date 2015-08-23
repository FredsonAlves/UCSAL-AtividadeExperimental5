/**
 * 
 */
package br.ucsal.projetoAE4.view;

/**
 * @author fredson
 *
 */
public class User {
	
	//construtor privado - N�o permite que a classe seja instanciada...
	private User() {};
	
	/**
	 * @return O nome do usu�rio logado
	 */
	public static String getUserLogado() {
		return System.getProperty("user.name");
	}


	/**
	 * @return O diret�rio home do usu�rio atual
	 */
	public static String getHomeDir() {
		return System.getProperty("user.home");
	}	
}

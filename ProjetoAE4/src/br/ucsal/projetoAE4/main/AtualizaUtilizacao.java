/**
 * 
 */
package br.ucsal.projetoAE4.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.ucsal.projetoAE4.dao.ComputadorDao;
import br.ucsal.projetoAE4.dao.UtilizacaoDao;
import br.ucsal.projetoAE4.modelo.Computador;
import br.ucsal.projetoAE4.modelo.Utilizacao;
import br.ucsal.projetoAE4.net.Rede;
import br.ucsal.projetoAE4.view.User;

/**
 * @author fredson
 * 
 */
public class AtualizaUtilizacao implements Runnable {

	private Utilizacao utiliz = new Utilizacao();
	String nomeUsuario = null;
	private Inicial instanciaInicial;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */

	public AtualizaUtilizacao(String userComputador, Inicial instancia) {
		//Abre o chat passando o nome do usu�rio...
		if (!userComputador.equals("") && !userComputador.equals("\r\n")) {
			this.nomeUsuario = userComputador;
		} else {
			this.nomeUsuario = User.getUserLogado();
		}
		this.instanciaInicial = instancia;
	}

	@Override
	// public void run() throws RuntimeException {
	public void run() throws RuntimeException {
		UtilizacaoDao utilizDao = new UtilizacaoDao();

		ComputadorDao computerDao = new ComputadorDao();
		Computador comp = computerDao.findByName(System.getenv("COMPUTERNAME"));

		if (comp != null) {
			Rede rede = new Rede(comp.getLaboratorio().getFaixaRedeIPV4());
			this.utiliz.setComputador(comp);

			/*
			 * aproveitando as informa��es do computador para baixar todas as
			 * utiliza��es existentes...
			 */
			ArrayList<Utilizacao> listaObtida = (ArrayList<Utilizacao>) utilizDao
					.getByComputador(utiliz);

			if (listaObtida.size() >= 1) {
				for (Utilizacao utilizacao : listaObtida) {
					Calendar dataAtual = Calendar.getInstance();
					dataAtual.setTime(new Date());
					utilizacao.setDataHoraLogoff(dataAtual);
					utilizDao.altera(utilizacao);
				}

			} // Fim da baixa de todos os computadores com utiliza��o existente

			this.utiliz.setMatriculaUtilizador(User.getUserLogado());
			Calendar dataAtual = Calendar.getInstance();
			this.utiliz.setDataHoraLogon(dataAtual);
			// gravando na base de Dados
			// fica em loop e a cada 1 minuto, verifica se esse
			// computador/usu�rio est� cadastrado, caso n�o esteja, cadastra...
			//colocado pois o usu�rio ao inv�s de dar logoff, poderia alternar no windows e quando o sistema retorna para o mesmo, o seu registro de uso j� vai estar baixado

			while (true) {
				ArrayList<Utilizacao> listaObtida2 = (ArrayList<Utilizacao>) utilizDao
						.getByComputador(utiliz);
				if (listaObtida2.size() == 0) {
					/*
					 * Continuando a atualizar dados da utiliza��o atual, para
					 * grava��o!!!
					 */
//					this.utiliz.setMatriculaUtilizador(User.getUserLogado());
					this.utiliz.setEnderecoIPV4(rede.getIp());
					this.utiliz.setNomeUtilizador(this.nomeUsuario);
					utilizDao.adiciona(utiliz);
					// obt�m o �ltimo id inserido em utiliza��o...
					this.utiliz.setCodUtilizacao(utilizDao.UltimoID());
					this.instanciaInicial.setUtilizAtual(utiliz);
				}
				try {
					// loopa a cada 1 minuto...
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

}


package br.ucsal.projetoAE4.main;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.ucsal.projetoAE4.dao.ComputadorDao;
import br.ucsal.projetoAE4.dao.UtilizacaoDao;
import br.ucsal.projetoAE4.modelo.Computador;
import br.ucsal.projetoAE4.modelo.Utilizacao;
import br.ucsal.projetoAE4.uteis.Uteis;
import br.ucsal.projetoAE4.view.FormChat;
import br.ucsal.projetoAE4.view.User;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class Inicial {
	
	static UtilizacaoDao utilizDao = new UtilizacaoDao();
	static Utilizacao utiliz = new Utilizacao();
	static Computador comp; 
	protected String UsuarioRetornado;
	static ArrayList<Utilizacao> listaObtida;
	private Utilizacao utilizAtual;
	public Inicial() throws AWTException {

		ComputadorDao computerDao = new ComputadorDao();
		comp = computerDao.findByName(System.getenv("COMPUTERNAME"));

		utiliz.setMatriculaUtilizador(User.getUserLogado());
		
		final TrayIcon trayIcon;
		// mudando o LookAndFell
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		} catch (UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// obtém o nome de usuário através de script vbscript localizado na
		// pasta ModuloClienteAE4
		// Há um pequeno retardo
		this.UsuarioRetornado = Uteis.obterNomeCompletoUsuario();

		// try {
		// inicia Thread para executar os comandos de atualização...
		AtualizaUtilizacao run = new AtualizaUtilizacao(UsuarioRetornado, this);
		Thread th = new Thread(run, "ModuloAtualizacaoUtilizacao");
		th.start();
		// } catch (RuntimeException e) {
		// Uteis.janelaErro("Erro no acesso, provavelmente seu computador não foi devidamente cadastrado",
		// "Erro na atualização dos dados do computador");
		// e.printStackTrace();
		// System.exit(0);
		// }

		// System.out.println(UsuarioRetornado);

		// lc.execute({"cscript", "//H:",
		// "c:\\ModuloClienteAE4\\buscadados_usuario.vbs"},
		// ,"c:\\ModuloClienteAE4");

		// executa ação no momento da saída...
		ShutdownHook shutdownHook = new ShutdownHook();
		Runtime.getRuntime().addShutdownHook(shutdownHook);

		PopupMenu popup = new PopupMenu();
		MenuItem defaultItem = new MenuItem("Sair");
		MenuItem abrirProg = new MenuItem("Acessar Módulo Administrativo");
		MenuItem chat = new MenuItem("Iniciar sessão de Chat");

		// Image image = Toolkit.getDefaultToolkit().getImage("tray.png");

		// URL imgURL = ClassLoader.getSystemResource("tray.png");

		// Image img = ImageIO.read(getClass().getResource("/tray.png"));

		// Image image = Toolkit.getDefaultToolkit().getImage(imgURL);

		Image image = Toolkit.getDefaultToolkit().getImage("tray.png");

		// File sourceimage = new File("tray.png");
		// Image image = ImageIO.read(sourceimage);

		// Image image = ImageIO.read(img);

		trayIcon = new TrayIcon(image,
				"Sistema de Controle de Uso de Laboratório", popup);

		if (SystemTray.isSupported()) {

			SystemTray tray = SystemTray.getSystemTray();

			MouseListener mouseListener = new MouseListener() {

				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						trayIcon.displayMessage("Módulo Administrativo",
								"Abrindo módulo administrativo...",
								TrayIcon.MessageType.INFO);
						new Mdi("Sistema de Controle de Uso do Laboratório");
					} else {
						System.out
								.println("Tray Icon - Mouse clicado - 1 clique!");
					}

				}

				public void mouseEntered(MouseEvent e) {
					System.out.println("Tray Icon - Mouse entered!");
				}

				public void mouseExited(MouseEvent e) {
					System.out.println("Tray Icon - Mouse exited!");
				}

				public void mousePressed(MouseEvent e) {
					System.out.println("Tray Icon - Mouse pressionado!");
				}

				public void mouseReleased(MouseEvent e) {
					System.out.println("Tray Icon - Mouse released!");
				}

			};

			ActionListener exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Saindo...");
					if (JOptionPane.showConfirmDialog(null, "Deseja sair") == JOptionPane.OK_OPTION) {
						// String shutdownCmd = "shutdown -l";
						/*
						 * try {
						 * 
						 * @SuppressWarnings("unused") Process child =
						 * Runtime.getRuntime().exec(shutdownCmd); } catch (F
						 * e1) { // TODO Auto-generated catch block
						 * e1.printStackTrace(); }
						 */
						System.exit(0);
					}
					// addWindowListener(new WindowAdapter() {
					// public void windowClosing(WindowEvent evt) {

					// salvarDados(); }
					// }
					// });

				}
			};

			ActionListener AbrirListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// JOptionPane.showMessageDialog(null, "Abrir Programa");
					new Mdi("Sistema de Controle de Uso do Laboratório");
				}
			};

			//Ação para a abrir o sistema de Chat...
			ActionListener ChatListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String nomeCompletoUsuario;
					if (!UsuarioRetornado.equals("") && !UsuarioRetornado.equals("\r\n")) {
						int posicao = UsuarioRetornado.trim().indexOf(" ");
						nomeCompletoUsuario = UsuarioRetornado.trim().substring(0, posicao) + " ("+User.getUserLogado()+")";
					} else {
						nomeCompletoUsuario = User.getUserLogado();
					}
					//Abre o chat passando o nome do usuário...
					new FormChat(nomeCompletoUsuario);
				}
			};

			abrirProg.addActionListener(AbrirListener);
			chat.addActionListener(ChatListener);

			defaultItem.addActionListener(exitListener);
			popup.add(abrirProg);
			popup.add(chat);
			popup.add(defaultItem);

			/*
			 * ActionListener actionListener = new ActionListener() { public
			 * void actionPerformed(ActionEvent e) {
			 * trayIcon.displayMessage("Action Event",
			 * "An Action Event Has Been Peformed!", TrayIcon.MessageType.INFO);
			 * } };
			 */

			trayIcon.setImageAutoSize(true);
			// trayIcon.addActionListener(actionListener);
			trayIcon.addMouseListener(mouseListener);

			// Depending on which Mustang build you have, you may need to
			// uncomment
			// out the following code to check for an AWTException when you add
			// an image to the system tray.

			// try {
			tray.add(trayIcon);
			trayIcon.displayMessage(
					"Sistema de Controle de Uso de Laboratório",
					"Sistema carregado, utilize o ícone na bandeja para utilizá-lo...",
					TrayIcon.MessageType.INFO);

			// } catch (AWTException e) {
			// System.err.println("TrayIcon could not be added.");
			// }

		} else {
			System.err.println("System tray não é suportado por este sistema operacional.");
		}
	}

	class ShutdownHook extends Thread {
		public void run() {
			System.out
					.println("Shutting down sistema - Icone da bandeja fechado, atualizando Base de Dados");
			//registraTerminoUtilizacao();
			Calendar dataAtual = Calendar.getInstance();
			dataAtual.setTime(new Date());
			utilizAtual.setDataHoraLogoff(dataAtual);
			utilizDao.altera(utilizAtual);

		}
	}

	public class EvSairJanela extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			super.windowClosing(e);
			System.out.println("Fechando o programa");

		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Saiu do programa");
			super.windowClosed(e);
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 * @throws AWTException
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws AWTException {
		InputStream imgStream = ClassLoader
				.getSystemResourceAsStream("tray.png");

		Inicial main = new Inicial();
	}

	public void registraTerminoUtilizacao() {
		
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.setTime(new Date());
		this.utilizAtual.setDataHoraLogoff(dataAtual);
		utilizDao.altera(this.utilizAtual);
		
		/*
		int contagem=0;
		if (comp != null) {
			utiliz.setComputador(comp);
		}

		
		
		
		
		listaObtida = (ArrayList<Utilizacao>) utilizDao
				.getByComputador(utiliz);
		
		if (listaObtida.size() >= 1) {
			for (Utilizacao utilizacao : listaObtida) {
				contagem++;
				Calendar dataAtual = Calendar.getInstance();
				dataAtual.setTime(new Date());
				utilizacao.setDataHoraLogoff(dataAtual);
				utilizDao.altera(utilizacao);
			}

		}
		// fecha conexão
		utilizDao.closeConnection();
		
		
		File arquivo;
        arquivo = new File("c:\\users\\f3530309\\desktop\\arquivo.txt");  
        FileOutputStream fos;
		try {
			fos = new FileOutputStream(arquivo);
	        String texto = "Quantidade de registros localizados:"+contagem;  
	        fos.write(texto.getBytes());  
	        fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		} */

			
	}

	public InputStream getArquivo(String end) {
		InputStream is = getClass().getResourceAsStream(end);

		return is;
	}

	public ImageIcon getImageIcon(String end) {
		InputStream inputStream = getArquivo(end);
		byte[] dados;
		try {
			dados = new byte[inputStream.available()];
			inputStream.read(dados);
			return new ImageIcon(dados);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public Utilizacao getUtilizAtual() {
		return utilizAtual;
	}

	public void setUtilizAtual(Utilizacao utilizAtual) {
		this.utilizAtual = utilizAtual;
	}

}

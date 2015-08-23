package br.ucsal.projetoAE4.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import br.ucsal.projetoAE4.view.FormBuscaCampus;
import br.ucsal.projetoAE4.view.FormBuscaComputador;
import br.ucsal.projetoAE4.view.FormBuscaLaboratorio;
import br.ucsal.projetoAE4.view.FormSenha;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class Mdi extends JFrame {
	JDesktopPane formMdi = new JDesktopPane();

	FormBuscaCampus frmBuscaCampus = null;
	FormBuscaLaboratorio frmBuscaLaboratorio = null;
	FormBuscaComputador frmBuscaComputador = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Mdi(String titulo) {
		super(titulo);
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		} catch (UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Image image = Toolkit.getDefaultToolkit().getImage("tray2.png");
		this.setIconImage(image);

		/**** Ajuste do tamanho da tela do formulário MDI - Vai iniciar maximizada ***/
		// Define tamanho da janela principal
		int bordas = 50;
		Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
		// setBounds(bordas, bordas, tela.width - bordas * 2, tela.height -
		// bordas *2);
		setBounds(0, 0, tela.width, tela.height - bordas * 2);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		/*** Fim do ajuste da tela **/

		// define cor de background para a janela MDI
		formMdi.setBackground(Color.WHITE);
		// formMdi.setBackground(getBackground());

		// cria a barra de menus
		JMenuBar barraMenu = new JMenuBar();
		// cria o menu Cadastro
		JMenu menuCadastro = new JMenu("Cadastro");
		JMenu menuInfo = new JMenu("Informações");
		// Cria os itens de Menu
		JMenuItem itemMenuCadCampus = new JMenuItem("Cadastrar Campus");
		JMenuItem itemMenuCadLaboratorio = new JMenuItem(
				"Cadastrar Laboratório");
		JMenuItem itemMenuCadComputador = new JMenuItem("Cadastrar Computador");

		// resposta ao clique no botão cadastrar Campus
		itemMenuCadCampus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (frmBuscaCampus == null) {
					frmBuscaCampus = new FormBuscaCampus(
							"Cadastramento de Campus", false, true, false,
							false);
					// Define posição do formulario
					frmBuscaCampus.setLocation(
							(formMdi.getWidth() / 2 - frmBuscaCampus.getWidth() / 2),
							(formMdi.getHeight() / 2 - frmBuscaCampus
									.getHeight() / 2));
					formMdi.add(frmBuscaCampus);
					frmBuscaCampus.setVisible(true);
				}
				frmBuscaCampus.setVisible(true);
				formMdi.moveToFront(frmBuscaCampus);

				frmBuscaCampus.requestFocus();
				try {
					frmBuscaCampus.setSelected(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// fim da resposta ao clique no botão cadastrar computador

				frmBuscaCampus
						.addInternalFrameListener(new InternalFrameAdapter() {

							@Override
							public void internalFrameClosing(
									InternalFrameEvent e) {
								// TODO Auto-generated method stub
								frmBuscaCampus = null;
								System.out
										.println("Frame de Busca de Campus Fechando...");
							}

							@Override
							public void internalFrameClosed(InternalFrameEvent e) {
								// TODO Auto-generated method stub
								frmBuscaCampus = null;
								System.out
										.println("Frame de Busca de Campus Fechado...");
							}

						});
			}
		});
		// cadastramento de campus fim...

		// ação em resposta ao clique na opção de cadastramento de laboratório
		itemMenuCadLaboratorio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (frmBuscaLaboratorio == null) {
					frmBuscaLaboratorio = new FormBuscaLaboratorio(
							"Cadastramento de Laboratório", false, true, false,
							false);
					// Define posição do formulario
					frmBuscaLaboratorio.setLocation(
							(formMdi.getWidth() / 2 - frmBuscaLaboratorio
									.getWidth() / 2),
							(formMdi.getHeight() / 2 - frmBuscaLaboratorio
									.getHeight() / 2));
					formMdi.add(frmBuscaLaboratorio);
					frmBuscaLaboratorio.setVisible(true);
				}
				frmBuscaLaboratorio.setVisible(true);
				formMdi.moveToFront(frmBuscaLaboratorio);

				frmBuscaLaboratorio.requestFocus();
				try {
					frmBuscaLaboratorio.setSelected(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				frmBuscaLaboratorio
						.addInternalFrameListener(new InternalFrameAdapter() {

							@Override
							public void internalFrameClosing(
									InternalFrameEvent e) {
								// TODO Auto-generated method stub
								frmBuscaLaboratorio = null;
								System.out
										.println("Frame de Busca de Laboratório Fechando...");
							}

							@Override
							public void internalFrameClosed(InternalFrameEvent e) {
								// TODO Auto-generated method stub
								frmBuscaLaboratorio = null;
								System.out
										.println("Frame de Busca de Laboratório Fechado...");
							}

						});
			}
		});
		// FIM ação em resposta ao clique na opção de cadastramento de
		// laboratório

		// adiciona o menu à barra de menu
		barraMenu.add(menuCadastro);
		barraMenu.add(menuInfo);
		// adiciona a barra de menu ao frame
		this.setJMenuBar(barraMenu);

		// cadastramento de computador início...
		itemMenuCadComputador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (frmBuscaComputador == null) {
					frmBuscaComputador = new FormBuscaComputador(
							"Cadastramento de Computador", false, true, false,
							false);
					// Define posição do formulario
					frmBuscaComputador.setLocation(
							(formMdi.getWidth() / 2 - frmBuscaComputador
									.getWidth() / 2),
							(formMdi.getHeight() / 2 - frmBuscaComputador
									.getHeight() / 2));
					formMdi.add(frmBuscaComputador);
					frmBuscaComputador.setVisible(true);
				}
				frmBuscaComputador.setVisible(true);
				formMdi.moveToFront(frmBuscaComputador);

				frmBuscaComputador.requestFocus();
				try {
					frmBuscaComputador.setSelected(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				frmBuscaComputador
						.addInternalFrameListener(new InternalFrameAdapter() {

							@Override
							public void internalFrameClosing(
									InternalFrameEvent e) {
								// TODO Auto-generated method stub
								frmBuscaComputador = null;
								System.out
										.println("Frame de Busca de Computador Fechando...");
							}

							@Override
							public void internalFrameClosed(InternalFrameEvent e) {
								// TODO Auto-generated method stub
								frmBuscaComputador = null;
								System.out
										.println("Frame de Busca de Computador Fechado...");
							}

						});
			}

		});

		menuCadastro.add(itemMenuCadLaboratorio);
		menuCadastro.add(itemMenuCadComputador);
		menuCadastro.add(itemMenuCadCampus);

		add(formMdi);

		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// this.setSize(200, 200);
		this.setVisible(true);
		abreFormSenha();
	}

	public static void main(String[] args) {
		// JFrame.setDefaultLookAndFeelDecorated(true);
		try {
			// UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel(new WindowsLookAndFeel());
		} catch (Exception e) {
			System.out.println("Erro no carregamento do tema");
		}

		new Mdi("Sistema de Controle de Uso do Laboratório");

	}

	private void abreFormSenha() {
		JDialog frame2 = new JDialog(Mdi.this, true);
		frame2.setTitle("Digite usuário e senha");

		// Mdi src = (Mdi) ().getParent().getParent().getParent().getParent();

		FormSenha frmSenha = new FormSenha();
		// frmSenha.windowPai = formMdi;

		frmSenha.setWindowPai(this);
		frame2.add(frmSenha);
		// Define posição do formulario
		frame2.setLocation((formMdi.getWidth() / 2 - frmSenha.getWidth() / 2),
				(formMdi.getHeight() / 2 - frmSenha.getHeight() / 2));

		frame2.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				Mdi.this.dispose();
			}

		});
		frame2.getRootPane().setDefaultButton(frmSenha.getBotOk());
		frame2.pack();
		// formMdi.add(frame2);
		frame2.setVisible(true);

	}

}
